package ca.on.conestogac.alj.studentassistancemanagerandroid;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddTransactionActivity extends AppCompatActivity {

    private TextView txtTransactionDate, txtAmount, txtNotes;
    private Spinner spnPayment, spnGoals;
    private Button btnSaveTran, btnTranCancel;
    private DatePickerDialog.OnDateSetListener datePickerListener;
    private String transactionDate, notes;
    private int amount, payment, category;
    private long epochTime;
    private Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        txtTransactionDate = findViewById(R.id.txtTransactionDate);
        txtAmount = findViewById(R.id.txtAmount);
        spnPayment = findViewById(R.id.spnPayment);
        spnGoals = findViewById(R.id.spnGoals);
        txtNotes = findViewById(R.id.txtNotes);

        btnSaveTran = findViewById(R.id.btnSaveTran);
        btnTranCancel = findViewById(R.id.btnTranCancel);


        //TEMPORARY REPLACE LATER
        String[] categoryArray = {"Category1", "Category2"};
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoryArray);
        spnGoals.setAdapter(categoryAdapter);

        String[] paymentArray = {"Payment1", "Payment2"};
        ArrayAdapter<String> paymentAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, paymentArray);
        spnPayment.setAdapter(paymentAdapter);


//Not tested, spinners filled with placeholders for now
//        List<Category> categoryList = ((SAMApplication) getApplication()).getAllCategory();
//        List<String> categoryListString = new ArrayList<String>();
//        for (int i=0; i < categoryList.size(); i++) {
//            categoryListString.add(categoryList.get(i).getName());
//        }
//        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoryListString);
//        spnGoals.setAdapter(categoryAdapter);
//
//        List<String> paymentList = ((SAMApplication) getApplication()).getPaymentTypes();
//        ArrayAdapter<String> paymentAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, paymentList);
//        spnPayment.setAdapter(paymentAdapter);

        btnSaveTran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateData()) {
                    addTransaction();
                }
            }
        });

        btnTranCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
            }
        });

        txtTransactionDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();

                int year, month, day;
                if (txtTransactionDate.getText().length() != 0) {
                    String[] dateArray = String.valueOf(txtTransactionDate.getText()).split("/");
                    year = Integer.parseInt(dateArray[0]);
                    month = Integer.parseInt(dateArray[1]) -1;
                    day = Integer.parseInt(dateArray[2]);
                } else {
                    year = calendar.get(Calendar.YEAR);
                    month = calendar.get(Calendar.MONTH);
                    day = calendar.get(Calendar.DAY_OF_MONTH);
                }


                DatePickerDialog dateDialog = new DatePickerDialog(
                        AddTransactionActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        datePickerListener,
                        year, month, day);
                dateDialog.show();
            }
        });

        datePickerListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                transactionDate = year + "/" + month + "/" + day;
                Log.i("DATE1", transactionDate);
                txtTransactionDate.setText(transactionDate);
            }
        };
    }

    private void addTransaction() {
        Log.i("Transaction Info", "\nDate: " + transactionDate +
                "\nAmount: " + amount +
                "\nPayment: " + payment +
                "\nGoal: " + category +
                "\nNotes: " + notes);

        ((SAMApplication) getApplication()).addTransaction(epochTime, amount, payment,category, notes);

        super.finish();

//Not tested with transaction detail so commented out for now
//        List<Transaction> transactions;
//        transactions = ((SAMApplication) getApplication()).getAllTransactions();
//
//        intent = new Intent(getApplicationContext(), TransactionDetailActivity.class);
//        intent.putExtra("tId", transactions.get(transactions.size() - 1).getId());
//        startActivity(intent);
    }

    private boolean validateData() {
        boolean goodData = true;

        //Transaction Date Validation
        Date today = new Date();
        SimpleDateFormat dfDate = new SimpleDateFormat("yyyy/MM/dd");

        if (txtTransactionDate.getText().length() == 0) {
            txtTransactionDate.setError("Select a transaction date");
            goodData = false;
        } else if (txtTransactionDate.getText().length() != 0) {
            try {
                epochTime = dfDate.parse(txtTransactionDate.getText().toString()).getTime();
            } catch (ParseException e) {
                txtTransactionDate.setError("Select a transaction date");
                goodData = false;
            }
            if (epochTime > today.getTime()) {
                txtTransactionDate.setError("Date cannot be in the future");
                goodData = false;
            } else {
                txtTransactionDate.setError(null);
            }
        }

        //Amount Validation

        if (txtAmount.getText().length() == 0) {
            txtAmount.setError("Enter an amount");
            goodData = false;
        }
        else {
            amount = Integer.parseInt(txtAmount.getText().toString());
            txtAmount.setError(null);
        }

        category = spnGoals.getSelectedItemPosition();
        payment = spnPayment.getSelectedItemPosition();
        notes = txtNotes.getText().toString();

        Log.i("Data Validation","Data is valid: " + goodData);

        return goodData;
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
