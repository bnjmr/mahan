package ir.aspacrm.my.app.mahanet;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import ir.aspacrm.my.app.mahanet.classes.DialogClass;
import ir.aspacrm.my.app.mahanet.classes.Logger;
import ir.aspacrm.my.app.mahanet.classes.WebService;
import ir.aspacrm.my.app.mahanet.enums.EnumResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnClickedStartFactor;
import ir.aspacrm.my.app.mahanet.events.EventOnErrorInConnectingToServer;
import ir.aspacrm.my.app.mahanet.events.EventOnGetCheckTarazResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnGetErrorCheckTaraz;
import ir.aspacrm.my.app.mahanet.events.EventOnGetErrorGetFactorDetail;
import ir.aspacrm.my.app.mahanet.events.EventOnGetFactorDetailsResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnGetPayFactorFromCreditResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnGetSelectFactorResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnShowFragmentBankListRequest;
import ir.aspacrm.my.app.mahanet.gson.FactorDetailResponse;
import ir.aspacrm.my.app.mahanet.gson.PayFactorFromCreditResponse;
import ir.aspacrm.my.app.mahanet.gson.SelectFactorResponse;

public class ActivityShowPackageFactorDetail extends AppCompatActivity {


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
    @Bind(R.id.layPay)
    LinearLayout layPay;
    @Bind(R.id.layLoading)
    LinearLayout layLoading;
    @Bind(R.id.txtShowMessage)
    TextView txtShowMessage;
    @Bind(R.id.layBtnClose)
    LinearLayout layBtnClose;

    @Bind(R.id.layBtnBack)
    LinearLayout layBtnBack;

    @Bind(R.id.txtDes)
    TextView txtDes;


    long factorCode;
    long orderId;// this filed use when User request to pay, and get this item from webservice chargeOnlineForPay
    DialogClass dlgPayMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_package_factor_detail);
        ButterKnife.bind(this);

        EventBus.getDefault().register(this);
        G.context = this;
        G.currentActivity = this;


        Intent intent = getIntent();
        factorCode = intent.getLongExtra("FACTOR_CODE", 0);

        initView();


    }

    private void initView() {
        layBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Logger.d("FragmentShowFactorDetail : onActivityCreated()");
        layShowFactor.setVisibility(View.GONE);
        WebService.sendGetFactorDetailRequest(factorCode);
        layBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        layPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                WebService.sendChargeOnlineForPayRequest(factorCode);
                WebService.sendCheckTaraz(factorCode);
                layLoading.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Logger.d("FragmentShowFactorDetail : onResume()");
        if (orderId != 0) {
//            WebService.sendCheckOrderIdResultRequest(orderId);
            layLoading.setVisibility(View.VISIBLE);
            orderId = 0;
        }
    }

    public void onEventMainThread(EventOnGetFactorDetailsResponse event) {
        Logger.d("FragmentShowFactorDetail : EventOnGetFactorDetailsResponse is raised");
        FactorDetailResponse response = event.getFactorDetailResponse().get(0);
        txtFactorDes.setText(response.Name);
        txtFactorGheymatPaye.setText(response.Price + " " + G.currentUserInfo.unit);
        txtDes.setText(response.Des);
        WebService.sendSelectFactorRequest(factorCode);
    }

    public void onEventMainThread(EventOnGetErrorGetFactorDetail event) {
        Logger.d("FragmentShowFactorDetail : EventOnGetErrorGetFactorDetail is raised");
        layLoading.setVisibility(View.GONE);
        txtShowMessage.setText("خطا در دریافت اطلاعات از سمت سرور لطفا مجددا تلاش کنید.");
    }

    public void onEventMainThread(EventOnGetSelectFactorResponse event) {
        Logger.d("FragmentShowFactorDetail : EventOnGetSelectFactorResponse is raised");
        SelectFactorResponse response = event.getSelectFactorResponse();
        txtFactorCode.setText("" + response.code);
        txtFactorStartDate.setText("" + response.StartDate);
        txtFactorGheymat.setText("" + response.Price + " " + G.currentUserInfo.unit);
        txtFactorTakhfif.setText("" + response.Discount + " " + G.currentUserInfo.unit);
        txtFactorMaliat.setText("" + response.Tax + " " + G.currentUserInfo.unit);
        txtFactorMablaghePardakhti.setText("" + response.Amount + " " + G.currentUserInfo.unit);
        layLoading.setVisibility(View.GONE);
        layShowFactor.setVisibility(View.VISIBLE);
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
            ImageView imgCloseDialog = (ImageView) dialog.findViewById(R.id.imgCloseDialog);
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
                    EventBus.getDefault().post(new EventOnShowFragmentBankListRequest(factorCode, 0));
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
            EventBus.getDefault().post(new EventOnShowFragmentBankListRequest(factorCode, 0));
        }
        layLoading.setVisibility(View.GONE);
    }

    public void onEventMainThread(EventOnGetErrorCheckTaraz event) {
        Logger.d("FragmentShowFactorDetail : EventOnGetErrorCheckTaraz is raised");
        layLoading.setVisibility(View.GONE);
        DialogClass dlgShowErrorMessage = new DialogClass();
        dlgShowErrorMessage.showMessageDialog("پیغام", "خطا در دریافت اطلاعات تراز مالی، لطفا مجددا تلاش کنید.");
    }

    public void onEventMainThread(EventOnGetPayFactorFromCreditResponse event) {
        Logger.d("FragmentShowFactorDetail : EventOnGetPayFactorFromCreditResponse is raised");
        final PayFactorFromCreditResponse response = event.getResponse();
        layLoading.setVisibility(View.GONE);
        if (response.Result!= EnumResponse.OK) {
            /** dar surate khata dashtan.*/
            DialogClass dlgShowErrorMessage = new DialogClass();
            dlgShowErrorMessage.showMessageDialog("خطا در پرداخت", response.Message);
        } else {
            try {

                Intent intent = new Intent(this,ActivityShowCurrentService.class);
                startActivity(intent);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (!response.started) {
                            /** dar surati ke factor shoruh nashode bashad.*/
//                dlgPayMessage = new DialogClass();
//                dlgPayMessage.showPayMessage(factorCode,"نتیجه پرداخت","آیا مایل به شروع فاکتور خود هستید؟" + "\n" + "تاریخ شروع فاکتور : " + response.startDate);

                            /** show dialog halati ke mitavanad factor ra ghabl az moaed shoruh konad.**/
                            final Dialog dialogPayMessage = new Dialog(G.currentActivity, R.style.DialogSlideAnim);
                            dialogPayMessage.setContentView(R.layout.dialog_start_factor_message);
                            dialogPayMessage.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                            dialogPayMessage.getWindow().setGravity(Gravity.BOTTOM);
                            dialogPayMessage.setCancelable(false);
                            dialogPayMessage.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                            CardView layBtnStartFactor = (CardView) dialogPayMessage.findViewById(R.id.layBtnStartFactor);
                            CardView layBtnCancel = (CardView) dialogPayMessage.findViewById(R.id.layBtnCancel);
                            ImageView imgCloseDialog = (ImageView) dialogPayMessage.findViewById(R.id.imgCloseDialog);
                            TextView txtMessageTitle = (TextView) dialogPayMessage.findViewById(R.id.txtMessageTitle);
                            TextView txtMessageDescription = (TextView) dialogPayMessage.findViewById(R.id.txtMessageDescription);

                            txtMessageTitle.setText("نتیجه پرداخت");
                            txtMessageDescription.setText("فاکتور شما با موفقیت پرداخت شد و در تاریخ " + response.startDate + " شروع خواهد شد." + "\n" + "آیا مایل به شروع فاکتور خود در همین لحظه هستید؟");

                            if (factorCode == 0)
                                layBtnStartFactor.setVisibility(View.GONE);
                            else
                                layBtnStartFactor.setVisibility(View.VISIBLE);

                            layBtnStartFactor.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    EventBus.getDefault().post(new EventOnClickedStartFactor(factorCode));
                                    dialogPayMessage.dismiss();
                                }
                            });
                            layBtnCancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialogPayMessage.dismiss();
//                        FragmentManager fm = getActivity().getSupportFragmentManager();
//                        for (int i = 0; i < fm.getBackStackEntryCount() - 1; ++i) {
//                            fm.popBackStack();
//                        }
                                }
                            });
                            imgCloseDialog.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialogPayMessage.dismiss();
