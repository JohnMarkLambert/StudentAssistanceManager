package ca.on.conestogac.alj.studentassistancemanagerandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

    int aId;
    private Button btnCreate, btnCancelCreation;
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
    private Boolean isEditing;
    SimpleDateFormat dfTime, df;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        intent = new Intent(this, CreateEventActivity.class);

        btnCreate = findViewById(R.id.btnCreate);
        btnCancelCreation = findViewById(R.id.btnCancelCreation);

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

        dfTime = new SimpleDateFormat("hh:mm aa");
        df = new SimpleDateFormat("yyyy/MM/dd hh:mm aa");

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
                Calendar calendar = Calendar.getInstance();
                if (aId != 0) {
                    calendar.setTimeInMillis(epochTime);
                }
                int year, month, day;
                if (txtDateDue.getText().length() != 0) {
                    String[] dateArray = String.valueOf(txtDateDue.getText()).split("/");
                    year = Integer.parseInt(dateArray[0]);
                    month = Integer.parseInt(dateArray[1]) -1;
                    day = Integer.parseInt(dateArray[2]);
                } else {
                    year = calendar.get(Calendar.YEAR);
                    month = calendar.get(Calendar.MONTH);
                    day = calendar.get(Calendar.DAY_OF_MONTH);
                }

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
                month = month + 1;
                dueDate = year + "/" + month + "/" + day;
                Log.i("DATE1", dueDate);
                txtDateDue.setText(dueDate);
            }
        };

        txtTimeDue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                if (aId != 0) {
                    calendar.setTimeInMillis(epochTime);
                }
                int hr, min;
                String am_pm;

                if (txtTimeDue.getText().length() != 0) {
                    String[] timeArray = String.valueOf(txtTimeDue.getText()).split(":| ");
                    hr = Integer.parseInt(timeArray[0]);
                    min = Integer.parseInt(timeArray[1]);
                    am_pm = timeArray[2]; //?
                } else {
                    hr = calendar.get(Calendar.HOUR_OF_DAY);
                    min = calendar.get(Calendar.MINUTE);
                }

                TimePickerDialog timeDialog = new TimePickerDialog(
                        CreateEventActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        timePickerListener,
                        hr, min, false);
                timeDialog.show();
            }
        });

        timePickerListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hours, int minutes) {
                String am_pm = "AM";
                if (timePicker.getHour() > 12) {
                    hours = timePicker.getHour() - 12;
                    am_pm = "PM";
                }
                Date time = null;

                timeDue = hours + ":" + timePicker.getMinute() + " " + am_pm;
                try {
                    time = dfTime.parse(timeDue);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                txtTimeDue.setText(dfTime.format(time));

            }
        };
        btnCancelCreation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }


    private void createEvent() {

        Log.i("Event info:",
                "\nName: " + name +
                        "\nDue date: " + dueDate +
                        "\nDuration: " + duration +
                        "\nPeriod: " + 2 +
                        "\nComplete: " + "0" +
                        "\nDescription: " + description);

        if (isEditing) {
            ((SAMApplication) getApplication()).updateAssignment(aId, name, epochTime, duration, 2, 0, description);
            Toast.makeText(this, "Event updated", Toast.LENGTH_SHORT).show();
        } else {
            ((SAMApplication) getApplication()).addAssignment(name, epochTime, duration, 2, 0, description);
            Toast.makeText(this, "Event added", Toast.LENGTH_SHORT).show();
        }

        super.finish();

        List<Assignment> assignments;
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
            goodData = false;
        } else if (name.length() > 15) {
            inlEventName.setError("Event name must be under 15 characters");
            goodData = false;
        } else {
            inlEventName.setError(null);
        }

        //Duration
        if (txtDuration.getText().length() == 0) {
            Toast.makeText(this, "Duration set to 0", Toast.LENGTH_SHORT).show();
            txtDuration.setText("0");
            duration = 0.0d;
        } else {
            duration = Double.parseDouble(txtDuration.getText().toString());
            Double dec = duration - duration.intValue();
            if (dec % 0.25 != 0d) {
                inlDuration.setError("Duration must be in intervals of .25");
                goodData = false;
            }
        }
        //Time
        if (txtTimeDue.getText().length() == 0) {
            inlTimeDue.setError("Enter the time due");
            goodData = false;
        } else {
            inlTimeDue.setError(null);
        }
        String date = dueDate + " " + timeDue;



        //Date
        Date today = new Date();

        if (txtDateDue.getText().length() == 0) {
            inlDateDue.setError("Enter the due date");
            goodData = false;
        } else if (txtTimeDue.getText().length() != 0) {
            try {
                epochTime = df.parse(date).getTime();
            } catch (ParseException e) {
                inlDateDue.setError("Select a due date");
                goodData = false;
            }
            if (epochTime < today.getTime()) {
                inlDateDue.setError("Date cannot be in the past");
                goodData = false;
            } else {
                inlDateDue.setError(null);
            }
        } else {
            inlDateDue.setError(null);
        }

        description = txtDescription.getText().toString();
        return goodData;
    }

    private void populateFields() {
        Assignment assignment = ((SAMApplication) getApplication()).getAssignment(aId);
        Calendar cal = Calendar.getInstance();
        setTitle(R.string.Edit_Event);

        txtEventName.setText(assignment.getName());

        SimpleDateFormat dfDate = new SimpleDateFormat("yyyy/MM/dd");

        txtDateDue.setText(dfDate.format(assignment.getDueDate()));
        dueDate = String.valueOf(txtDateDue.getText());

        txtTimeDue.setText(dfTime.format(assignment.getDueDate()));
        timeDue = String.valueOf(txtTimeDue.getText());

        txtDuration.setText(String.valueOf(assignment.getDuration()));

        txtDescription.setText(assignment.getDesc());

        try {
            epochTime = df.parse(dueDate + " " + timeDue).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        validateData();


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        boolean result = true;
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                break;
            default:
                result = super.onOptionsItemSelected(item);
                break;
        }
        return result;
    }

}