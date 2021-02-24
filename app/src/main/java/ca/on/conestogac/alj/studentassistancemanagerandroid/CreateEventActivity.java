package ca.on.conestogac.alj.studentassistancemanagerandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CreateEventActivity extends AppCompatActivity {

    private Button btnCreate;
    private TextView txtDuration, txtEventName, txtDescription, txtTimeDue;
    private SharedPreferences sharedPref;

    private String name;
    private String description;
    Double duration;
    String dueDate;
    private Intent intent;
    String timeDue;
    Long epochTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        intent = new Intent(this, CreateEventActivity.class);

        btnCreate = findViewById(R.id.btnCreate);

        txtEventName = findViewById(R.id.txtEventName);
        txtDuration = findViewById(R.id.txtDuration);
        txtDescription = findViewById(R.id.txtDescription);
        txtTimeDue = findViewById(R.id.txtTimeDue);

        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = txtEventName.getText().toString();

                description = txtDescription.getText().toString();

                Bundle bundle = getIntent().getExtras();
                dueDate = bundle.getString("selectedDate", "");

                if (validateData()) {
                    createEvent();
                }
            }
        });
    }

    private void createEvent() {

        Log.i("Event created",
                "\nName: " + name +
                "\nDue date: " + dueDate +
                "\nDuration: " + duration +
                "\nPeriod: " + 2 +
                "\nComplete: " + "0" +
                "\nDescription: " + description);


        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd kk:mm");
        String date = dueDate + " " + timeDue;
        try {
            Log.i("date", String.valueOf(df.parse(date)));
            epochTime = df.parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.i("epoch", epochTime + "");
        ((SAMApplication) getApplication()).addAssignment(name, epochTime, duration, 2, 0, description);
        Toast.makeText(this, "Event added", Toast.LENGTH_SHORT).show();

        super.finish();

        List<Assignment> assignments = new ArrayList<>();
        assignments = ((SAMApplication) getApplication()).getAllAssignments();

        intent = new Intent(getApplicationContext(), AssignmentDetailsActivity.class);
        intent.putExtra("aId", assignments.get(assignments.size() - 1).getId());
        startActivity(intent);

    }


    private boolean validateData() {

        boolean goodData = true;
        if (name.length() == 0) {
            txtEventName.setError("Event must have name");
            goodData =false;
        }
        if (name.length() > 15) {
            txtEventName.setError("Event name must be under 15 characters");
            goodData =false;
        }
        if (txtDuration.getText().length() == 0) {
            Toast.makeText(this,"Duration set to 0", Toast.LENGTH_SHORT).show();
            txtDuration.setText("0");
            duration  = 0.0d;
        }
        else {
            duration = Double.parseDouble(txtDuration.getText().toString());
            Double dec = duration - duration.intValue();
            if (dec % 0.25 != 0d) {
                txtDuration.setError("Duration must be in intervals of .25");
                goodData =false;
            }
        }

        if (txtTimeDue.getText().length() ==0) {
            txtTimeDue.setError("Enter the time due");
            goodData =false;
        }
        else {
            Pattern timePattern = Pattern.compile("^(2[0-3]|[01]?[0-9]):([0-5]?[0-9])$");
            timeDue = txtTimeDue.getText().toString();
            Matcher matcher = timePattern.matcher(timeDue);
            if (!matcher.find()){
                txtTimeDue.setError("Time must be in valid format");
                goodData =false;
            }
        }

        return goodData;
    }
}