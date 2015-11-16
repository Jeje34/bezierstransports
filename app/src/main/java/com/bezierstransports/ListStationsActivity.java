package com.bezierstransports;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.RadioButton;

import com.bezierstransports.adapter.AdapterStationSchedule;
import com.bezierstransports.database.LineStationDAO;
import com.bezierstransports.database.ScheduleDAO;
import com.bezierstransports.model.Line;
import com.bezierstransports.model.LineStation;
import com.bezierstransports.model.Schedule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListStationsActivity extends AppCompatActivity {

    RadioButton radioButtonAller;
    RadioButton radioButtonRetour;
    ExpandableListView expandableListView;
    AdapterStationSchedule expandableAdapterLineStation;
    HashMap<LineStation, List<Schedule>> listDataChild;
    Line line;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_stations_activity);

        radioButtonAller = (RadioButton) findViewById(R.id.radio_button_aller);
        radioButtonRetour = (RadioButton) findViewById(R.id.radio_button_retour);
        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);

        // get the object line from the last activity
        Intent i = getIntent();
        line = (Line) i.getParcelableExtra("line");
        getData("A");

        // split the name line to set 2 directions
        String[] parts = line.getLineName().split("- ");
        radioButtonAller.setText(parts[0]);
        radioButtonRetour.setText(parts[parts.length - 1]);

        radioButtonAller.setOnClickListener(radioButtonAllerListener);
        radioButtonRetour.setOnClickListener(radioButtonRetourListener);

        // thread that update time every minute and update the list
        Thread t = new Thread() {
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            public void run() {
                                BeziersTransports.updateTime();
                                expandableAdapterLineStation.notifyDataSetChanged();
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        t.start();

    }

    private View.OnClickListener radioButtonAllerListener = new View.OnClickListener(){
        public void onClick(View v) {
            getData("A");
        }
    };

    private View.OnClickListener radioButtonRetourListener = new View.OnClickListener(){
        public void onClick(View v) {
            getData("R");
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_stations, menu);
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

    private void getData (final String direction) {
        new AsyncTask<Void, Void, List<LineStation>>() {


            protected List<LineStation> doInBackground(Void... params) {
                List<LineStation> liste = LineStationDAO.getLineStationDAO().getLineStations(line, direction);
                if (liste != null) {
                    return liste;
                }
                return new ArrayList<>();
            }

            protected void onPostExecute(java.util.List<LineStation> result) {
                listDataChild = new HashMap<LineStation, List<Schedule>>();

                for (int i=0 ; i < result.size() ; i++) {
                    List<Schedule> next3Departures = ScheduleDAO.getScheduleDAO().get3NextDepartures(result.get(i));
                    listDataChild.put(result.get(i), next3Departures);
                }
                expandableAdapterLineStation = new AdapterStationSchedule(BeziersTransports.getAppContext(),
                        result, listDataChild);
                expandableListView.setAdapter(expandableAdapterLineStation);
            }
        }.execute();
    }

}
