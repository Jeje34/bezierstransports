package com.bezierstransports;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioGroup;

import com.bezierstransports.adapter.AdapterLine;
import com.bezierstransports.database.LineDAO;
import com.bezierstransports.model.Line;
import com.bezierstransports.service.WebService;

import java.util.ArrayList;
import java.util.List;

public class ListLinesActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView listViewLines;
    AdapterLine adapterLine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_lines_activity);

        listViewLines = (ListView) findViewById(R.id.listViewLines);

        getData();
        listViewLines.setOnItemClickListener(this);


    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list_lines, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    private void getData() {
        new AsyncTask<Void, Void, List<Line>>() {

            protected List<Line> doInBackground(Void... params) {
                // call webservice to get the lines list
                WebService webService = new WebService(BeziersTransports.getAppContext());
                webService.getLinesAndWriteInDatabase();
                List<Line> liste = LineDAO.getLineDAO().getLines();
                if (liste != null) {
                    return liste;
                }
                return new ArrayList<>();
            }

            protected void onPostExecute(java.util.List<Line> result) {
                adapterLine = new AdapterLine(getBaseContext(), android.R.layout.simple_list_item_1, result);
                listViewLines.setAdapter(adapterLine);
            }
        }.execute();
    }


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
}
