package ir.aspacrm.my.app.mahanet;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.activeandroid.query.Select;
import com.afollestad.materialdialogs.MaterialDialog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import ir.aspacrm.my.app.mahanet.adapter.AdapterMainItems;
import ir.aspacrm.my.app.mahanet.classes.DialogClass;
import ir.aspacrm.my.app.mahanet.classes.Logger;
import ir.aspacrm.my.app.mahanet.classes.U;
import ir.aspacrm.my.app.mahanet.classes.WebService;
import ir.aspacrm.my.app.mahanet.component.ColorTool;
import ir.aspacrm.my.app.mahanet.enums.EnumMainItemsTag;
import ir.aspacrm.my.app.mahanet.events.EventOnChoseImageClicked;
import ir.aspacrm.my.app.mahanet.events.EventOnClickedLogoutButton;
import ir.aspacrm.my.app.mahanet.events.EventOnGetNewsResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnGetStartFactorResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnStartUploadingAvatar;
import ir.aspacrm.my.app.mahanet.events.EventOnStopUploadingAvatar;
import ir.aspacrm.my.app.mahanet.model.License;
import ir.aspacrm.my.app.mahanet.model.MainItems;

import static ir.aspacrm.my.app.mahanet.G.context;

public class ActivityMain0 extends AppCompatActivity implements View.OnTouchListener, View.OnClickListener {

    //    @Bind(R.id.mask_mainbg)
//    ImageView mask;
//    @Bind(R.id.bg_main)
//    ImageView bgMain;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.imgDrawerToggle)
    ImageView imgDrawerToggle;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle mActionBarDrawerToggle;


    final private int PICK_IMAGE = 1;
    final private int CAPTURE_IMAGE = 2;
    private String imgPath;
    String imageAddresses = "";

    @Bind(R.id.layPayment)
    FrameLayout layPayment;
    @Bind(R.id.layShowConnections)
    FrameLayout layShowConnections;
    @Bind(R.id.layProfile)
    FrameLayout layProfile;
    @Bind(R.id.laySpeed)
    FrameLayout laySpeed;
    @Bind(R.id.layChargeOnline)
    FrameLayout layChargeOnline;
    @Bind(R.id.layFeshfeshe)
    FrameLayout layFeshfeshe;
    @Bind(R.id.layScores)
    FrameLayout layScores;
    @Bind(R.id.laySupport)
    FrameLayout laySupport;
    @Bind(R.id.layShowFactors)
    FrameLayout layShowFactors;
    @Bind(R.id.layMessages)
    FrameLayout layMessages;
    @Bind(R.id.layGraph)
    FrameLayout layGraph;
    @Bind(R.id.layGame)
    FrameLayout layGame;
    @Bind(R.id.layBtnBack)
    LinearLayout layBtnBack;

    @Bind(R.id.RcyItems)
    RecyclerView RcyItems;

    @Bind(R.id.layLoading)
    LinearLayout layLoading;

    AdapterMainItems adapterMainItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        initToolbar();


//        if (bgMain != null)
//            bgMain.setOnTouchListener(this);

        /** yani mostaghim vared safheye asli shodeim. */
        // bareye check kardane inke bashgah darad ya na.
        if (G.currentLicense == null)
            G.currentLicense = new Select().from(License.class).executeSingle();

//        if (G.currentLicense != null && !G.currentLicense.Club) {
//            bgMain.setImageResource(R.drawable.bg_main_no_feshfeshe_club);
//            mask.setImageResource(R.drawable.mask_mainbg_no_feshfeshe_club);
//        } else if (G.currentLicense != null && G.currentLicense.Club) {
//            bgMain.setImageResource(R.drawable.bg_main0);
//            mask.setImageResource(R.drawable.mask_mainbg);
//        }

        WebService.sendGetUserAccountInfoRequest();
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Logger.d("ActivityMain : onResume()");
        G.currentActivity = this;
        G.context = this;
        adapterMainItems.notifyDataSetChanged();
        G.startGpsService();
        WebService.sendGetCountNotify();
    }

    private void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
