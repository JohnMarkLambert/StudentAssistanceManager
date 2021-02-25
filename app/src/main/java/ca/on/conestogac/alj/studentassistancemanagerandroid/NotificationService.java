package ca.on.conestogac.alj.studentassistancemanagerandroid;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class NotificationService extends Service {
    public NotificationService() {
    }

    public static final int ID = 1;
    private DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm", Locale.getDefault());

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        onTaskRemoved(intent);

        List<Assignment> assignments = new ArrayList<>();
        assignments = ((SAMApplication) getApplication()).getAllAssignments();

        if (assignments.size() > 0) {
            for (Assignment a : assignments) {
                if ((a.getDueDate() - System.currentTimeMillis()) <= TimeUnit.DAYS.toMillis(a.getPeriod())) {
                    if(!a.isNotified()) {
                        //****for some reason it will send a toast from here but not a notification****
                        String dueDate = df.format(a.getDueDate());
                        String [] dateTime = dueDate.split(" ");

                        Toast.makeText(getApplicationContext(), a.getName() + " is due on " + dateTime[0] + " at " + dateTime[1], Toast.LENGTH_LONG).show();

                        final Intent NotifyIntent = new Intent(getApplicationContext(), MainActivity.class);
                        NotifyIntent.addFlags(NotifyIntent.FLAG_ACTIVITY_CLEAR_TOP);
                        final PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, NotifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                        final NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                        final Notification notification = new Notification.Builder(getApplicationContext())
                                .setSmallIcon(R.drawable.ic_launcher_foreground)
                                .setContentTitle("Assignment Due Soon!")
                                .setContentText(a.getName() + " is due on " + dateTime[0] + " at " + dateTime[1])
                                .setContentIntent(pendingIntent)
                                .setAutoCancel(true)
                                .build();
                        manager.notify(ID, notification);

                        ((SAMApplication) getApplication()).updateNotified(a.getId(), true);
                    }
                }

            }
        }
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
        super.onCreate();
    }
}