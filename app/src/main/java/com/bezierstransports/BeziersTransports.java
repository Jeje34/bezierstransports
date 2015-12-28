package com.bezierstransports;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

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


    private SensorManager mSensorManager;
    private ShakeEventListener mSensorListener;


    public void onCreate() {
        super.onCreate();
        BeziersTransports.context = getApplicationContext();
        BeziersTransports.scheduleFormat = new SimpleDateFormat("HH:mm");
        BeziersTransports.updateTime();

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorListener = new ShakeEventListener();
        mSensorListener.setOnShakeListener(new ShakeEventListener.OnShakeListener() {
            public void onShake() {
                // shake => close application
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        mSensorManager.registerListener(mSensorListener,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_UI);
    }

    public void onTerminate() {
        super.onTerminate();
        mSensorManager.unregisterListener(mSensorListener);
    }


    public static void updateTime() {
        BeziersTransports.now = new Date();
        BeziersTransports.dayFormat = new SimpleDateFormat("EEEE");
        try {
            BeziersTransports.time_now = scheduleFormat.parse(scheduleFormat.format(now));
            BeziersTransports.day = dayFormat.parse(dayFormat.format(now));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static Context getAppContext() {
        return BeziersTransports.context;
    }

    public static SimpleDateFormat getScheduleFormat() {
        return BeziersTransports.scheduleFormat;
    }

    public static SimpleDateFormat getDayFormat() {
        return BeziersTransports.dayFormat;
    }

    public static Date getDateNow() {
        return BeziersTransports.now;
    }

    public static Date getTimeNow() {
        return BeziersTransports.time_now;
    }

    public static Date getDay() {
        return BeziersTransports.day;
    }

    public static void initActionBar(AppCompatActivity activity, String colorActionStatusBar) {
        // if version used is newer that Lollipop (>= 5.0 API 21)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // change color of the top bar
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.parseColor(colorActionStatusBar));

            android.support.v7.app.ActionBar bar = activity.getSupportActionBar();
            bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(colorActionStatusBar)));
            bar.setDisplayShowTitleEnabled(false);
            bar.setDisplayShowTitleEnabled(true);

            //bar.setIcon(R.drawable.logo_beziers_transports);
            //bar.setDisplayShowHomeEnabled(true);
        }

    }

}