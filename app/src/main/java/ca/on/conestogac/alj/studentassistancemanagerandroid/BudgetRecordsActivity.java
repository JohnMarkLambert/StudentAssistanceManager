package ca.on.conestogac.alj.studentassistancemanagerandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.util.ArrayList;
import java.util.List;

public class BudgetRecordsActivity extends AppCompatActivity {

    private List<Record> records;
    private LinearLayout ll;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budgetrecords);

        try {
            records = ((SAMApplication) getApplication()).getAllRecords();
        } catch (Exception ex) {

        }

        ll = (LinearLayout)findViewById(R.id.LLRecords);

        showRecords();
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


    public void showRecords(){
        if (records != null){
            List<String> dates = new ArrayList<>();
            for (Record record: records) {
                if (!dates.contains(record.getDate())){
                    dates.add(record.getDate());
                }
            }
            for (String date: dates){
                double goal = 0.0;
                double total = 0.0;
                double diff = 0.0;
                for (Record record: records) {
                    if (record.getDate() == date) {
                        goal += record.getGoalAmount();
                        total += record.getAmountSpent();
                        diff += record.getDifference();
                    }
                }
                CardView newCard = new CardView(this);
                TextView txtDate = new TextView(this);
                TextView goalTitle = new TextView(this);
                TextView txtGoal = new TextView(this);
                TextView totalTitle = new TextView(this);
                TextView txtTotal = new TextView(this);
                TextView diffTitle = new TextView(this);
                TextView txtDiff = new TextView(this);
                LinearLayout newLL = new LinearLayout(this);

                txtDate.setText(date);
                goalTitle.setText("Goal:");
                txtGoal.setText(Double.toString(goal));
                totalTitle.setText("Total:");
                txtTotal.setText(Double.toString(total));
                diffTitle.setText("Difference");
                txtDiff.setText(Double.toString(diff));
                if (diff > 0) {
                    txtDiff.setTextColor(getResources().getColor(R.color.red, null));
                } else if (diff <= 0) {
                    txtDiff.setTextColor(getResources().getColor(R.color.green, null));
                }
                newLL.addView(txtDate);
                newLL.addView(goalTitle);
                newLL.addView(txtGoal);
                newLL.addView(totalTitle);
                newLL.addView(txtTotal);
                newLL.addView(diffTitle);
                newLL.addView(txtDiff);
                newCard.addView(newLL);

                ll.addView(newCard);

                newCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), RecordDetailsActivity.class);
                        intent.putExtra("rId", date);
                        startActivity(intent);
                    }
                });
            }
        }
    }


}
