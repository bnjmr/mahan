package ir.aspacrm.my.app.mahanet;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Base64;
import android.util.Base64OutputStream;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import ir.aspacrm.my.app.mahanet.classes.DialogClass;
import ir.aspacrm.my.app.mahanet.classes.WebService;
import ir.aspacrm.my.app.mahanet.component.CustomEditText;
import ir.aspacrm.my.app.mahanet.component.PersianTextViewNormal;
import ir.aspacrm.my.app.mahanet.enums.EventOnSuccessUploadDocument;

import static ir.aspacrm.my.app.mahanet.G.context;

public class ActivityUploadDocuments extends AppCompatActivity implements View.OnClickListener {

    final private int PICK_IMAGE = 1;
    final private int CAPTURE_IMAGE = 2;
    private String imgPath;
    String imageAddresses = "";


    @Bind(R.id.layBtnUpload)
    CardView layBtnUpload;
    @Bind(R.id.edtDes)
    CustomEditText edtDes;

    @Bind(R.id.txtChooseFile)
    PersianTextViewNormal txtChooseFile;

    @Bind(R.id.txtFileName)
    PersianTextViewNormal txtFileName;

    @Bind(R.id.prgBar)
    ProgressBar prgBar;

    @Bind(R.id.layBtnBack)
    LinearLayout layBtnBack;

    @Bind(R.id.layMain)
    LinearLayout layMain;

    String des;
    String extention;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_documents);
        ButterKnife.bind(this);
        txtChooseFile.setOnClickListener(this);
        layBtnBack.setOnClickListener(this);
        layBtnUpload.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
        G.context = this;
        G.currentActivity = this;
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_CANCELED) {
            if (requestCode == PICK_IMAGE) {
                imageAddresses = (getPath(context, data.getData()));

            } else if (requestCode == CAPTURE_IMAGE) {
                imageAddresses = (getImagePath());
            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }
            if (imageAddresses != null && !imageAddresses.equals("")) {
                String[] ss = imageAddresses.split("\\.");
                extention = ss[ss.length - 1];
                txtFileName.setText(imageAddresses);
            }

        }
    }

    private String getEncoded64(String imageAddresses) {
        InputStream inputStream = null;//You can get an inputStream using any IO API
        try {
            inputStream = new FileInputStream(imageAddresses);
            byte[] buffer = new byte[8192];
            int bytesRead;
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            Base64OutputStream output64 = new Base64OutputStream(output, Base64.DEFAULT);
            try {
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    output64.write(buffer, 0, bytesRead);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            output64.close();
            return output.toString();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";

    }

    public String getImagePath() {
        return imgPath;
    }

    /**
     * Get a file path from a Uri. This will get the the path for Storage Access
     * Framework Documents, as well as the _data field for the MediaStore and
     * other file-based ContentProviders.
     *
     * @param context The context.
     * @param uri     The Uri to query.
     * @author paulburke
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }


            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtChooseFile:
                Intent intent = new Intent();
                intent.setType("image/*");
//                intent.setType("application/pdf");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, ""), PICK_IMAGE);
                break;
            case R.id.layBtnUpload:
                if (checkConditions()) {
                    File file = new File(imageAddresses);
                    WebService.sendUploadDocumentRequest(ActivityUploadDocuments.this, file, des, extention, prgBar);

                    txtChooseFile.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                    layBtnUpload.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                    prgBar.setVisibility(View.VISIBLE);
                }

                break;
            case R.id.layBtnBack:
                finish();
                break;


        }
    }

    private boolean checkConditions() {
        if (imageAddresses.equals("")) {
            DialogClass.showMessageDialog("خطا", "لطفا فایل مورد نظر را انتخاب کنید");
            return false;
        }
        des = edtDes.getText().toString();
//        if (des.equals("")) {
//            DialogClass.showMessageDialog("خطا", "لطفا توضیحات را کامل کنید");
//            return false;
//        }
        return true;
    }


    public void onEventMainThread(EventOnSuccessUploadDocument document) {
        DialogClass.showMessageDialog("بارگذاری مدارک", document.getMessage());
        imageAddresses = "";
        txtFileName.setText("");
        edtDes.setText("");
        txtChooseFile.setOnClickListener(this);
        layBtnBack.setOnClickListener(this);
        layBtnUpload.setOnClickListener(this);
        prgBar.setVisibility(View.GONE);
    }
}
