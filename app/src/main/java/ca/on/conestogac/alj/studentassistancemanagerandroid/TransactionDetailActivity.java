package ca.on.conestogac.alj.studentassistancemanagerandroid;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class TransactionDetailActivity extends AppCompatActivity {

    private Button btnTranDelete, btnTranEdit;
    private int tId;
    private Intent intent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_detail);

        btnTranDelete = findViewById(R.id.btnTranDelete);
        btnTranEdit = findViewById(R.id.btnTranEdit);

        tId = getIntent().getExtras().getInt("tId");

        DialogInterface.OnClickListener dialogListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int result) {
                switch (result) {
                    case DialogInterface.BUTTON_POSITIVE:
                        ((SAMApplication) getApplication()).deleteTransaction(tId);
                        intent = new Intent(getApplicationContext(), AllTransactionActivity.class);
                        finish();
                        startActivity(intent);
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
