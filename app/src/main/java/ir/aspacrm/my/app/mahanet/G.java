package ir.aspacrm.my.app.mahanet;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.os.Handler;
import android.support.multidex.MultiDexApplication;
import android.support.v7.app.AppCompatActivity;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import com.activeandroid.util.ReflectionUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.time4j.android.ApplicationStarter;

import java.lang.reflect.Modifier;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.List;

import de.greenrobot.event.EventBus;
import ir.aspacrm.my.app.mahanet.classes.CheckNotification;
import ir.aspacrm.my.app.mahanet.classes.DialogClass;
import ir.aspacrm.my.app.mahanet.classes.GpsService;
import ir.aspacrm.my.app.mahanet.classes.Logger;
import ir.aspacrm.my.app.mahanet.classes.U;
import ir.aspacrm.my.app.mahanet.enums.EnumResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnAddScoreResponse;
import ir.aspacrm.my.app.mahanet.events.EventOnExKeyMistake;
import ir.aspacrm.my.app.mahanet.model.Account;
import ir.aspacrm.my.app.mahanet.model.Info;
import ir.aspacrm.my.app.mahanet.model.License;
import ir.aspacrm.my.app.mahanet.model.Locations;
import ir.aspacrm.my.app.mahanet.model.User;

/**
 * Created by Microsoft on 3/1/2016.
 */
public class G extends MultiDexApplication {
    public static Context context;
    public static final Handler handler = new Handler();
    public static User currentUser;
    public static License currentLicense;
    public static Account currentAccount;
    public static Info currentUserInfo;
    public static AppCompatActivity currentActivity;
    public static SharedPreferences localMemory;
    public static int categorySize;
    public static CheckNotification checkNotification;
    public static long customerId;

    //    public static List<Locations> locations;
    public static boolean isFirstCheckGps = false;
    public static boolean isTurnOnsGpsDialogShowedInOfflineode = false;

    public static List<Locations> GpsPoints;


    /**
     * static field
     */
    public static final String DIR_APP_DOWNLOAD_FOLDER = Environment.getExternalStorageDirectory() + "/ASPA";
    public static final long NOTIFICATION_CHECKER_TIME = 20 * 60 * 1000;
    //    public static final long NOTIFICATION_CHECKER_TIME = 30 * 1000;
    public static final String JMWS = "http://mng.aspacrm.ir/service.aspx"; // WebService Jahanmir
    public static final String WS_PAGE = "/aspamobile.aspx";
    public static final String Api = "http://185.179.168.5:700/api/";
    public static final NumberFormat formatterPrice = new DecimalFormat("#,###,###,###");
    public static Gson gson;
    public static String FB_SHARED_PREF;
    public static boolean hasImage = false;
    public static String versionName = BuildConfig.VERSION_NAME;
    public static int versionCode = BuildConfig.VERSION_CODE;
    public static boolean isCheckUpdate = false;
    private static String update = "update2";


    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        ApplicationStarter.initialize(this, true); // with prefetch on background thread

        /** initialize database*/
        initDb();

        EventBus.getDefault().register(this);
//        updateLocations();

        /** set SharedPreferences*/
        localMemory = getSharedPreferences("LOCAL", MODE_PRIVATE);

        /** get current User*/
        currentUser = new Select().from(User.class).where("isLogin = ? ", true).executeSingle();
        if (currentUser == null)
            currentUser = new User();

        try {
            currentUserInfo = new Select().from(Info.class).executeSingle();
        } catch (Exception e) {
            e.printStackTrace();
        }


        /** get customerId */
        customerId = getResources().getInteger(R.integer.customer_id);
//        if(customerId != 0)
//            WebService.sendGetIspUrlRequest(customerId);
//        else {
//           User lastLoginUser = new Select().from(User.class).where("isLastLogin = ? ", true).executeSingle();
//            if(lastLoginUser != null)
//                WebService.sendGetIspUrlRequest(lastLoginUser.ispId);
//        }

        U.overrideFont(getApplicationContext(), "MONOSPACE", "fonts/biran.ttf");
        U.overrideFont(getApplicationContext(), "SERIF", "fonts/iransans_medium.ttf");
        U.overrideFont(getApplicationContext(), "SANS_SERIF", "fonts/sl.ttf");

        Logger.d("G : values folder is " + getResources().getString(R.string.values_folder));

        checkNotification = new CheckNotification();
        checkNotification.SetRepeatAlarm(69, Calendar.getInstance().getTimeInMillis() + NOTIFICATION_CHECKER_TIME, NOTIFICATION_CHECKER_TIME);


        buildGSON();


    }

    private void initDb() {
        SharedPreferences prefs = this.getSharedPreferences(
                G.update, Context.MODE_PRIVATE);

        String up = prefs.getString(G.update, "false");
        if (G.versionCode > 30 && up.equals("false")) {
            deleteDb();
            prefs.edit().putString(G.update, "true").apply();
        } else {
            ActiveAndroid.initialize(this);
        }
    }

    public void deleteDb() {
//        ActiveAndroid.initialize(this);

//        ActiveAndroid.dispose();

        String aaName = ReflectionUtils.getMetaData(getApplicationContext(), "AA_DB_NAME");

        if (aaName == null) {
            aaName = "Application.db";
        }

        deleteDatabase(aaName);
        ActiveAndroid.initialize(this);
    }


    private void buildGSON() {
        GsonBuilder builder = new GsonBuilder();
        builder.excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC);
        gson = builder.create();
    }

    public static void startGpsService() {
        try {
            Intent i = new Intent(G.context, GpsService.class);
            G.context.startService(i);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void stopGpsService() {
        try {
            Intent i = new Intent(G.context, GpsService.class);
            G.context.stopService(i);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

//    public static void updateLocations() {
//        try {
//
//            locations = new Select(new String[]{"Id,code,Latitude,Longitude,startDate,endDate,scoreTypeCode,hasConditions"}).from(Locations.class).execute();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public void onEventMainThread(EventOnAddScoreResponse event) {
        DialogClass showMessage = new DialogClass();

        if (event.getResponse().isResult() == EnumResponse.OK) {
            Locations locations = event.getLocation();
            if (event.getResponse().getErr() == 1) {
                showMessage.showMessageDialog(" ", "امتیاز مربوط به رویداد " + event.getResponse().getName() + " با موفقیت ثبت شد ");
            } else {
                G.GpsPoints.remove(event.getLocation());

//                if (event.getResponse().getMessage() != null)
//                    showMessage.showMessageDialog(" ", event.getResponse().getMessage());
            }
//            switch (event.getResponse().getErr()) {
//                case 1:// اوکی
//                    showMessage.showMessageDialog(" ", "امتیاز مربوط به رویداد " + event.getResponse().getName() + " با موفقیت ثبت شد ");
//                    try {
//                        G.GpsPoints.remove(locations);
//                        EventBus.getDefault().post(new EventOnAddScoreResponse());
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    break;
//
//                case 0://  مشکل تعداد در روز یا ماه
//                case 2:// مشکل در زمان
//                case 3:// مشکل در گروه
////                    new Delete().from(Locations.class)
////                            .where("id = ?", event.getLocation().getCode())
////                            .execute();
////                    G.updateLocations();
//                    break;
//            }

        }
    }

    public void onEventMainThread(EventOnExKeyMistake mistake) {
        currentUser.isLogin = false;
        currentUser.save();
        context.startActivity(new Intent(context, ActivityLogin.class));
        currentActivity.finish();
    }


}
