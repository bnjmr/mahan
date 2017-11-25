package ir.aspacrm.my.app.mahanet;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import ir.aspacrm.my.app.mahanet.adapter.AdapterTicketDetails;
import ir.aspacrm.my.app.mahanet.classes.DialogClass;
import ir.aspacrm.my.app.mahanet.classes.Logger;
import ir.aspacrm.my.app.mahanet.classes.WebService;
import ir.aspacrm.my.app.mahanet.enums.EnumResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnErrorInConnectingToServer;
import ir.aspacrm.my.app.mahanet.events.EventOnGetAddTicketDetailsResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnGetErrorGetTicketDetails;
import ir.aspacrm.my.app.mahanet.events.EventOnGetTicketDetailsResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnNoAccessServerResponse;
import ir.aspacrm.my.app.mahanet.model.TicketDetail;
import ir.aspacrm.my.app.mahanet.model.Unit;

public class ActivityShowTicketDetails extends AppCompatActivity{

    @Bind(R.id.lstTicketDetail) RecyclerView lstTicketDetail;
    @Bind(R.id.layBtnClose) LinearLayout layBtnClose;
    @Bind(R.id.layBtnBack) LinearLayout layBtnBack;
    @Bind(R.id.layReplay) LinearLayout layReplay;
    @Bind(R.id.txtShowMessage) TextView txtShowMessage;
    @Bind(R.id.swipeRefreshLayout) SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.laySendChatMessage) LinearLayout laySendChatMessage;
    @Bind(R.id.layLoading) LinearLayout layLoading;
    @Bind(R.id.edtTicketReplay) EditText edtTicketReplay;

    AdapterTicketDetails adapterTicketDetail;
    LinearLayoutManager linearLayoutManager;
    List<TicketDetail> tickets = new ArrayList<>();


    long ticketCode = 0;
    int ticketOpen = 0;
    Unit ticketUnit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_ticket_details);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);




        if (Build.VERSION.SDK_INT >= 21)
            getWindow().setStatusBarColor(ContextCompat.getColor(G.context, R.color.dark_dark_grey));


        ticketCode = getIntent().getExtras().getLong("TICKET_CODE");
        ticketOpen = getIntent().getExtras().getInt("TICKET_OPEN");
        if (ticketOpen==0){
            layReplay.setVisibility(View.INVISIBLE);
        }

        adapterTicketDetail = new AdapterTicketDetails(tickets);
        linearLayoutManager = new LinearLayoutManager(this);
        lstTicketDetail.setLayoutManager(linearLayoutManager);
        lstTicketDetail.setAdapter(adapterTicketDetail);

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
        WebService.sendGetTicketDetailsRequest(ticketCode);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                Logger.d("ActivityShowTickets : swipeRefreshLayout onRefresh()");
                WebService.sendGetTicketDetailsRequest(ticketCode);
            }
        });
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

        laySendChatMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = edtTicketReplay.getText().toString().trim();
                if(message.length() == 0){
                    return;
                }else{
                    WebService.sendAddTicketDetailRequest(ticketCode, ticketUnit.code, message);
                    laySendChatMessage.setOnClickListener(null);
                }
            }
        });
    }
    public void onEventMainThread(EventOnGetTicketDetailsResponse event){
        layLoading.setVisibility(View.GONE);
        Logger.d("ActivityShowTicketDetails : EventOnGetTicketDetailsResponse is rasied");
//        tickets = event.getTicketDetails();
//        adapterTicketDetail.updateList(tickets);
        getTicketDetailsFromDB();
        swipeRefreshLayout.setRefreshing(false);
    }
    public void onEventMainThread(EventOnGetErrorGetTicketDetails event){
        layLoading.setVisibility(View.GONE);

        Logger.d("ActivityShowTicketDetails : EventOnGetErrorGetTicketDetails is rasied");
        getTicketDetailsFromDB();
        swipeRefreshLayout.setRefreshing(false);
    }
    public void onEventMainThread(EventOnGetAddTicketDetailsResponse event){
        layLoading.setVisibility(View.GONE);

        Logger.d("ActivityShowTicketDetails : EventOnGetAddTicketDetailsResponse is rasied");
        swipeRefreshLayout.setRefreshing(true);
        WebService.sendGetTicketDetailsRequest(ticketCode);
        laySendChatMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = edtTicketReplay.getText().toString().trim();
                if (message.length() == 0) {
                    return;
                } else {
                    WebService.sendAddTicketDetailRequest(ticketCode, tickets.get(0).UnitCode, message);
                    laySendChatMessage.setOnClickListener(null);
                }
            }
        });
        if(event.getStatus()== EnumResponse.OK){
            edtTicketReplay.setText("");
        }else{
            DialogClass dlgMessage = new DialogClass();
            dlgMessage.showMessageDialog("خطا","خطایی در دریافت تیکت بوجود آمده است.");
        }
    }
    public void onEventMainThread(EventOnErrorInConnectingToServer errorInConnectingToServer){
        layLoading.setVisibility(View.GONE);
    }

    public void onEventMainThread(EventOnNoAccessServerResponse event){
        layLoading.setVisibility(View.GONE);

        Logger.d("ActivityShowTicketDetails : EventOnNoAccessServerResponse is rasied");
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        getTicketDetailsFromDB();
    }
    private void getTicketDetailsFromDB() {
        tickets = new Select()
                .from( TicketDetail.class)
                .where("parentCode="+ticketCode)
                .orderBy("Id desc")
                .execute();
        adapterTicketDetail.updateList(tickets);
        ticketUnit = (new Select().from(Unit.class).where("Name = ? ",tickets.get(0).UnitName).executeSingle());
        lstTicketDetail.post(new Runnable() {
            @Override
            public void run() {
                lstTicketDetail.scrollToPosition(tickets.size());
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        G.currentActivity = this;
        G.context = this;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
