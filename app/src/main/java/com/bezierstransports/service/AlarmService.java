package com.bezierstransports.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.bezierstransports.alarms.Alarm;

public class AlarmService extends Service {
    Alarm alarm = new Alarm();

    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        alarm.SetAlarm(this);
        return START_STICKY;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        alarm.SetAlarm(this);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}