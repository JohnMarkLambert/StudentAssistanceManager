package ca.on.conestogac.alj.studentassistancemanagerandroid;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class TransactionDetailActivity extends AppCompatActivity {

    private TextView txtTranDDate, txtTranDAmount, txtTranDPayment, txtTranDGoal, txtTranDNotes;
    private Transaction transaction;
    private Category category;
    private int tId;
    private DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm", Locale.getDefault());

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_detail);

        txtTranDDate = findViewById(R.id.txtTranDDate);
        txtTranDAmount = findViewById(R.id.txtTranDAmount);
        txtTranDPayment = findViewById(R.id.txtTranDPayment);
        txtTranDGoal = findViewById(R.id.txtTranDGoal);
        txtTranDNotes = findViewById(R.id.txtTranDNotes);

        tId = getIntent().getExtras().getInt("tId");
        transaction = ((SAMApplication) getApplication()).getTransaction(tId);


        //category = ((SAMApplication) getApplication()).getCategory(transaction.getCategory());


        String date = df.format(transaction.getDate());
        txtTranDDate.setText(date);
        txtTranDAmount.setText(String.valueOf(transaction.getAmount()));

        //txtTranDPayment.setText(transaction.getPaymentType());
        //txtTranDGoal.setText(category.getName());
        //Just have these until payment table issues are resolved, and category is implemented into transaction creation
        txtTranDPayment.setText("FILLER");
        txtTranDGoal.setText("FILLER");

        if (transaction.getNotes().length() != 0){
            txtTranDNotes.setText(transaction.getNotes());
        }
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
