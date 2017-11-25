package ir.aspacrm.my.app.mahanet;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import ir.aspacrm.my.app.mahanet.adapter.AdapterBank;
import ir.aspacrm.my.app.mahanet.classes.DialogClass;
import ir.aspacrm.my.app.mahanet.classes.Logger;
import ir.aspacrm.my.app.mahanet.classes.WebService;
import ir.aspacrm.my.app.mahanet.enums.EnumGridType;
import ir.aspacrm.my.app.mahanet.events.EventOnBankSelected;
import ir.aspacrm.my.app.mahanet.events.EventOnErrorInConnectingToServer;
import ir.aspacrm.my.app.mahanet.events.EventOnGetBankListResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnGetErrorGetBankList;
import ir.aspacrm.my.app.mahanet.gson.LoadBanksResponse;

import static ir.aspacrm.my.app.mahanet.G.context;

public class ActivityShowBankList extends AppCompatActivity {
    @Bind(R.id.lstBankList)
    RecyclerView lstBankList;
    @Bind(R.id.layLoading)
    LinearLayout layLoading;
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.txtShowMessage)
    TextView txtShowMessage;
    @Bind(R.id.layBtnBack)
    LinearLayout layBtnBack;
    //    @Bind(R.id.layBtnClose)
//    LinearLayout layBtnClose;
    @Bind(R.id.txtPageTitle)
    TextView txtPageTitle;

    AdapterBank adapterBank;
    LoadBanksResponse[] banks = new LoadBanksResponse[0];

    int typeOfGrid = EnumGridType.GRID;
    int columnsOfGrid = 0;

    long factorCode;
    int money;
    boolean openWebView = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_bank_list);
        ButterKnife.bind(this);
        openWebView = false;

        EventBus.getDefault().register(this);
        initView();

        Intent intent = getIntent();
        factorCode = intent.getLongExtra("FACTOR_CODE", 0);
        money = intent.getIntExtra("MONEY", 0);

    }

    private void initView() {
        Logger.d("FragmentShowBankList : onActivityCreated()");

        layLoading.setVisibility(View.GONE);

        adapterBank = new AdapterBank(banks);
        /** baraye anke moshakhas konim grid
         * ma be surate list namayesh dade sahvad ya grid chand setune*/
        typeOfGrid = G.localMemory.getInt("GRID_TYPE", 1);
        if (typeOfGrid == EnumGridType.GRID) {
            columnsOfGrid = getResources().getInteger(R.integer.grid_columns);
        } else {
            columnsOfGrid = getResources().getInteger(R.integer.grid_list_columns);
        }
        lstBankList.setLayoutManager(new GridLayoutManager(context, columnsOfGrid));
        lstBankList.setHasFixedSize(true);
        lstBankList.setAdapter(adapterBank);

        /** be dalil inke dar fragment
         *  swipeRefreshLayout.setRefreshing(true);
         *  dar ebteda kar nemikonad ke listener
         *  swipeRefreshLayout.setOnRefreshListener ra seda bezanad be surate dasti aan ra seda mizanim.*/
//        swipeRefreshLayout.post(new Runnable() {
//            @Override
//            public void run() {
//                swipeRefreshLayout.setRefreshing(true);
//            }
//        });
        WebService.sendGetBankList();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                Logger.d("FragmentShowBankList : swipeRefreshLayout onRefresh()");
                WebService.sendGetBankList();
            }
        });

