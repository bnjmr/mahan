package ir.aspacrm.my.app.mahanet;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import ir.aspacrm.my.app.mahanet.adapter.AdapterFactor;
import ir.aspacrm.my.app.mahanet.classes.DialogClass;
import ir.aspacrm.my.app.mahanet.classes.Logger;
import ir.aspacrm.my.app.mahanet.classes.U;
import ir.aspacrm.my.app.mahanet.classes.WebService;
import ir.aspacrm.my.app.mahanet.enums.EnumInternetErrorType;
import ir.aspacrm.my.app.mahanet.enums.EnumYesNoKind;
import ir.aspacrm.my.app.mahanet.events.EventOnClickedFactorMoreDetail;
import ir.aspacrm.my.app.mahanet.events.EventOnClickedStartFactor;
import ir.aspacrm.my.app.mahanet.events.EventOnClickedYesOnYesNoDialog;
import ir.aspacrm.my.app.mahanet.events.EventOnErrorInConnectingToServer;
import ir.aspacrm.my.app.mahanet.events.EventOnGetErrorGetFactor;
import ir.aspacrm.my.app.mahanet.events.EventOnGetErrorGetFactorDetail;
import ir.aspacrm.my.app.mahanet.events.EventOnGetErrorStartFactor;
import ir.aspacrm.my.app.mahanet.events.EventOnGetFactorDetailsResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnGetFactorResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnGetStartFactorResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnNoAccessServerResponse;
import ir.aspacrm.my.app.mahanet.gson.FactorDetailResponse;
import ir.aspacrm.my.app.mahanet.model.Factor;
import ir.aspacrm.my.app.mahanet.model.FactorDetail;
import ir.aspacrm.my.app.mahanet.model.ModelYesNoDialog;

public class ActivityShowFactors extends AppCompatActivity{


    @Bind(R.id.lstFactor) RecyclerView lstFactor;
    @Bind(R.id.layBtnClose) LinearLayout layBtnClose;
    @Bind(R.id.layBtnBack) LinearLayout layBtnBack;
    @Bind(R.id.layLoading) LinearLayout layLoading;
    @Bind(R.id.txtShowMessage) TextView txtShowMessage;
    @Bind(R.id.swipeRefreshLayout) SwipeRefreshLayout swipeRefreshLayout;


    AdapterFactor adapterFactor;
    LinearLayoutManager linearLayoutManager;
    List<Factor> factors = new ArrayList<>();
    private String current = "";
    DialogClass dlgWaiting;

