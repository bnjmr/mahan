package ir.aspacrm.my.app.mahanet;

import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import ir.aspacrm.my.app.mahanet.classes.DialogClass;
import ir.aspacrm.my.app.mahanet.classes.Logger;
import ir.aspacrm.my.app.mahanet.events.EventOnAddScoreResponse;
import ir.aspacrm.my.app.mahanet.model.Locations;

import static ir.aspacrm.my.app.mahanet.R.id.map;

public class ActivityPoints extends AppCompatActivity implements
        OnMapReadyCallback,
        GoogleMap.OnMyLocationButtonClickListener,
        ActivityCompat.OnRequestPermissionsResultCallback {

    private GoogleMap mMap;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;
    boolean isFindMyLocation = false;
    private LocationManager locationManager;
    private DialogClass dialogClass;

    LinearLayout layBtnBack;
    LinearLayout layFragment;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_points);
        isFindMyLocation = false;
        locationManager = (LocationManager) ActivityPoints.this.getSystemService(LOCATION_SERVICE);
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        layBtnBack = (LinearLayout) findViewById(R.id.layBtnBack);
        layFragment = (LinearLayout) findViewById(R.id.layFragment);
        layBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(map);
        mapFragment.getMapAsync(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
        G.context = this;
        G.currentActivity = this;
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        mMap.setOnMyLocationButtonClickListener(this);
        enableMyLocation();

        Locations locations0 = new Locations();
        locations0.setLatitude(29.680345);
        locations0.setLatitude(52.513475);

        Locations locations1 = new Locations();
        locations0.setLatitude(29.679533);
        locations0.setLatitude(52.498048);

        Locations locations2 = new Locations();
        locations0.setLatitude(29.680781);
        locations0.setLatitude(52.493663);


        List<Locations> list = new ArrayList<>();
        list.add(locations0);
        list.add(locations1);
        list.add(locations2);

        if (G.GpsPoints != null)
            if (G.GpsPoints.size() != 0) {

                for (int i = 0; i < G.GpsPoints.size(); i++) {

                    Locations location = G.GpsPoints.get(i);
                    String data = location.getScore() + "امتیاز : " + "تاریخ شروع : " + location.getStartDate();
                    Logger.d("ActivityPoints : G.GpsPoints " + i + " is  " + data);

                    Marker markerr = googleMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(location.getLatitude(), location.getLongitude())))
//                            .title(location.getDes())
//                            .snippet(data))
                            ;
                    markerr.setTag(i);


                    googleMap.setMyLocationEnabled(true);


                }

                googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        int position = (int) (marker.getTag());
                        DialogClass.showPointsMessageDialog(G.GpsPoints.get(position).getName(),G.GpsPoints.get(position).getDes());
                        return false;
                    }
                });

                googleMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                    @Override
                    public void onMyLocationChange(Location location) {
                        if (!isFindMyLocation) {
                            LatLngBounds.Builder builder = new LatLngBounds.Builder();
                            for (Locations markers : G.GpsPoints) {
                                builder.include(new LatLng(markers.getLatitude(), markers.getLongitude()));
                            }
                            builder.include(new LatLng(location.getLatitude(), location.getLongitude()));
                            LatLngBounds bounds = builder.build();
                            int padding = 150; // offset from edges of the map in pixels
                            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
                            mMap.animateCamera(cu);


//                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(G.GpsPoints.get(0).getLatitude(), G.GpsPoints.get(0).getLongitude()), 15.0f));
                            isFindMyLocation = true;

                        } else {
                            float zoom = mMap.getCameraPosition().zoom;
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), zoom));

                        }
                    }

                });
//                try {
//                    LatLngBounds.Builder builder = new LatLngBounds.Builder();
//                    for (Locations markers : G.GpsPoints) {
//                        builder.include(new LatLng(markers.getLatitude(), markers.getLongitude()));
//                    }
//                    LatLngBounds bounds = builder.build();
//                    int padding = 100; // offset from edges of the map in pixels
//                    CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
//                    mMap.animateCamera(cu);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }


            } else {
                dialogClass.showMessageDialog("", "برای شما رویدادی ثبت نشده است");
                layFragment.setVisibility(View.INVISIBLE);
            }
//        googleMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }


    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private void enableMyLocation() {

        if (!isGPSEnabled && !isNetworkEnabled) {
            if (G.GpsPoints != null && G.GpsPoints.size() != 0) {
                dialogClass = new DialogClass();
                if (!G.isFirstCheckGps) {
                    dialogClass.enableGps(" برای مشاهده موقعیت خود نسبت به رویداد ها لازم است GPS خود را روشن کنید");
                }
            }
        } else {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
        }


//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED) {
//            // Permission to access the location is missing.
//            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
//                    Manifest.permission.ACCESS_FINE_LOCATION, true);
//        } else if (mMap != null) {
//            // Access to the location has been granted to the app.
//            mMap.setMyLocationEnabled(true);
//        }
    }

    @Override
    public boolean onMyLocationButtonClick() {

        return false;
    }


    public void onEventMainThread(EventOnAddScoreResponse event) {
        Intent intent = new Intent(ActivityPoints.this, ActivityPoints.class);
        startActivity(intent);
    }
}
