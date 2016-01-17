package com.bezierstransports.tasks;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;

import com.bezierstransports.FileDownloader;
import com.bezierstransports.R;
import com.bezierstransports.activities.ListSchedulesActivity;

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
        FileDownloader.downloadFile(fileUrl, pdfFile);
        return pdfFile;
    }


    protected void onPostExecute(File result) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {

            Uri path = Uri.fromFile(result);
            Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
            pdfIntent.setDataAndType(path, "application/pdf");
            pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            Notification.Builder notifBuilder = new Notification.Builder(context)
                    .setContentTitle("Télécharchement terminé")
                    .setContentText(fileName + " a été téléchargé avec succès!")
                    .setSmallIcon(R.drawable.logo_beziers_transports);
            Notification notif = notifBuilder.build();

            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
            stackBuilder.addNextIntent(pdfIntent);
            PendingIntent pdfPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            stackBuilder.addParentStack(ListSchedulesActivity.class);
            notifBuilder.setContentIntent(pdfPendingIntent);

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notif.flags |= Notification.FLAG_AUTO_CANCEL;
            notificationManager.notify(0, notif);
        }


    }



    /*
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

    private void sendNotificationDownloadedOK(String fileName) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            Notification notif = new Notification.Builder(context)
                    .setContentTitle("Télécharchement terminé")
                    .setContentText(fileName + " a été téléchargé avec succès!")
                    .setSmallIcon(R.drawable.logo_beziers_transports)
                    .build();
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notif.flags |= Notification.FLAG_AUTO_CANCEL;
            notificationManager.notify(0, notif);
        }
    }
    */
}