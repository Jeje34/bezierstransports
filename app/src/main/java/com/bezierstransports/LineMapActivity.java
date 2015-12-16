package com.bezierstransports;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.bezierstransports.database.StationDAO;
import com.bezierstransports.model.Line;
import com.bezierstransports.model.Station;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public class LineMapActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private Line line;
    private ArrayList<Marker> markers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.line_map_activity);


        // get the object line from the last activity
        Intent i = getIntent();
        line = (Line) i.getParcelableExtra("line");
        line.setStations(StationDAO.getStationDAO().getStations(line));

        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera.
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        for (Station station : line.getStations()) {

            /* add markers for each station of the line on the map
            and save marker in a list */
            markers.add(mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(station.getLatitude(), station.getLongitude()))
                    .title(station.getStationName())));

            // zoom and move camera to see all the markers
            mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {

                public void onMapLoaded() {
                    LatLngBounds.Builder builder = new LatLngBounds.Builder();
                    for (Marker marker : markers) {
                        builder.include(marker.getPosition());
                    }
                    LatLngBounds bounds = builder.build();
                    int padding = 0;
                    CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
                    mMap.moveCamera(cu);
                }
            });
        }

        drawLine();
    }

    private void drawLine() {
        PolylineOptions rectOptions = new PolylineOptions()
                .add(new LatLng(43.34576, 3.217365))
                .add(new LatLng(43.345954, 3.216877))
                .add(new LatLng(43.348932, 3.220735))
                .add(new LatLng(43.350356, 3.218777))
                .add(new LatLng(43.350904, 3.219306))
                .add(new LatLng(43.351731, 3.220312))
                .add(new LatLng(43.353076, 3.221369))
                .add(new LatLng(43.355371, 3.222028))
                .add(new LatLng(43.356609, 3.221757))
                .add(new LatLng(43.357030, 3.222071))
                .add(new LatLng(43.357773, 3.224026))
                .add(new LatLng(43.358372, 3.224686))
                .add(new LatLng(43.358975, 3.224874))
                .add(new LatLng(43.359282, 3.224812))
                .add(new LatLng(43.360007, 3.225461))
                .add(new LatLng(43.359614, 3.226691))
                .add(new LatLng(43.358185, 3.225621))
                .add(new LatLng(43.355104, 3.226459))
                .add(new LatLng(43.355143, 3.226598))
                .add(new LatLng(43.355880, 3.227161))
                .add(new LatLng(43.357128, 3.227805))
                .add(new LatLng(43.361153, 3.230895))
                .add(new LatLng(43.360295, 3.233663))
                .add(new LatLng(43.359577, 3.234275 ))
                .add(new LatLng(43.359273, 3.234699))
                .add(new LatLng(43.360178, 3.235992))
                .add(new LatLng(43.361274, 3.235461))
                .add(new LatLng(43.361929, 3.234324))
                .add(new LatLng(43.361952, 3.232994))
                .add(new LatLng(43.362395, 3.231827))
                .width(5)
                .color(Color.BLUE)
                .geodesic(true);

        Polyline polyline = mMap.addPolyline(rectOptions);


    }
}
