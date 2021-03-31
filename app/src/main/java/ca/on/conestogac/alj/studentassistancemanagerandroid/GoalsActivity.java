package ca.on.conestogac.alj.studentassistancemanagerandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.util.ArrayList;
import java.util.List;

public class GoalsActivity extends AppCompatActivity {

    private Button btnNewGoal;
    private LinearLayout ll;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goals);

        //Add new goal button
        btnNewGoal = findViewById(R.id.btnNewGoal);
        btnNewGoal.setOnClickListener(new View.OnClickListener() {
            Intent intent;
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), SetGoalActivity.class);
                //intent.putExtra("darkTheme", darkTheme);
                intent.putExtra("cId", 0);
                startActivity(intent);
            }
        });

        //All budget goal cards
        ll = (LinearLayout) findViewById(R.id.llShowGoals);
        ll.removeAllViews();

        List<Category> categories = new ArrayList<>();
        categories = ((SAMApplication) getApplication()).getAllCategory();

        for (Category c : categories){
            CardView newCard = new CardView(this);
            TextView cAmount = new TextView(this);
            TextView cName = new TextView(this);
            LinearLayout gLayout = new LinearLayout(this);

            String addName = c.getName();
            String addAmount = Double.toString(c.getGoal());
            cName.setText(addName);
            cAmount.setText("$" + addAmount);
            gLayout.setOrientation(LinearLayout.VERTICAL);
            gLayout.addView(cName);
            gLayout.addView(cAmount);


            newCard.addView(gLayout);
            newCard.setCardElevation(10);
            newCard.setPadding(10, 10, 10, 10);
            newCard.setRadius(15);
            newCard.setContentPadding(10, 10, 10, 10);

            ll.addView(newCard);


            newCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), SetGoalActivity.class);
                    intent.putExtra("cId", c.getId());
                    startActivity(intent);
                }
            });


        }

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
            case R.id.menuSettings:
                intent = new Intent(getApplicationContext(), SettingsActivity.class);
                //intent.putExtra("darkTheme", darkTheme);
                startActivity(intent);
                break;
            default:
                result = super.onOptionsItemSelected(item);
                break;
        }
        return result;
    }
}
