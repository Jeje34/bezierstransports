package com.bezierstransports.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.bezierstransports.BeziersTransports;
import com.bezierstransports.model.LineStation;
import com.bezierstransports.model.Schedule;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jérémy Pastor on 11/11/2015.
 */
public class ScheduleDAO {



    private static ScheduleDAO scheduleDAO = null;
    private DatabaseHandler dh;


    private ScheduleDAO(Context context){
        dh = new DatabaseHandler(context);
    }

    public final static ScheduleDAO getScheduleDAO() {
        if (ScheduleDAO.scheduleDAO == null) {
            // "synchronized" forbids multiple instantiations in different threads
            synchronized(ScheduleDAO .class) {
                if (ScheduleDAO.scheduleDAO == null) {
                    ScheduleDAO.scheduleDAO = new ScheduleDAO(BeziersTransports.getAppContext());
                }
            }
        }
        return ScheduleDAO.scheduleDAO;
    }

    public void addSchedules(ArrayList<Schedule> schedulesList) {
        SQLiteDatabase db = this.dh.getWritableDatabase();
        db.beginTransactionNonExclusive();

        for (Schedule schedule : schedulesList) {

            // insert line and station
            LineStationDAO.getLineStationDAO().addLineStation(db, schedule.getLineStation());

            // insert period
            PeriodDAO.getPeriodDAO().addPeriod(db, schedule.getPeriod());

            String sql = "INSERT OR REPLACE INTO " + DatabaseHandler.TABLE_SCHEDULE + " ( " + DatabaseHandler.KEY_LINENUMBER +
                    ", " + DatabaseHandler.KEY_IDSTATION + ", " + DatabaseHandler.KEY_DIRECTION + ", " +
                    DatabaseHandler.KEY_IDPERIOD + ", " + DatabaseHandler.KEY_SCHEDULE + ") VALUES (?, ?, ?, ?, ?)";
            SQLiteStatement stmt = db.compileStatement(sql);
            stmt.bindString(1, schedule.getLineStation().getLine().getLineNumber());
            stmt.bindLong(2, schedule.getLineStation().getStation().getId());
            stmt.bindString(3, schedule.getLineStation().getDirection());
            stmt.bindLong(4, schedule.getPeriod().getId());
            stmt.bindString(5, BeziersTransports.getScheduleFormat().format(schedule.getSchedule()));
            stmt.execute();
            stmt.clearBindings();
        }

        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }

    /*public void addSchedule(Schedule schedule)  {
        SQLiteDatabase db = this.dh.getWritableDatabase();
        ContentValues values = new ContentValues();

        // insert line and station
        LineStationDAO.getLineStationDAO().addLineStation(schedule.getLineStation());

        // insert period
        PeriodDAO.getPeriodDAO().addPeriod(schedule.getPeriod());

        values.put(DatabaseHandler.KEY_LINENUMBER, schedule.getLineStation().getLine().getLineNumber());
        values.put(DatabaseHandler.KEY_IDSTATION, schedule.getLineStation().getStation().getId());
        values.put(DatabaseHandler.KEY_DIRECTION, schedule.getLineStation().getDirection());
        values.put(DatabaseHandler.KEY_IDPERIOD, schedule.getPeriod().getId());
        values.put(DatabaseHandler.KEY_SCHEDULE, BeziersTransports.getScheduleFormat().format(schedule.getSchedule()));

        // insert the schedule in the DB
        db.insert(DatabaseHandler.TABLE_SCHEDULE, null, values);

    }*/


