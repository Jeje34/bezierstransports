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
import com.bezierstransports.database.LineStationDAO;
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
    private Line busLine = null;
    private ArrayList<LineStation> listLineStationA;
    private ArrayList<LineStation> listLineStationR;
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
        listLineStationA = LineStationDAO.getLineStationDAO().getLineStations(busLine, "A");
        listLineStationR = LineStationDAO.getLineStationDAO().getLineStations(busLine, "R");

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
            drawLine(listLineStationA, busLine.getColor());
            drawLine(listLineStationR, busLine.getColor());
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
            switch (busLine.getLineNumber()) {
                case "1":
                    bd = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN);
                    break;
                case "2":
                    bd = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE);
                    break;
                default:
                    bd = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);
            }

//            List<Schedule> next3Departures = ScheduleDAO.getScheduleDAO().get3NextDepartures(result.get(i));
            /* add markers for each station of the line on the map
            and save marker in a list */

            Marker m = map.addMarker(new MarkerOptions()
                            .position(new LatLng(station.getLatitude(), station.getLongitude()))
                            .title(station.getStationName())
                            .icon(bd));
            markers.add(m);
            markers.add(map.addMarker(new MarkerOptions()
                    .position(new LatLng(station.getLatitude(), station.getLongitude()))
                    .title(station.getStationName())
                    .snippet(getString(R.string.next_departure))
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


    private void drawLine(ArrayList<LineStation> listLineStation, final String color) {
        for (int i = 0; i < listLineStation.size() - 1; i++) {
            GoogleDirection.withServerKey("AIzaSyDkjdEZ74pmQFy5D3F6-YKE84KChZtAt6Y")
                    .from(new LatLng(listLineStation.get(i).getStation().getLatitude(),
                            listLineStation.get(i).getStation().getLongitude()))
                    .to(new LatLng(listLineStation.get(i + 1).getStation().getLatitude(),
                            listLineStation.get(i + 1).getStation().getLongitude()))
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
                                        directionPositionList, 5, Color.parseColor(color));
                                map.addPolyline(polylineOptions);

                            } else {
                                // Do something
                            }
                        }

                        public void onDirectionFailure(Throwable t) {
                        }
                    });
        }
    }
}
