package ir.aspacrm.my.app.mahanet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import ir.aspacrm.my.app.mahanet.classes.DialogClass;
import ir.aspacrm.my.app.mahanet.classes.Logger;
import ir.aspacrm.my.app.mahanet.classes.WebService;
import ir.aspacrm.my.app.mahanet.enums.EnumChargeOnlineMenuItem;
import ir.aspacrm.my.app.mahanet.enums.EnumResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnChargeOnlineMenuItemClicked;
import ir.aspacrm.my.app.mahanet.events.EventOnErrorInConnectingToServer;
import ir.aspacrm.my.app.mahanet.events.EventOnGetChargeOnlineForCountCategoryResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnGetChargeOnlineMainItem;
import ir.aspacrm.my.app.mahanet.events.EventOnGetCheckChargeOnlineClub;
import ir.aspacrm.my.app.mahanet.events.EventOnGetErrorChargeOnline;
import ir.aspacrm.my.app.mahanet.events.EventOnNoAccessServerResponse;
import ir.aspacrm.my.app.mahanet.gson.ChargeOnlineMainItemResponse;

public class ActivityChargeOnline extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.layBtnTamdidService)
    LinearLayout layBtnTamdidService;
    @Bind(R.id.layTamdidService)
    LinearLayout layTamdidService;
    @Bind(R.id.layBtnTaghirService)
    LinearLayout layBtnTaghirService;
    @Bind(R.id.layTaghirService)
    LinearLayout layTaghirService;
    @Bind(R.id.layBtnTraffic)
    LinearLayout layBtnTraffic;
    @Bind(R.id.layTraffic)
    LinearLayout layTraffic;
    @Bind(R.id.layBtnIP)
    LinearLayout layBtnIP;
    @Bind(R.id.layIP)
    LinearLayout layIP;
    @Bind(R.id.layBtnFeshfeshe)
    LinearLayout layBtnFeshfeshe;
    @Bind(R.id.layFeshfeshe)
    LinearLayout layFeshfeshe;
    @Bind(R.id.layShowMenuItem)
    LinearLayout layShowMenuItem;
    @Bind(R.id.layLoading)
    LinearLayout layLoading;
    @Bind(R.id.txtShowMessage)
    TextView txtShowMessage;
    @Bind(R.id.layBtnClose)
    LinearLayout layBtnClose;
    @Bind(R.id.layBtnTaghsimTrafic)
    LinearLayout layBtnTaghsimTrafic;
    @Bind(R.id.layBtnBack)
    LinearLayout layBtnBack;


    DialogClass dlgMessage;

    private long GROUP_CODE;
    private int IS_CLUB;
    private int WHICH_MENU_ITEM;
    private int CATEGORY_CODE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charge_online0);
        ButterKnife.bind(this);


        layShowMenuItem.setVisibility(View.GONE);
        layBtnTamdidService.setOnClickListener(this);
        layBtnTaghirService.setOnClickListener(this);
        layBtnBack.setOnClickListener(this);
        layBtnTraffic.setOnClickListener(this);
        layBtnIP.setOnClickListener(this);
        layBtnFeshfeshe.setOnClickListener(this);
        layBtnTaghsimTrafic.setOnClickListener(this);
        WebService.sendGetChargeOnlineMainItemsRequest();
        layBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        G.context = this;
        G.currentActivity = this;
        EventBus.getDefault().register(this);

    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    public void onEventMainThread(EventOnGetChargeOnlineMainItem event) {
        Logger.d("FragmentChargeOnlineMainMenu : EventOnGetChargeOnlineMainItem is raised.");
        layLoading.setVisibility(View.GONE);
        ChargeOnlineMainItemResponse response = event.getChargeOnlineMainItemResponse();

        if (response.Result == EnumResponse.OK) {
            if (!response.Feshfeshe)
                layFeshfeshe.setVisibility(View.GONE);
            if (!response.Taghir)
                layTaghirService.setVisibility(View.GONE);
            if (!response.Tamdid)
                layTamdidService.setVisibility(View.GONE);
            if (!response.Traffic)
                layTraffic.setVisibility(View.GONE);
            if (!response.Ip)
                layIP.setVisibility(View.GONE);
            if (!response.TrafficSplit)
                layBtnTaghsimTrafic.setVisibility(View.GONE);
            layShowMenuItem.setVisibility(View.VISIBLE);
        } else {
            txtShowMessage.setVisibility(View.VISIBLE);
            txtShowMessage.setText(response.Message);
        }

        View visibleItems[] = new View[6];
        visibleItems[0] = layTamdidService;
        visibleItems[1] = layTaghirService;
        visibleItems[2] = layTraffic;
        visibleItems[3] = layFeshfeshe;
        visibleItems[4] = layIP;
        visibleItems[5] = layBtnTaghsimTrafic;


        List<Integer> visibleIds = new ArrayList<>();

        for (int i = 0; i < visibleItems.length; i++) {
            if (visibleItems[i].getVisibility() == View.VISIBLE)
                visibleIds.add(visibleItems[i].getId());
        }
        for (int i = 0; i < visibleIds.size(); i++) {
            if (i % 2 == 0) {
                findViewById(visibleIds.get(i)).setBackgroundColor(getResources().getColor(R.color.back_items));
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layBtnTamdidService:
                Intent intent = new Intent(G.context, ActivityChargeOnlineTamdid.class);
                intent.putExtra("IS_CLUB", IS_CLUB);
                startActivity(intent);
                finish();

//                EventBus.getDefault().post(new EventOnChargeOnlineMenuItemClicked(EnumChargeOnlineMenuItem.TAMDID_SERVICE));
                break;
            case R.id.layBtnTaghirService:
                EventBus.getDefault().post(new EventOnChargeOnlineMenuItemClicked(EnumChargeOnlineMenuItem.TAGHIR_SERVICE));
                break;
            case R.id.layBtnTraffic:
                EventBus.getDefault().post(new EventOnChargeOnlineMenuItemClicked(EnumChargeOnlineMenuItem.TRAFFIC));
                break;
            case R.id.layBtnIP:
                EventBus.getDefault().post(new EventOnChargeOnlineMenuItemClicked(EnumChargeOnlineMenuItem.IP));
                break;
            case R.id.layBtnFeshfeshe:
                EventBus.getDefault().post(new EventOnChargeOnlineMenuItemClicked(EnumChargeOnlineMenuItem.FESHFESHE));
                break;
            case R.id.layBtnTaghsimTrafic:
                EventBus.getDefault().post(new EventOnChargeOnlineMenuItemClicked(EnumChargeOnlineMenuItem.TAGHSIM_TERAFIC));
                break;
            case R.id.layBtnBack:
                onBackPressed();
                break;
        }
    }


    public void onEventMainThread(EventOnGetErrorChargeOnline event) {
        Logger.d("FragmentChargeOnlineMainMenu : EventOnGetErrorChargeOnline is raised");
        layLoading.setVisibility(View.GONE);
        txtShowMessage.setVisibility(View.VISIBLE);
        txtShowMessage.setText("خطا در دریافت اطلاعات از سرور لطفا دوباره تلاش کنید.");
    }

    public void onEventMainThread(EventOnNoAccessServerResponse event) {
        Logger.d("FragmentChargeOnlineMainMenu : EventOnNoAccessServerResponse is raised");
        /** use this event to hide loading */
        layLoading.setVisibility(View.GONE);
    }

    public void onEventMainThread(EventOnChargeOnlineMenuItemClicked event) {
        Logger.d("ActivityChargeOnline : EventOnChargeOnlineMenuItemClicked is raised.");
        int whichMenuItem = event.getWhichMenuItem();
        layLoading.setVisibility(View.VISIBLE);
        WebService.sendCheckChargeOnlineClubRequest(whichMenuItem);
    }

    public void onEventMainThread(EventOnGetChargeOnlineForCountCategoryResponse event) {
        // agar tedade categoryha barabar ba 1 bood activitycategory nmayesh dade nemishavad   va group_code -1 ersal mishavad
        G.categorySize = event.getChargeOnlineCategoryList().size();
        WHICH_MENU_ITEM = event.getWhichMenuItem();
        GROUP_CODE = event.getGroupCode();
        CATEGORY_CODE = -1;
        IS_CLUB = event.isClub();
//        int whichMenuItem = event.getWhichMenuItem();
//        Long groupCode = event.getGroupCode();
        int isClub = event.isClub();
        if (isClub == 1) {
            Intent i = new Intent(this, ActivityChargeOnlineAddiBashgah.class);
            i.putExtra("IS_CLUB", isClub);
            i.putExtra("WHICH_MENU_ITEM", event.getWhichMenuItem());
            i.putExtra("GROUP_CODE", G.currentAccount.GrpId);
            startActivity(i);
        } else {
            switch (WHICH_MENU_ITEM) {
                case EnumChargeOnlineMenuItem.TAMDID_SERVICE:
                    WebService.sendChargeOnlineForTamdidRequest(IS_CLUB);
                    break;

                case EnumChargeOnlineMenuItem.TAGHIR_SERVICE:
                    Intent intent = new Intent(this, ActivityChargeOnlineGroup.class);
                    intent.putExtra("IS_CLUB", 0);
                    intent.putExtra("WHICH_MENU_ITEM", EnumChargeOnlineMenuItem.TAGHIR_SERVICE);
                    startActivity(intent);
                    break;
                case EnumChargeOnlineMenuItem.TRAFFIC:
                    if (G.categorySize > 1) {
                        Intent intentTRAFFIC = new Intent(this, ActivityChargeOnlineCategory.class);
                        intentTRAFFIC.putExtra("IS_CLUB", isClub);
                        intentTRAFFIC.putExtra("WHICH_MENU_ITEM", event.getWhichMenuItem());
                        intentTRAFFIC.putExtra("GROUP_CODE", G.currentAccount.GrpId);
                        startActivity(intentTRAFFIC);
                    } else {
                        Intent i = new Intent(G.context, ActivityChargeOnlineGroupPackage.class);
                        i.putExtra("WHICH_MENU_ITEM", WHICH_MENU_ITEM);
                        i.putExtra("IS_CLUB", isClub);
                        i.putExtra("GROUP_CODE", GROUP_CODE);
                        i.putExtra("CATEGORY_CODE", -1);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        G.context.startActivity(i);
                    }
                    break;
                case EnumChargeOnlineMenuItem.IP:
                    if (G.categorySize > 1) {
                        Intent intentTRAFFIC = new Intent(this, ActivityChargeOnlineCategory.class);
                        intentTRAFFIC.putExtra("IS_CLUB", isClub);
                        intentTRAFFIC.putExtra("WHICH_MENU_ITEM", event.getWhichMenuItem());
                        intentTRAFFIC.putExtra("GROUP_CODE", G.currentAccount.GrpId);
                        startActivity(intentTRAFFIC);
                    } else {
                        Intent i = new Intent(G.context, ActivityChargeOnlineGroupPackage.class);
                        i.putExtra("WHICH_MENU_ITEM", WHICH_MENU_ITEM);
                        i.putExtra("IS_CLUB", isClub);
                        i.putExtra("GROUP_CODE", GROUP_CODE);
                        i.putExtra("CATEGORY_CODE", -1);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        G.context.startActivity(i);
                    }
                    break;
                case EnumChargeOnlineMenuItem.FESHFESHE:
                    if (G.categorySize > 1) {
                        Intent intentTRAFFIC = new Intent(this, ActivityChargeOnlineCategory.class);
                        intentTRAFFIC.putExtra("IS_CLUB", isClub);
                        intentTRAFFIC.putExtra("WHICH_MENU_ITEM", event.getWhichMenuItem());
                        intentTRAFFIC.putExtra("GROUP_CODE", G.currentAccount.GrpId);
                        startActivity(intentTRAFFIC);
                    } else {
                        Intent i = new Intent(G.context, ActivityChargeOnlineGroupPackage.class);
                        i.putExtra("WHICH_MENU_ITEM", WHICH_MENU_ITEM);
                        i.putExtra("IS_CLUB", isClub);
                        i.putExtra("GROUP_CODE", GROUP_CODE);
                        i.putExtra("CATEGORY_CODE", -1);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        G.context.startActivity(i);
                    }
                    break;
                case EnumChargeOnlineMenuItem.TAGHSIM_TERAFIC:
                    if (G.categorySize > 1) {
                        Intent intentTRAFFIC = new Intent(this, ActivityChargeOnlineCategory.class);
                        intentTRAFFIC.putExtra("IS_CLUB", isClub);
                        intentTRAFFIC.putExtra("WHICH_MENU_ITEM", event.getWhichMenuItem());
                        intentTRAFFIC.putExtra("GROUP_CODE", G.currentAccount.GrpId);
                        startActivity(intentTRAFFIC);
                    } else {
                        Intent i = new Intent(G.context, ActivityChargeOnlineGroupPackage.class);
                        i.putExtra("WHICH_MENU_ITEM", WHICH_MENU_ITEM);
                        i.putExtra("IS_CLUB", isClub);
                        i.putExtra("GROUP_CODE", GROUP_CODE);
                        i.putExtra("CATEGORY_CODE", -1);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        G.context.startActivity(i);
                    }
                    break;
            }
        }

    }

    public void onEventMainThread(EventOnGetCheckChargeOnlineClub event) {
        Logger.d("ActivityChargeOnline : EventOnGetCheckChargeOnlineClub is raised.");
        layLoading.setVisibility(View.GONE);
        int status = event.getStatus();


        if (status == EnumResponse.OK) {// avaltedat categoryHa ra az service migirim
            IS_CLUB = 0;
            if (event.getIsClub())
                IS_CLUB = 1;

            WebService.sendChargeOnlineForCountCategoryRequest(IS_CLUB, G.currentAccount.GrpId, event.getWhichMenuItem());
        } else {
            dlgMessage = new DialogClass();
            dlgMessage.showMessageDialog("خطا", "خطا در دریافت اطلاعاتی دریافتی از سمت سرور، لطفا دوباره تلاش کنید.");
        }
    }

    public void onEventMainThread(EventOnErrorInConnectingToServer errorInConnectingToServer){
        layLoading.setVisibility(View.GONE);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }
}
