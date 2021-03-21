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
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class SetGoalActivity extends AppCompatActivity {

    private Button btnSetGoal;
    private Button btnCancelSet;
    private TextView txtSetGoalName;
    private TextView txtSetGoalAmount;
    private TextInputEditText lblGoalName;
    private TextInputEditText lblGoalAmount;
    private String goalName;
    private double goalAmount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setgoal);

        btnSetGoal = findViewById(R.id.btnSetGoal);
        btnCancelSet = findViewById(R.id.btnCancelSet);
        txtSetGoalName = findViewById(R.id.txtSetGoalName);
        txtSetGoalAmount = findViewById(R.id.txtSetGoalAmount);
        lblGoalName = findViewById(R.id.lblGoalName);
        lblGoalAmount = findViewById(R.id.lblGoalAmount);

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
                Intent intent;
                intent = new Intent(getApplicationContext(), GoalsActivity.class);
                //intent.putExtra("darkTheme", darkTheme);
                startActivity(intent);
            }
        });
    }

    private void createGoal(){
        Log.i("Goal info:", "\nName: " + goalName + "\nAmount: " + goalAmount);

        /*
        ((SAMApplication) getApplication()).createCategory(goalName, goalAmount);
         */
        Toast.makeText(this, "Budget Goal Created", Toast.LENGTH_SHORT).show();


        Intent intent;
        intent = new Intent(getApplicationContext(), GoalsActivity.class);
        //intent.putExtra("darkTheme", darkTheme);
        startActivity(intent);
    }

    private boolean validateData(){
        boolean validData = true;

        //Name
        goalName = txtSetGoalName.getText().toString();
        if(goalName.length() == 0){
            lblGoalName.setError("Budget Goal must have name");
            validData = false;
        }
        if(goalName.length() > 15){
            lblGoalName.setError("Budget Goal name must be 15 or less characters");
            validData = false;
        }

        //Amount
        if (txtSetGoalAmount.getText().length() == 0){
            lblGoalAmount.setError("Must have a budget goal amount");
            validData = false;
        }
        else{
            goalAmount = Double.parseDouble(txtSetGoalAmount.getText().toString());
        }


        return validData;
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
