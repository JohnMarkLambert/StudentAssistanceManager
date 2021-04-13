package ca.on.conestogac.alj.studentassistancemanagerandroid;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
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
import androidx.preference.PreferenceManager;

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
    private int payment, tId;
    private Category category;
    private double amount;
    private long epochTime;
    private Intent intent;
    private boolean isEditing;
    private SimpleDateFormat dfDate;
    private TextView txtGoalHelper;
    private TextView txtPaymentHelper;
    private TextInputLayout inlTransactionDate, inlTransactionAmount, inlTransactionCategory;
    private SharedPreferences sharedPref;
    private String currencyType;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_StudentAssistanceManagerAndroid);
        setContentView(R.layout.activity_add_transaction);


        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_baseline_close);
        }

        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        currencyType = sharedPref.getString("currencyType", "$");

        txtTransactionDate = findViewById(R.id.txtTransactionDate);
        txtAmount = findViewById(R.id.txtAmount);
        spnPayment = findViewById(R.id.spnPayment);
        spnGoals = findViewById(R.id.spnGoals);
        txtNotes = findViewById(R.id.txtNotes);


        txtGoalHelper = findViewById(R.id.txtGoalHelper);
        txtPaymentHelper = findViewById(R.id.txtPaymentHelper);

        btnSaveTran = findViewById(R.id.btnSaveTran);

        inlTransactionDate = findViewById(R.id.inlTransactionDate);
        inlTransactionAmount = findViewById(R.id.inlTransactionAmount);
        inlTransactionCategory = findViewById(R.id.inlTransactionCategory);

        inlTransactionAmount.setHint("Amount (" + currencyType + ")*");

        isEditing = false;
        dfDate = new SimpleDateFormat("dd/MM/yyyy");

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

        txtGoalHelper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((SAMApplication) getApplication()).getAllCategory().size() !=0) {
                    spnGoals.performClick();
                }
            }
        });

        txtPaymentHelper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                spnPayment.performClick();

            }
        });

        spnGoals.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                category = (Category) parent.getAdapter().getItem(position);
                if (((SAMApplication) getApplication()).getAllCategory() != null) {
                    txtGoalHelper.setText(category.getName());
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spnPayment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                payment =  position;


                switch (payment) {
                    case 0: txtPaymentHelper.setText("Debit");
                        break;
                    case 1:
                        txtPaymentHelper.setText("Credit");
                        break;
                }
                }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btnSaveTran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateData()) {
                    addTransaction();
                }
            }
        });


        txtTransactionDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();

                int year, month, day;
                if (txtTransactionDate.getText().length() != 0) {
                    String[] dateArray = String.valueOf(txtTransactionDate.getText()).split("/");
                    day = Integer.parseInt(dateArray[0]);
                    month = Integer.parseInt(dateArray[1]) - 1;
                    year = Integer.parseInt(dateArray[2]);
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
                transactionDate = day + "/" + month + "/" + year;
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
            inlTransactionDate.setError("Select a transaction date");
            goodData = false;
        } else if (txtTransactionDate.getText().length() != 0) {
            try {
                epochTime = dfDate.parse(txtTransactionDate.getText().toString()).getTime();
            } catch (ParseException e) {
                inlTransactionDate.setError("Select a transaction date");
                goodData = false;
            }
            if (epochTime > today.getTime()) {
                inlTransactionDate.setError("Date cannot be in the future");
                goodData = false;
            } else {
                inlTransactionDate.setError(null);
            }
        }

        //Amount Validation

        if (txtAmount.getText().length() == 0) {
            inlTransactionAmount.setError("Enter an amount");
            goodData = false;
        } else {
            amount = Double.parseDouble(txtAmount.getText().toString());
            inlTransactionAmount.setError(null);
        }

        //Category Validation

        if (spnGoals.getSelectedItem() == null) {
            goodData = false;
            inlTransactionCategory.setError("Select a category");
        } else {
            inlTransactionCategory.setError(null);
            category = (Category) spnGoals.getSelectedItem();
        }


        payment = spnPayment.getSelectedItemPosition() + 1 ;
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

