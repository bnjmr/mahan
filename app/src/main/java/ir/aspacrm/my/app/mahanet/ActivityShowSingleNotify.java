package ir.aspacrm.my.app.mahanet;

import android.content.Intent;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ir.aspacrm.my.app.mahanet.adapter.AdapterSingleNotify;
import ir.aspacrm.my.app.mahanet.model.Notify;

public class ActivityShowSingleNotify extends AppCompatActivity {
    @Bind(R.id.layBtnBack)
    LinearLayout layBtnBack;
    @Bind(R.id.lstNotifies)
    RecyclerView lstNotifies;
    @Bind(R.id.txtShowMessage)
    TextView txtShowMessage;

    AdapterSingleNotify adapterNofify;
    LinearLayoutManager linearLayoutManager;
    List<Notify> notifies= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_notifes);



        ButterKnife.bind(this);

        Intent i = getIntent();

        Notify notify = new Notify();
        notify.Title = i.getStringExtra("title");
        notify.message = i.getStringExtra("body");
        notify.Date = i.getStringExtra("date");

        notifies.add(notify);

        if (Build.VERSION.SDK_INT >= 21)
            getWindow().setStatusBarColor(ContextCompat.getColor(G.context, R.color.dark_dark_grey));

        adapterNofify = new AdapterSingleNotify(notifies);
        linearLayoutManager = new LinearLayoutManager(this);
        lstNotifies.setLayoutManager(linearLayoutManager);
        lstNotifies.setAdapter(adapterNofify);

        /** be dalil inke dar fragment
         *  swipeRefreshLayout.setRefreshing(true);
         *  dar ebteda kar nemikonad ke listener
         *  swipeRefreshLayout.setOnRefreshListener ra seda bezanad be surate dasti aan ra seda mizanim.*/

        layBtnBack.setOnClickListener(new View.OnClickListener() {
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
        G.context = this;
    }
}
