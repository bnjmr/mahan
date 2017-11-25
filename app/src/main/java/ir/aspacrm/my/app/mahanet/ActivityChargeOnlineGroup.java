package ir.aspacrm.my.app.mahanet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
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
import ir.aspacrm.my.app.mahanet.adapter.AdapterChargeOnlineGroup;
import ir.aspacrm.my.app.mahanet.classes.Logger;
import ir.aspacrm.my.app.mahanet.classes.WebService;
import ir.aspacrm.my.app.mahanet.enums.EnumGridType;
import ir.aspacrm.my.app.mahanet.events.EventOnClickedChargeOnlineGroup;
import ir.aspacrm.my.app.mahanet.events.EventOnErrorInConnectingToServer;
import ir.aspacrm.my.app.mahanet.events.EventOnGetChargeOnlineForCountCategoryResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnGetChargeOnlineForLoadGroups;
import ir.aspacrm.my.app.mahanet.gson.ChargeOnlineGroup;

public class ActivityChargeOnlineGroup extends AppCompatActivity {

    int isClub;
    int whichMenuItem;
    int columnsOfGrid = 2;
    int typeOfGrid = 1;

    @Bind(R.id.lstGroup)
    RecyclerView lstGroup;
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.txtShowMessage)
    TextView txtShowMessage;
    @Bind(R.id.imgToolbar)
    ImageView imgToolbar;

    @Bind(R.id.layBtnBack)
    LinearLayout layBtnBack;

    @Bind(R.id.layLoading) LinearLayout layLoading;

    //    @Bind(R.id.layBtnBack) LinearLayout layBtnBack;
    @Bind(R.id.txtPageTitle)
    TextView txtPageTitle;


    AdapterChargeOnlineGroup adapterChargeOnlineGroup;
    LinearLayoutManager linearLayoutManager;
    List<ChargeOnlineGroup> groups = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charge_online_group);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        isClub=intent.getIntExtra("IS_CLUB",0);
        whichMenuItem=intent.getIntExtra("WHICH_MENU_ITEM",0);


        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        G.currentActivity=this;
        G.context=this;
        EventBus.getDefault().register(this);

    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    private void initView() {

        layBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Logger.d("FragmentChargeOnlineGroup : onActivityCreated()");

//        txtPageTitle.setText(U.getMenuItemName(whichMenuItem));
        txtPageTitle.setText("گروه ها");
//        U.getMenuItemIcon(imgToolbar,whichMenuItem);
        imgToolbar.setImageResource(R.drawable.ic_factor_detail_toolbar);

        adapterChargeOnlineGroup = new AdapterChargeOnlineGroup(groups, isClub, whichMenuItem);
        /** baraye anke moshakhas konim grid
         * ma be surate list namayesh dade sahvad ya grid chand setune*/
        typeOfGrid = G.localMemory.getInt("GRID_TYPE", 1);
        if (typeOfGrid == EnumGridType.GRID) {
            columnsOfGrid = getResources().getInteger(R.integer.grid_columns);
        } else {
            columnsOfGrid = getResources().getInteger(R.integer.grid_list_columns);
        }
        linearLayoutManager = new GridLayoutManager(G.context, columnsOfGrid);
        lstGroup.setLayoutManager(linearLayoutManager);
        lstGroup.setHasFixedSize(true);
        lstGroup.setAdapter(adapterChargeOnlineGroup);

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

        WebService.sendChargeOnlineForLoadGroupsRequest(isClub);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                Logger.d("ActivityShowFactors : swipeRefreshLayout onRefresh()");
                WebService.sendChargeOnlineForLoadGroupsRequest(isClub);
            }
        });

//        layBtnBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getActivity().onBackPressed();
//            }
//        });
//
    }

    public void onEventMainThread(EventOnGetChargeOnlineForLoadGroups event) {
        Logger.d("FragmentChargeOnlineGroup : EventOnGetChargeOnlineForLoadGroups is raised");
        layLoading.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(false);
        groups = event.getChargeOnlineGroups();
        if (groups.size() == 0) {
            txtShowMessage.setVisibility(View.VISIBLE);
            txtShowMessage.setText("موردی یافت نشد.");
        } else {
            adapterChargeOnlineGroup.updateList(groups);
        }
    }

    public void onEventMainThread(EventOnClickedChargeOnlineGroup event) {
        Logger.d("ActivityChargeOnline : EventOnClickedChargeOnlineGroup is raised");
        WebService.sendChargeOnlineForCountCategoryRequest(event.getIsClub(),event.getGroupCode(),event.getWhichMenuItem());
        layLoading.setVisibility(View.GONE);

    }

    public void onEventMainThread(EventOnGetChargeOnlineForCountCategoryResponse event) {
        layLoading.setVisibility(View.GONE);

        if (G.categorySize > 1) {
            Intent intentTRAFFIC = new Intent(this, ActivityChargeOnlineCategory.class);
            intentTRAFFIC.putExtra("IS_CLUB", event.isClub());
            intentTRAFFIC.putExtra("WHICH_MENU_ITEM", event.getWhichMenuItem());
            intentTRAFFIC.putExtra("GROUP_CODE", event.getGroupCode());
            startActivity(intentTRAFFIC);
        } else {
            Intent i = new Intent(G.context, ActivityChargeOnlineGroupPackage.class);
            i.putExtra("WHICH_MENU_ITEM", event.getWhichMenuItem());
            i.putExtra("IS_CLUB", event.isClub());
            i.putExtra("GROUP_CODE", event.getGroupCode());
            i.putExtra("CATEGORY_CODE", -1);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            G.context.startActivity(i);
        }



    }

    public void onEventMainThread(EventOnErrorInConnectingToServer errorInConnectingToServer){
        layLoading.setVisibility(View.GONE);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        ButterKnife.unbind(this);

    }
}
