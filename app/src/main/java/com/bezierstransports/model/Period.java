package com.bezierstransports.model;

/**
 * Created by Jérémy Pastor on 11/11/2015.
 */
public class Period {

    private long id;
    private String period;
    private String season;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }
}
