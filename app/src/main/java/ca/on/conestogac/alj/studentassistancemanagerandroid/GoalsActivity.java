package ca.on.conestogac.alj.studentassistancemanagerandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class GoalsActivity extends AppCompatActivity {

    private Button btnNewGoal;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goals);

        btnNewGoal = findViewById(R.id.btnNewGoal);
        btnNewGoal.setOnClickListener(new View.OnClickListener() {
            Intent intent;
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), SetGoalActivity.class);
                //intent.putExtra("darkTheme", darkTheme);
                startActivity(intent);
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.budgetmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        boolean result = true;
        Intent intent;

        switch (item.getItemId()) {
            case R.id.BMenuHome:
                intent = new Intent(getApplicationContext(), MainActivity.class);
                //intent.putExtra("darkTheme", darkTheme);
                startActivity(intent);
                break;
            case R.id.BMenuBudget:
                intent = new Intent(getApplicationContext(), BudgetHomeActivity.class);
                //intent.putExtra("darkTheme", darkTheme);
                startActivity(intent);
                break;
            case R.id.BMenuGoals:
                intent = new Intent(getApplicationContext(), GoalsActivity.class);
                //intent.putExtra("darkTheme", darkTheme);
                startActivity(intent);
                break;
            case R.id.BMenuTrans:
                intent = new Intent(getApplicationContext(), AddTransactionActivity.class);
                //intent.putExtra("darkTheme", darkTheme);
                startActivity(intent);
                break;
            case R.id.BMenuRecords:
                intent = new Intent(getApplicationContext(), BudgetRecordsActivity.class);
                //intent.putExtra("darkTheme", darkTheme);
                startActivity(intent);
                break;
            case R.id.BMenuTransactions:
                intent = new Intent(getApplicationContext(), AllTransactionActivity.class);
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