    public List<Schedule> get3NextDepartures(LineStation ls) {
        SQLiteDatabase db = this.dh.getReadableDatabase();
        List<Schedule> schedulesList = new ArrayList<Schedule>();

        // select the period that match with the current day (to improve)
        String period1, period2;
        String dayString = BeziersTransports.getDayFormat().format(BeziersTransports.getDay());
        if (dayString.equals("lundi") || dayString.equals("mardi") || dayString.equals("mercredi")
                || dayString.equals("jeudi") || dayString.equals("vendredi")) {
            period1 = "1"; // because idPeriod = 1 <=> LS
            period2 = "3"; // because idPeriod = 3 <=> LV
        }
        else if (dayString.equals("samedi")) {
            period1 = "4"; // because idPeriod = 4 <=> S
            period2 = "1"; // because idPeriod = 1 <=> LS
        }
        else { // dayString = "Sun"
            period1 = "2";
            period2 = "2";  // because idPeriod = 2 <=> D
        }

        Cursor cursor = db.query(DatabaseHandler.TABLE_SCHEDULE,
                new String[]{DatabaseHandler.KEY_LINENUMBER, DatabaseHandler.KEY_IDSTATION,
                        DatabaseHandler.KEY_IDPERIOD, DatabaseHandler.KEY_DIRECTION, DatabaseHandler.KEY_SCHEDULE},
                DatabaseHandler.KEY_LINENUMBER + "= ? AND " + DatabaseHandler.KEY_IDSTATION + " = ? AND " +
                        DatabaseHandler.KEY_DIRECTION + " = ? AND " + DatabaseHandler.KEY_SCHEDULE + " >= ? AND ("
                        + DatabaseHandler.KEY_IDPERIOD + " = ? OR " + DatabaseHandler.KEY_IDPERIOD + " = ? )",
                new String[]{ls.getLine().getLineNumber(), String.valueOf(ls.getStation().getId()),
                        ls.getDirection(), BeziersTransports.getScheduleFormat().format(BeziersTransports.getDateNow()),
                period1, period2}, null, null, null, "3");

        if (cursor.moveToFirst()) {
            do {
                Schedule schedule = new Schedule();
                schedule.setLineStation(LineStationDAO.getLineStationDAO().getLineStation(
                                LineDAO.getLineDAO().getLine(cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_LINENUMBER))),
                                StationDAO.getStationDAO().getStation(cursor.getLong(cursor.getColumnIndex(DatabaseHandler.KEY_IDSTATION))),
                                cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_DIRECTION)))
                );
                schedule.setPeriod(PeriodDAO.getPeriodDAO().getPeriod(cursor.getLong(cursor.getColumnIndex(DatabaseHandler.KEY_IDPERIOD))));
                try {
                    schedule.setSchedule(BeziersTransports.getScheduleFormat().parse(cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_SCHEDULE))));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                schedulesList.add(schedule);
            } while (cursor.moveToNext());
        }
        return schedulesList;
    }


    // get all schedules from LineStation whatever the direction
    public List<Schedule> getSchedules(LineStation ls) {
        SQLiteDatabase db = this.dh.getReadableDatabase();
        List<Schedule> schedulesList = new ArrayList<Schedule>();

        Cursor cursor = db.query(DatabaseHandler.TABLE_SCHEDULE,
                new String[]{DatabaseHandler.KEY_LINENUMBER, DatabaseHandler.KEY_IDSTATION,
                        DatabaseHandler.KEY_IDPERIOD, DatabaseHandler.KEY_DIRECTION, DatabaseHandler.KEY_SCHEDULE},
                DatabaseHandler.KEY_LINENUMBER + "= ? AND " + DatabaseHandler.KEY_IDSTATION + " = ?",
                new String[]{ls.getLine().getLineNumber(), String.valueOf(ls.getStation().getId())}
                , null, null, null, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    Schedule schedule = new Schedule();
                    schedule.setLineStation(LineStationDAO.getLineStationDAO().getLineStation(
                                    LineDAO.getLineDAO().getLine(cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_LINENUMBER))),
                                    StationDAO.getStationDAO().getStation(cursor.getLong(cursor.getColumnIndex(DatabaseHandler.KEY_IDSTATION))),
                                    cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_DIRECTION)))
                    );
                    schedule.setPeriod(PeriodDAO.getPeriodDAO().getPeriod(cursor.getLong(cursor.getColumnIndex(DatabaseHandler.KEY_IDPERIOD))));
                    try {
                        schedule.setSchedule(BeziersTransports.getScheduleFormat().parse(cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_SCHEDULE))));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    schedulesList.add(schedule);
                } while (cursor.moveToNext());
            }
        } finally {
            cursor.close();
        }

        return schedulesList;
    }


}