//        layBtnClose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onBackPressed();
//            }
//        });

        layBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

    public void onEventMainThread(EventOnGetBankListResponse event) {
        Logger.d("FragmentShowBankList : EventOnGetBankListResponse is raised");
        LoadBanksResponse[] banks = event.getBankList();
        if (banks.length == 0) {
            DialogClass dlgShowErrorMessage = new DialogClass();
            dlgShowErrorMessage.showMessageDialog("پیغام", "برای شما بانکی ثبت نشده است، لطفا با پشتبانی تماس بگیرید.");
        } else {
            /** dar surati ke banki baraye moshtarak sabt shode bashad.*/
            adapterBank.updateList(banks);
        }
        layLoading.setVisibility(View.GONE);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public void onEventMainThread(EventOnGetErrorGetBankList event) {
        Logger.d("FragmentShowBankList : EventOnGetErrorGetBankList is raised");
        layLoading.setVisibility(View.GONE);
        DialogClass dlgShowErrorMessage = new DialogClass();
        dlgShowErrorMessage.showMessageDialog("پیغام", "خطا در دریافت لیست بانک ها، لطفا مجددا تلاش کنید.");
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    public void onEventMainThread(EventOnBankSelected event) {
        Logger.d("FragmentShowBankList : EventOnBankSelected is raised");


        /**
         *
         *
         .add("rt", "CallBankPageForPayment")
         .add("UserID", "" + G.currentUser.userId)
         .add("ExKey", "" + G.currentUser.exKey)
         .add("FactorCode", "" + factorCode)
         .add("BankCode", "" + bankCode)
         .add("Money", "" + money)

         */


//        getActivity().getSupportFragmentManager().beginTransaction()
//                .add(R.id.layFragment, FragmentBrowser.newInstance(G.currentUser.ispUrl + G.WS_PAGE
//                                + "?rt=CallBankPageForPayment&UserID=" + G.currentUser.userId
//                                + "&ExKey=" + G.currentUser.exKey
//                                + "&FactorCode=" + factorCode + "&BankCode=" + event.getCode()
//                                + "&Money=" + money
//                        , event.getBankName()))
//                .addToBackStack("FragmentBrowser")
//                .commit();

//        Intent intent = new Intent(this, ActivityBrowser.class);
//        intent.putExtra("PAY_URL",
//                G.currentUser.ispUrl + G.WS_PAGE
//                        + "?rt=CallBankPageForPayment&UserID=" + G.currentUser.userId
//                        + "&ExKey=" + G.currentUser.exKey
//                        + "&FactorCode=" + factorCode + "&BankCode=" + event.getCode()
//                        + "&Money=" + money
//        );
//        intent.putExtra("BankName", event.getBankName());
//        startActivity(intent);

//        String url = G.currentUser.ispUrl + G.WS_PAGE
//                + "?rt=CallBankPageForPayment&UserID=" + G.currentUser.Token
//                + "&ExKey=" + G.currentUser.Token
//                + "&FactorCode=" + factorCode + "&BankCode=" + event.getCode()
//                + "&Money=" + money;

        String url;
        String si = G.currentUser.ispUrl;
        String[] s = si.split(":");
        String site = s[0] + ":" + s[1];
        if (factorCode != 0) {
            url = site + "/Charge/MobileBill?Token=" +
                    G.currentUser.Token + "&FactorCode=" +
                    factorCode + "&BankCode=" + event.getCode() +
                    "&CanPayFromCredit=False";
        } else {
            url = site +
                    "/Payment/MobilePayment?Token=" + G.currentUser.Token +
                    "&BankCode=" + event.getCode() +
                    "&Amount=" + money;
        }
        Logger.d("payment Url : " + url);

//        Intent intentweb = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//        intentweb.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intentweb.setPackage("com.android.chrome");
//        try {
//            context.startActivity(intentweb);
//            openWebView = true;
//        } catch (ActivityNotFoundException ex) {
//            // Chrome browser presumably not installed so allow User to choose instead
//            intentweb.setPackage(null);
//            context.startActivity(intentweb);
//        }


        Intent intent = new Intent(this, ActivityBrowser.class);
        intent.putExtra("PAY_URL", url);
        intent.putExtra("BankName", event.getBankName());
        startActivity(intent);
    }

    public void onEventMainThread(EventOnErrorInConnectingToServer errorInConnectingToServer){
        layLoading.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        if (openWebView) {
            Intent intent = new Intent(ActivityShowBankList.this, ActivityShowCurrentService.class);
            startActivity(intent);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);

    }
}
