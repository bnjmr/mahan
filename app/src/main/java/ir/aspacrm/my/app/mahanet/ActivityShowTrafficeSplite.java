package ir.aspacrm.my.app.mahanet;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import ir.aspacrm.my.app.mahanet.adapter.AdapterTrafficSplit;
import ir.aspacrm.my.app.mahanet.classes.DialogClass;
import ir.aspacrm.my.app.mahanet.classes.Logger;
import ir.aspacrm.my.app.mahanet.classes.WebService;
import ir.aspacrm.my.app.mahanet.component.PersianTextViewThin;
import ir.aspacrm.my.app.mahanet.enums.EventOnSuccessGoToMainTraffic;
import ir.aspacrm.my.app.mahanet.events.EventOnClickedYesOnYesNoDialog;
import ir.aspacrm.my.app.mahanet.events.EventOnErrorInConnectingToServer;
import ir.aspacrm.my.app.mahanet.events.EventOnFailStartTrafficSplit;
import ir.aspacrm.my.app.mahanet.events.EventOnGetCurrentTrafficSplite;
import ir.aspacrm.my.app.mahanet.events.EventOnLoadTrafficSplitNotMain;
import ir.aspacrm.my.app.mahanet.events.EventOnSuccessEndTrafficSplit;
import ir.aspacrm.my.app.mahanet.events.EventOnSuccessStartTrafficSplit;
import ir.aspacrm.my.app.mahanet.model.ModelYesNoDialog;

import static ir.aspacrm.my.app.mahanet.enums.EnumYesNoKind.EndCurrentTrafficSplit;
import static ir.aspacrm.my.app.mahanet.enums.EnumYesNoKind.GoToMainTraffic;


public class ActivityShowTrafficeSplite extends AppCompatActivity {

    @Bind(R.id.txtCurrentTrafficSpliteExpireDate)
    PersianTextViewThin txtCurrentTrafficSpliteExpireDate;

    @Bind(R.id.txtCurrentTrafficSplitTraffic)
    PersianTextViewThin txtCurrentTrafficSplitTraffic;

    @Bind(R.id.imgEndCurrentTrafficSplitRequest)
    CardView imgEndCurrentTrafficSplitRequest;

    @Bind(R.id.imgGoToMainTraffic)
    CardView imgGoToMainTraffic;

    @Bind(R.id.layCurrentTrafficSplit)
    LinearLayout layCurrentTrafficSplit;

    @Bind(R.id.lstTrafficSplit)
    RecyclerView lstTrafficSplit;

    @Bind(R.id.layBtnClose)
    LinearLayout layBtnClose;

    @Bind(R.id.layBtnBack)
    LinearLayout layBtnBack;

    @Bind(R.id.txtShowMessage)
    TextView txtShowMessage;

    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Bind(R.id.layLoading)
    LinearLayout layLoading;

    AdapterTrafficSplit adapterTrafficSplit;
    DialogClass dialogClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_traffice_splite);
        ButterKnife.bind(this);
        layBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                Logger.d("ActivityShowFeshfeshe : swipeRefreshLayout onRefresh()");
                WebService.sendGetCurrentTrraficSplite();
                WebService.sendLoadTrafficSplitsNotMain();

            }
        });
        dialogClass = new DialogClass();
        layBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        G.currentActivity = this;
        EventBus.getDefault().register(this);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                WebService.sendGetCurrentTrraficSplite();
                WebService.sendLoadTrafficSplitsNotMain();

            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
