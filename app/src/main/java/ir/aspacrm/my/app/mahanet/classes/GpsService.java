package ir.aspacrm.my.app.mahanet.classes;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.activeandroid.query.Select;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import de.greenrobot.event.EventBus;
import ir.aspacrm.my.app.mahanet.G;
import ir.aspacrm.my.app.mahanet.events.EventOnGetLocations;
import ir.aspacrm.my.app.mahanet.model.Locations;
import ir.aspacrm.my.app.mahanet.model.LocationsToSend;


/**
 * Created by HaMiD on 6/16/2016.
 */
public class GpsService extends Service implements LocationListener {
    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;
    boolean canGetLocation = false;
    public boolean isRunning = false;

    GpsParams[] gpsParams;
    boolean gpsProvider = false;
    Location location;

    double latitude = 0;               // latitude
    double longitude = 0;               // longitude

    double lastLatitude = 0;               // last-latitude
    double lastLongitude = 0;               // last-longitude

    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 50;              // 30 meters
    private static final long MIN_TIME_BW_UPDATES = 2 * 1000;   // 1 minute


    private LocationManager locationManager;
    String URL = "";
    static final double _eQuatorialEarthRadius = 6378.1370D;
    static final double _d2r = (Math.PI / 180D);
    int distance;
    DialogClass dialogClass;
    Date date = new Date();
    final String currentDate = date.toString();

    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);


        U.updateLocations();
        try {
            gpsParams = new GpsParams[G.GpsPoints.size()];
            for (int i = 0; i < G.GpsPoints.size(); i++) {
                GpsParams gpsParam = new GpsParams();
                gpsParam.longitude = G.GpsPoints.get(i).getLongitude();
                gpsParam.latitude = G.GpsPoints.get(i).getLatitude();
                gpsParams[i] = gpsParam;
            }
        } catch (Exception e) {
            e.printStackTrace();
//            getLocation();
        }
        getLocation();


    }

    public void onEventMainThread(EventOnGetLocations event) {
        getLocation();
    }

    public Location getLocation() {
        try {
            locationManager = (LocationManager) GpsService.this.getSystemService(LOCATION_SERVICE);
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
            if (!isGPSEnabled && !isNetworkEnabled) {
                if (G.GpsPoints != null && G.GpsPoints.size() != 0) {
                    dialogClass = new DialogClass();
                    if (!G.isFirstCheckGps) {
                        dialogClass.enableGps("برای امتیاز گیری از رویدادها،نیاز است GPS خود را روشن کنید");
                    }
                }
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
            } else {
                this.canGetLocation = true;
                if (isGPSEnabled) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Logger.d("GPSService : GPS Enabled");
                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        Logger.d("GPSService :  Location in GPS " + location);
//                                G.toast("Location in GPS " + location);
                                gpsProvider = true;
                    }
                }
                if (isNetworkEnabled) {
                    Logger.d("GPSService :  IF is Network Enabled");
                    if (location == null) {
                        Logger.d("GPSService :  IF Network location is null ");
                        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Logger.d("GPSService : Network");
                        if (locationManager != null) {
                            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//                                    G.toast("Location in Network " + location);
                                    gpsProvider = true;
                        }
                    } else
                        gpsProvider = true;
                }
                updateGPSCoordinates(location);
            }
            G.isFirstCheckGps = true;
        } catch (Exception e) {
            Logger.d("GPSService : Impossible to connect to LocationManager : " + e);
        }
        return location;
    }

    public void updateGPSCoordinates(Location location) {
        if (location != null) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            if (lastLatitude == 0 && lastLongitude == 0) {
                lastLatitude = latitude;
                lastLongitude = longitude;
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void checkLocation(final double sLat, final double sLon) {
        final DialogClass showMessage = new DialogClass();

        try {
//            G.updateLocations();
                    if (G.GpsPoints != null && G.GpsPoints.size() != 0) {
                        for (int i = 0; i < G.GpsPoints.size(); i++) {
                            Locations location = G.GpsPoints.get(i);
                            distance = getDistanceInM(sLat, sLon, location.getLatitude(), location.getLongitude());
                            if (distance < 100) {

                                WebService.sendAddScoreRequest(location);

//                            if (G.locations != null || G.locations.size() != 0) {
//
////                                dialogClass = new DialogClass();
////                                if (G.isFirstCheckGps) {
////
////                                    ///agar sharayet dsht va namayesh dade nashode bod
////                                    if (G.locations.get(i).isHasConditions() && !G.locations.get(i).isShowInOffline()) {
////                                        final int finalI = i;
////                                        G.currentActivity.runOnUiThread(new Runnable() {
////                                            @Override
////                                            public void run() {
////                                                showMessage.showMessageDialog("تبریک", "امتیاز مربوط به رویداد " + G.locations.get(finalI).getDes() + " پس از اتصال به اینترنت به شما تعلق می گیرد .");
////                                            }
////                                        });
////                                        // baraye inke dialog marbot be yek roidad 2bar namayesh dade nashavad
////                                        new Update(Locations.class)
////                                                .set("isShowInOffline = ?", 1)
////                                                .where("id = ?", G.locations.get(i).getId())
////                                                .execute();
////                                    }
////                                }
//                            }
//                            // agar fasele kamtar az 100 metr bood hasCondition True mishavad
//                            new Update(Locations.class)
//                                    .set("hasConditions = ? ", 1)
//                                    .where("id = ?", G.locations.get(i).getId())
//                                    .execute()
                            }
                        }
                    }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private boolean checkScoreInDey(Locations location) {
        LocationsToSend toSend = new Select().from(LocationsToSend.class).where("scoreTypeCode = " + location.getScoreTypeCode()).executeSingle();
        if (toSend.getDate().equals(currentDate)) { // امتیاز امروز رو گرفته
            return false;
        } else {
            return true;
        }
    }

    private boolean checkDateCondition(Locations location) {
        String startDate = location.getStartDate();
        String endDate = location.getEndDate();

        try {
            DateFormat currentDay = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            DateFormat currentHour = new SimpleDateFormat("hh:mm:ss ", Locale.ENGLISH);
            String nDay = currentDay.format(Calendar.getInstance().getTime());
            String nHour = currentHour.format(Calendar.getInstance().getTime());


            SimpleDateFormat Datee = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            SimpleDateFormat houre = new SimpleDateFormat("hh:mm:ss", Locale.ENGLISH);
            String[] startDay = startDate.split("T");
            String[] endDay = endDate.split("T");


            Date sDay = Datee.parse(nDay);
            Date sHour = houre.parse(nHour);

            Date eDay = Datee.parse(endDay[0]);
            Date eHour = houre.parse(endDay[1]);
            int campareDay = eDay.compareTo(sDay);
            int campareHour = eHour.compareTo(sHour);

            switch (campareDay) {
                case 0:
                    return campareHour > 0;
                case 1:
                    return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

//    private void sendLocations() {
//        if (G.locations != null && G.locations.size() != 0) {
//            for (int i = 0; i < G.locations.size(); i++) {
//                if (G.locations.get(i).isHasConditions()) {
//                    WebService.addLocationScore(G.locations.get(i));
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }
//
//    }


    public static int getDistanceInM(double lat1, double long1, double lat2, double long2) {
        return (int) (1000D * getDistanceInKM(lat1, long1, lat2, long2));
    }

    public static double getDistanceInKM(double lat1, double long1, double lat2, double long2) {

        double dlong = (long2 - long1) * _d2r;
        double dlat = (lat2 - lat1) * _d2r;
        double a = Math.pow(Math.sin(dlat / 2D), 2D) + Math.cos(lat1 * _d2r) * Math.cos(lat2 * _d2r)
                * Math.pow(Math.sin(dlong / 2D), 2D);
        double c = 2D * Math.atan2(Math.sqrt(a), Math.sqrt(1D - a));
        double d = _eQuatorialEarthRadius * c;
        return d;
    }


    @Override
    public void onLocationChanged(Location location) {
//        Toast.makeText(getApplicationContext(), "Latitude>>" + location.getLatitude() + "\nLongitude>>" + location.getLongitude() + "\n<<Sent To>>\n" + URL, Toast.LENGTH_LONG).show();
        checkLocation(location.getLatitude(), location.getLongitude());
//        sendLocations();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

        Logger.d("GPSService : onProviderEnabled");
        Logger.d("onProviderEnabled");
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (isGPSEnabled)
            gpsProvider = true;

    }

    @Override
    public void onProviderDisabled(String provider) {

        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (isNetworkEnabled) {
            locationManager.removeUpdates(GpsService.this);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

            try {
                Handler handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        gpsProvider = false;
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (isGPSEnabled) {
            locationManager.removeUpdates(GpsService.this);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
            Logger.d("GPSService : Change Provider To GPS...");

            Handler handler = new Handler();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    gpsProvider = true;
                }
            });
        } else if (!isGPSEnabled && !isNetworkEnabled) {
            locationManager.removeUpdates(GpsService.this);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
//            Logger.d("GPSService : two provider disable and force provider to Mobile Network");

            gpsProvider = true;
        }

    }

    private class GpsParams {
        public double latitude;
        public double longitude;
        public double distance;
    }
}