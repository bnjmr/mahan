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
import ir.aspacrm.my.app.mahanet.adapter.AdapterClubScore;
import ir.aspacrm.my.app.mahanet.classes.Logger;
import ir.aspacrm.my.app.mahanet.classes.WebService;
import ir.aspacrm.my.app.mahanet.enums.EnumResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnErrorInConnectingToServer;
import ir.aspacrm.my.app.mahanet.events.EventOnGetClubScoreResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnGetClubScoresResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnGetErrorClubScores;
import ir.aspacrm.my.app.mahanet.events.EventOnGetErrorGetClubScore;
import ir.aspacrm.my.app.mahanet.events.EventOnNoAccessServerResponse;
import ir.aspacrm.my.app.mahanet.model.ClubScore;

public class ActivityShowClubScores extends AppCompatActivity {

    @Bind(R.id.layBtnClose)
    LinearLayout layBtnClose;
    @Bind(R.id.layBtnBack)
    LinearLayout layBtnBack;
    @Bind(R.id.lstClubScore)
    RecyclerView lstClubScore;
    @Bind(R.id.txtTotalClubScore)
    TextView txtTotalClubScore;
    @Bind(R.id.layTotalClubScore)
    LinearLayout layTotalClubScore;
    @Bind(R.id.txtShowMessage)
    TextView txtShowMessage;
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Bind(R.id.layLoading) LinearLayout layLoading;

    AdapterClubScore adapterClubScore;
    List<ClubScore> scores = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_club_scores);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);


        if (Build.VERSION.SDK_INT >= 21)
            getWindow().setStatusBarColor(ContextCompat.getColor(G.context, R.color.dark_dark_grey));

        layTotalClubScore.setVisibility(View.GONE);

        adapterClubScore = new AdapterClubScore(scores);
        linearLayoutManager = new LinearLayoutManager(G.context);
        lstClubScore.setLayoutManager(linearLayoutManager);
        lstClubScore.setAdapter(adapterClubScore);
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
        WebService.sendGetClubScoreRequest();
        WebService.sendClubScoresRequest();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                Logger.d("ActivityShowFeshfeshe : swipeRefreshLayout onRefresh()");
                WebService.sendClubScoresRequest();
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
    }

    public void onEventMainThread(EventOnGetClubScoreResponse event) {
        Logger.d("ActivityShowClubScores : EventOnGetClubScoreResponse is raised.");
        layLoading.setVisibility(View.GONE);

        int result = event.isResult();
        if (result == EnumResponse.OK) {
            layTotalClubScore.setVisibility(View.VISIBLE);
            int score = event.getScore();
            if (score == 0)
                txtTotalClubScore.setText("مجموع امتیازات : " + "0");
            else if (score > 0)
                txtTotalClubScore.setText("مجموع امتیازات : " + score + "");
            else
                txtTotalClubScore.setText("مجموع امتیازات : " + score);
        } else {
            layTotalClubScore.setVisibility(View.GONE);
        }
    }

    public void onEventMainThread(EventOnGetErrorGetClubScore event) {
        Logger.d("ActivityShowClubScores : EventOnGetErrorGetClubScore is raised.");
        layLoading.setVisibility(View.GONE);

    }

    public void onEventMainThread(EventOnGetClubScoresResponse event) {
        Logger.d("ActivityShowClubScores : EventOnGetClubScoresResponse is raised.");
        layLoading.setVisibility(View.GONE);

        swipeRefreshLayout.setRefreshing(false);
        scores = new Select().from(ClubScore.class)
//                .orderBy("Score desc")
                .execute();
        if (scores.size() == 0) {
            txtShowMessage.setVisibility(View.VISIBLE);
            txtShowMessage.setText("موردی یافت نشد.");
            scores = new ArrayList<>();
            adapterClubScore.updateList(scores);
        } else {
            scores = new Select().from(ClubScore.class)
//                    .orderBy("Score desc")
                    .execute();
            adapterClubScore.updateList(scores);
        }
    }

    public void onEventMainThread(EventOnGetErrorClubScores event) {
        Logger.d("ActivityShowClubScores : EventOnGetErrorClubScores is raised.");
        layLoading.setVisibility(View.GONE);

        swipeRefreshLayout.setRefreshing(false);
        getClubScoresFormDB();
    }

    public void onEventMainThread(EventOnNoAccessServerResponse event) {
        Logger.d("ActivityShowClubScores : EventOnNoAccessServerResponse is raised.");
        layLoading.setVisibility(View.GONE);

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        getClubScoresFormDB();
    }

    public void onEventMainThread(EventOnErrorInConnectingToServer errorInConnectingToServer){
        layLoading.setVisibility(View.GONE);
    }

    private void getClubScoresFormDB() {
        scores = new Select().from(ClubScore.class)
                .orderBy("Score desc")
                .execute();
        adapterClubScore.updateList(scores);
    }

    @Override
    protected void onResume() {
        super.onResume();
        G.currentActivity = this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