//         toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_down));
        drawerLayout = (DrawerLayout) findViewById(R.id.main_navgdrawer);
        setupDrawerToggleInActionBar();
    }

    private void setupDrawerToggleInActionBar() {
        imgDrawerToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                    drawerLayout.closeDrawer(Gravity.RIGHT);
                } else {
                    drawerLayout.openDrawer(Gravity.RIGHT);
                }
            }
        });

        // setup the action bar properties that give us a hamburger menu
        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }

        //  the toggle allows for the simplest of open/close handling
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout,
                R.string.open,
                R.string.close);
        // drawerListener must be set before syncState is called
        drawerLayout.setDrawerListener(mActionBarDrawerToggle);
        mActionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        mActionBarDrawerToggle.syncState();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                    drawerLayout.closeDrawer(Gravity.RIGHT);
                } else {
                    drawerLayout.openDrawer(Gravity.RIGHT);
                }
            }
        });

    }


    public void onEventMainThread(EventOnClickedLogoutButton event) {
        Logger.d("ActivityMain : EventOnClickedLogoutButton is raised");
        G.currentUser.isLogin = false;
        G.currentUser.save();
        startActivity(new Intent(context, ActivityLogin.class));
        finish();
    }




    public void onEventMainThread(final EventOnGetStartFactorResponse event) {
        Logger.d("ActivityMain : EventOnGetStartFactorResponse is raised");
        WebService.sendGetUserAccountInfoRequest();
    }


    public void onEventMainThread(EventOnGetNewsResponse event) {
        Logger.d("ActivityMain : EventOnGetNewsResponse is raised");
    }





    /////////////////

    public void onEventMainThread(EventOnChoseImageClicked event) {
        Logger.d("ActivityMain : EventOnChoseImageClicked is raised");

        if (G.hasImage) {
            final MaterialDialog dialogChoiceImage = new MaterialDialog.Builder(G.currentActivity)
                    .customView(R.layout.dialog_choice_image_type, false)
                    .build();
            View v = dialogChoiceImage.getCustomView();
            dialogChoiceImage.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            LinearLayout layChoiceGallery = (LinearLayout) v.findViewById(R.id.layChoiceGallery);
            LinearLayout layChoiceCamera = (LinearLayout) v.findViewById(R.id.layChoiceCamera);
            layChoiceCamera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    WebService.removeAvatarImage();
//                    final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                    intent.putExtra(MediaStore.EXTRA_OUTPUT, setImageUri());
//                    startActivityForResult(intent, CAPTURE_IMAGE);
                    dialogChoiceImage.dismiss();
                }
            });
            layChoiceGallery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, ""), PICK_IMAGE);
                    dialogChoiceImage.dismiss();
                }
            });
            dialogChoiceImage.show();
        } else {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, ""), PICK_IMAGE);
        }
    }

    public void onEventMainThread(EventOnStartUploadingAvatar avatar) {
        layLoading.setVisibility(View.VISIBLE);
    }

    public void onEventMainThread(EventOnStopUploadingAvatar avatar) {
        layLoading.setVisibility(View.GONE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_CANCELED) {
            if (requestCode == PICK_IMAGE) {
                imageAddresses = (getPath(G.context, data.getData()));

            } else if (requestCode == CAPTURE_IMAGE) {
                imageAddresses = (getImagePath());
            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }
            if (imageAddresses != null && !imageAddresses.equals("")) {
                String encodedImage = getEncoded64ImageStringFromBitmap(imageAddresses);
                WebService.uploadAvatarImage(encodedImage);
            }

        }
    }

    public Uri setImageUri() {
        // Store image in dcim
        File file = new File(Environment.getExternalStorageDirectory() + "/DCIM/", "image" + new Date().getTime() + ".png");
        Uri imgUri = Uri.fromFile(file);
        this.imgPath = file.getAbsolutePath();
        return imgUri;
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

    public static String getEncoded64ImageStringFromBitmap(String path) {
        Bitmap bm = null;
        ByteArrayOutputStream baos = null;
        byte[] b = null;
        String encodeString = null;

        double finalWeith;
        double finalHeight;
        try {
            bm = BitmapFactory.decodeFile(path);
            baos = new ByteArrayOutputStream();


            int width = bm.getWidth();
            int height = bm.getHeight();

            if (width > height) {
                double mainScale = width / height;
                finalWeith = 150;
                finalHeight = 150 / mainScale;
            } else {
                double mainScale = height / width;
                finalHeight = 150;
                finalWeith = 150 / mainScale;
            }

            int ScaleSize = 150 / height;

            bm = Bitmap.createScaledBitmap(
                    bm, (int) (finalWeith), (int) (finalHeight), false);


            bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            b = baos.toByteArray();
            encodeString = Base64.encodeToString(b, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Logger.d("getEncoded64ImageStringFromBitmap is " + encodeString);
        return encodeString;
    }

    ////////////////

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(Gravity.RIGHT)) {
            drawerLayout.closeDrawer(Gravity.RIGHT);
        } else {
//            super.onBackPressed();
            startActivity(new Intent(ActivityMain0.this, ActivityShowCurrentService.class));
            finish();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Logger.d("ActivityMain : onDestroy()");
        EventBus.getDefault().unregister(this);
        G.stopGpsService();
    }

    public void initView() {

        if (!G.currentLicense.Graph) {
            layGraph.setVisibility(View.INVISIBLE);
        }
        layPayment.setOnClickListener(this);
        layMessages.setOnClickListener(this);
        layScores.setOnClickListener(this);
        layShowConnections.setOnClickListener(this);
        layProfile.setOnClickListener(this);
        layGraph.setOnClickListener(this);
        laySupport.setOnClickListener(this);
        layShowFactors.setOnClickListener(this);
        layFeshfeshe.setOnClickListener(this);
        laySpeed.setOnClickListener(this);
        layChargeOnline.setOnClickListener(this);
        layGame.setOnClickListener(this);
        layBtnBack.setOnClickListener(this);


        adapterMainItems = new AdapterMainItems(makeItemsList(), this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 3);

//        RcyItems.setHasFixedSize(true);
        RcyItems.setLayoutManager(layoutManager);
        RcyItems.setAdapter(adapterMainItems);

    }

    private List<MainItems> makeItemsList() {

        List<MainItems> mainItemsList = new ArrayList<>();


        if (G.currentLicense.Chargeonline) {
            MainItems chargeOnline = new MainItems();
            chargeOnline.setTag(EnumMainItemsTag.chargeOnline);
            chargeOnline.setName(getString(R.string.onlineSharj));
            chargeOnline.setImage(R.drawable.ic_charge_online);
            mainItemsList.add(chargeOnline);
        }
        ///////////////////////

        MainItems factores = new MainItems();
        factores.setTag(EnumMainItemsTag.factors);
        factores.setName(getString(R.string.factors));
        factores.setImage(R.drawable.ic_factor_detail_toolbar);
        mainItemsList.add(factores);
        /////////////////////////

        MainItems payments = new MainItems();
        payments.setTag(EnumMainItemsTag.payments);
        payments.setName(getString(R.string.payReport));
        payments.setImage(R.drawable.ic_payments);
        mainItemsList.add(payments);
        ////////////////////////////
        if (G.currentLicense.Ticket) {
            MainItems support = new MainItems();
            support.setTag(EnumMainItemsTag.support);
            support.setName(getString(R.string.support));
            support.setImage(R.mipmap.support72);
            mainItemsList.add(support);
        }

        ///////////////////////////

        if (G.currentLicense.connection) {
            MainItems connections = new MainItems();
            connections.setTag(EnumMainItemsTag.connections);
            connections.setName(getString(R.string.connectionReport));
            connections.setImage(R.drawable.ic_connections);
            mainItemsList.add(connections);
        }
        //////////////////////////

//        MainItems news = new MainItems();
//        news.setTag(EnumMainItemsTag.news);
//        news.setName("اخبار");
//        news.setImage(R.drawable.ic_news);
//        mainItemsList.add(news);
        ///////////////////////////


        MainItems profile = new MainItems();
        profile.setTag(EnumMainItemsTag.profile);
        profile.setName(getString(R.string.profile));
        profile.setImage(R.drawable.ic_profile);
        mainItemsList.add(profile);
        ////////////////////////

        if (G.currentLicense.ChangePass) {
            MainItems changePass = new MainItems();
            changePass.setTag(EnumMainItemsTag.changePassword);
            changePass.setName(getString(R.string.change_password));
            changePass.setImage(R.drawable.ic_changepassword);
            mainItemsList.add(changePass);
        }
        ///////////////////////

        MainItems messages = new MainItems();
        messages.setTag(EnumMainItemsTag.Messages);
        messages.setName(getString(R.string.messages));
        messages.setImage(R.drawable.ic_flag);
        mainItemsList.add(messages);
        ///////////////////////

        if (G.currentLicense.Club) {
            MainItems scores = new MainItems();
            scores.setTag(EnumMainItemsTag.scores);
            scores.setName(getString(R.string.pointReports));
            scores.setImage(R.drawable.ic_scores);
            mainItemsList.add(scores);
        }
        //////////////////////////

        if (G.currentLicense.Feshfeshe) {
            MainItems feshfeshe = new MainItems();
            feshfeshe.setTag(EnumMainItemsTag.feshfeshe);
            feshfeshe.setName(getString(R.string.feshfeshe));
            feshfeshe.setImage(R.drawable.ic_feshfeshe);
            mainItemsList.add(feshfeshe);
        }
        /////////////////////////

        if (G.currentLicense.trafficSplit) {
            MainItems trafficSplite = new MainItems();
            trafficSplite.setName(getString(R.string.traffic_splite));
            trafficSplite.setImage(R.drawable.ic_traffic_split);
            trafficSplite.setTag(EnumMainItemsTag.trafficeSplite);
            mainItemsList.add(trafficSplite);
        }

        /////////////////////////

        if (G.currentLicense.Club) {
            MainItems game = new MainItems();
            game.setName(getString(R.string.games));
            game.setImage(R.drawable.game72);
            game.setTag(EnumMainItemsTag.game);
            mainItemsList.add(game);
        }


        return mainItemsList;
    }


    public boolean onTouch(View v, MotionEvent ev) {

        final int action = ev.getAction();

        final int evX = (int) ev.getX();
        final int evY = (int) ev.getY();

        // If we cannot find the imageView, return.
        ImageView bgMain = (ImageView) v.findViewById(R.id.bg_main);
        if (bgMain == null) return false;

        // When the action is Down, see if we should show the "pressed" image for the default image.
        // We do this when the default image is showing. That condition is detectable by looking at the
        // tag of the view. If it is null or contains the resource number of the default image, display the pressed image.

        // Now that we know the current resource being displayed we can handle the DOWN and UP events.

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                Logger.d("action down");
                break;

            case MotionEvent.ACTION_UP:
                Logger.d("action up");
                ColorTool ct = new ColorTool();
                int tolerance = 25;
                int touchColor = getHotspotColor(R.id.mask_mainbg, evX, evY);

                if (ct.closeMatch(Color.BLUE, touchColor, tolerance))
                    startActivity(new Intent(context, ActivityPayments.class));
                else if (ct.closeMatch(Color.YELLOW, touchColor, tolerance))
                    startActivity(new Intent(context, ActivityShowNotify.class));
                else if (ct.closeMatch(Color.GREEN, touchColor, tolerance))
                    startActivity(new Intent(context, ActivityShowClubScores.class));
                else if (ct.closeMatch(Color.parseColor("#" + Integer.toHexString(context.getResources().getColor(R.color.purple))), touchColor, tolerance))
                    startActivity(new Intent(context, ActivityShowUserInfo.class));
                else if (ct.closeMatch(Color.DKGRAY, touchColor, tolerance))
                    startActivity(new Intent(context, ActivityShowConnections.class));
                else if (ct.closeMatch(Color.CYAN, touchColor, tolerance))
                    startActivity(new Intent(context, ActivityShowGraph.class));
                else if (ct.closeMatch(Color.GRAY, touchColor, tolerance))
                    new DialogClass().showMessageDialog(getString(R.string.alert), getString(R.string.item_available_in_future));
                else if (ct.closeMatch(Color.parseColor("#" + Integer.toHexString(context.getResources().getColor(R.color.orange))), touchColor, tolerance))
                    startActivity(new Intent(context, ActivityShowTickets.class));
                else if (ct.closeMatch(Color.WHITE, touchColor, tolerance))
                    startActivity(new Intent(context, ActivityShowFactors.class));
                else if (ct.closeMatch(Color.MAGENTA, touchColor, tolerance))
                    new DialogClass().showMessageDialog(getString(R.string.alert), getString(R.string.item_available_in_future));
//                    startActivity(new Intent(context, ActivityShowNews.class));
                else if (ct.closeMatch(Color.RED, touchColor, tolerance))
                    startActivity(new Intent(context, ActivityShowFeshfeshe.class));
                else if (ct.closeMatch(Color.parseColor("#" + Integer.toHexString(context.getResources().getColor(R.color.brown))), touchColor, tolerance)) {
                    startActivity(new Intent(context, ActivityShowCurrentService.class));
//                    overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                    finish();
                } else if (ct.closeMatch(Color.LTGRAY, touchColor, tolerance)) {
                    if (G.currentLicense != null) {
                        if (G.currentLicense.Chargeonline) {
                            startActivity(new Intent(context, ActivityChargeOnline.class));
                        } else {
                            U.toast("امکان شارژ آنلاین برای شما فعال     نمیباشد.");
                        }
                    }
                }

                break;
        } // end switch
        return true;
    }

    public int getHotspotColor(int hotspotId, int x, int y) {
        ImageView img = (ImageView) findViewById(hotspotId);
        if (img == null) {
            Logger.d("Hot spot image not found");
            return 0;
        } else {
            img.setDrawingCacheEnabled(true);
            Bitmap hotspots = Bitmap.createBitmap(img.getDrawingCache());
            if (hotspots == null) {
                Logger.d("Hot spot bitmap was not created");
                return 0;
            } else {
                img.setDrawingCacheEnabled(false);
                return hotspots.getPixel(x, y);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layPayment:
                startActivity(new Intent(context, ActivityPayments.class));
//                finish();
                break;
            case R.id.layMessages:
                startActivity(new Intent(context, ActivityShowNotify.class));
//                finish();
                break;
            case R.id.layScores:
                startActivity(new Intent(context, ActivityShowClubScores.class));
//                finish();
                break;
            case R.id.layShowConnections:
                startActivity(new Intent(context, ActivityShowConnections.class));
//                finish();
                break;
            case R.id.layProfile:
                startActivity(new Intent(context, ActivityShowUserInfo.class));
//                finish();
                break;
            case R.id.layGraph:
//                new DialogClass().showMessageDialog(context.getString(R.string.alert), context.getString(R.string.item_available_in_future));
                startActivity(new Intent(context, ActivityShowGraph.class));
                finish();
                break;
            case R.id.laySupport:
                startActivity(new Intent(context, ActivityShowTickets.class));
//                finish();
                break;
            case R.id.layShowFactors:
                startActivity(new Intent(context, ActivityShowFactors.class));
//                finish();
                break;
            case R.id.layFeshfeshe:
                startActivity(new Intent(context, ActivityShowFeshfeshe.class));
//                finish();
                break;
            case R.id.laySpeed:
                new DialogClass().showMessageDialog(getString(R.string.alert), getString(R.string.item_available_in_future));
                break;
            case R.id.layChargeOnline:
                if (G.currentLicense != null) {
                    if (G.currentLicense.Chargeonline) {
                        startActivity(new Intent(context, ActivityChargeOnline.class));
//                        finish();
                    } else {
                        U.toast("امکان شارژ آنلاین برای شما فعال     نمیباشد.");
                    }
                }
                break;
            case R.id.layGame:
                Intent intent = new Intent(G.context, ActivityPoints.class);
                G.context.startActivity(intent);
//                new DialogClass().showMessageDialog(getString(R.string.alert), getString(R.string.item_available_in_future));
                break;
            case R.id.layBtnBack:
                startActivity(new Intent(context, ActivityShowCurrentService.class));
                break;
        }
    }
}
