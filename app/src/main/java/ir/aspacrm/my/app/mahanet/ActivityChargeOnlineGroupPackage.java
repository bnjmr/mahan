package ir.aspacrm.my.app.mahanet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import ir.aspacrm.my.app.mahanet.adapter.AdapterChargeOnlineGroupPackage;
import ir.aspacrm.my.app.mahanet.classes.DialogClass;
import ir.aspacrm.my.app.mahanet.classes.Logger;
import ir.aspacrm.my.app.mahanet.classes.U;
import ir.aspacrm.my.app.mahanet.classes.WebService;
import ir.aspacrm.my.app.mahanet.enums.EnumResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnClickSlecetChargeOnlinePakage;
import ir.aspacrm.my.app.mahanet.events.EventOnErrorInConnectingToServer;
import ir.aspacrm.my.app.mahanet.events.EventOnGetChargeOnlineForLoadPackages;
import ir.aspacrm.my.app.mahanet.events.EventOnGetChargeOnlineForSelectPackage;
import ir.aspacrm.my.app.mahanet.events.EventOnGetErrorChargeOnlineForLoadPackages;
import ir.aspacrm.my.app.mahanet.gson.ChargeOnlineGroupPackage;

public class ActivityChargeOnlineGroupPackage extends AppCompatActivity {
    @Bind(R.id.lstPackage)
    RecyclerView lstPackage;
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.txtShowMessage)
    TextView txtShowMessage;
    @Bind(R.id.layLoading)
    LinearLayout layLoading;

    @Bind(R.id.layBtnClose)
    LinearLayout layBtnClose;
    @Bind(R.id.txtPageTitle)
    TextView txtPageTitle;
    @Bind(R.id.imgToolbar)
    ImageView imgToolbar;

    @Bind(R.id.layBtnBack)
    LinearLayout layBtnBack;

    AdapterChargeOnlineGroupPackage adapterChargeOnlineGroupPackage;
    LinearLayoutManager linearLayoutManager;
    List<ChargeOnlineGroupPackage> packages = new ArrayList<>();

    int isClub;
    int whichMenuItem;
    long groupCode;
    int categoryCode;
    boolean isOneItem = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charge_online_group_pakage);

        Intent intent = getIntent();
        isClub = intent.getIntExtra("IS_CLUB", 0);
        isOneItem = intent.getBooleanExtra("isOneItem", false);
        whichMenuItem = intent.getIntExtra("WHICH_MENU_ITEM", 0);
        groupCode = intent.getLongExtra("GROUP_CODE", 0);
        categoryCode = intent.getIntExtra("CATEGORY_CODE", 0);
        Logger.d("ActivityChargeOnlineGroupPackage : onCreate()");
        ButterKnife.bind(this);
        initView();


    }

    @Override
    protected void onResume() {
        super.onResume();
        G.context = this;
        G.currentActivity = this;
        EventBus.getDefault().register(this);

    }

    private void initView() {
        layBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        layLoading.setVisibility(View.GONE);

        txtPageTitle.setText(U.getMenuItemName(whichMenuItem));
        U.getMenuItemIcon(imgToolbar, whichMenuItem);


        adapterChargeOnlineGroupPackage = new AdapterChargeOnlineGroupPackage(packages, isClub, groupCode);
        linearLayoutManager = new LinearLayoutManager(G.context);
        lstPackage.setLayoutManager(linearLayoutManager);
        lstPackage.setHasFixedSize(true);
        lstPackage.setAdapter(adapterChargeOnlineGroupPackage);
        /** be dalil inke dar fragment
         *  swipeRefreshLayout.setRefreshing(true);
         *  dar ebteda kar nemikonad ke listener
         *  swipeRefreshLayout.setOnRefreshListener ra seda bezanad be surate dasti aan ra seda mizanim.*/
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
        WebService.sendChargeOnlineForLoadPackagesRequest(isClub, groupCode, whichMenuItem, categoryCode);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                Logger.d("ActivityChargeOnlineGroupPackage : swipeRefreshLayout onRefresh()");
                WebService.sendChargeOnlineForLoadPackagesRequest(isClub, groupCode, whichMenuItem, categoryCode);
            }
        });

        layBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void onEventMainThread(EventOnGetChargeOnlineForLoadPackages event) {
        Logger.d("ActivityChargeOnlineGroupPackage : EventOnGetChargeOnlineForLoadPackages is raised");
        swipeRefreshLayout.setRefreshing(false);
        packages = event.getChargeOnlineGroupPackages();
        if (packages.size() == 0) {
            txtShowMessage.setVisibility(View.VISIBLE);
            txtShowMessage.setText("موردی یافت نشد.");
        } else {
            adapterChargeOnlineGroupPackage.updateList(packages);
        }
    }

    public void onEventMainThread(EventOnGetErrorChargeOnlineForLoadPackages event) {
        Logger.d("ActivityChargeOnlineGroupPackage : EventOnGetErrorChargeOnlineForLoadPackages is raised");
        swipeRefreshLayout.setRefreshing(false);
        packages = new ArrayList<>();
        adapterChargeOnlineGroupPackage.updateList(packages);
        txtShowMessage.setVisibility(View.VISIBLE);
        txtShowMessage.setText("خطا در دریافت اطلاعات از سرور لطفا دوباره تلاش کنید.");
    }

    public void onEventMainThread(EventOnClickSlecetChargeOnlinePakage event) {
        Logger.d("ActivityChargeOnlineGroupPackage : EventOnClickedChargeOnlineGroupPackage0 is raised");
        long packageCode = event.getPackageCode();
        long groupCode = event.getGroupCode();
        layLoading.setVisibility(View.VISIBLE);
        WebService.sendChargeOnlineForSelectPackageRequest(isClub, packageCode, groupCode);
    }

    public void onEventMainThread(EventOnGetChargeOnlineForSelectPackage event) {
        layLoading.setVisibility(View.GONE);
        Logger.d("ActivityChargeOnline : EventOnGetChargeOnlineForSelectPackage is raised");
        int result = event.isResult();
        String message = event.getMessage();
        long factorCode = event.getFactorCode();
        if (result == EnumResponse.OK) {
            if (message.length() == 0) {

                Intent intent = new Intent(this, ActivityShowPackageFactorDetail.class);
                intent.putExtra("FACTOR_CODE", factorCode);
                startActivity(intent);
//                fragmentManager.beginTransaction()
//                        .add(ir.aspacrm.my.app.mahanet.R.id.layFragment, FragmentShowFactorDetail.newInstance(factorCode))
//                        .addToBackStack("FragmentShowFactorDetail")
//                        .commit();
            } else {
                DialogClass dlgShow = new DialogClass();
                dlgShow.showMessageDialog("پیغام", "" + message);
            }
        } else {
            DialogClass dlgShow = new DialogClass();
            dlgShow.showMessageDialog("پیغام", "" + message);
        }
    }

    public void onEventMainThread(EventOnErrorInConnectingToServer errorInConnectingToServer){
        layLoading.setVisibility(View.GONE);
    }
    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);

    }
}
