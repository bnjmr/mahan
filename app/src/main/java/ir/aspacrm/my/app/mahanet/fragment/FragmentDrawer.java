package ir.aspacrm.my.app.mahanet.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.activeandroid.query.Select;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import ir.aspacrm.my.app.mahanet.G;
import ir.aspacrm.my.app.mahanet.R;
import ir.aspacrm.my.app.mahanet.adapter.AdapterDrawerRecycler;
import ir.aspacrm.my.app.mahanet.classes.DialogClass;
import ir.aspacrm.my.app.mahanet.classes.Logger;
import ir.aspacrm.my.app.mahanet.classes.WebService;
import ir.aspacrm.my.app.mahanet.component.PersianTextViewThin;
import ir.aspacrm.my.app.mahanet.enums.EnumDrawerItemsTag;
import ir.aspacrm.my.app.mahanet.enums.EnumResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnChoseImageClicked;
import ir.aspacrm.my.app.mahanet.events.EventOnGetAvatarImageResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnGetClubScoreResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnGetErrorGetUserAccountInfo;
import ir.aspacrm.my.app.mahanet.events.EventOnGetReperesenterURL;
import ir.aspacrm.my.app.mahanet.events.EventOnGetUserAccountInfoResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnGetUserLicenseResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnRemoveAvatarImageResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnUploadImageResponse;
import ir.aspacrm.my.app.mahanet.model.Account;
import ir.aspacrm.my.app.mahanet.model.Info;
import ir.aspacrm.my.app.mahanet.model.License;
import ir.aspacrm.my.app.mahanet.model.ModelDrawerItems;

import static ir.aspacrm.my.app.mahanet.G.context;

/**
 * Created by HaMiD on 1/22/2017.
 */

public class FragmentDrawer extends Fragment {

    private View view;
    private RecyclerView RecyDrawer;
    List<String> title;
    ImageView imgAvatar;
    PersianTextViewThin txtStatus, txtScore, txtName, txtRemainDay2;
    PersianTextViewThin txtRemainDay;
    LinearLayout layTotalClubScore;
    ProgressBar prgLoadingScore;
    private FrameLayout layAvatar;
    private ImageView imgProfileBtn;
    private String txt = "با این لینک تو ماهان نت ثبت نام کن و از اینترنت خوب لذت ببر";
    List<ModelDrawerItems> modelDrawerItemses;
    AdapterDrawerRecycler adapterDrawerRecycler = null;


    final private int PICK_IMAGE = 1;
    final private int CAPTURE_IMAGE = 2;
    private String imgPath;
    String imageAddresses = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_drawer, container, false);
        initView();

