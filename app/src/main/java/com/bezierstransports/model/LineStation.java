package com.bezierstransports.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Jérémy Pastor on 10/11/2015.
 */
public class LineStation {

    private Line line;
    private Station station;
    private String direction;
    private int ordre;

    public LineStation() { }

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public int getOrdre() {
        return ordre;
    }

    public void setOrdre(int ordre) {
        this.ordre = ordre;
    }


}
