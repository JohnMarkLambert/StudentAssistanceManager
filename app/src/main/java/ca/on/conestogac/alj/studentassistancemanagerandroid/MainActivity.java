package ca.on.conestogac.alj.studentassistancemanagerandroid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import androidx.preference.PreferenceManager;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Preferences need to go first
        PreferenceManager.setDefaultValues(this, R.xml.root_preferences, false);
        sp = PreferenceManager.getDefaultSharedPreferences(this);

//        if (sp.getBoolean("themeType", false)) {
//            //Dark Theme
//            setTheme();
//        } else {
//            //Light Theme
//            setTheme();
//        }


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_calendar,R.id.nav_assignments, R.id.nav_budget,R.id.nav_transactions, R.id.nav_reports, R.id.nav_goals, R.id.nav_settings)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        //dummy Data
        List<Record> recordList = ((SAMApplication) getApplication()).getAllRecords();
        if (recordList == null | recordList.isEmpty()) {
            ((SAMApplication) getApplication()).addRecord("01/21", "Food", 1250.50, 1255.99);
            ((SAMApplication) getApplication()).addRecord("01/21", "Rent", 1535.20, 1535.20);
            ((SAMApplication) getApplication()).addRecord("01/21", "Utilities", 80.85, 70.36);
            ((SAMApplication) getApplication()).addRecord("01/21", "Transportation", 125.65, 120.32);
            ((SAMApplication) getApplication()).addRecord("02/21", "Food", 1250.50, 1199.99);
            ((SAMApplication) getApplication()).addRecord("02/21", "Rent", 1535.20, 1535.20);
            ((SAMApplication) getApplication()).addRecord("02/21", "Utilities", 80.85, 90.00);
            ((SAMApplication) getApplication()).addRecord("02/21", "Transportation", 125.65, 150.32);
            ((SAMApplication) getApplication()).addRecord("12/20", "Food", 1550.50, 1549.99);
            ((SAMApplication) getApplication()).addRecord("12/20", "Rent", 1450.00, 1535.20);
            ((SAMApplication) getApplication()).addRecord("12/20", "Utilities", 80.85, 80.00);
            ((SAMApplication) getApplication()).addRecord("12/20", "Transportation", 125.65, 110.34);
            ((SAMApplication) getApplication()).addRecord("12/20", "Xmas Presents", 300.00, 301.45);
        }

        List<List<String>> categoryList = ((SAMApplication) getApplication()).getPaymentTypes();
        if (categoryList ==  null | categoryList.isEmpty()) {
            ((SAMApplication) getApplication()).addPaymentType("Debit");
            ((SAMApplication) getApplication()).addPaymentType("Credit");
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        boolean result = true;
        Intent intent;

        switch (item.getItemId()) {
            case R.id.menuHome:
                intent = new Intent(getApplicationContext(), MainActivity.class);
                //intent.putExtra("darkTheme", darkTheme);
                startActivity(intent);
                break;
//            case R.id.menuSettings:
//                intent = new Intent(getApplicationContext(), SettingsActivity.class);
//                //intent.putExtra("darkTheme", darkTheme);
//                startActivity(intent);
//                break;
            default:
                result = super.onOptionsItemSelected(item);
                break;
        }
        return result;
    }

    @Override
    protected void onStop() {
        startService(new Intent(getApplicationContext(), NotificationService.class));
        super.onStop();
    }
}