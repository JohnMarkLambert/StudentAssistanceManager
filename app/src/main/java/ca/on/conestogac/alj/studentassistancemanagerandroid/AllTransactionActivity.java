package ca.on.conestogac.alj.studentassistancemanagerandroid;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
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
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static ca.on.conestogac.alj.studentassistancemanagerandroid.R.id;
import static ca.on.conestogac.alj.studentassistancemanagerandroid.R.layout;

public class AllTransactionActivity extends AppCompatActivity {

    private Button btnNewTransaction;
    private List<Transaction> transactions;
    private Spinner dateSpinner;
    private String sortBy;
    private LinearLayout ll;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_all_transactions);

        transactions = ((SAMApplication) getApplication()).getAllTransactions();

        btnNewTransaction = findViewById(id.btnATNewTrans);
        dateSpinner = (Spinner) findViewById(id.tranDateSpinner);
        ll = (LinearLayout) findViewById(id.llShowTransactions);
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

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        boolean result = true;
        Intent intent;

        switch (item.getItemId()) {
            case id.BMenuHome:
                intent = new Intent(getApplicationContext(), MainActivity.class);
                //intent.putExtra("darkTheme", darkTheme);
                startActivity(intent);
                break;
            case id.BMenuBudget:
                intent = new Intent(getApplicationContext(), BudgetHomeActivity.class);
                //intent.putExtra("darkTheme", darkTheme);
                startActivity(intent);
                break;
            case id.BMenuGoals:
                intent = new Intent(getApplicationContext(), GoalsActivity.class);
                //intent.putExtra("darkTheme", darkTheme);
                startActivity(intent);
                break;
            case id.BMenuTrans:
                intent = new Intent(getApplicationContext(), AddTransactionActivity.class);
                //intent.putExtra("darkTheme", darkTheme);
                startActivity(intent);
                break;
            case id.BMenuRecords:
                intent = new Intent(getApplicationContext(), BudgetRecordsActivity.class);
                //intent.putExtra("darkTheme", darkTheme);
                startActivity(intent);
                break;
            case id.BMenuTransactions:
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

    private void populateSpinner() {
        List<String> checking = new ArrayList<>();
        @SuppressLint("ResourceType") List<String> items = Arrays.asList(getResources().getString(R.array.transSpinnerItems));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dateSpinner.setAdapter(adapter);
        for (Transaction t : transactions) {
            String tDate = new SimpleDateFormat("MM/yyyy").format(new Date(t.getDate()));
            checking.add(tDate);
            if (!checking.contains(tDate)) {
                adapter.add(tDate);
            }
        }
        adapter.notifyDataSetChanged();
    }

    private void showTransactions(String month){


        for (Transaction t : transactions) {

            String checkDate = new SimpleDateFormat("MM/yyyy").format(new Date(t.getDate()));
            if (month == "all" || checkDate == month) {
                CardView newCard = new CardView(this);
                TextView tAmount = new TextView(this);
                TextView tDate = new TextView(this);
                TextView tPayment = new TextView(this);
                LinearLayout tLayout = new LinearLayout(this);

                String addDate = new SimpleDateFormat("dd/MM/yy HH:mm").format(new Date(t.getDate()));
                String addAmount = Double.toString(t.getAmount());
                tDate.setText(addDate);
                tAmount.setText(addAmount);
                tPayment.setText(((SAMApplication) getApplication()).getPaymentType(t.getPaymentType()));
                tLayout.addView(tDate);
                tLayout.addView(tAmount);
                tLayout.addView(tPayment);

                newCard.addView(tLayout);
                newCard.setCardElevation(10);
                newCard.setPadding(10,10,10,10);
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
