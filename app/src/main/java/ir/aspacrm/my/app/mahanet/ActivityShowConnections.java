package ir.aspacrm.my.app.mahanet;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.activeandroid.query.Select;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;
import ir.aspacrm.my.app.mahanet.adapter.AdapterConnection;
import ir.aspacrm.my.app.mahanet.adapter.AdapterSpinnerNetType;
import ir.aspacrm.my.app.mahanet.classes.DialogClass;
import ir.aspacrm.my.app.mahanet.classes.Logger;
import ir.aspacrm.my.app.mahanet.classes.U;
import ir.aspacrm.my.app.mahanet.classes.WebService;
import ir.aspacrm.my.app.mahanet.component.PersianTextViewNormal;
import ir.aspacrm.my.app.mahanet.enums.EnumNetType;
import ir.aspacrm.my.app.mahanet.events.EventOnErrorInConnectingToServer;
import ir.aspacrm.my.app.mahanet.events.EventOnGetConnectionResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnGetErrorGetConnections;
import ir.aspacrm.my.app.mahanet.events.EventOnNoAccessServerResponse;
import ir.aspacrm.my.app.mahanet.model.Connection;
import ir.aspacrm.my.app.mahanet.model.NetType;
import ir.hamsaa.persiandatepicker.Listener;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;
import saman.zamani.persiandate.PersianDate;

import static ir.aspacrm.my.app.mahanet.G.context;

public class ActivityShowConnections extends AppCompatActivity {


    @Bind(R.id.lstConnection)
    RecyclerView lstConnection;
    @Bind(R.id.layBtnClose)
    LinearLayout layBtnClose;
    @Bind(R.id.layBtnBack)
    LinearLayout layBtnBack;
    @Bind(R.id.txtShowMessage)
    TextView txtShowMessage;
    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Bind(R.id.btnStart)
    PersianTextViewNormal btnStart;
    @Bind(R.id.btnEndDate)
    PersianTextViewNormal btnEndDate;

    @Bind(R.id.txtStartDate)
    PersianTextViewNormal txtStartDate;

    @Bind(R.id.txtEndDate)
    PersianTextViewNormal txtEndDate;

    @Bind(R.id.spNetTypes)
    Spinner spNetTypes;

    @Bind(R.id.btnFilter)
    PersianTextViewNormal btnFilter;

    @Bind(R.id.laySelect)
    LinearLayout laySelect;
    @Bind(R.id.layEndDate)
    LinearLayout layEndDate;
    @Bind(R.id.layStartDate)
    LinearLayout layStartDate;
    @Bind(R.id.layLoading)
    LinearLayout layLoading;

    String netType = "1";


    AdapterConnection adapterConnection;
    LinearLayoutManager linearLayoutManager;
    List<Connection> connections = new ArrayList<>();
    private String current = "";
    private PersianDatePickerDialog StartPicker;
    private PersianDatePickerDialog EndPicker;
    String StartDate;
    String EndDate;
    Calendar cal = Calendar.getInstance();
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
    private AdapterSpinnerNetType adapterSpinnerCodes;
    private boolean firstSelect = false;
    private boolean firstSelect2 = false;
    int StartDate3[] = {0, 0, 0};
    int EndDate3[] = {0, 0, 0};
    PersianCalendar StartinitDate;
    PersianCalendar EndinitDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_connections);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        StartinitDate = new PersianCalendar();
        EndinitDate = new PersianCalendar();

        btnFilter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (netType.equals("-1")) {
                    DialogClass dialogClass = new DialogClass();
                    dialogClass.showMessageDialog("خطا", "لطفا ترافیک داخلی و خارجی را مشخص کنید");
                } else {
                    WebService.sendGetConnectionsRequest(StartDate, EndDate, netType);
                    layLoading.setVisibility(View.VISIBLE);
                }

            }
        });

        layEndDate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showEndPicker();
            }
        });
        layStartDate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showStartPicker(StartDate3);
            }
        });
        StartDate = U.getDate(-5);
        EndDate = U.getCurrentDate();


        if (Build.VERSION.SDK_INT >= 21)
            getWindow().setStatusBarColor(ContextCompat.getColor(context, R.color.dark_dark_grey));

        Calendar calendar = Calendar.getInstance(Locale.ENGLISH);

        int day0 = calendar.get(Calendar.DAY_OF_MONTH);
        int m0 = calendar.get(Calendar.MONTH) + 1;
        int y0 = calendar.get(Calendar.YEAR);

        StartDate = df.format(getDate(y0, m0, day0));
        EndDate = df.format(getDate(y0, m0, day0));

        PersianDate pdate0 = new PersianDate();
        int date[] = pdate0.toJalali(y0, m0, day0);
        String d0 = date[0] + " / " + date[1] + " / " + date[2];

        txtStartDate.setText(d0);
        txtEndDate.setText(d0);


        final List<NetType> netTypes = new ArrayList<>();
//        netTypes.add(new NetType("ترافیک داخلی/ خارجی", EnumNetType.Title));
        netTypes.add(new NetType("مصرف داخلی و خارجی", EnumNetType.All));
//        netTypes.add(new NetType("اصلی", EnumNetType.Master));
        netTypes.add(new NetType("مصرف خارجی", EnumNetType.INTERNET));
        netTypes.add(new NetType("مصرف داخلی", EnumNetType.INTRANET));


        adapterSpinnerCodes = new AdapterSpinnerNetType(netTypes);
        spNetTypes.setAdapter(adapterSpinnerCodes);

        spNetTypes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                netType = netTypes.get(position).getValue() + "";
                if (firstSelect2) {
                    if (!firstSelect) {
//                        if (!netTypes.equals("-1")) {
//                            firstSelect = true;
//                            netTypes.remove(0);
//                            adapterSpinnerCodes.notifyDataSetChanged();
//                            spNetTypes.setSelection(0);
//                        }
                    }
                }
                firstSelect2 = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


