package com.bezierstransports.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bezierstransports.BeziersTransports;
import com.bezierstransports.model.Line;
import com.bezierstransports.model.Station;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jérémy Pastor on 10/11/2015.
 */

// use pattern singleton
public final class StationDAO {

    private static StationDAO stationDAO = null;
    private DatabaseHandler dh;

    private StationDAO(Context context){
        dh = new DatabaseHandler(context);
    }

    public final static StationDAO getStationDAO() {
        if (StationDAO.stationDAO == null) {
            // "synchronized" forbids multiple instantiations in different threads
            synchronized(StationDAO .class) {
                if (StationDAO.stationDAO == null) {
                    StationDAO.stationDAO = new StationDAO(BeziersTransports.getAppContext());
                }
            }
        }
        return StationDAO.stationDAO;
    }


    // add a bus station into the database
    public void addStation(Station station)  {
        // insert only if the value does not exist
        if (dh.getCount(DatabaseHandler.TABLE_STATION, DatabaseHandler.KEY_ID + " = ? ", new String[]{String.valueOf(station.getId())}) == 0) {
            SQLiteDatabase db = this.dh.getWritableDatabase();

            //insert city
            CityDAO.getCityDAO().addCity(station.getCity());

            ContentValues values = new ContentValues();
            values.put(DatabaseHandler.KEY_STATIONNAME, station.getStationName());
            values.put(DatabaseHandler.KEY_LATITUDE, station.getLatitude());
            values.put(DatabaseHandler.KEY_LONGITUDE, station.getLongitude());
            values.put(DatabaseHandler.KEY_IDCITY, station.getCity().getId());

            // insert the bus station in the DB and get ID
            station.setId(db.insert(DatabaseHandler.TABLE_STATION, null, values));

            db.close();
        }
    }

    // get the stations list of the line given
    public List<Station> getStations(Line line) {

        SQLiteDatabase db = this.dh.getReadableDatabase();
        List<Station> stationsList = new ArrayList<Station>();

        String SELECT_STATIONS_QUERY = "SELECT * FROM " +
                DatabaseHandler.TABLE_STATION + " s, " + DatabaseHandler.TABLE_LINE_STATION + " ls WHERE s."
                + DatabaseHandler.KEY_ID + "=ls." + DatabaseHandler.KEY_IDSTATION + " AND ls." + DatabaseHandler.KEY_LINENUMBER +
                " = ?";/* GROUP BY s." + DatabaseHandler.KEY_ID*/;
        Cursor cursor = db.rawQuery(SELECT_STATIONS_QUERY, new String[]{line.getLineNumber()});

        if (cursor.moveToFirst()) {
            do {
                Station station = getStation(cursor.getInt(cursor.getColumnIndex(DatabaseHandler.KEY_IDSTATION)));
                stationsList.add(station);
            } while (cursor.moveToNext());
        }
        return stationsList;
    }

    // get the bus station that matches with ID given
    public Station getStation(long id) {
        SQLiteDatabase db = this.dh.getReadableDatabase();

        Cursor cursor = db.query(DatabaseHandler.TABLE_STATION,
                new String[]{DatabaseHandler.KEY_ID, DatabaseHandler.KEY_STATIONNAME,
                        DatabaseHandler.KEY_LATITUDE, DatabaseHandler.KEY_LONGITUDE, DatabaseHandler.KEY_IDCITY},
                DatabaseHandler.KEY_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor.moveToFirst()) {
            Station station = new Station();
            station.setId(cursor.getLong(cursor.getColumnIndex(DatabaseHandler.KEY_ID)));
            station.setStationName(cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_STATIONNAME)));
            station.setLatitude(cursor.getFloat(cursor.getColumnIndex(DatabaseHandler.KEY_LATITUDE)));
            station.setLongitude(cursor.getFloat(cursor.getColumnIndex(DatabaseHandler.KEY_LONGITUDE)));
            station.setCity(CityDAO.getCityDAO().getCity(cursor.getLong(cursor.getColumnIndex(DatabaseHandler.KEY_IDCITY))));
            return station;
        }
        else return null;
    }


    // get the bus station and its lines that matches with ID given
    public Station getStationWithLines(long id) {

        SQLiteDatabase db = this.dh.getReadableDatabase();
        Station station = getStation(id);

        String SELECT_STATION_QUERY = "SELECT * FROM " +
                DatabaseHandler.TABLE_STATION + " s, " + DatabaseHandler.TABLE_LINE_STATION + " ls WHERE s."
                + DatabaseHandler.KEY_ID + "=ls." + DatabaseHandler.KEY_IDSTATION +
                " AND s." + DatabaseHandler.KEY_ID + " = ? GROUP BY s." + DatabaseHandler.KEY_ID;
        Cursor cursor = db.rawQuery(SELECT_STATION_QUERY, new String[]{String.valueOf(id)});

        if (cursor.moveToFirst()) {
            List<Line> linesList = new ArrayList<Line>();
            do {
                Line line = LineDAO.getLineDAO().getLine(cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_LINENUMBER)));
                linesList.add(line);
            } while (cursor.moveToNext());
            station.setLines(linesList);
        }
        return station;
    }

}
