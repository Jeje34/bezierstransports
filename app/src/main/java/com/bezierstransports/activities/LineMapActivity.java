package com.bezierstransports.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.AvoidType;
import com.akexorcist.googledirection.constant.Language;
import com.akexorcist.googledirection.constant.Unit;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Leg;
import com.akexorcist.googledirection.model.Route;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.bezierstransports.BeziersTransports;
import com.bezierstransports.R;
import com.bezierstransports.database.StationDAO;
import com.bezierstransports.model.Line;
import com.bezierstransports.model.LineStation;
import com.bezierstransports.model.Station;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

public class LineMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private Line line = null;
    Line busLine = null;
    private LineStation lineStation = null;
    private Station closerStation = null;
    private Location currentLocation = null;
    private ArrayList<Marker> markers = new ArrayList<>();
    private GoogleMap map;
    private LocationManager mLocationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.line_map_activity);

        // get the object line from the last activity
        Intent i = getIntent();
        line = (Line) i.getParcelableExtra("line");
        if (line != null) {
            line.setStations(StationDAO.getStationDAO().getStations(line));
            busLine = line;
        }
        lineStation = (LineStation) i.getParcelableExtra("lineStation");
        if (lineStation != null) {
            lineStation.getLine().setStations(StationDAO.getStationDAO().getStations(lineStation.getLine()));
            busLine = lineStation.getLine();
        }



        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 10.0f, mLocationListener);
        }

        // map
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private Station getCloserStation(List<Station> stations) {
        float[] distanceResult = new float[2];
        Station closer = stations.get(0);
        Location.distanceBetween(currentLocation.getLatitude(), currentLocation.getLongitude(),
                closer.getLatitude(), closer.getLongitude(), distanceResult);
        float distanceMin = distanceResult[0];

        for (Station s : stations) {
            Location.distanceBetween(currentLocation.getLatitude(), currentLocation.getLongitude(),
                    s.getLatitude(), s.getLongitude(), distanceResult);
            if (distanceResult[0] < distanceMin) {
                closer = s;
                distanceMin = distanceResult[0];
            }
        }
        return closer;
    }

    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            currentLocation = location;
            if (line != null) {
                closerStation = getCloserStation(line.getStations());
                drawDirection(location, closerStation);
            } else if (lineStation != null) {
                drawDirection(location, lineStation.getStation());
            }
        }

        public void onStatusChanged (String provider, int status, Bundle extras) {  }
        public void onProviderEnabled (String provider) { }
        public void onProviderDisabled (String provider) {}
    };


    @Override
    public void onMapReady(final GoogleMap retMap) {

        this.map = retMap;

        List<Station> stations = new ArrayList<Station>();
        if (line != null) {
            stations = line.getStations();
            drawMarkers(stations);
            // zoom and move camera to see all stations of the line
            map.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
                public void onMapLoaded() {
                    LatLngBounds.Builder builder = new LatLngBounds.Builder();
                    for (Marker marker : markers) {
                        builder.include(marker.getPosition());
                    }
                    LatLngBounds bounds = builder.build();
                    int padding = 0;
                    CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
                    map.moveCamera(cu);
                }
            });
        } else if (lineStation != null) {
            stations = lineStation.getLine().getStations();
            drawMarkers(stations);
            // zoom on station
            CameraPosition cameraPosition = new CameraPosition.Builder().target(
                    new LatLng(lineStation.getStation().getLatitude(), lineStation.getStation().getLongitude()))
                    .zoom(17).build();
            map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);
        }

    }


    private void drawMarkers(List<Station> stations) {
        BitmapDescriptor bd = null;
        for (Station station : stations) {
            // select color of marker and draw line
            if (busLine.getLineNumber().equals("1")) {
                drawLine1(busLine.getColor());
                bd = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN);
            }

            /* add markers for each station of the line on the map
            and save marker in a list */
            markers.add(map.addMarker(new MarkerOptions()
                    .position(new LatLng(station.getLatitude(), station.getLongitude()))
                    .title(station.getStationName())
                    .icon(bd)));
        }
    }


    // draw direction between current location and
    private void drawDirection(Location current, Station station) {
        GoogleDirection.withServerKey("AIzaSyDkjdEZ74pmQFy5D3F6-YKE84KChZtAt6Y")
            .from(new LatLng(current.getLatitude(), current.getLongitude()))
            .to(new LatLng(station.getLatitude(), station.getLongitude()))
                .unit(Unit.METRIC)
                .language(Language.FRENCH)
                .avoid(AvoidType.FERRIES)
                .avoid(AvoidType.HIGHWAYS)
                .execute(new DirectionCallback() {
                    public void onDirectionSuccess(Direction direction) {
                        if (direction.isOK()) {
                            Route route = direction.getRouteList().get(0);
                            Leg leg = route.getLegList().get(0);
                            ArrayList<LatLng> directionPositionList = leg.getDirectionPoint();
                            PolylineOptions polylineOptions = DirectionConverter.createPolyline(BeziersTransports.getAppContext(),
                                    directionPositionList, 5, Color.RED);
                            map.addPolyline(polylineOptions);

                        } else {
                            // Do something
                        }
                    }

                    public void onDirectionFailure(Throwable t) {
                    }
            });
    }


    private void drawLine1(String color) {
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
                .color(Color.parseColor(color))
                .geodesic(true);

        map.addPolyline(rectOptions);
    }
}
