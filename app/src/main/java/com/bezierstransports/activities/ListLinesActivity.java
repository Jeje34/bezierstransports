package com.bezierstransports.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bezierstransports.BeziersTransports;
import com.bezierstransports.R;
import com.bezierstransports.adapters.AdapterLine;
import com.bezierstransports.database.LineDAO;
import com.bezierstransports.model.Line;
import com.bezierstransports.tasks.GetDataTask;

import java.util.List;

public class ListLinesActivity extends AppCompatActivity {

    ListView listViewLines;
    AdapterLine adapterLine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_lines_activity);

        setTitle("Béziers Transports");
        BeziersTransports.initActionBar(this, "#83BD44");

        listViewLines = (ListView) findViewById(R.id.listViewLines);

        // first launch ever: get data
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (!prefs.getBoolean("firstTime", false)) {
            new GetDataTask(ListLinesActivity.this, listViewLines, adapterLine).execute();
            // mark first time has runned
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstTime", true);
            editor.commit();

        } else {
            List<Line> liste = LineDAO.getLineDAO().getLines();
            adapterLine = new AdapterLine(ListLinesActivity.this, R.layout.row_line, liste);
            listViewLines.setAdapter(adapterLine);
            listViewLines.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                    if (adapterLine != null) {
                        // get the line selected
                        Line line = adapterLine.getItem(position);
                        if (line != null) {
                            Intent i = new Intent(ListLinesActivity.this, ListStationsActivity.class);
                            i.putExtra("line", line);
                            startActivity(i);
                        }
                    }
                }
            });
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list_lines, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sync:
                new GetDataTask(ListLinesActivity.this, listViewLines, adapterLine).execute();
                return true;
            case R.id.action_map:
                Intent i = new Intent(ListLinesActivity.this, LineMapActivity.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