//        /** be dalil inke dar fragment
//         *  swipeRefreshLayout.setRefreshing(true);
//         *  dar ebteda kar nemikonad ke listener
//         *  swipeRefreshLayout.setOnRefreshListener ra seda bezanad be surate dasti aan ra seda mizanim.*/
//        swipeRefreshLayout.post(new Runnable() {
//            @Override
//            public void run() {
//                swipeRefreshLayout.setRefreshing(true);
//            }
//        });

//        WebService.sendGetConnectionsRequest(StartDate, EndDate, netType);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                Logger.d("ActivityShowFactors : swipeRefreshLayout onRefresh()");
                WebService.sendGetConnectionsRequest(StartDate, EndDate, netType);
            }
        });

        layBtnClose.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        layBtnBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void showStartPicker(int[] date) {

        StartPicker = new PersianDatePickerDialog(this)
                .setPositiveButtonString("باشه")
                .setNegativeButton("بیخیال")
                .setTodayButton("امروز")
                .setTodayButtonVisible(true)
                .setInitDate(StartinitDate)
                .setMaxYear(1398)
                .setMinYear(1300)
                .setActionTextColor(Color.GRAY)
                .setListener(new Listener() {
                    @Override
                    public void onDateSelected(PersianCalendar persianCalendar) {
                        PersianDate pdate = new PersianDate();
                        StartinitDate = persianCalendar;
                        EndDate3 = pdate.toGregorian(persianCalendar.getPersianYear(), persianCalendar.getPersianMonth(), persianCalendar.getPersianDay());
                        StartDate = df.format(getDate(EndDate3[0], EndDate3[1] - 1, EndDate3[2]));
                        String d = persianCalendar.getPersianYear() + " / " + persianCalendar.getPersianMonth() + " / " + persianCalendar.getPersianDay();
                        txtStartDate.setText(d);
                    }

                    @Override
                    public void onDisimised() {

                    }
                });

        StartPicker.show();

    }

    private void showEndPicker() {

        EndPicker = new PersianDatePickerDialog(this)
                .setPositiveButtonString("باشه")
                .setNegativeButton("بیخیال")
                .setTodayButton("امروز")
                .setTodayButtonVisible(true)
                .setMaxYear(1398)
                .setMinYear(1300)
                .setInitDate(EndinitDate)
                .setActionTextColor(Color.GRAY)
                .setListener(new Listener() {
                    @Override
                    public void onDateSelected(PersianCalendar persianCalendar) {
//                        Toast.makeText(context, persianCalendar.getPersianYear() + "/" + persianCalendar.getPersianMonth() + "/" + persianCalendar.getPersianDay(), Toast.LENGTH_SHORT).show();
                        PersianDate pdate = new PersianDate();
                        EndinitDate = persianCalendar;
                        StartDate3 = pdate.toGregorian(persianCalendar.getPersianYear(), persianCalendar.getPersianMonth(), persianCalendar.getPersianDay());
                        EndDate = df.format(getDate(StartDate3[0], StartDate3[1] - 1, StartDate3[2] + 1));
                        String d = persianCalendar.getPersianYear() + " / " + persianCalendar.getPersianMonth() + " / " + persianCalendar.getPersianDay();
                        txtEndDate.setText(d);
                    }

                    @Override
                    public void onDisimised() {

                    }
                });
        EndPicker.show();
    }

    public static Date getDate(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    @Override
    protected void onResume() {
        super.onResume();
        G.currentActivity = this;
    }

    public void onEventMainThread(EventOnGetConnectionResponse event) {
        Logger.d("ActivityShowConnections : EventOnGetConnectionResponse is raised");
        swipeRefreshLayout.setRefreshing(false);
        layLoading.setVisibility(View.GONE);
        connections = event.getGetConnectionsResponses();
        for (Connection connection : event.getGetConnectionsResponses()) {
            if (connection.Reason != null && connection.Reason.equals("sum")) {
                connections.add(0, connection);
                return;
            }

        }

        adapterConnection = new AdapterConnection(connections);
        linearLayoutManager = new LinearLayoutManager(this);
        lstConnection.setLayoutManager(linearLayoutManager);
        lstConnection.setAdapter(adapterConnection);


        adapterConnection.updateList(connections);
        if (connections.size() == 0) {
            txtShowMessage.setVisibility(View.VISIBLE);
            txtShowMessage.setText("موردی یافت نشد.");
        } else {
            txtShowMessage.setVisibility(View.GONE);
        }
    }

    public void onEventMainThread(EventOnGetErrorGetConnections event) {
        Logger.d("ActivityShowConnections : EventOnGetErrorGetConnections is raised");
        layLoading.setVisibility(View.GONE);

        swipeRefreshLayout.setRefreshing(false);
        txtShowMessage.setVisibility(View.VISIBLE);
        txtShowMessage.setText("خطا در دریافت اطلاعات از سرور لطفا دوباره تلاش کنید.");
    }

    public void onEventMainThread(EventOnNoAccessServerResponse event) {
        Logger.d("ActivityShowConnections : EventOnNoAccessServerResponse is raised");
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        getConnectionsFromDB();
    }

    public void onEventMainThread(EventOnErrorInConnectingToServer errorInConnectingToServer) {
        layLoading.setVisibility(View.GONE);
    }


    private void getConnectionsFromDB() {
        connections = new Select().from(Connection.class).orderBy("StartTime DESC").execute();
        adapterConnection.updateList(connections);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    /*-------------------------------------------------------------------------------------------------------------*/

}
