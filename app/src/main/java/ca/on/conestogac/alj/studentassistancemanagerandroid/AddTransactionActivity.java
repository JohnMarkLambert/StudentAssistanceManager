package ca.on.conestogac.alj.studentassistancemanagerandroid;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
    private int payment, tId;
    private Category category;
    private double amount;
    private long epochTime;
    private Intent intent;
    private boolean isEditing;
    private SimpleDateFormat dfDate;
    private TextView txtGoalError;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);


        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        txtTransactionDate = findViewById(R.id.txtTransactionDate);
        txtAmount = findViewById(R.id.txtAmount);
        spnPayment = findViewById(R.id.spnPayment);
        spnGoals = findViewById(R.id.spnGoals);
        txtNotes = findViewById(R.id.txtNotes);
        txtGoalError = findViewById(R.id.txtGoalError);

        btnSaveTran = findViewById(R.id.btnSaveTran);
        btnTranCancel = findViewById(R.id.btnTranCancel);

        isEditing = false;
        dfDate = new SimpleDateFormat("yyyy/MM/dd");

        //Temp
        String[] paymentArray = {"Debit", "Credit"};
        ArrayAdapter<String> paymentAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, paymentArray);
        spnPayment.setAdapter(paymentAdapter);


        List<Category> categoryList = ((SAMApplication) getApplication()).getAllCategory();
        ArrayAdapter<Category> categoryAdapter = new ArrayAdapter<Category>(this, android.R.layout.simple_spinner_item, categoryList);
        spnGoals.setAdapter(categoryAdapter);
        try {
            tId = getIntent().getExtras().getInt("tId");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (tId != 0) {
            populateFields();
            isEditing = true;
            btnSaveTran.setText("Update");
        }

//Not tested, spinners filled with placeholders for now
//        List<List<String>> paymentList = ((SAMApplication) getApplication()).getPaymentTypes();
//        List<String> paymentListString = new ArrayList<String>();
//        for (int i=0; i < paymentList.size(); i++) {
//            paymentListString.add(paymentList.get(0).get(i));
//        }
//        ArrayAdapter<String> paymentAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, paymentListString);
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
                    month = Integer.parseInt(dateArray[1]) - 1;
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

        if (isEditing) {
            ((SAMApplication) getApplication()).updateTransaction(tId, epochTime, amount, payment, category.getId(), notes);
            Toast.makeText(this, "Transaction updated", Toast.LENGTH_SHORT).show();
        } else {
            ((SAMApplication) getApplication()).addTransaction(epochTime, amount, payment, category.getId(), notes);
            Toast.makeText(this, "Transaction created", Toast.LENGTH_SHORT).show();
        }


        super.finish();

        List<Transaction> transactions;
        transactions = ((SAMApplication) getApplication()).getAllTransactions();

        intent = new Intent(getApplicationContext(), TransactionDetailActivity.class);


        int i;
        for (i = 0; i < transactions.size(); i++) {
            if (transactions.get(i).getId() == transactions.size()) {
                break;
            }
        }
        intent.putExtra("tId", transactions.get(i).getId());
        startActivity(intent);
    }

    private boolean validateData() {
        boolean goodData = true;

        //Transaction Date Validation
        Date today = new Date();

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
        } else {
            amount = Double.parseDouble(txtAmount.getText().toString());
            txtAmount.setError(null);
        }

        //Category Validation

        if (spnGoals.getSelectedItem() == null) {
            goodData = false;
            txtGoalError.setError("Select a category");
        } else {
            txtGoalError.setError(null);
            category = (Category) spnGoals.getSelectedItem();
        }


        payment = spnPayment.getSelectedItemPosition();
        notes = txtNotes.getText().toString();

        Log.i("Data Validation", "Data is valid: " + goodData);

        return goodData;
    }

    private void populateFields() {
        Transaction transaction = ((SAMApplication) getApplication()).getTransaction(tId);
        Calendar cal = Calendar.getInstance();
        setTitle("Edit Transaction");

        txtAmount.setText(String.valueOf(transaction.getAmount()));

        Adapter adapter = spnGoals.getAdapter();

        List<Category> categories = new ArrayList<Category>(adapter.getCount());
        for (int i = 0; i < adapter.getCount(); i++) {
            Category c = (Category) adapter.getItem(i);
            if (transaction.getCategory() == c.getId()) {
                spnGoals.setSelection(i);
            }
        }

        spnPayment.setSelection(transaction.getPaymentType());

        txtTransactionDate.setText(dfDate.format(transaction.getDate()));

        txtNotes.setText(transaction.getNotes());

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