//                        FragmentManager fm = getActivity().getSupportFragmentManager();
//                        for (int i = 0; i < fm.getBackStackEntryCount() - 1; ++i) {
//                            fm.popBackStack();
//                        }
                                }
                            });
                            dialogPayMessage.show();

                        } else {

                            final Dialog dialog = new Dialog(G.currentActivity, R.style.DialogSlideAnim);
                            dialog.setContentView(R.layout.dialog_show_message);
                            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                            dialog.getWindow().setGravity(Gravity.BOTTOM);
                            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                            ImageView imgCloseDialog = (ImageView) dialog.findViewById(R.id.imgCloseDialog);
                            TextView txtMessageTitle = (TextView) dialog.findViewById(R.id.txtMessageTitle);
                            TextView txtMessageDescription = (TextView) dialog.findViewById(R.id.txtMessageDescription);
                            txtMessageTitle.setText("" + "پیغام");
                            txtMessageDescription.setText("" + "پرداخت با موفقیت انجام شد.");
                            imgCloseDialog.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    dialog.dismiss();
                                }
                            });
                            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                @Override
                                public void onCancel(DialogInterface dialogInterface) {
//                        FragmentManager fm = getActivity().getSupportFragmentManager();
//                        for (int i = 0; i < fm.getBackStackEntryCount() - 1; ++i) {
//                            fm.popBackStack();
//                        }
                                }
                            });
                            dialog.show();
                        }
                    }
                },500);
            }catch (Exception e){
                e.printStackTrace();
            }

        }//

    }

    public void onEventMainThread(EventOnShowFragmentBankListRequest event) {
        Logger.d("ActivityChargeOnline : EventOnShowFragmentBankListRequest is raised");
        Intent intent = new Intent(this, ActivityShowBankList.class);
        intent.putExtra("FACTOR_CODE", event.getFactorCode());
        intent.putExtra("MONEY", event.getMoney());
        startActivity(intent);
//        fragmentManager.beginTransaction()
//                .add(ir.aspacrm.my.app.mahanet.R.id.layFragment, FragmentShowBankList.newInstance(event.getFactorCode(), event.getMoney()))
//                .addToBackStack("FragmentShowBankList")
//                .commit();

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
