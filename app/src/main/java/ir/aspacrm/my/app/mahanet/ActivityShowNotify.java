package ir.aspacrm.my.app.mahanet;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import ir.aspacrm.my.app.mahanet.adapter.AdapterNotify;
import ir.aspacrm.my.app.mahanet.classes.Logger;
import ir.aspacrm.my.app.mahanet.classes.U;
import ir.aspacrm.my.app.mahanet.classes.WebService;
import ir.aspacrm.my.app.mahanet.enums.EnumInternetErrorType;
import ir.aspacrm.my.app.mahanet.events.EventOnErrorInConnectingToServer;
import ir.aspacrm.my.app.mahanet.events.EventOnGetErrorGetNotifies;
import ir.aspacrm.my.app.mahanet.events.EventOnGetNotifiesResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnNotifyDeleted;
import ir.aspacrm.my.app.mahanet.model.Notify;
import me.leolin.shortcutbadger.ShortcutBadgeException;
import me.leolin.shortcutbadger.ShortcutBadger;

public class ActivityShowNotify extends AppCompatActivity {
    @Bind(R.id.layBtnClose)
    LinearLayout layBtnClose;
    @Bind(R.id.lstNotify)
    RecyclerView lstNotify;
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.txtShowMessage)
    TextView txtShowMessage;
    @Bind(R.id.layBtnBack)
    LinearLayout layBtnBack;
    @Bind(R.id.layLoading)
    LinearLayout layLoading;

    AdapterNotify adapterNotify;
    LinearLayoutManager linearLayoutManager;
    List<Notify> notifies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_notify);
        ButterKnife.bind(this);
        WebService.sendSetNotifyReaded();
        EventBus.getDefault().register(this);


        if (Build.VERSION.SDK_INT >= 21)
            getWindow().setStatusBarColor(ContextCompat.getColor(G.context, R.color.dark_dark_grey));

        adapterNotify = new AdapterNotify(notifies);
        linearLayoutManager = new LinearLayoutManager(this);
        lstNotify.setLayoutManager(linearLayoutManager);
        lstNotify.setAdapter(adapterNotify);

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


        sendRequestGetNewNotify();
        refresh();


        layBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        layBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void refresh() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                Logger.d("ActivityShowNotify : swipeRefreshLayout onRefresh()");
                sendRequestGetNewNotify();
            }
        });
    }

    private void sendRequestGetNewNotify() {
        Notify lastNotify = new Select()
                .from(Notify.class)
                .orderBy("NotifyCode desc")
                .limit(1)
                .executeSingle();
        if (lastNotify == null) {
            WebService.sendGetNotifiesRequest(0, false);
        } else {
            WebService.sendGetNotifiesRequest(0, false);
        }
    }

    public void onEventMainThread(EventOnGetNotifiesResponse event) {
        Logger.d("ActivityShowNotify : EventOnGetNotifiesResponse is raised.");
        layLoading.setVisibility(View.GONE);
        getNotifyFromDB();
        swipeRefreshLayout.setRefreshing(false);


    }

    public void onEventMainThread(EventOnGetErrorGetNotifies event) {
        Logger.d("ActivityShowNotify : EventOnGetErrorGetNotifies is raised.");
        layLoading.setVisibility(View.GONE);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        getNotifyFromDB();

        if (event.getErrorType() == EnumInternetErrorType.NO_INTERNET_ACCESS) {
            U.toastOnMainThread("ارتباط اینترنتی خود را چک کنید.");
        }
    }

    public void onEventMainThread(EventOnErrorInConnectingToServer errorInConnectingToServer) {
        layLoading.setVisibility(View.GONE);
    }

    public void getNotifyFromDB() {
        notifies = new Select()
                .from(Notify.class)
                .where("IsSeen = 0")
                .orderBy("NotifyCode desc")
                .execute();
        adapterNotify.updateList(notifies);

        if (notifies.size() == 0) {
            txtShowMessage.setText(R.string.no_new_notifies);
            txtShowMessage.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        G.currentActivity = this;
        G.context = this;
        try {
            ShortcutBadger.removeCountOrThrow(G.context);
        } catch (ShortcutBadgeException e) {
            //e.printStackTrace();
            Logger.d("ActivityShowNotify : " + e.getMessage());
        }
    }


    public void onEventMainThread(EventOnNotifyDeleted event) {
        getNotifyFromDB();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


}
