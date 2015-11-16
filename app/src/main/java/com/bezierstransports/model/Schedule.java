package com.bezierstransports.model;

import java.util.Date;

/**
 * Created by Jérémy Pastor on 11/11/2015.
 */
public class Schedule {

    private LineStation lineStation;
    private Period period;
    private Date schedule;

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    public LineStation getLineStation() {
        return lineStation;
    }

    public void setLineStation(LineStation lineStation) {
        this.lineStation = lineStation;
    }

    public Date getSchedule() {
        return schedule;
    }

    public void setSchedule(Date schedule) {
        this.schedule = schedule;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "schedule=" + schedule +
                '}';
    }
}
