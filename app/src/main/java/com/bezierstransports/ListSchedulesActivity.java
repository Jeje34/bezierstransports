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
    RadioButton radioButtonAller;
    RadioButton radioButtonRetour;

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



        radioButtonAller = (RadioButton) findViewById(R.id.radio_button_aller_2);
        radioButtonRetour = (RadioButton) findViewById(R.id.radio_button_retour_2);

        // split the name line to set 2 directions
        String[] parts = lineStation.getLine().getLineName().split("- ");
        radioButtonAller.setText(parts[parts.length - 1]);
        radioButtonRetour.setText(parts[0]);

        scheduleList = ScheduleDAO.getScheduleDAO().getSchedules(lineStation);
        addRadioButtons();
        radioGroupPeriod.check(0);

        radioButtonAller.setOnClickListener(radioButtonAllerListener);
        radioButtonRetour.setOnClickListener(radioButtonRetourListener);
        if (lineStation.getDirection().equals("A")) {
            radioButtonAller.setChecked(true);
            radioButtonAller.performClick();
        }
        else if (lineStation.getDirection().equals("R")) {
            radioButtonRetour.setChecked(true);
            radioButtonRetour.performClick();
        }
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
                button.setId(i); i++;
                button.setText(labelPeriod);
                radioGroupPeriod.addView(button);
            }
        }

        radioGroupPeriod.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radiobutton = (RadioButton) findViewById(checkedId);
                String period = radiobutton.getText().toString();
                if (period.equals(getString(R.string.lundiASamedi))) {
                    if (radioButtonAller.isChecked()) {
                        getData("LS", "A");
                    } else {
                        getData("LS", "R");
                    }
                } else if (period.equals(getString(R.string.dimanche))) {
                    if (radioButtonAller.isChecked()) {
                        getData("D", "A");
                    } else {
                        getData("D", "R");
                    }
                }
            }
        });
    }

    private View.OnClickListener radioButtonAllerListener = new View.OnClickListener(){
        public void onClick(View v) {
            for (int i=0 ; i < radioGroupPeriod.getChildCount() ; i++) {
                RadioButton radiobutton = (RadioButton) findViewById(i);
                if (radiobutton.isChecked()) {
                    String period = radiobutton.getText().toString();
                    if (period.equals(getString(R.string.lundiASamedi))) {
                        getData("LS", "A");
                        break;
                    } else if (period.equals(getString(R.string.dimanche))) {
                        getData("D", "A");
                        break;
                    }
                }
            }
        }
    };

    private View.OnClickListener radioButtonRetourListener = new View.OnClickListener(){
        public void onClick(View v) {
            for (int i=0 ; i < radioGroupPeriod.getChildCount() ; i++) {
                RadioButton radiobutton = (RadioButton) findViewById(i);
                if (radiobutton.isChecked()) {
                    String period = radiobutton.getText().toString();
                    if (period.equals(getString(R.string.lundiASamedi))) {
                        getData("LS", "R");
                        break;
                    } else if (period.equals(getString(R.string.dimanche))) {
                        getData("D", "R");
                        break;
                    }
                }
            }
        }
    };


    private void getData (final String period, final String direction) {
        new AsyncTask<Void, Void, List<Schedule>>() {

            protected List<Schedule> doInBackground(Void... params) {
                if (scheduleList != null) {
                    ArrayList<Schedule> liste = new ArrayList<Schedule>();
                    for (Schedule s : scheduleList) {
                        if (s.getPeriod().getPeriod().equals(period)) {
                            if (s.getLineStation().getDirection().equals(direction)) {
                                liste.add(s);
                            }
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
