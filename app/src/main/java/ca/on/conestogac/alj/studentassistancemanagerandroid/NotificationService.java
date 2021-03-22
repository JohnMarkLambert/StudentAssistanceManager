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
    private boolean firstDateChecked = false;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        onTaskRemoved(intent);

        checkForAssignmentNotification();
        checkForStartOfMonth();

        return START_STICKY;
    }

    //Assignment Notification
    private void checkForAssignmentNotification(){
        List<Assignment> assignments = new ArrayList<>();
        assignments = ((SAMApplication) getApplication()).getAllAssignments();

        if (assignments.size() > 0) {
            for (Assignment a : assignments) {
                if ((a.getDueDate() - System.currentTimeMillis()) <= TimeUnit.DAYS.toMillis(a.getPeriod())) {
                    if(!a.isNotified()) {
                        //****for some reason it will send a toast from here but not a notification****
                        String dueDate = df.format(a.getDueDate());
                        String [] dateTime = dueDate.split(" ");

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
    }

    //Spending Report Notification
    private void checkForStartOfMonth(){
        String currentDate = df.format(System.currentTimeMillis());
        String transactionDate = "";

        if(currentDate.startsWith("01") && !firstDateChecked){
            //Toast.makeText(this, "First Day of month " + currentDate, Toast.LENGTH_LONG).show();

            //Notification
            final Intent NotifyIntent = new Intent(getApplicationContext(), MainActivity.class);
            NotifyIntent.addFlags(NotifyIntent.FLAG_ACTIVITY_CLEAR_TOP);
            final PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, NotifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            final NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            final Notification notification = new Notification.Builder(getApplicationContext())
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle("New Spending Report")
                    .setContentText("You have a spending report from the last month")
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .build();
            manager.notify(ID, notification);

            //Make spending report
            List<Transaction> transactions = new ArrayList<>();
            transactions = ((SAMApplication) getApplication()).getAllTransactions();

            String [] splitCurrentDate = currentDate.split("/");

            //get the current month
            int currentMonth = Integer.parseInt(splitCurrentDate[1]);
            int previousMonth = 0;

            //sets the previous month to one less then the current month, unless it is jan (1) then it sets it to 12
            if(currentMonth == 1){
                previousMonth = 12;
            }
            else{
                previousMonth = currentMonth--;
            }

            splitCurrentDate[1] = "";

            //sets the current month to the previous month to check if the transactions are in the correct month. adds a 0 on the start of months 1-9 to match formatting
            if(!(previousMonth > 9)){
                splitCurrentDate[1] = "0";
            }
            splitCurrentDate[1] += String.valueOf(previousMonth);

            String [] splitTransactionDate;

            int numberOfCategories = ((SAMApplication) getApplication()).getAllCategory().size();
            double [] categoryAmount = new double[numberOfCategories];

            //make sure that there are transactions
            if (transactions.size() > 0) {
                for (Transaction t : transactions) {
                    transactionDate = df.format(t.getDate());
                    splitTransactionDate = transactionDate.split("/");

                    //make sure that the transaction has the same month and year as the previous month
                    if (splitCurrentDate[1] == splitTransactionDate[1] && splitCurrentDate[2].substring(0, 1) == splitTransactionDate[2].substring(0, 1)){
                        for (int i = 0; i <= categoryAmount.length; i++){
                            if(t.getCategory() == i){
                                categoryAmount [i] += t.getAmount();
                            }
                        }
                    }
                }

                List<Category> categories = new ArrayList<>();
                categories = ((SAMApplication) getApplication()).getAllCategory();

                for (Category c : categories){
                    ((SAMApplication) getApplication()).addRecord(currentDate, c.getName(), c.getGoal(), categoryAmount[c.getId()]);
                }
            }

            //check off that the past month has been done
            firstDateChecked = true;
        }

        //then as soon as it's not the first day of the month, unchecks the first day so it can flag the next time it is the first day
        if (!currentDate.startsWith("01")){
            firstDateChecked = false;
        }
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