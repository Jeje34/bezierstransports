package com.bezierstransports;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.bezierstransports.model.Line;
import com.bezierstransports.model.LineStation;

public class ListSchedulesActivity extends AppCompatActivity {

    LineStation lineStation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_schedules_activity);

        // get the object lineStation from the last activity
        Intent i = getIntent();
        lineStation = (LineStation) i.getParcelableExtra("lineStation");

        setTitle(getString(R.string.ligne) + " " + lineStation.getLine().getLineNumber() +
        " - " + lineStation.getStation().getStationName());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_schedules, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
