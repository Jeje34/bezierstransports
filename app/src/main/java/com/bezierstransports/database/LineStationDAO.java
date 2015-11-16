package com.bezierstransports.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.bezierstransports.BeziersTransports;
import com.bezierstransports.model.Line;
import com.bezierstransports.model.LineStation;
import com.bezierstransports.model.Station;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jérémy Pastor on 10/11/2015.
 */
public final class LineStationDAO {

    private static LineStationDAO lineStationDAO = null;
    private DatabaseHandler dh;

    private LineStationDAO(Context context){
        dh = new DatabaseHandler(context);
    }

    public final static LineStationDAO getLineStationDAO() {
        if (LineStationDAO.lineStationDAO == null) {
            // "synchronized" forbids multiple instantiations in different threads
            synchronized(LineStationDAO .class) {
                if (LineStationDAO.lineStationDAO == null) {
                    LineStationDAO.lineStationDAO = new LineStationDAO(BeziersTransports.getAppContext());
                }
            }
        }
        return LineStationDAO.lineStationDAO;
    }

    public void addLineStation(LineStation lineStation)  {
        SQLiteDatabase db = this.dh.getWritableDatabase();
        ContentValues values = new ContentValues();

        // insert line
        LineDAO.getLineDAO().addLine(lineStation.getLine());

        // insert station
        StationDAO.getStationDAO().addStation(lineStation.getStation());

        // insert lineStation (only if it does not exist)
        if (dh.getCount(DatabaseHandler.TABLE_LINE_STATION, DatabaseHandler.KEY_LINENUMBER + " = ? AND " +
                        DatabaseHandler.KEY_IDSTATION + " = ? AND " + DatabaseHandler.KEY_DIRECTION + " = ?" ,
                new String[]{lineStation.getLine().getLineNumber(), String.valueOf(lineStation.getStation().getId()),
                        lineStation.getDirection()}) == 0) {
            values.put(DatabaseHandler.KEY_LINENUMBER, lineStation.getLine().getLineNumber());
            values.put(DatabaseHandler.KEY_IDSTATION, lineStation.getStation().getId());
            values.put(DatabaseHandler.KEY_DIRECTION, lineStation.getDirection());
            values.put(DatabaseHandler.KEY_ORDRE, lineStation.getOrdre());
            db.insert(DatabaseHandler.TABLE_LINE_STATION, null, values);
            db.close();
        }
    }

    // get the LineStation list that matches with line and direction given
    public ArrayList<LineStation> getLineStations(Line line, String direction) {

        SQLiteDatabase db = this.dh.getReadableDatabase();
        ArrayList<LineStation> lineStationsList = new ArrayList<LineStation>();
        String SELECT_LINE_QUERY = "SELECT * FROM " +
                DatabaseHandler.TABLE_LINE + " l, " + DatabaseHandler.TABLE_LINE_STATION + " ls WHERE l."
                + DatabaseHandler.KEY_LINENUMBER + "=ls." + DatabaseHandler.KEY_LINENUMBER +
                " AND l." + DatabaseHandler.KEY_LINENUMBER + " = ? AND " + DatabaseHandler.KEY_DIRECTION +
                " = ? GROUP BY ls." + DatabaseHandler.KEY_IDSTATION + " ORDER BY " + DatabaseHandler.KEY_ORDRE;
        Cursor cursor = db.rawQuery(SELECT_LINE_QUERY, new String[]{line.getLineNumber(), direction});

        if (cursor.moveToFirst()) {
            do {
                LineStation lineStation = new LineStation();
                lineStation.setLine(LineDAO.getLineDAO().getLine(cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_LINENUMBER))));
                lineStation.setStation(StationDAO.getStationDAO().getStation(cursor.getLong(cursor.getColumnIndex(DatabaseHandler.KEY_IDSTATION))));
                lineStation.setDirection(cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_DIRECTION)));
                lineStation.setOrdre(cursor.getInt(cursor.getColumnIndex(DatabaseHandler.KEY_ORDRE)));
                lineStationsList.add(lineStation);
            } while (cursor.moveToNext());
        }
        return lineStationsList;
    }

    // get the LineStation that matches with line, station, and direction given
    public LineStation getLineStation(Line line, Station station, String direction) {
        SQLiteDatabase db = this.dh.getReadableDatabase();

        Cursor cursor = db.query(DatabaseHandler.TABLE_LINE_STATION,
                new String[]{DatabaseHandler.KEY_LINENUMBER, DatabaseHandler.KEY_IDSTATION, DatabaseHandler.KEY_DIRECTION, DatabaseHandler.KEY_ORDRE},
                DatabaseHandler.KEY_LINENUMBER + " = ? AND " + DatabaseHandler.KEY_IDSTATION + " = ? AND " +
                        DatabaseHandler.KEY_DIRECTION + " = ?",
                new String[]{line.getLineNumber(), String.valueOf(station.getId()), direction}, null, null, null, null);

        if (cursor.moveToFirst()) {
            LineStation ls = new LineStation();
            ls.setLine(LineDAO.getLineDAO().getLine(cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_LINENUMBER))));
            ls.setStation(StationDAO.getStationDAO().getStation(cursor.getLong(cursor.getColumnIndex(DatabaseHandler.KEY_IDSTATION))));
            ls.setDirection(cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_DIRECTION)));
            ls.setOrdre(cursor.getInt(cursor.getColumnIndex(DatabaseHandler.KEY_ORDRE)));
            return ls;
        }
        else return null;
    }

}
