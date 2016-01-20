package com.bezierstransports.tasks;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.Toast;

import com.bezierstransports.FileDownloader;
import com.bezierstransports.R;

import java.io.File;
import java.io.IOException;

public class DownloadFileTask extends AsyncTask<String, Void, File> {

    private Context context;
    private String fileName;

    public DownloadFileTask(Context context) {
        this.context = context;
    }

    @Override
    protected File doInBackground(String... strings) {
        String fileUrl = strings[0];
        fileName = strings[1];
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        File folder = new File(extStorageDirectory, "");
        folder.mkdir();

        File pdfFile = new File(folder, fileName);

        try {
            pdfFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileDownloader fd = new FileDownloader(context, fileName);
        fd.downloadFile(fileUrl, pdfFile);
        return pdfFile;
    }

    protected void onPostExecute(File result) {
        Uri path = Uri.fromFile(result);
        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
        pdfIntent.setDataAndType(path, "application/pdf");
        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        try{
            context.startActivity(pdfIntent);
        } catch(ActivityNotFoundException e){
            Toast.makeText(context, context.getString(R.string.noApplicationPDF), Toast.LENGTH_SHORT).show();
        }
    }
}