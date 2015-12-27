package com.bezierstransports.service;

import android.content.Context;
import android.util.Log;

import com.bezierstransports.BeziersTransports;
import com.bezierstransports.database.DatabaseHandler;
import com.bezierstransports.database.ScheduleDAO;
import com.bezierstransports.model.LineStation;
import com.bezierstransports.model.Period;
import com.bezierstransports.model.Schedule;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.List;


public class WebService {

    private final String URL = "http://www.petit-fichier.fr/2015/12/27/servlet-3/servlet.json";
    Gson gson;


    public WebService(Context context) {
        this.gson = new GsonBuilder().setDateFormat("HH:mm").create();;
    }


    private InputStream sendRequest(URL url) throws Exception {

        try {
            // open connection and connection to URL
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();

            // test if server OK
            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                return urlConnection.getInputStream();
            }
        } catch (Exception e) {
            Log.e("WebService", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }


    // get bus lines from the JSON file and add them in the database
    public void getLinesAndWriteInDatabase() {
        try {
            // send request
            InputStream inputStream = sendRequest(new URL(URL));
            // check inputStream
            if(inputStream != null) {
                // read inputStream in reader
                InputStreamReader reader = new InputStreamReader(inputStream);
                // return list deserialized by GSON
                Response response = gson.fromJson(reader, Response.class);
                // delete the database
                BeziersTransports.getAppContext().deleteDatabase(DatabaseHandler.DATABASE_NAME);
                // write all objects in database
                for (ScheduleTemp st : response.schedulesArray) {
                    for (Date s : st.schedules) {
                        ScheduleDAO.getScheduleDAO().addSchedule(new Schedule(st.lineStation, st.period, s));
                    }
                }
            }
        } catch (Exception e) {
            Log.e("WebService", e.getMessage());
            e.printStackTrace();
        }
    }

    private class Response {
        public List<ScheduleTemp> schedulesArray;
    }

    private class ScheduleTemp {
        private LineStation lineStation;
        private Period period;
        protected Date[] schedules;
    }

}