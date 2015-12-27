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
public final class LineDAO {

    private static LineDAO lineDAO = null;
    private DatabaseHandler dh;

    private LineDAO(Context context){
        dh = new DatabaseHandler(context);
    }

    public final static LineDAO getLineDAO() {
        if (LineDAO.lineDAO == null) {
            // "synchronized" forbids multiple instantiations in different threads
            synchronized(LineDAO .class) {
                if (LineDAO.lineDAO == null) {
                    LineDAO.lineDAO = new LineDAO(BeziersTransports.getAppContext());
                }
            }
        }
        return LineDAO.lineDAO;
    }

    // add a bus line into the database
    public void addLine(Line line)  {

        // insert only if the value does not exist
        if (dh.getCount(DatabaseHandler.TABLE_LINE, DatabaseHandler.KEY_LINENUMBER + " = ?",
                new String[]{line.getLineNumber()}) == 0) {
            SQLiteDatabase db = this.dh.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DatabaseHandler.KEY_LINENUMBER, line.getLineNumber());
            values.put(DatabaseHandler.KEY_LINENAME, line.getLineName());
            values.put(DatabaseHandler.KEY_COLOR, line.getColor());
            // insert the bus line in the DB
            db.insert(DatabaseHandler.TABLE_LINE, null, values);
            db.close();
        }
    }

    // get all the bus lines of the database
    public List<Line> getLines() {
        SQLiteDatabase db = this.dh.getReadableDatabase();
        List<Line> linesList = new ArrayList<Line>();

        Cursor cursor = db.query(DatabaseHandler.TABLE_LINE,
                new String[]{DatabaseHandler.KEY_LINENUMBER},
                null, null, null, null, DatabaseHandler.KEY_LINENUMBER + "* 1", null);

        if (cursor.moveToFirst()) {
            do {
                Line line = getLine(cursor.getString(0));
                linesList.add(line);
            } while (cursor.moveToNext());
        }
        return linesList;
    }



    // get the bus line that matches with line number given
    public Line getLine(String lineNumber) {
        SQLiteDatabase db = this.dh.getReadableDatabase();

        Cursor cursor = db.query(DatabaseHandler.TABLE_LINE,
                new String[]{DatabaseHandler.KEY_LINENUMBER, DatabaseHandler.KEY_LINENAME, DatabaseHandler.KEY_COLOR},
                DatabaseHandler.KEY_LINENUMBER + " = ?", new String[]{lineNumber}, null, null, null, null);

        try {
            if (cursor.moveToFirst()) {
                Line line = new Line();
                line.setLineNumber(cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_LINENUMBER)));
                line.setLineName(cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_LINENAME)));
                line.setColor(cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_COLOR)));
                return line;
            } else return null;
        } finally {
            cursor.close();
        }
    }


    // get the bus line and its stations that matches with line number given
    public Line getLineWithStations(String lineNumber, String direction) {

            SQLiteDatabase db = this.dh.getReadableDatabase();
            Line line = getLine(lineNumber);

            String SELECT_LINE_QUERY = "SELECT * FROM " +
                    DatabaseHandler.TABLE_LINE + " l, " + DatabaseHandler.TABLE_LINE_STATION + " ls WHERE l."
                    + DatabaseHandler.KEY_LINENUMBER + "=ls." + DatabaseHandler.KEY_LINENUMBER +
                    " AND l." + DatabaseHandler.KEY_LINENUMBER + " = ? AND " + DatabaseHandler.KEY_DIRECTION +
                    " = ? GROUP BY ls." + DatabaseHandler.KEY_IDSTATION + " ORDER BY " + DatabaseHandler.KEY_ORDRE;
            Cursor cursor = db.rawQuery(SELECT_LINE_QUERY, new String[]{lineNumber, direction});

            if (cursor.moveToFirst()) {
                List<Station> stationsList = new ArrayList<Station>();
                do {
                    Station station = StationDAO.getStationDAO()
                            .getStation(cursor.getLong(cursor.getColumnIndex(DatabaseHandler.KEY_IDSTATION)));
                    stationsList.add(station);
                } while (cursor.moveToNext());
                line.setStations(stationsList);
            }
            return line;
    }


}
