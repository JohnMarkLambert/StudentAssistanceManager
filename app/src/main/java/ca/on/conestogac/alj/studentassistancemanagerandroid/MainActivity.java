package ca.on.conestogac.alj.studentassistancemanagerandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //dummy Data
//        ((SAMApplication) getApplication()).addRecord("01/2021", "Food", 1250.50, 1255.99);
//        ((SAMApplication) getApplication()).addRecord("01/2021", "Rent", 1535.20, 1535.20);
//        ((SAMApplication) getApplication()).addRecord("01/2021", "Utilities", 80.85, 70.36);
//        ((SAMApplication) getApplication()).addRecord("01/2021", "Transportation", 125.65, 120.32);
//        ((SAMApplication) getApplication()).addRecord("02/2021", "Food", 1250.50, 1199.99);
//        ((SAMApplication) getApplication()).addRecord("02/2021", "Rent", 1535.20, 1535.20);
//        ((SAMApplication) getApplication()).addRecord("02/2021", "Utilities", 80.85, 90.00);
//        ((SAMApplication) getApplication()).addRecord("02/2021", "Transportation", 125.65, 150.32);
//        ((SAMApplication) getApplication()).addRecord("12/2020", "Food", 1550.50, 1549.99);
//        ((SAMApplication) getApplication()).addRecord("12/2020", "Rent", 1450.00, 1535.20);
//        ((SAMApplication) getApplication()).addRecord("12/2020", "Utilities", 80.85, 80.00);
//        ((SAMApplication) getApplication()).addRecord("12/2020", "Transportation", 125.65, 110.34);
//        ((SAMApplication) getApplication()).addRecord("12/2020", "Xmas Presents", 300.00, 301.45);
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