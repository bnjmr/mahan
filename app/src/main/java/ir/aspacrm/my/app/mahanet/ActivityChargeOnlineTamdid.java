package ir.aspacrm.my.app.mahanet;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import de.greenrobot.event.EventBus;
import ir.aspacrm.my.app.mahanet.adapter.AdapterFactorDetail;
import ir.aspacrm.my.app.mahanet.classes.DialogClass;
import ir.aspacrm.my.app.mahanet.classes.Logger;
import ir.aspacrm.my.app.mahanet.classes.WebService;
import ir.aspacrm.my.app.mahanet.enums.EnumResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnErrorInConnectingToServer;
import ir.aspacrm.my.app.mahanet.events.EventOnGetChargeOnlineForTamdid;
import ir.aspacrm.my.app.mahanet.events.EventOnGetCheckTarazResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnGetErrorGetFactorDetail;
import ir.aspacrm.my.app.mahanet.events.EventOnGetFactorDetailsResponse;
import ir.aspacrm.my.app.mahanet.model.FactorDetail;

public class ActivityChargeOnlineTamdid extends AppCompatActivity implements View.OnClickListener {


    @Bind(R.id.layShowFactor)
    LinearLayout layShowFactor;
    @Bind(R.id.txtFactorDes)
    TextView txtFactorDes;
    @Bind(R.id.txtFactorCode)
    TextView txtFactorCode;
    @Bind(R.id.txtFactorStartDate)
    TextView txtFactorStartDate;
    @Bind(R.id.txtFactorGheymatPaye)
    TextView txtFactorGheymatPaye;
    @Bind(R.id.txtFactorGheymat)
    TextView txtFactorGheymat;
    @Bind(R.id.txtFactorMaliat)
    TextView txtFactorMaliat;
    @Bind(R.id.txtFactorTakhfif)
    TextView txtFactorTakhfif;
    @Bind(R.id.txtFactorMablaghePardakhti)
    TextView txtFactorMablaghePardakhti;
    @Bind(R.id.txtPay)
    TextView txtPay;
    @Bind(R.id.txtShowMessage)
    TextView txtShowMessage;
    @Bind(R.id.layBtnClose)
    LinearLayout layBtnClose;


    RecyclerView lstFactorDetail;
    LinearLayout layPay;
    LinearLayout layBtnBack;
    LinearLayout layLoading;

    AdapterFactorDetail adapterFactorDetail;
    List<FactorDetail> factorDetails = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;


