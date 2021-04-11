package ca.on.conestogac.alj.studentassistancemanagerandroid;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class TransactionDetailActivity extends AppCompatActivity {

    private Button btnTranDelete, btnTranEdit;
    private int tId;
    private Intent intent;

    private TextView txtTranDDate, txtTranDAmount, txtTranDPayment, txtTranDGoal, txtTranDNotes;
    private Transaction transaction;
    private Category category;

    private DateFormat df = new SimpleDateFormat("dd/MM/yy", Locale.getDefault());

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_detail);

        btnTranDelete = findViewById(R.id.btnTranDelete);
        btnTranEdit = findViewById(R.id.btnTranEdit);

        txtTranDDate = findViewById(R.id.txtTranDDate);
        txtTranDAmount = findViewById(R.id.txtTranDAmount);
        txtTranDPayment = findViewById(R.id.txtTranDPayment);
        txtTranDGoal = findViewById(R.id.txtTranDGoal);
        txtTranDNotes = findViewById(R.id.txtTranDNotes);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        tId = getIntent().getExtras().getInt("tId");

        transaction = ((SAMApplication) getApplication()).getTransaction(tId);

        String date = df.format(transaction.getDate());
        txtTranDDate.setText(date);
        txtTranDAmount.setText(String.valueOf(String.format("%.2f", transaction.getAmount())));

        category = ((SAMApplication) getApplication()).getCategory(transaction.getCategory());
        txtTranDGoal.setText(category.getName());

        String paymentType = String.valueOf(transaction.getCategory());
        if (paymentType.equals("1")){
            txtTranDPayment.setText("Debit");
        }
        else{
            txtTranDPayment.setText("Credit");
        }


        if (transaction.getNotes().length() != 0){
            txtTranDNotes.setText(transaction.getNotes());
        }

        DialogInterface.OnClickListener dialogListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int result) {
                switch (result) {
                    case DialogInterface.BUTTON_POSITIVE:
                        ((SAMApplication) getApplication()).deleteTransaction(tId);
//                        intent = new Intent(getApplicationContext(), AllTransactionActivity.class);
                        finish();
//                        startActivity(intent);
                }
            }
        };

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.Theme_StudentAssistanceManagerAndroid));
        dialogBuilder.setMessage("Delete this transaction?").setPositiveButton("Yes", dialogListener)
                .setNegativeButton("No", dialogListener);

        btnTranDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                dialogBuilder.show();
            }
        });

        btnTranEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                intent = new Intent(getApplicationContext(), AddTransactionActivity.class);
                intent.putExtra("tId", tId);
                startActivity(intent);
            }
        });
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