    /** baraye inke bedunim range dokme bayad chejuri bashad **/
    boolean isCloseButtonShow = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_factors);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);


        if (Build.VERSION.SDK_INT >= 21)
            getWindow().setStatusBarColor(ContextCompat.getColor(G.context,R.color.dark_dark_grey));

        adapterFactor = new AdapterFactor(factors,this);
        linearLayoutManager = new LinearLayoutManager(this);
        lstFactor.setLayoutManager(linearLayoutManager);
        lstFactor.setAdapter(adapterFactor);

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
        WebService.sendGetFactorRequest();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                Logger.d("ActivityShowFactors : swipeRefreshLayout onRefresh()");
                WebService.sendGetFactorRequest();
                txtShowMessage.setVisibility(View.GONE);
            }
        });

        layBtnBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        layBtnClose.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        G.currentActivity = this;
    }
    /** darkhaste inke mikhahim yek factor ra start konim az tarafe adapterFactor*/
    public void onEventMainThread(EventOnClickedStartFactor event){
        layLoading.setVisibility(View.GONE);

        Logger.d("ActivityShowFactors : EventOnClickedStartFactor is raised");
        DialogClass dialogClass = new DialogClass();
        ModelYesNoDialog modelYesNoDialog = new ModelYesNoDialog("شروع فاکتور","آیا مطمئن هستید میخواهید این فاکتور را شروع کنید؟",event.getFactorId()+"", EnumYesNoKind.startFactor);
        dialogClass.showYesNoDialog(modelYesNoDialog);

    }
    /** darkhaste start factor*/
    public void onEventMainThread(EventOnGetStartFactorResponse event){

        Logger.d("ActivityShowFactors : EventOnGetStartFactorResponse is raised");
        layLoading.setVisibility(View.GONE);
        txtShowMessage.setVisibility(View.GONE);

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
        WebService.sendGetFactorRequest();
    }
    public void onEventMainThread(EventOnGetErrorStartFactor event){
        layLoading.setVisibility(View.GONE);

        Logger.d("ActivityShowFactors : EventOnGetErrorStartFactor is raised");
        layLoading.setVisibility(View.GONE);
        if(event.getErrorType() == EnumInternetErrorType.NO_INTERNET_ACCESS){
            U.toast("ارتباط اینترنتی خود را چک کنید.");
        }else{
            U.toast("خطا در دریافت اطلاعات از سرور");
        }
    }
    /** daryaft list factor'ha*/
    public void onEventMainThread(EventOnGetFactorResponse event){
        Logger.d("ActivityShowFactors : EventOnGetPaymentResponse is raised");
        layLoading.setVisibility(View.GONE);
        txtShowMessage.setVisibility(View.GONE);

        swipeRefreshLayout.setRefreshing(false);
        factors = event.getFactorResponses();
        adapterFactor.updateList(factors);
        if ( factors.size() == 0 ){
            txtShowMessage.setVisibility(View.VISIBLE);
            txtShowMessage.setText("موردی یافت نشد.");
        }else {
            txtShowMessage.setVisibility(View.GONE);
        }
    }
    public void onEventMainThread(EventOnGetErrorGetFactor event){
        Logger.d("ActivityShowFactors : EventOnGetErrorGetPayment is raised");
        layLoading.setVisibility(View.GONE);
        getFactorFromDB();
        swipeRefreshLayout.setRefreshing(false);
//        txtShowMessage.setVisibility(View.VISIBLE);
        txtShowMessage.setText("خطا در دریافت اطلاعات از سرور لطفا دوباره تلاش کنید.");
    }
    public void onEventMainThread(EventOnClickedFactorMoreDetail event){
        layLoading.setVisibility(View.GONE);

        Logger.d("ActivityShowFactors : EventOnClickedFactorMoreDetail is raised");
        WebService.sendGetFactorDetailRequest(event.getFactorCode());
        dlgWaiting = new DialogClass();
        dlgWaiting.DialogWaiting();
    }
    public void onEventMainThread(EventOnGetFactorDetailsResponse event){
        layLoading.setVisibility(View.GONE);
        txtShowMessage.setVisibility(View.GONE);

        Logger.d("ActivityShowFactors : EventOnGetFactorDetailsResponse is raised");
        ArrayList<FactorDetailResponse> response = event.getFactorDetailResponse();
        if( response.size() == 0){
            U.toast("موردی ثبت نشده است.");

//        }else if( response.size() == 1 ) {
//            DialogClass dlgFactorDetail = new DialogClass();
//            dlgFactorDetail.showFactorDetail(response.get(0));
//

        }else {
            String json = new Gson().toJson(response);
            Intent intent = new Intent(G.context,ActivityShowFactorDetail.class);
            Bundle bundle = new Bundle();
            bundle.putString("FACTOR_DETAIL", json);
            intent.putExtras(bundle);
            startActivity(intent);
        }
        if(dlgWaiting != null){
            dlgWaiting.DialogWaitingClose();
            dlgWaiting = null;
        }
    }
    public void onEventMainThread(EventOnGetErrorGetFactorDetail event){
        layLoading.setVisibility(View.GONE);
        Logger.d("ActivityShowFactors : EventOnGetErrorGetFactorDetail is raised");
        if(dlgWaiting != null){
            dlgWaiting.DialogWaitingClose();
            dlgWaiting = null;
        }
        if(event.getErrorType() == EnumInternetErrorType.NO_INTERNET_ACCESS){
            List<FactorDetail> factorDetails = new Select()
                    .from(FactorDetail.class)
                    .where("ParentId = ?", event.getFactorCode())
                    .execute();

            ArrayList<FactorDetailResponse> response = new ArrayList<>();
            for (FactorDetail detail : factorDetails){
                FactorDetailResponse detailResponse = new FactorDetailResponse(detail.Name,detail.Des,detail.Price);
                response.add(detailResponse);
            }

            if( response.size() == 1 ) {
                DialogClass dlgFactorDetail = new DialogClass();
                dlgFactorDetail.showFactorDetail(response.get(0));

            }else {
                Intent intent = new Intent(G.context,ActivityShowFactorDetail.class);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("FACTOR_DETAIL", response);
                intent.putExtras(bundle);
                startActivity(intent);
            }

        }else {
            U.toast("خطا در دریافت اطلاعات از سمت سرور لطفا مجددا تلاش کنید.");
        }
    }
    public void onEventMainThread(EventOnNoAccessServerResponse event){
        Logger.d("ActivityShowFactors : EventOnNoAccessServerResponse is raised");
        layLoading.setVisibility(View.GONE);

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        getFactorFromDB();
        if(dlgWaiting != null){
            dlgWaiting.DialogWaitingClose();
            dlgWaiting = null;
        }

    }
    public void onEventMainThread(EventOnClickedYesOnYesNoDialog dialog){
        switch (dialog.getYesNoKind()){
            case startFactor:
                WebService.sendStartFactorRequest(Long.parseLong(dialog.getDate()));
                layLoading.setVisibility(View.VISIBLE);
                break;
        }
    }
    public void onEventMainThread(EventOnErrorInConnectingToServer errorInConnectingToServer){
        layLoading.setVisibility(View.GONE);
    }

    private void getFactorFromDB() {
        factors = new Select().from(Factor.class).orderBy("Date desc").execute();
        adapterFactor.updateList(factors);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    /*-------------------------------------------------------------------------------------------------------------*/

}
