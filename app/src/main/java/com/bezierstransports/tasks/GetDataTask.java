package com.bezierstransports.tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bezierstransports.BeziersTransports;
import com.bezierstransports.activities.ListStationsActivity;
import com.bezierstransports.adapters.AdapterLine;
import com.bezierstransports.database.LineDAO;
import com.bezierstransports.model.Line;
import com.bezierstransports.service.WebService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jérémy Pastor on 23/12/2015.
 */
public class GetDataTask extends AsyncTask<Void, Void, List<Line>> {

    private Activity activity;
    private ProgressDialog dialog;
    private ListView listViewLines;
    private AdapterLine adapterLine;

    public GetDataTask(Activity activity, ListView listViewLines, AdapterLine adapterLine) {
        this.activity = activity;
        this.dialog = new ProgressDialog(activity);
        this.listViewLines = listViewLines;
        this.adapterLine = adapterLine;
    }

    protected void onPreExecute() {
        // display a feedback during loading
        dialog.setMessage("Premier lancement de l'application : nous récupérons les données. Veuillez patientez, svp");
        dialog.setCancelable(false);
        dialog.show();
    }

    protected List<Line> doInBackground(Void... params) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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
        // delete the feedback one loading finished
        if (dialog.isShowing()) {
            dialog.dismiss();
        }

        adapterLine = new AdapterLine(activity.getBaseContext(), android.R.layout.simple_list_item_1, result);
        listViewLines.setAdapter(adapterLine);
        listViewLines.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                if (adapterLine != null) {
                    // get the line selected
                    Line line = adapterLine.getItem(position);
                    if (line != null) {
                        Intent i = new Intent(activity.getBaseContext(), ListStationsActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        i.putExtra("line", line);
                        activity.getBaseContext().startActivity(i);
                    }
                }
            }
        });
    }
}
