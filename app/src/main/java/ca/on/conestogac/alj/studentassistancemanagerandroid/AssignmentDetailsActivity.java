package ca.on.conestogac.alj.studentassistancemanagerandroid;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
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

        txtAssignName.setText(assignment.getName());
        date = new Date((long)assignment.getDueDate()*1000);
        txtDueDate.setText(df.format(date));
        txtDuration.setText(assignment.getDuration() + " Hours");
        ckbComplete.setChecked(assignment.isComplete());
        txtDesc.setText(assignment.getDesc());
        calDetails.setDate(assignment.getDueDate());

//        btnEdit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //code to go to Edit Event/Assignment view.
//            }
//        });

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
