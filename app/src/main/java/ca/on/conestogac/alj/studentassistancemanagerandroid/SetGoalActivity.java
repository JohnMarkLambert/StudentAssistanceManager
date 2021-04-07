package ca.on.conestogac.alj.studentassistancemanagerandroid;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SetGoalActivity extends AppCompatActivity {

    private Button btnSetGoal;
    private Button btnCancelSet;
    private Button btnDeleteGoal;
    private TextView txtGoalName;
    private TextView txtGoalAmount;
    private TextInputLayout inlGoalName;
    private TextInputLayout inlGoalAmount;
    private String goalName;
    private double goalAmount;
    private int cId;
    private boolean isEditing;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setgoal);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        btnSetGoal = findViewById(R.id.btnSetGoal);
        btnCancelSet = findViewById(R.id.btnCancelSet);
        btnDeleteGoal = findViewById(R.id.btnDeleteGoal);
        txtGoalName = findViewById(R.id.txtGoalName);
        txtGoalAmount = findViewById(R.id.txtGoalAmount);
        inlGoalName = findViewById(R.id.inlGoalName);
        inlGoalAmount = findViewById(R.id.inlGoalAmount);

        isEditing = false;

        cId = getIntent().getExtras().getInt("cId");
        if (cId != 0) {
            populateFields();
            isEditing = true;
            btnSetGoal.setText("Update Goal");
            btnDeleteGoal.setVisibility(View.VISIBLE);
        }
        else {
            btnDeleteGoal.setVisibility(View.INVISIBLE);
        }

        btnSetGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateData()) {
                    createGoal();
                }
            }
        });

        btnCancelSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnDeleteGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((SAMApplication) getApplication()).deleteCategory(cId);
            }
        });
    }

    private void createGoal(){
        Log.i("Goal info:", "\nName: " + goalName + "\nAmount: " + goalAmount);

        if (isEditing){
            ((SAMApplication) getApplication()).updateCategory(cId, goalName, goalAmount);
        }
        else{
            ((SAMApplication) getApplication()).createCategory(goalName, goalAmount);
        }

        Toast.makeText(this, "Budget Goal Created", Toast.LENGTH_SHORT).show();



        //intent.putExtra("darkTheme", darkTheme);

        this.finish();
    }

    private boolean validateData(){
        boolean validData = true;



        //Name
        goalName = txtGoalName.getText().toString();

        List<Category> categories = new ArrayList<>();
        categories = ((SAMApplication) getApplication()).getAllCategory();

        boolean duplicateGoal = false;

        for (Category c : categories){
            if(c.getName().equals(goalName) && !isEditing){
                duplicateGoal = true;
                inlGoalName.setError(goalName + " is already a budget goal");
                validData = false;
            }
        }

        if(goalName.length() == 0){
            inlGoalName.setError("Budget Goal must have name");
            validData = false;
        }
        else if(goalName.length() > 15){
            inlGoalName.setError("Budget Goal name must be 15 or less characters");
            validData = false;
        }
        else{
            inlGoalName.setError(null);
        }

        //Amount
        if (String.valueOf(txtGoalAmount.getText()).length() == 0){
            inlGoalAmount.setError("Must have a budget goal amount");
            validData = false;
        }
        else if (Double.parseDouble(txtGoalAmount.getText().toString()) < 0){
            inlGoalAmount.setError("Budget Goal Amount cannot be negative");
            validData = false;
        }
        else{
            goalAmount = Double.parseDouble(txtGoalAmount.getText().toString());
            inlGoalAmount.setError(null);
        }

        return validData;
    }

    private void populateFields(){
        Category category = ((SAMApplication) getApplication()).getCategory(cId);
        setTitle("Update Budget Goal");

        txtGoalName.setText(category.getName());
        txtGoalAmount.setText(String.valueOf(category.getGoal()));

        validateData();
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