//        EventBus.getDefault().register(this);
        WebService.sendGetClubScoreRequest();
        WebService.sendGetUserAccountInfoRequest();
        return view;
    }

    private void initView() {
        txtRemainDay = (PersianTextViewThin) view.findViewById(R.id.txtRemainDay);
        txtRemainDay2 = (PersianTextViewThin) view.findViewById(R.id.txtRemainDay2);
        layAvatar = (FrameLayout) view.findViewById(R.id.layAvatar);
        txtScore = (PersianTextViewThin) view.findViewById(R.id.txtScore);
        prgLoadingScore = (ProgressBar) view.findViewById(R.id.prgLoadingScore);
        imgAvatar = (ImageView) view.findViewById(R.id.imgAvatar);
        imgProfileBtn = (ImageView) view.findViewById(R.id.imgProfileBtn);

        layAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choseImage();
            }
        });

        initRecyclreItems();

        try {
            adapterDrawerRecycler = new AdapterDrawerRecycler(modelDrawerItemses, context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        RecyDrawer.setAdapter(adapterDrawerRecycler);


        if (G.currentUserInfo.Encoded64ImageString != null
                && !G.currentUserInfo.Encoded64ImageString.equals("")
                && !G.currentUserInfo.Encoded64ImageString.equals("null")) {
            showEncodedImage(G.currentUserInfo.Encoded64ImageString);
        } else {
            WebService.getAvatarImage();
        }


    }

    private void initRecyclreItems() {
        try {
            if (G.currentLicense == null)
                G.currentLicense = new Select().from(License.class).executeSingle();

            if (G.currentLicense != null) {
                modelDrawerItemses = new ArrayList<>();
                if (G.currentLicense.Chargeonline)
                    modelDrawerItemses.add(new ModelDrawerItems(getString(R.string.onlineSharj), EnumDrawerItemsTag.onlineSharj, R.drawable.ic_charge_online));

                modelDrawerItemses.add(new ModelDrawerItems(getString(R.string.factors), EnumDrawerItemsTag.factors, R.drawable.ic_factor_detail_toolbar));
                modelDrawerItemses.add(new ModelDrawerItems(getString(R.string.payReport), EnumDrawerItemsTag.payReport, R.drawable.ic_payments));
                if (G.currentLicense.Ticket)
                    modelDrawerItemses.add(new ModelDrawerItems(getString(R.string.support), EnumDrawerItemsTag.support, R.drawable.support72));

                modelDrawerItemses.add(new ModelDrawerItems(getString(R.string.connectionReport), EnumDrawerItemsTag.connectionReport, R.drawable.ic_connections));
                modelDrawerItemses.add(new ModelDrawerItems(getString(R.string.profile), EnumDrawerItemsTag.profile, R.drawable.ic_profile));
                if (G.currentLicense.ChangePass)
                    modelDrawerItemses.add(new ModelDrawerItems(getString(R.string.change_password), EnumDrawerItemsTag.changePassword, R.drawable.ic_changepassword));
                modelDrawerItemses.add(new ModelDrawerItems(getString(R.string.messages), EnumDrawerItemsTag.messages, R.drawable.ic_flag));
                if (G.currentLicense.Club)
                    modelDrawerItemses.add(new ModelDrawerItems(getString(R.string.pointReports), EnumDrawerItemsTag.pointReports, R.drawable.ic_scores));
                if (G.currentLicense.Feshfeshe)
                    modelDrawerItemses.add(new ModelDrawerItems(getString(R.string.feshfeshe), EnumDrawerItemsTag.feshfeshe, R.drawable.ic_feshfeshe));
                if (G.currentLicense.trafficSplit)
                    modelDrawerItemses.add(new ModelDrawerItems(getString(R.string.traffic_splite), EnumDrawerItemsTag.trafficSplit, R.drawable.ic_traffic_split));

                modelDrawerItemses.add(new ModelDrawerItems(getString(R.string.games), EnumDrawerItemsTag.games, R.drawable.game72));
                modelDrawerItemses.add(new ModelDrawerItems(getString(R.string.inviteFriends), EnumDrawerItemsTag.inviteFriends, R.drawable.freenet72));
                modelDrawerItemses.add(new ModelDrawerItems(getString(R.string.news), EnumDrawerItemsTag.news, R.drawable.ic_news));
                modelDrawerItemses.add(new ModelDrawerItems(getString(R.string.exit), EnumDrawerItemsTag.exit, R.drawable.ic_exit));

                RecyDrawer = (RecyclerView) view.findViewById(R.id.RecyDrawer);
                RecyDrawer.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                RecyDrawer.setHasFixedSize(true);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    public void showEncodedImage(String encodeString) {
        if (!encodeString.equals("") && !encodeString.equals("NotFound") && !encodeString.equals("null")) {
            G.hasImage = true;
            imgProfileBtn.setImageResource(R.drawable.ic_edit_image);
        } else {
            imgProfileBtn.setImageResource(R.drawable.ic_plus);
            imgAvatar.setImageResource(R.drawable.user);
        }
        final byte[] decodedBytes = Base64.decode(encodeString, Base64.DEFAULT);
//        Glide.with(G.context).load(decodedBytes).crossFade().fitCenter().into(imgAvatar);

        Glide.with(context).load(decodedBytes).into(new SimpleTarget<GlideDrawable>() {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                imgAvatar.setImageDrawable(resource);
            }
        });

    }

    private void initializeUserAccountView() {


//        // current date
//        PersianCalendar jalali = SystemClock.inLocalView().now(PersianCalendar.axis());
//        String fDate = jalali.toString();// AP-1394-08-04
//
////        // localized format of tomorrow (English and Farsi)
////        ChronoFormatter<PersianCalendar> f =
////                ChronoFormatter.ofStyle(DisplayMode.FULL, Locale.ENGLISH, PersianCalendar.axis());
////        Locale farsi = new Locale("fa");
////        String da = f.with(farsi).format(jalali);
////
////        String[] separated0 = da.split(",");
////        String date = separated0[0];
////        String dayOfWeek = separated0[1];
////
////        String[] separated = date.split(" ");
////        String day = separated[3];
////        String month= separated[2];
////        String year= separated[1];
////
////        String farsiDate = dayOfWeek+","+day +" "+ month+" "+year;
//
//        String[] separated = fDate.split("-");
//        String day = separated[3];
//        String month = separated[2];
//        String year = separated[1];
//        String farsiDate = year + " /" + month + " /" + day;
        String farsiDate = "سامانه اسپا نسخه : " + G.versionName;


//        txtRemainDay1.setText(dayOfWeek);
        txtRemainDay2.setText(farsiDate);
        txtRemainDay2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("http://aspacrm.ir"));
                context.startActivity(i);
            }
        });

