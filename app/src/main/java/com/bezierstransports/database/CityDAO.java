package com.bezierstransports.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bezierstransports.BeziersTransports;
import com.bezierstransports.model.City;

/**
 * Created by Jérémy Pastor on 11/11/2015.
 */
public class CityDAO {
    private static CityDAO cityDAO = null;
    private DatabaseHandler dh;

    private CityDAO(Context context){
        dh = new DatabaseHandler(context);
    }

    public final static CityDAO getCityDAO() {
        if (CityDAO.cityDAO == null) {
            // "synchronized" forbids multiple instantiations in different threads
            synchronized(CityDAO .class) {
                if (CityDAO.cityDAO == null) {
                    CityDAO.cityDAO = new CityDAO(BeziersTransports.getAppContext());
                }
            }
        }
        return CityDAO.cityDAO;
    }

    // add a city into the database
    public void addCity(City city)  {

        // insert only if the value does not exist
        if (dh.getCount(DatabaseHandler.TABLE_CITY, DatabaseHandler.KEY_ID + " = ?",
                new String[]{String.valueOf(city.getId())}) == 0) {
            SQLiteDatabase db = this.dh.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(DatabaseHandler.KEY_ID, city.getId());
            values.put(DatabaseHandler.KEY_CITYNAME, city.getCityName());

            // insert the city in the DB and get ID
            city.setId(db.insert(DatabaseHandler.TABLE_CITY, null, values));
            db.close();
        }
    }

    // get the city that matches with ID given
    public City getCity(long id) {
        SQLiteDatabase db = this.dh.getReadableDatabase();

        Cursor cursor = db.query(DatabaseHandler.TABLE_CITY,
                new String[]{DatabaseHandler.KEY_ID, DatabaseHandler.KEY_CITYNAME},
                DatabaseHandler.KEY_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null, null);

        try {
            if (cursor.moveToFirst()) {
                City city = new City();
                city.setId(cursor.getLong(cursor.getColumnIndex(DatabaseHandler.KEY_ID)));
                city.setCityName(cursor.getString(cursor.getColumnIndex(DatabaseHandler.KEY_CITYNAME)));
                return city;
            } else return null;
        } finally {
            cursor.close();
        }
    }
}
