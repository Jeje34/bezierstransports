package com.bezierstransports.service;

import android.content.Context;
import android.util.Log;

import com.bezierstransports.BeziersTransports;
import com.bezierstransports.database.DatabaseHandler;
import com.bezierstransports.database.LineDAO;
import com.bezierstransports.database.LineStationDAO;
import com.bezierstransports.database.ScheduleDAO;
import com.bezierstransports.database.StationDAO;
import com.bezierstransports.model.Line;
import com.bezierstransports.model.LineStation;
import com.bezierstransports.model.Schedule;
import com.bezierstransports.model.Station;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.HttpURLConnection;
import java.util.List;


public class WebService {

    //private final String URL = "http://www.petit-fichier.fr/2015/11/14/bezierstransports-1/bezierstransports.json";
    private final String URL = "http://www.petit-fichier.fr/2015/12/10/bezierstransport/bezierstransport.json";
    Gson gson;


    public WebService(Context context) {
        this.gson = new GsonBuilder().setDateFormat("hh:mm").create();;
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
                for (Schedule h : response.schedules) {
                    ScheduleDAO.getScheduleDAO().addSchedule(h);
                }
            }
        } catch (Exception e) {
            Log.e("WebService", e.getMessage());
            e.printStackTrace();
        }
    }


    private class Response {
        /*
        @SerializedName("lines") public List<Line> mLines;
        @SerializedName("stations") public List<Station> mStations;
        @SerializedName("linesStations") public List<LineStation> mLinesStations;
        */
        public List<Schedule> schedules;
    }

}