//        Calendar cc = Calendar.getInstance();
//        int year=cc.get(Calendar.YEAR);
//        int month=cc.get(Calendar.MONTH);
//        int mDay = cc.get(Calendar.DAY_OF_MONTH);
//        System.out.println("Date", year+":"+month+":"+mDay);

        txtStatus = (PersianTextViewThin) view.findViewById(R.id.txtStatus);
        txtStatus.setText(G.currentUserInfo != null ? G.currentUserInfo.status : "");

        txtName = (PersianTextViewThin) view.findViewById(R.id.txtName);
        txtName.setText(G.currentUserInfo != null ? G.currentUserInfo.fullName : "");

        if (G.currentAccount.RHour == -11111) {
            if (G.currentAccount.Rday == -11111) {
                txtRemainDay.setText("نامحدود");
//                txtRemainDay.setVisibility(View.GONE);
            } else {
                txtRemainDay.setText("شما تا اتمام سرویس اینترنتی خود " + G.currentAccount.Rday + " " + " روز " + "فرصت دارید ");
            }
        } else {
            if (G.currentAccount.RHour == -11111) {
                txtRemainDay.setText("نامحدود");
//                txtRemainDay.setVisibility(View.GONE);
            } else {
                txtRemainDay.setText("شما تا اتمام سرویس اینترنتی خود " + G.currentAccount.RHour + " " + " ساعت " + " فرصت دارید ");
            }
        }
//        if (G.currentAccount.rTraffic == -11111) {
//            txtRemainDay.setText("نامحدود");
////            lblRemainingDays.setVisibility(View.GONE);
//        } else {
//            txtRemainDay.setText(G.currentAccount.rTraffic + " " + "مگابایت" + " ");
//        }
    }

    private void choseImage() {
        EventBus.getDefault().post(new EventOnChoseImageClicked());

    }

    public void onEventMainThread(EventOnGetClubScoreResponse event) {
        Logger.d("FragmentDrawer : EventOnGetClubScoreResponse is raised.");
        int result = event.isResult();
        if (result == EnumResponse.OK) {
            prgLoadingScore.setVisibility(View.GONE);
            txtScore.setVisibility(View.VISIBLE);
//            layTotalClubScore.setVisibility(View.VISIBLE);
            int score = event.getScore();
            if (score == 0)
                txtScore.setText("میزان امتیاز : " + "0");
            else if (score > 0)
                txtScore.setText("میزان امتیاز : " + score + "");
            else
                txtScore.setText("میزان امتیاز : " + score);
        } else {
//            layTotalClubScore.setVisibility(View.GONE);
        }
    }

    public void onEventMainThread(EventOnGetErrorGetUserAccountInfo event) {
        Logger.d("ActivityMain : EventOnGetErrorGetUserAccountInfo is raised");
        G.currentAccount = new Select().from(Account.class).executeSingle();
        G.currentLicense = new Select().from(License.class).executeSingle();
        initializeUserAccountView();
    }

    public void onEventMainThread(EventOnGetUserAccountInfoResponse event) {
        Logger.d("FragmentDrawer : EventOnGetUserAccountInfoResponse is raised");
        initializeUserAccountView();
        /** Send this request to get point for current user if set
         * from managment.*/
    }

    public void onEventMainThread(EventOnUploadImageResponse event) {
        Logger.d("FragmentDrawer : EventOnUploadImageResponse is raised");
        WebService.getAvatarImage();
    }

    public void onEventMainThread(EventOnGetAvatarImageResponse event) {
        showEncodedImage(G.currentUserInfo.Encoded64ImageString);
    }

    public void onEventMainThread(EventOnRemoveAvatarImageResponse event) {
        G.hasImage = false;
        //encoded ra dar db insert mikonim
        Info info = new Select().from(Info.class).executeSingle();

        if (info == null) {
            Info newInfo = new Info();
            newInfo.Encoded64ImageString = "";
            newInfo.save();
            G.currentUserInfo = newInfo;
        } else {
            info.Encoded64ImageString = "";
            info.save();
            G.currentUserInfo = info;
        }
        EventBus.getDefault().post(new EventOnGetAvatarImageResponse());
    }

    public void onEventMainThread(EventOnGetReperesenterURL reperesenterURL) {
        try {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, txt + "\n\n" + reperesenterURL.getRepresenterURL());
            sendIntent.setType("text/plain");
            sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(sendIntent);
        } catch (Exception e) {
            e.printStackTrace();
            new DialogClass().showMessageDialog(context.getString(R.string.error), context.getString(R.string.send_failure));

        }


//        Intent sendIntent = new Intent(Intent.ACTION_SEND);
//        sendIntent.setType("Text/plain");
//        sendIntent.putExtra(Intent.EXTRA_TEXT, txt + "\n\n" + reperesenterURL.getRepresenterURL());
//        try {
//            context.startActivity(Intent.createChooser(sendIntent, context.getString(R.string.choose_sender)).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
//        } catch (android.content.ActivityNotFoundException ex) {
//            new DialogClass().showMessageDialog(context.getString(R.string.error), context.getString(R.string.send_failure));
//        }


    }

    public void onEventMainThread(EventOnGetUserLicenseResponse event) {
        Logger.d("FragmentDrawer : EventOnGetUserLicenseResponse is raised.");
        initRecyclreItems();

    }
}
