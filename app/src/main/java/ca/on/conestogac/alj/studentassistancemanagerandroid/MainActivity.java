package ca.on.conestogac.alj.studentassistancemanagerandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //remove later
        ((SAMApplication) getApplication()).deleteAllAssignments();
        ((SAMApplication) getApplication()).addAssignment("test 1", 1614555000,
                0, 1, 0, "This is a test");
        ((SAMApplication) getApplication()).addAssignment("test 2", 1614295800,
                1.5, 1, 0, "this is also a test");
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
//            case R.id.menuBudget:
//                intent = new Intent(getApplicationContext(), BudgetActivity.class);
//                //intent.putExtra("darkTheme", darkTheme);
//                startActivity(intent);
//                break;
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
}