package ca.on.conestogac.alj.studentassistancemanagerandroid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PreferenceManager.setDefaultValues(this, R.xml.root_preferences, false);
        sp = PreferenceManager.getDefaultSharedPreferences(this);

//        if (sp.getBoolean("themeType", false)) {
//            //Dark Theme
//            setTheme();
//        } else {
//            //Light Theme
//            setTheme();
//        }


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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
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
            case R.id.menuCalendar:
                intent = new Intent(getApplicationContext(), CalendarActivity.class);
                //intent.putExtra("darkTheme", darkTheme);
                startActivity(intent);
                break;
            case R.id.menuBudget:
                intent = new Intent(getApplicationContext(), BudgetHomeActivity.class);
                //intent.putExtra("darkTheme", darkTheme);
                startActivity(intent);
                break;
            case R.id.menuSettings:
                intent = new Intent(getApplicationContext(), SettingsActivity.class);
                //intent.putExtra("darkTheme", darkTheme);
                startActivity(intent);
                break;
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