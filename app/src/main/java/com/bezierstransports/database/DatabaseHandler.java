package com.bezierstransports.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Jérémy Pastor on 10/11/2015.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    protected static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "db_bezierstransport";
    protected static final String KEY_ID = "_id";

    protected static final String TABLE_LINE = "line";
    protected static final String KEY_LINENUMBER = "lineNumber";
    protected static final String KEY_LINENAME = "lineName";
    protected static final String KEY_COLOR = "color";

    protected static final String TABLE_STATION = "station";
    protected static final String KEY_STATIONNAME = "stationName";
    protected static final String KEY_LATITUDE = "latitude";
    protected static final String KEY_LONGITUDE = "longitude";
    protected static final String KEY_IDCITY = "idCity";

    protected static final String TABLE_LINE_STATION = "line_station";
    protected static final String KEY_IDSTATION = "idStation";
    protected static final String KEY_DIRECTION = "direction";
    protected static final String KEY_ORDRE = "ordre";

    protected static final String TABLE_CITY = "city";
    protected static final String KEY_CITYNAME = "cityName";

    protected static final String TABLE_PERIOD = "period";
    protected static final String KEY_PERIOD = "period";
    protected static final String KEY_SEASON = "season";

    protected static final String TABLE_SCHEDULE = "schedule";
    protected static final String KEY_IDPERIOD = "idPeriod";
    protected static final String KEY_SCHEDULE = "schedule";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    public void onCreate(SQLiteDatabase db) {
        String CREATE_LINE_TABLE = "CREATE TABLE " + TABLE_LINE + "("  +
                KEY_LINENUMBER + "  VARCHAR(4) PRIMARY KEY," +
                KEY_LINENAME + " VARCHAR(200) NOT NULL," +
                KEY_COLOR + " VARCHAR(7))";
        db.execSQL(CREATE_LINE_TABLE);

        String CREATE_CITY_TABLE = "CREATE TABLE " + TABLE_CITY + "("  +
                KEY_ID + " INTEGER PRIMARY KEY," +
                KEY_CITYNAME + " VARCHAR(100) NOT NULL" + ")";
        db.execSQL(CREATE_CITY_TABLE);

        String CREATE_STATION_TABLE = "CREATE TABLE " + TABLE_STATION + "("  +
                KEY_ID + " INTEGER PRIMARY KEY," +
                KEY_STATIONNAME + "  VARCHAR(100) NOT NULL," +
                KEY_LATITUDE + " FLOAT," +
                KEY_LONGITUDE + " FLOAT," +
                KEY_IDCITY + " INTEGER," +
                " FOREIGN KEY (" + KEY_IDCITY + ") REFERENCES " + TABLE_CITY + "(" + KEY_ID +"))";
        db.execSQL(CREATE_STATION_TABLE);

        String CREATE_LINE_STATION_TABLE = "CREATE TABLE " + TABLE_LINE_STATION + "("  +
                KEY_LINENUMBER + "  VARCHAR(4)," +
                KEY_IDSTATION + " INTEGER," +
                KEY_DIRECTION + " VARCHAR(100)," +
                KEY_ORDRE + " INTEGER(2) NOT NULL," +
                " PRIMARY KEY (" + KEY_LINENUMBER + ", " + KEY_IDSTATION + ", " + KEY_DIRECTION + ")," +
                " FOREIGN KEY ("+ KEY_LINENUMBER + ") REFERENCES " + TABLE_LINE +"(" + KEY_LINENUMBER + ")," +
                " FOREIGN KEY ("+ KEY_IDSTATION + ") REFERENCES " + TABLE_STATION +"(" + KEY_ID + "))";
        db.execSQL(CREATE_LINE_STATION_TABLE);

        String CREATE_PERIOD_TABLE = "CREATE TABLE " + TABLE_PERIOD + "("  +
                KEY_ID + " INTEGER PRIMARY KEY," +
                KEY_PERIOD + "  VARCHAR(2) NOT NULL," +
                KEY_SEASON + " VARCHAR(100)" + ")";
        db.execSQL(CREATE_PERIOD_TABLE);

        String CREATE_SCHEDULE_TABLE = "CREATE TABLE " + TABLE_SCHEDULE + "("  +
                KEY_LINENUMBER + "  VARCHAR(4)," +
                KEY_IDSTATION + " INTEGER," +
                KEY_IDPERIOD + " INTEGER," +
                KEY_DIRECTION + " VARCHAR(100)," +
                KEY_SCHEDULE + " TIME," +
                " PRIMARY KEY (" + KEY_LINENUMBER + ", " + KEY_IDSTATION + ", " + KEY_IDPERIOD + ", "
                + KEY_DIRECTION + ", " + KEY_SCHEDULE + ")," +
                " FOREIGN KEY ("+ KEY_LINENUMBER + ") REFERENCES " + TABLE_LINE_STATION +"(" + KEY_LINENUMBER + ")," +
                " FOREIGN KEY ("+ KEY_IDPERIOD + ") REFERENCES " + TABLE_PERIOD +"(" + KEY_ID + ")," +
                " FOREIGN KEY ("+ KEY_DIRECTION + ") REFERENCES " + TABLE_LINE_STATION +"(" + KEY_ID + ")," +
                " FOREIGN KEY ("+ KEY_IDSTATION+ ") REFERENCES " + TABLE_LINE_STATION +"(" + KEY_ID + "))";
        db.execSQL(CREATE_SCHEDULE_TABLE);
    }


    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCHEDULE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PERIOD);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LINE_STATION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STATION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CITY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LINE);
        onCreate(db);
    }

    public int getCount(SQLiteDatabase db, String table, String selection, String[] selectionArgs) {
        Cursor c = null;
        try {
            String query = "SELECT COUNT(*) FROM " + table + " WHERE " + selection;
            c = db.rawQuery(query, selectionArgs);
            if (c.moveToFirst()) {
                return c.getInt(0);
            }
            return 0;
        }
        finally {
            if (c != null) {
                c.close();
            }
        }
    }
}
