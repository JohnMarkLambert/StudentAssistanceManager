package ca.on.conestogac.alj.studentassistancemanagerandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class CreateEventActivity extends AppCompatActivity {

    private Button btnCreate;
    private TextView txtDuration, txtEventName, txtDescription, txtTimeDue, txtDateDue;
    private TextInputLayout inlEventName, inlDateDue, inlTimeDue, inlDuration;

    private DatePickerDialog.OnDateSetListener datePickerListener;
    private TimePickerDialog.OnTimeSetListener timePickerListener;
    private String name, description;
    private Double duration;
    private String dueDate;
    private Intent intent;
    private String timeDue;
    private Long epochTime;
    int aId;
    private Boolean isEditing;

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
        txtDateDue = findViewById(R.id.txtDateDue);

        inlEventName = findViewById(R.id.inlEventName);
        inlDateDue = findViewById(R.id.inlDueDate);
        inlTimeDue = findViewById(R.id.inlTimeDue);
        inlDuration = findViewById(R.id.inlDuration);

        isEditing = false;

        aId = getIntent().getExtras().getInt("aId");
        if (aId != 0) {
            populateFields();
            isEditing = true;
            btnCreate.setText("Update");
        }


        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (validateData()) {
                    createEvent();
                }

            }
        });

        txtDateDue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar= Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                Log.d("in txtDueDate listener", "in txtDueDate listener");
                DatePickerDialog dateDialog = new DatePickerDialog(
                        CreateEventActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        datePickerListener,
                        year, month, day);
                dateDialog.show();
            }
        });

        datePickerListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month +1;
                dueDate = year + "/" + month + "/" + day;
                txtDateDue.setText(dueDate);
            }
        };

        txtTimeDue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar= Calendar.getInstance();
                int hr = calendar.get(Calendar.HOUR_OF_DAY);
                int min = calendar.get(Calendar.MINUTE);
                TimePickerDialog timeDialog = new TimePickerDialog(
                        CreateEventActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        timePickerListener,
                        hr,min, false);
                timeDialog.show();
            }
        });

        timePickerListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hours, int minutes) {
                timeDue = hours + ":" + minutes;
                Log.d("time", timeDue+"d");
                txtTimeDue.setText(timeDue);
            }
        };

    }



    private void createEvent() {

        Log.i("Event created",
                "\nName: " + name +
                "\nDue date: " + dueDate +
                "\nDuration: " + duration +
                "\nPeriod: " + 2 +
                "\nComplete: " + "0" +
                "\nDescription: " + description);

        if (isEditing) {
            ((SAMApplication) getApplication()).updateAssignment(aId, name, epochTime, duration, 2, 0 ,description);
            Toast.makeText(this, "Event updated", Toast.LENGTH_SHORT).show();
        }
        else {
            ((SAMApplication) getApplication()).addAssignment(name, epochTime, duration, 2, 0, description);
            Toast.makeText(this, "Event added", Toast.LENGTH_SHORT).show();
        }

        super.finish();

        List<Assignment> assignments = new ArrayList<>();
        assignments = ((SAMApplication) getApplication()).getAllAssignments();

        intent = new Intent(getApplicationContext(), AssignmentDetailsActivity.class);
        intent.putExtra("aId", assignments.get(assignments.size() - 1).getId());
        startActivity(intent);

    }


    private boolean validateData() {

        boolean goodData = true;
        //Name
        name = txtEventName.getText().toString();
        if (name.length() == 0) {
            inlEventName.setError("Event must have name");
            goodData =false;
        }
        else if (name.length() > 15) {
            inlEventName.setError("Event name must be under 15 characters");
            goodData =false;
        }
        else {
            inlEventName.setError(null);
        }

        //Duration
        if (txtDuration.getText().length() == 0) {
            Toast.makeText(this,"Duration set to 0", Toast.LENGTH_SHORT).show();
            txtDuration.setText("0");
            duration  = 0.0d;
        }
        else {
            duration = Double.parseDouble(txtDuration.getText().toString());
            Double dec = duration - duration.intValue();
            if (dec % 0.25 != 0d) {
                inlDuration.setError("Duration must be in intervals of .25");
                goodData =false;
            }
        }
        //Time
        if (txtTimeDue.getText().length() ==0) {
            inlTimeDue.setError("Enter the time due");
            goodData =false;
        }
        else {
            inlTimeDue.setError(null);
        }

        String date = dueDate + " " + timeDue;
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd kk:mm");


        //Date
        Date today = new Date();

        if(txtDateDue.getText().length() == 0) {
            inlDateDue.setError("Enter the due date");
            goodData = false;
        }
        else if (txtTimeDue.getText().length() != 0) {
            try {
                epochTime = df.parse(date).getTime();
            } catch (ParseException e) {
                inlDateDue.setError("Select a due date");
                goodData = false;
            }
            if (epochTime < today.getTime()) {
                inlDateDue.setError("Date cannot be in the past");
                goodData = false;
            }
            else {
                inlDateDue.setError(null);
            }
        }
        else {
            inlDateDue.setError(null);
        }

        description = txtDescription.getText().toString();

        return goodData;
    }

    private void populateFields() {
        Log.d("Edit", "In populate fields function");
        Assignment assignment = ((SAMApplication) getApplication()).getAssignment(aId);
        Calendar cal = Calendar.getInstance();

        txtEventName.setText(assignment.getName());
        SimpleDateFormat dfDate = new SimpleDateFormat("yyyy/MM/dd");
        txtDateDue.setText(dfDate.format(assignment.getDueDate()));
        dueDate = String.valueOf(txtDateDue.getText());
        SimpleDateFormat dfTime = new SimpleDateFormat("kk:mm");

        txtTimeDue.setText(dfTime.format(assignment.getDueDate()));
        timeDue = String.valueOf(txtTimeDue.getText());
        txtDuration.setText(String.valueOf(assignment.getDuration()));
        txtDescription.setText(assignment.getDesc());



    }
}