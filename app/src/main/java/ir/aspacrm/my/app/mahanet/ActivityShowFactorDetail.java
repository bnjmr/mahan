package ir.aspacrm.my.app.mahanet;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import ir.aspacrm.my.app.mahanet.adapter.AdapterFactorDetail;
import ir.aspacrm.my.app.mahanet.model.FactorDetail;

public class ActivityShowFactorDetail extends AppCompatActivity {


    @Bind(R.id.layBtnClose)
    LinearLayout layBtnClose;
    @Bind(R.id.lstFactorDetail)
    RecyclerView lstFactorDetail;
    @Bind(R.id.layBtnBack)
    LinearLayout layBtnBack;


    LinearLayoutManager linearLayoutManager;
    AdapterFactorDetail adapterFactorDetail;
    FactorDetail[] factorDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_factor_detail);
        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT >= 21)
            getWindow().setStatusBarColor(ContextCompat.getColor(G.context, R.color.dark_dark_grey));

//        factorDetails = new Select().from(FactorDetail.class).orderBy("Price desc").execute();
        String json = getIntent().getStringExtra("FACTOR_DETAIL");
        try {
            factorDetails = G.gson.fromJson(json, FactorDetail[].class);
        } catch (Exception e) {
            e.printStackTrace();
            factorDetails = new FactorDetail[0];
        }

        linearLayoutManager = new LinearLayoutManager(this);
        adapterFactorDetail = new AdapterFactorDetail(Arrays.asList(factorDetails));
        lstFactorDetail.setLayoutManager(linearLayoutManager);
        lstFactorDetail.setAdapter(adapterFactorDetail);
        layBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        layBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


}
