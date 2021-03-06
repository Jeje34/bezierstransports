package com.bezierstransports.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.bezierstransports.BeziersTransports;
import com.bezierstransports.R;
import com.bezierstransports.adapters.AdapterStationSchedule;
import com.bezierstransports.database.LineStationDAO;
import com.bezierstransports.database.ScheduleDAO;
import com.bezierstransports.model.Line;
import com.bezierstransports.model.LineStation;
import com.bezierstransports.model.Schedule;
import com.bezierstransports.tasks.DownloadFileTask;

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

        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_stations_activity);

        radioButtonAller = (RadioButton) findViewById(R.id.radio_button_aller);
        radioButtonRetour = (RadioButton) findViewById(R.id.radio_button_retour);
        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);

        // get the object line from the last activity
        Intent i = getIntent();
        line = (Line) i.getParcelableExtra("line");

        getData("A");
        BeziersTransports.initActionBar(this, line.getColor());

        // split the name line to set 2 directions
        String[] parts = line.getLineName().split("- ");
        radioButtonAller.setText(parts[parts.length - 1]);
        radioButtonRetour.setText(parts[0]);

        setTitle(getString(R.string.ligne) + " " + line.getLineNumber());

        Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.stayPress),
                Toast.LENGTH_LONG).show();

        radioButtonAller.setOnClickListener(radioButtonAllerListener);
        radioButtonRetour.setOnClickListener(radioButtonRetourListener);
        expandableListView.setOnItemLongClickListener(groupLongClickListener);

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
        switch (item.getItemId()) {
            case R.id.action_upload_schedules:
                downloadPDF(line.getLineNumber());
                return true;
            case R.id.action_map:
                Intent i = new Intent(ListStationsActivity.this, LineMapActivity.class);
                i.putExtra("line", line);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void downloadPDF(String lineNumber) {
        String url = "http://www.beziers-transports.com/ftp/FR_documents/";
        switch (lineNumber) {
            case "1":
                url += "L1-Web.pdf";
                break;
            case "2":
                url += "L2-Web.pdf";
                break;
            case "3":
                url += "L3-%203%20nov%202014%20-%20Web.pdf";
                break;
            case "4":
                url += "L4-Web.pdf";
                break;
            case "5":
                url += "L5.pdf";
                break;
            case "15":
                url += "L15-Web%20corig%C3%A9e.pdf";
                break;
        }
        new DownloadFileTask(this).execute(url, "L" + lineNumber + ".pdf");
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


    // long click on item LineStation launches activity that list all schedules of the LineStation
    private ExpandableListView.OnItemLongClickListener groupLongClickListener = new ExpandableListView.OnItemLongClickListener() {

        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {

            int itemType = ExpandableListView.getPackedPositionType(id);

            // handle only long click on group item
            if (itemType == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {
                long groupPosition = expandableListView.getPackedPositionGroup(id);
                onGroupLongClick(groupPosition);
                return true;
            } else {
                // do nothing if long click on child item
                return false;
            }
        }
    };

    private void onGroupLongClick(long packedPosition) {
         if (expandableAdapterLineStation != null) {
            // get the lineStation selected
            LineStation lineStation = (LineStation) expandableAdapterLineStation.getGroup((int) packedPosition);
            if (lineStation != null) {
                Intent i = new Intent(ListStationsActivity.this, ListSchedulesActivity.class);
                i.putExtra("lineStation", lineStation);
                startActivity(i);
            }
        }
    }
}