    long factorCode;
    long factorPrice;
    int IS_CLUB;
    long orderId;// this filed use when User request to pay, and get this item from webservice chargeOnlineForPay
    DialogClass dlgPayMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charge_online_tamdid);


        lstFactorDetail = (RecyclerView) findViewById(R.id.lstFactorDetail);
        layPay = (LinearLayout) findViewById(R.id.layPay);
        layBtnBack = (LinearLayout) findViewById(R.id.layBtnBack);
        layLoading = (LinearLayout) findViewById(R.id.layLoading);
        layPay.setOnClickListener(this);
        layBtnBack.setOnClickListener(this);

        Intent intent = getIntent();
        if (intent != null) {
            factorCode = intent.getLongExtra("factorCode", -1);
            IS_CLUB = intent.getIntExtra("IS_CLUB", -1);
        }
        WebService.sendChargeOnlineForTamdidRequest(IS_CLUB);
    }


    public void onEventMainThread(EventOnGetChargeOnlineForTamdid event) {
        Logger.d("ActivityShowGraph : EventOnGetChargeOnlineForTamdid is raised");
        int result = event.isResult();
        final String message = event.getMessage();
        long factorCode = event.getFactorCode();
        if (result == EnumResponse.OK) {
            WebService.sendGetFactorDetailRequest(factorCode);
        } else {
            Intent intent = new Intent(ActivityChargeOnlineTamdid.this, ActivityChargeOnline.class);
            startActivity(intent);
            finish();
            layLoading.setVisibility(View.GONE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    DialogClass.showMessageDialog("پیغام", "" + message);
                }
            }, 500);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        G.currentActivity = this;
        G.context = this;
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        Logger.d("FragmentShowFactorDetail : onPause()");
    }

    @Override
    public void onStop() {
        super.onStop();
        Logger.d("FragmentShowFactorDetail : onStop()");
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.d("FragmentShowFactorDetail : onDestroy()");
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Intent intent = new Intent(this, ActivityChargeOnline.class);
        startActivity(intent);
        finish();
    }

    public void onEventMainThread(EventOnGetFactorDetailsResponse event) {
        Logger.d("FragmentShowFactorDetail : EventOnGetFactorDetailsResponse is raised");
//        FactorDetailResponse response = event.getFactorDetailResponse().get(0);
//        factorDetails = event.getFactorDetailResponse();
        factorDetails = new Select().from(FactorDetail.class).orderBy("Price desc").execute();
        final RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        adapterFactorDetail = new AdapterFactorDetail(factorDetails);
        lstFactorDetail.setLayoutManager(layoutManager2);
        lstFactorDetail.setAdapter(adapterFactorDetail);
        layLoading.setVisibility(View.GONE);

//        txtFactorDes.setText(response.Name);
//        txtFactorGheymatPaye.setText(response.Price + " " + G.currentUserInfo.UnitName);
//        WebService.sendSelectFactorRequest(factorCode);
    }

    public void onEventMainThread(EventOnGetErrorGetFactorDetail event) {
        Logger.d("FragmentShowFactorDetail : EventOnGetErrorGetFactorDetail is raised");
        layLoading.setVisibility(View.GONE);
        txtShowMessage.setText("خطا در دریافت اطلاعات از سمت سرور لطفا مجددا تلاش کنید.");
    }

    public void onEventMainThread(EventOnGetCheckTarazResponse event) {
        Logger.d("FragmentShowFactorDetail : EventOnGetCheckTarazResponse is raised");
        int taraz = event.getTaraz();
        boolean CanPayment = event.isCanPayment();
        if (taraz > 0 && CanPayment) {
            Logger.d("FragmentShowFactorDetail : taraz is positive and it's " + taraz);
            // TODO: 5/23/2016 showDialog aya mikhahid az etebar khod pardakht konid ya kheir.
            final Dialog dialog = new Dialog(G.currentActivity, R.style.DialogSlideAnim);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.dialog_pay_form_credit);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.getWindow().setGravity(Gravity.BOTTOM);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            LinearLayout layBtnCancel = (LinearLayout) dialog.findViewById(R.id.layBtnCancel);
            LinearLayout layBtnOk = (LinearLayout) dialog.findViewById(R.id.layBtnOk);
            final ImageView imgCloseDialog = (ImageView) dialog.findViewById(R.id.imgCloseDialog);
            TextView txtLayBtnCancel = (TextView) layBtnCancel.findViewById(R.id.txtValue);
            TextView txtLayBtnOk = (TextView) layBtnOk.findViewById(R.id.txtValue);

            txtLayBtnCancel.setText("  ورود به درگاه بانک  ");
            txtLayBtnOk.setText("  پرداخت از اعتبار  ");

            layBtnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    WebService.sendPayFactorFromCredit(factorCode);
                    dialog.dismiss();
                    layLoading.setVisibility(View.VISIBLE);
                }
            });

            layBtnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(G.context, ActivityShowBankList.class);
                    intent.putExtra("FACTOR_CODE", factorCode);
                    intent.putExtra("MONEY", 0);
                    startActivity(intent);
                    dialog.dismiss();
                }
            });

            imgCloseDialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            dialog.show();

        } else {
            Logger.d("FragmentShowFactorDetail : taraz is not positive and it's " + taraz);
            Intent intent = new Intent(G.context, ActivityShowBankList.class);
            intent.putExtra("FACTOR_CODE", factorCode);
            intent.putExtra("MONEY", 0);
            startActivity(intent);
        }
        layLoading.setVisibility(View.GONE);
    }

    public void onEventMainThread(EventOnErrorInConnectingToServer errorInConnectingToServer){
        layLoading.setVisibility(View.GONE);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layPay:
                WebService.sendCheckTaraz(factorCode);
                break;

            case R.id.layBtnBack:
                onBackPressed();
                break;

        }
    }
}
