package com.bezierstransports.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jérémy Pastor on 10/11/2015.
 */
public class Station {

    private long id;
    private String stationName;
    private float latitude;
    private float longitude;
    private City city;
    List<Line> lines = new ArrayList<Line>();

    public Station() { }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public List<Line> getLines() {
        return lines;
    }

    public void setLines(List<Line> lines) {
        this.lines = lines;
    }

    @Override
    public String toString() {
        return "Station{" +
                "longitude=" + longitude +
                ", latitude=" + latitude +
                ", stationName='" + stationName + '\'' +
                ", id=" + id +
                '}';
    }
}
