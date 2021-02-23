package ca.on.conestogac.alj.studentassistancemanagerandroid;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import java.util.ArrayList;
import java.util.List;

public class NotificationService extends Service {
    public NotificationService() {
    }

    public static final int ID= 0;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //test to see if calculation with time is right. Just have this for now as there is nothing in the database
        //this is a due date of feb 23 at 11:59:59 pm (in seconds) with a notification period of one day (in seconds)
        if ((1614142799 - 86400) >= (System.currentTimeMillis() / 1000)) {
            final Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            final Notification unlockNotification = new Notification.Builder(getApplicationContext())
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle("Assignment Due Soon!")
                    .setContentText("TEST")
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .build();
            final NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.notify(ID, unlockNotification);
        }


        List<Assignment> assignments = new ArrayList<>();
        assignments = ((SAMApplication) getApplication()).getAllAssignments();


        /*
        if (assignments.size() > 0) {
            for (Assignment a : assignments) {
                if ((a.getDueDate() - a.getPeriod()) <= (System.currentTimeMillis() / 1000)) {
                    final Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                    final Notification unlockNotification = new Notification.Builder(getApplicationContext())
                            .setSmallIcon(R.drawable.ic_launcher_foreground)
                            .setContentTitle("Assignment Due Soon!")
                            .setContentText(a.getName() + " is due in " + a.getPeriod())
                            .setContentIntent(pendingIntent)
                            .setAutoCancel(true)
                            .build();
                    final NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    manager.notify(ID, unlockNotification);
                }
            }
        }

         */
    }
}