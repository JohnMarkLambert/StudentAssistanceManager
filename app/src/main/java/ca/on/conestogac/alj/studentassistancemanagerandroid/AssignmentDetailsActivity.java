package ca.on.conestogac.alj.studentassistancemanagerandroid;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AssignmentDetailsActivity extends AppCompatActivity {

    private Assignment assignment;
    private int aId;

    private CalendarView calDetails;
    private TextView txtAssignName;
    private TextView txtDueDate;
    private TextView txtDuration;
    private TextView txtDesc;
    private Button btnEditADetails;
    private Button btnCancelAssignment;
    private Button btnCompleteAssignment;
    private Intent intent;

    private DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm", Locale.getDefault());
    private Date date;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_details);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        try {
            aId = getIntent().getExtras().getInt("aId");
        } catch (Exception e) {}


        assignment = ((SAMApplication) getApplication()).getAssignment(aId);

        calDetails = findViewById(R.id.calDetails);
        txtAssignName = findViewById(R.id.txtAssignName);
        txtDueDate = findViewById(R.id.txtDueDate);
        txtDuration = findViewById(R.id.txtDuration);
        txtDesc = findViewById(R.id.txtDesc);
        btnEditADetails = findViewById(R.id.btnEditADetails);
        btnCancelAssignment = findViewById(R.id.btnCancelAssignment);
        btnCompleteAssignment = findViewById(R.id.btnCompleteAssignment);

        txtAssignName.setText(assignment.getName());
        date = new Date((long)assignment.getDueDate());
        txtDueDate.setText(df.format(date));
        txtDuration.setText(assignment.getDuration() + " Hours");
        txtDesc.setText(assignment.getDesc());
        calDetails.setDate(assignment.getDueDate());

        btnEditADetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateEventActivity.class);
                intent.putExtra("aId", assignment.getId());
                startActivity(intent);
            }
        });


        DialogInterface.OnClickListener dialogListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int result) {
                switch (result) {
                    case DialogInterface.BUTTON_POSITIVE:
                        ((SAMApplication) getApplication()).deleteAssignment(aId);
//                        intent = new Intent(getApplicationContext(), AllAssignmentActivity.class);
                        finish();
//                        startActivity(intent);
                }
            }
        };

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.Theme_StudentAssistanceManagerAndroid));
        dialogBuilder.setMessage("Cancel this event?").setPositiveButton("Yes", dialogListener)
                .setNegativeButton("No", dialogListener);

        btnCancelAssignment.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                dialogBuilder.show();
            }
        });


        //Marking Assignment as Complete
        DialogInterface.OnClickListener dialogListenerComplete = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int result) {
                switch (result) {
                    case DialogInterface.BUTTON_POSITIVE:
                        ((SAMApplication) getApplication()).updateNotified(aId, true);
//                        intent = new Intent(getApplicationContext(), AllAssignmentActivity.class);
                        finish();
//                        startActivity(intent);
                }
            }
        };

        AlertDialog.Builder dialogBuilderComplete = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.Theme_StudentAssistanceManagerAndroid));
        dialogBuilderComplete.setMessage("Mark Assignment as complete?").setPositiveButton("Yes", dialogListenerComplete)
                .setNegativeButton("No", dialogListenerComplete);

        btnCompleteAssignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBuilderComplete.show();
            }
        });



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
