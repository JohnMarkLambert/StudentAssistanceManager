package ca.on.conestogac.alj.studentassistancemanagerandroid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalendarActivity extends AppCompatActivity {



    private Button btnCreateEvent;
    private CalendarView calendar;
    private SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    private String selectedDate;

    private Button btnShowAll;
    private TextView txtCEventName;
    private TextView txtCDueDate;
    private TextView txtCDuration;
    private TextView txtCDescription;

    private List<Assignment> assignments;
    private Assignment displayAssignment;

    private DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm", Locale.getDefault());
    private Date dueDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        assignments = ((SAMApplication) getApplication()).getAllAssignments();

        btnCreateEvent = findViewById(R.id.btnCreateEvent);

        calendar = findViewById(R.id.calendarView);
        txtCEventName = findViewById(R.id.txtMMEventName);
        txtCDueDate = findViewById(R.id.txtMMDueDate);
        txtCDuration = findViewById(R.id.txtMMDuration);
        txtCDescription = findViewById(R.id.txtMMDescription);

        if (!assignments.isEmpty()) {
            for (Assignment a : assignments) {
                if (a.isComplete() == false) {
                    displayAssignment = a;
                    break;
                }
            }

            txtCEventName.setText(displayAssignment.getName());
            dueDate = new Date((long) displayAssignment.getDueDate() );
            txtCDueDate.setText(df.format(dueDate));
            txtCDuration.setText(displayAssignment.getDuration() + " Hours");
            txtCDescription.setText(displayAssignment.getDesc());
        }

        btnCreateEvent.setOnClickListener(new View.OnClickListener() {
            Intent intent;
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), CreateEventActivity.class);
                intent.putExtra("aId", 0);
                //intent.putExtra("darkTheme", darkTheme);
                startActivity(intent);

            }
        });


        btnShowAll = findViewById(R.id.btnShowAll);

        btnShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AllAssignmentActivity.class);
                startActivity(intent);
            }
        });

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
}
