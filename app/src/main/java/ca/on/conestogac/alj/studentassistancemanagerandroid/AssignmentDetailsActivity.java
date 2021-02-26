package ca.on.conestogac.alj.studentassistancemanagerandroid;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
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
    private CheckBox ckbComplete;
    private TextView txtDesc;
    private Button btnEdit;
    private Button btnCancel;
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

        aId = getIntent().getExtras().getInt("aId");

        assignment = ((SAMApplication) getApplication()).getAssignment(aId);

        calDetails = findViewById(R.id.calDetails);
        txtAssignName = findViewById(R.id.txtAssignName);
        txtDueDate = findViewById(R.id.txtDueDate);
        txtDuration = findViewById(R.id.txtDuration);
        ckbComplete = findViewById(R.id.ckbComplete);
        txtDesc = findViewById(R.id.txtDesc);
        btnEdit = findViewById(R.id.btnEditADetails);
        btnCancel = findViewById(R.id.btnCancelEvent);

        txtAssignName.setText(assignment.getName());
        date = new Date((long)assignment.getDueDate());
        txtDueDate.setText(df.format(date));
        txtDuration.setText(assignment.getDuration() + " Hours");
        ckbComplete.setChecked(assignment.isComplete());
        txtDesc.setText(assignment.getDesc());
        calDetails.setDate(assignment.getDueDate());

        btnEdit.setOnClickListener(new View.OnClickListener() {
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
                        intent = new Intent(getApplicationContext(), AllAssignmentActivity.class);
                        startActivity(intent);
                }
            }
        };

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.Theme_StudentAssistanceManagerAndroid));
        dialogBuilder.setMessage("Cancel this event?").setPositiveButton("Yes", dialogListener)
                .setNegativeButton("No", dialogListener);

        btnCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                dialogBuilder.show();
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
