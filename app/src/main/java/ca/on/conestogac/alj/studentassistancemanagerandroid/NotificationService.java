package ca.on.conestogac.alj.studentassistancemanagerandroid;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class NotificationService extends Service {
    public NotificationService() {
    }

    public static final int ID = 1;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        onTaskRemoved(intent);
        //Toast.makeText(getApplicationContext(),"Test", Toast.LENGTH_SHORT).show();
        return START_STICKY;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Intent restartServiceIntent = new Intent(getApplicationContext(),this.getClass());
        restartServiceIntent.setPackage(getPackageName());
        startService(restartServiceIntent);
        super.onTaskRemoved(rootIntent);
    }

    @Override
    public void onCreate() {


        List<Assignment> assignments = new ArrayList<>();
        assignments = ((SAMApplication) getApplication()).getAllAssignments();



        if (assignments.size() > 0) {
            for (Assignment a : assignments) {
                System.out.println(a.getName());

                //if ((a.getDueDate() - a.getPeriod()) <= (System.currentTimeMillis() / 1000)) {
                if (/*a.getDueDate()*/true){

                    final Intent intent = new Intent(getApplicationContext(), MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    final PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    final NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    final Notification notification = new Notification.Builder(getApplicationContext())
                            .setSmallIcon(R.drawable.ic_launcher_foreground)
                            .setContentTitle("Assignment Due Soon!")
                            .setContentText("TEST")
                            .setContentIntent(pendingIntent)
                            .setAutoCancel(true)
                            .build();
                    manager.notify(ID, notification);
                }

            }
        }

        super.onCreate();
    }
}