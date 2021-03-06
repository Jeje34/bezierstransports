package com.bezierstransports.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.bezierstransports.BeziersTransports;
import com.bezierstransports.model.Line;
import com.bezierstransports.model.LineStation;
import com.bezierstransports.model.Station;

import java.util.ArrayList;

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

    public void addLineStation(SQLiteDatabase db, LineStation lineStation) {

        // insert line
        LineDAO.getLineDAO().addLine(db, lineStation.getLine());

        // insert station
        StationDAO.getStationDAO().addStation(db, lineStation.getStation());
        String sql = "INSERT OR REPLACE INTO " + DatabaseHandler.TABLE_LINE_STATION + " ( " + DatabaseHandler.KEY_LINENUMBER +
                ", " + DatabaseHandler.KEY_IDSTATION + ", " + DatabaseHandler.KEY_DIRECTION + ", " +
                DatabaseHandler.KEY_ORDRE + ") VALUES (?, ?, ?, ?)";
        SQLiteStatement stmt = db.compileStatement(sql);
        stmt.bindString(1, lineStation.getLine().getLineNumber());
        stmt.bindLong(2, lineStation.getStation().getId());
        stmt.bindString(3, lineStation.getDirection());
        stmt.bindLong(4, lineStation.getOrdre());
        stmt.execute();
        stmt.clearBindings();
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

        try {
            if (cursor.moveToFirst()) {
                LineStation ls = new LineStation();
                ls.setLine(LineDAO.getLineDAO().getLine(cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_LINENUMBER))));
                ls.setStation(StationDAO.getStationDAO().getStation(cursor.getLong(cursor.getColumnIndex(DatabaseHandler.KEY_IDSTATION))));
                ls.setDirection(cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_DIRECTION)));
                ls.setOrdre(cursor.getInt(cursor.getColumnIndex(DatabaseHandler.KEY_ORDRE)));
                return ls;
            } else return null;
        } finally {
            cursor.close();
        }
    }

}
