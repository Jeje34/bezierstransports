package com.bezierstransports;

import android.app.Application;
import android.content.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Jérémy Pastor on 12/11/2015.
 */

// CLASS APPLICATION
public class BeziersTransports extends Application {

    // BeziersTransports.getAppContext() to get application context statically every where
    private static Context context;

    // BeziersTransports.getScheduleFormat() to get schedule format statically every where
    private static SimpleDateFormat scheduleFormat;

    private static SimpleDateFormat dayFormat;

    // BeziersTranspors.getDateNow() to get current date
    private static Date now;

    // BeziersTranspors.getDateNow() to get time (ex: 15:12)
    private static Date time_now;

    // BeziersTranspors.getDay() to get day (ex: "Mer")
    private static Date day;



    public void onCreate(){
        super.onCreate();
        BeziersTransports.context = getApplicationContext();
        BeziersTransports.scheduleFormat = new SimpleDateFormat("HH:mm");
        BeziersTransports.updateTime();
    }

    public static void updateTime() {
        BeziersTransports.now = new Date();
        BeziersTransports.dayFormat = new SimpleDateFormat("EEEE");
        try {
            BeziersTransports.time_now = scheduleFormat.parse(scheduleFormat.format(now));
            BeziersTransports.day = dayFormat.parse(dayFormat.format(now));
        }  catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static Context getAppContext() {
        return BeziersTransports.context;
    }

    public static SimpleDateFormat getScheduleFormat() { return BeziersTransports.scheduleFormat; }

    public static SimpleDateFormat getDayFormat() { return BeziersTransports.dayFormat; }

    public static Date getDateNow() { return BeziersTransports.now; }

    public static Date getTimeNow() { return BeziersTransports.time_now; }

    public static Date getDay () { return BeziersTransports.day; }
}