//        G.currentActivity = this;
        EventBus.getDefault().unregister(this);
    }

    public void onEventMainThread(EventOnGetCurrentTrafficSplite splite) {
        swipeRefreshLayout.setRefreshing(false);
        layLoading.setVisibility(View.GONE);
        if (splite.getCurrentTrraficSplite().isExist()) {
            layCurrentTrafficSplit.setVisibility(View.VISIBLE);
            txtCurrentTrafficSpliteExpireDate.setText(splite.getCurrentTrraficSplite().getEndDateTrafficSplit());
            txtCurrentTrafficSplitTraffic.setText(" باقیمانده جاری : " + splite.getCurrentTrraficSplite().getCurrentRemain() + "\n"
                    + "باقیمانده کل : " + (int) splite.getCurrentTrraficSplite().getSumRemainTraffic());
            if (splite.getCurrentTrraficSplite().isEndPackage()) {
                imgEndCurrentTrafficSplitRequest.setEnabled(true);
                imgEndCurrentTrafficSplitRequest.setVisibility(View.VISIBLE);
                imgEndCurrentTrafficSplitRequest.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ModelYesNoDialog modelYesNoDialog = new ModelYesNoDialog("هشدار", "آیا مطمن هستید میخواهید تقسیم ترافیک خود را متوقف کنید؟", "", EndCurrentTrafficSplit);
                        dialogClass.showYesNoDialog(modelYesNoDialog);
                    }
                });
            } else {
                imgEndCurrentTrafficSplitRequest.setEnabled(false);
                imgEndCurrentTrafficSplitRequest.setVisibility(View.GONE);
            }

            if (splite.getCurrentTrraficSplite().isGoToMainTraffic()) {
                imgGoToMainTraffic.setVisibility(View.VISIBLE);
                imgGoToMainTraffic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ModelYesNoDialog modelYesNoDialog = new ModelYesNoDialog("هشدار", "آیا مطمئن هستید میخواهید به سرویس اصلی بروید؟", "", GoToMainTraffic);
                        dialogClass.showYesNoDialog(modelYesNoDialog);
                    }
                });
            } else {
                imgGoToMainTraffic.setVisibility(View.GONE);
            }
        } else {
            layCurrentTrafficSplit.setVisibility(View.GONE);
        }
    }


    public void onEventMainThread(EventOnLoadTrafficSplitNotMain splite) {
        swipeRefreshLayout.setRefreshing(false);

        lstTrafficSplit.setHasFixedSize(true);
        if (splite.getTrafficSplitNotMainModel().length == 0) {
            txtShowMessage.setVisibility(View.VISIBLE);
            txtShowMessage.setText("برای شما تقسیم ترافیکی ثبت نشده");
        } else {
            txtShowMessage.setVisibility(View.GONE);

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            adapterTrafficSplit = new AdapterTrafficSplit(Arrays.asList(splite.getTrafficSplitNotMainModel()), this);
            lstTrafficSplit.setLayoutManager(layoutManager);
            lstTrafficSplit.setAdapter(adapterTrafficSplit);
        }

    }

    public void onEventMainThread(EventOnErrorInConnectingToServer errorInConnectingToServer) {
        layLoading.setVisibility(View.GONE);
        DialogClass.showMessageDialog("خطا", "لطفا اینترنت خود را چک کنید و دوباره امتحان کنید");
    }

    public void onEventMainThread(EventOnClickedYesOnYesNoDialog yesNoDialog) {
        switch (yesNoDialog.getYesNoKind()) {
            case EndCurrentTrafficSplit:
                WebService.sendEndTrraficSplite();
                layLoading.setVisibility(View.VISIBLE);
                break;
            case GoToMainTraffic:
                WebService.sendGoToMainTrrafic();
                layLoading.setVisibility(View.VISIBLE);

                break;
            case StartTrafficSplit:
                WebService.sendStartTrafficSplit(Integer.parseInt(yesNoDialog.getDate()));
                layLoading.setVisibility(View.VISIBLE);
                break;
        }

    }

    public void onEventMainThread(EventOnSuccessStartTrafficSplit split) {
        layLoading.setVisibility(View.GONE);

        DialogClass.showMessageDialog("", "عمیلات با موفقیت انجام شد");
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                WebService.sendGetCurrentTrraficSplite();
                WebService.sendLoadTrafficSplitsNotMain();

            }
        });

    }

    public void onEventMainThread(EventOnFailStartTrafficSplit split) {
        layLoading.setVisibility(View.GONE);
        DialogClass.showMessageDialog("خطا", split.getMessage());
    }

    public void onEventMainThread(EventOnSuccessGoToMainTraffic split) {
        layLoading.setVisibility(View.GONE);
        DialogClass.showMessageDialog("", "عمیلات با موفقیت انجام شد");
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                WebService.sendGetCurrentTrraficSplite();
                WebService.sendLoadTrafficSplitsNotMain();

            }
        });

    }
    public void onEventMainThread(EventOnSuccessEndTrafficSplit split) {
        layLoading.setVisibility(View.GONE);
        DialogClass.showMessageDialog("", "عمیلات با موفقیت انجام شد");
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                WebService.sendGetCurrentTrraficSplite();
                WebService.sendLoadTrafficSplitsNotMain();

            }
        });

    }

}
