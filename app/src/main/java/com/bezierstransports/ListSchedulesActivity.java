package com.bezierstransports;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bezierstransports.adapter.AdapterLine;
import com.bezierstransports.adapter.AdapterSchedule;
import com.bezierstransports.adapter.AdapterStationSchedule;
import com.bezierstransports.database.LineDAO;
import com.bezierstransports.database.LineStationDAO;
import com.bezierstransports.database.ScheduleDAO;
import com.bezierstransports.model.Line;
import com.bezierstransports.model.LineStation;
import com.bezierstransports.model.Schedule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListSchedulesActivity extends AppCompatActivity {

    LineStation lineStation;
    RadioGroup radioGroupPeriod;
    ListView listViewSchedules;
    AdapterSchedule adapterSchedule;
    List<Schedule> scheduleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_schedules_activity);

        // get the object lineStation from the last activity
        Intent i = getIntent();
        lineStation = (LineStation) i.getParcelableExtra("lineStation");

        setTitle(getString(R.string.ligne) + " " + lineStation.getLine().getLineNumber() +
        " - " + lineStation.getStation().getStationName());

        listViewSchedules = (ListView) findViewById(R.id.listViewSchedules);
        radioGroupPeriod = (RadioGroup) findViewById(R.id.radio_group_period);

        scheduleList = ScheduleDAO.getScheduleDAO().getSchedules(lineStation);
        addRadioButtons();
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

    private void addRadioButtons() {

        List<String> labelRadioButtonList = new ArrayList<String>();
        int i = 0;
        for (Schedule s : scheduleList) {
            final String period = s.getPeriod().getPeriod();
            String labelPeriod = null;
            switch (period) {
                case "LS":
                    labelPeriod = getString(R.string.lundiASamedi);
                    break;
                case "D":
                    labelPeriod = getString(R.string.dimanche);
                    break;
            }

            if (!labelRadioButtonList.contains(period)) {
                labelRadioButtonList.add(period);
                // add radio button to radio group
                RadioButton button = new RadioButton(this);
                //if (i==0) button.setChecked(true);
                button.setId(i); i++;
                button.setText(labelPeriod);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getData(period);
                    }
                });
                radioGroupPeriod.addView(button);
            }
        }
    }


    private void getData (final String period) {
        new AsyncTask<Void, Void, List<Schedule>>() {

            protected List<Schedule> doInBackground(Void... params) {
                if (scheduleList != null) {
                    ArrayList<Schedule> liste = new ArrayList<Schedule>();
                    for (Schedule s : scheduleList) {
                        if (s.getPeriod().getPeriod().equals(period)) {
                            liste.add(s);
                        }
                    }
                    return liste;
                }
                return new ArrayList<>();
            }

            protected void onPostExecute(java.util.List<Schedule> result) {
                adapterSchedule = new AdapterSchedule(getBaseContext(), android.R.layout.simple_list_item_1, result);
                listViewSchedules.setAdapter(adapterSchedule);
            }
        }.execute();
    }
}
