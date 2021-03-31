package ca.on.conestogac.alj.studentassistancemanagerandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AllTransactionActivity extends AppCompatActivity {

    private Button btnNewTransaction;
    private List<Transaction> transactions;
    private Spinner dateSpinner;
    private String sortBy;
    private LinearLayout ll;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_transactions);

        try {
            transactions = ((SAMApplication) getApplication()).getAllTransactions();
        } catch(Exception ex){

        }

        btnNewTransaction = findViewById(R.id.btnATNewTrans);
        dateSpinner = (Spinner) findViewById(R.id.tranDateSpinner);
        ll = (LinearLayout) findViewById(R.id.llShowTransactions);
        populateSpinner();

        btnNewTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddTransactionActivity.class);
                intent.putExtra("tId", 0);
                startActivity(intent);
            }
        });

        dateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sortBy = dateSpinner.getSelectedItem().toString();
                showTransactions(sortBy);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                showTransactions("all");
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

    private void populateSpinner() {
        List<String> checking = new ArrayList<>();
        checking.add("all");
        if (transactions != null) {
            for (Transaction t : transactions) {
                String tDate = new SimpleDateFormat("MM/yyyy").format(new Date(t.getDate()));
                if (!checking.contains(tDate)) {
                    checking.add(tDate);
                }
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, checking);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dateSpinner.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void showTransactions(String month){
        ll.removeAllViews();
        if (transactions != null) {
            for (Transaction t : transactions) {

                String checkDate = new SimpleDateFormat("MM/yyyy").format(new Date(t.getDate()));
                if (month == "all" | month.equals(checkDate)) {
                    CardView newCard = new CardView(this);
                    TextView tAmount = new TextView(this);
                    TextView tDate = new TextView(this);
                    //TextView tPayment = new TextView(this);
                    LinearLayout tLayout = new LinearLayout(this);

                    String addDate = new SimpleDateFormat("dd/MM/yy").format(new Date(t.getDate()));
                    String addAmount = Double.toString(t.getAmount());
                    tDate.setText(addDate);
                    tAmount.setText("$" + addAmount);
                    //tPayment.setText(((SAMApplication) getApplication()).getPaymentType(t.getPaymentType()));
                    tLayout.setOrientation(LinearLayout.VERTICAL);
                    tLayout.addView(tDate);
                    tLayout.addView(tAmount);
                   //tLayout.addView(tPayment);


                    newCard.addView(tLayout);
                    newCard.setCardElevation(10);
                    newCard.setPadding(10, 10, 10, 10);
                    newCard.setRadius(15);
                    newCard.setContentPadding(10, 10, 10, 10);

                    ll.addView(newCard);

                    newCard.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getApplicationContext(), TransactionDetailActivity.class);
                            intent.putExtra("tId", t.getId());
                            startActivity(intent);
                        }
                    });
                }
            }
        }
    }
}
