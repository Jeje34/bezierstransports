package com.bezierstransports.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bezierstransports.BeziersTransports;
import com.bezierstransports.model.Period;
import com.bezierstransports.model.Station;

/**
 * Created by Jérémy Pastor on 11/11/2015.
 */
public class PeriodDAO {
    private static PeriodDAO periodDAO = null;
    private DatabaseHandler dh;

    private PeriodDAO(Context context){
        dh = new DatabaseHandler(context);
    }

    public final static PeriodDAO getPeriodDAO() {
        if (PeriodDAO.periodDAO == null) {
            // "synchronized" forbids multiple instantiations in different threads
            synchronized(PeriodDAO .class) {
                if (PeriodDAO.periodDAO == null) {
                    PeriodDAO.periodDAO = new PeriodDAO(BeziersTransports.getAppContext());
                }
            }
        }
        return PeriodDAO.periodDAO;
    }

    // add a period into the database
    public void addPeriod(Period period)  {

        // insert only if the value does not exist
        if (dh.getCount(DatabaseHandler.TABLE_PERIOD, DatabaseHandler.KEY_ID + " = ?",
                new String[]{String.valueOf(period.getId())}) == 0) {

            SQLiteDatabase db = this.dh.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DatabaseHandler.KEY_PERIOD, period.getPeriod());
            values.put(DatabaseHandler.KEY_SEASON, period.getSeason());

            // insert the period in the DB and get ID
            period.setId(db.insert(DatabaseHandler.TABLE_PERIOD, null, values));
            db.close();
        }
    }


    // get a period that matches with the ID given
    public Period getPeriod(long id) {
        SQLiteDatabase db = this.dh.getReadableDatabase();

        Cursor cursor = db.query(DatabaseHandler.TABLE_PERIOD,
                new String[]{DatabaseHandler.KEY_ID, DatabaseHandler.KEY_PERIOD,
                        DatabaseHandler.KEY_SEASON},
                DatabaseHandler.KEY_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor.moveToFirst()) {
            Period period = new Period();
            period.setId(cursor.getLong(cursor.getColumnIndex(DatabaseHandler.KEY_ID)));
            period.setPeriod(cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_PERIOD)));
            period.setSeason(cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_SEASON)));
            return period;
        }
        else return null;
    }
}
