package ca.on.conestogac.alj.studentassistancemanagerandroid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    private SharedPreferences sp;
    private boolean darkTheme;

    private DateFormat df = new SimpleDateFormat("dd/MM/yy hh:mm aa", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_calendar, R.id.nav_assignments, R.id.nav_budget, R.id.nav_transactions, R.id.nav_reports, R.id.nav_goals, R.id.nav_settings)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // Made it a method so it's just one line to comment out...
        insertDummyData();

        List<List<String>> categoryList = ((SAMApplication) getApplication()).getPaymentTypes();
        if (categoryList == null | categoryList.isEmpty()) {
            ((SAMApplication) getApplication()).addPaymentType("Debit");
            ((SAMApplication) getApplication()).addPaymentType("Credit");
        }
    }

    private void insertDummyData(){
        List<Record> recordList = ((SAMApplication) getApplication()).getAllRecords();
        List<Transaction> transactionList = ((SAMApplication) getApplication()).getAllTransactions();
        List<Category> categoryList = ((SAMApplication) getApplication()).getAllCategory();
        List<Assignment> assignmentList = ((SAMApplication) getApplication()).getAllAssignments();

        // ASSIGNMENTS
        if (assignmentList == null || assignmentList.isEmpty()){
            String timeDue = "10:00 PM";
            String EtimeDue = "05:00 PM";
            String J1DATE = "30/04/21";
            String J2DATE = "10/05/21";
            String J3DATE = "20/05/21";
            String M1DATE = "25/04/21";
            String M2DATE = "07/05/21";
            String M3DATE = "15/05/21";
            String EDATE = "15/05/21";
            try {
                ((SAMApplication) getApplication()).addAssignment("Java Assignment 1", df.parse(J1DATE + " " + timeDue).getTime(), 0, 2, "");
                ((SAMApplication) getApplication()).addAssignment("Java Assignment 2", df.parse(J2DATE + " " + timeDue).getTime(), 0, 2, "");
                ((SAMApplication) getApplication()).addAssignment("Java Assignment 3", df.parse(J3DATE + " " + timeDue).getTime(), 0, 2, "");
                ((SAMApplication) getApplication()).addAssignment("Mobile Assignment 1", df.parse(M1DATE + " " + timeDue).getTime(), 0, 2, "");
                ((SAMApplication) getApplication()).addAssignment("Mobile Assignment 2", df.parse(M2DATE + " " + timeDue).getTime(), 0, 2, "");
                ((SAMApplication) getApplication()).addAssignment("Mobile Assignment 3", df.parse(M3DATE + " " + timeDue).getTime(), 0, 2, "");
                ((SAMApplication) getApplication()).addAssignment("English Essay 1", df.parse(EDATE + " " + EtimeDue).getTime(), 0, 2, "");
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        // BUDGET GOALS
        if (categoryList == null || categoryList.isEmpty()){
            ((SAMApplication) getApplication()).createCategory("Food", 500);
            ((SAMApplication) getApplication()).createCategory("Transportation", 150);
            ((SAMApplication) getApplication()).createCategory("Utilities", 100);
            ((SAMApplication) getApplication()).createCategory("Rent", 1600);
        }

        // TRANSACTIONS
        if (transactionList == null || transactionList.isEmpty()){
            String T1 = "05/02/21"; //food
            String T2 = "10/02/21"; //trans
            String T3 = "20/02/21"; //f
            String T4 = "25/02/21"; //t
            String T5 = "28/02/21"; //u
            String T6 = "28/02/21"; //r

            String T7 = "02/03/21"; //f
            String T8 = "11/03/21"; //t
            String T9 = "18/03/21"; //f
            String T10 = "25/03/21"; //t
            String T11 = "31/03/21"; //u
            String T12 = "31/03/21"; //r

            String T13 = "02/04/21"; //f
            String T14 = "08/04/21"; //t
            try {
                ((SAMApplication) getApplication()).addTransaction(df.parse(T1).getTime(), 250, 1,4, "Zehrs Waterloo");
                ((SAMApplication) getApplication()).addTransaction(df.parse(T2).getTime(), 45, 1,4, "Canadian Tire");
                ((SAMApplication) getApplication()).addTransaction(df.parse(T3).getTime(), 100, 1,4, "Costco");
                ((SAMApplication) getApplication()).addTransaction(df.parse(T4).getTime(), 30, 1,4, "Train");
                ((SAMApplication) getApplication()).addTransaction(df.parse(T5).getTime(), 105, 1,4, "");
                ((SAMApplication) getApplication()).addTransaction(df.parse(T6).getTime(), 1600, 1,4, "");

                ((SAMApplication) getApplication()).addTransaction(df.parse(T7).getTime(), 200, 1,4, "Walmart");
                ((SAMApplication) getApplication()).addTransaction(df.parse(T8).getTime(), 50, 1,4, "Train");
                ((SAMApplication) getApplication()).addTransaction(df.parse(T9).getTime(), 200, 1,4, "Zehrs");
                ((SAMApplication) getApplication()).addTransaction(df.parse(T10).getTime(), 75, 1,4, "Petro Canada");
                ((SAMApplication) getApplication()).addTransaction(df.parse(T11).getTime(), 95, 1,4, "");
                ((SAMApplication) getApplication()).addTransaction(df.parse(T12).getTime(), 1600, 1,4, "");

                ((SAMApplication) getApplication()).addTransaction(df.parse(T13).getTime(), 15, 1,4, "Mc Ds");
                ((SAMApplication) getApplication()).addTransaction(df.parse(T14).getTime(), 20, 1,4, "Bus");
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        // RECORDS
        if (recordList == null || recordList.isEmpty()){
            ((SAMApplication) getApplication()).addRecord("02/21", "Food", 500.00, 350.00);
            ((SAMApplication) getApplication()).addRecord("02/21", "Rent", 1600.00, 1600.00);
            ((SAMApplication) getApplication()).addRecord("02/21", "Utilities", 100.00, 105.00);
            ((SAMApplication) getApplication()).addRecord("02/21", "Transportation", 150.00, 75.00);

            ((SAMApplication) getApplication()).addRecord("03/21", "Food", 500.00, 400.00);
            ((SAMApplication) getApplication()).addRecord("03/21", "Rent", 1600.00, 1600.00);
            ((SAMApplication) getApplication()).addRecord("03/21", "Utilities", 100.00, 95.00);
            ((SAMApplication) getApplication()).addRecord("03/21", "Transportation", 150.00, 125.00);
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    @Override
    protected void onStop() {
        startService(new Intent(getApplicationContext(), NotificationService.class));
        super.onStop();
    }
}

