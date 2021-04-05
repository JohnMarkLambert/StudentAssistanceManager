package ca.on.conestogac.alj.studentassistancemanagerandroid.ui.Budget;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

import ca.on.conestogac.alj.studentassistancemanagerandroid.AddTransactionActivity;
import ca.on.conestogac.alj.studentassistancemanagerandroid.R;
import ca.on.conestogac.alj.studentassistancemanagerandroid.SAMApplication;
import ca.on.conestogac.alj.studentassistancemanagerandroid.Transaction;

public class BudgetFragment extends Fragment {

    private BudgetViewModel mViewModel;
    private Button btnAddTransaction;
    private TextView txtBHDate, txtBHAmount, txtBHPayment, txtBHNotes;
    public static BudgetFragment newInstance() {
        return new BudgetFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_budget, container, false);

        btnAddTransaction = view.findViewById(R.id.btnAddTransaction);

        txtBHDate = view.findViewById(R.id.txtBHDate);
        txtBHAmount = view.findViewById(R.id.txtBHAmount);
        txtBHPayment = view.findViewById(R.id.txtBHPayment);
        txtBHNotes = view.findViewById(R.id.txtBHNotes);

        btnAddTransaction.setOnClickListener(new View.OnClickListener() {
            Intent intent;
            @Override
            public void onClick(View view) {
                intent = new Intent(getActivity().getApplicationContext(), AddTransactionActivity.class);
                intent.putExtra("tId", "");
                //intent.putExtra("darkTheme", darkTheme);
                startActivity(intent);
            }
        });


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(BudgetViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onResume() {
        super.onResume();

        List<Transaction> transactions;
        transactions = ((SAMApplication) getActivity().getApplication()).getAllTransactions();
        if (transactions.size() != 0) {

            Transaction latestTransaction = ((SAMApplication) getActivity().getApplication()).getTransaction(transactions.size());
            SimpleDateFormat dfDate = new SimpleDateFormat("yyyy/MM/dd");

            txtBHDate.setText(dfDate.format(latestTransaction.getDate()));

            txtBHAmount.setText(String.valueOf(latestTransaction.getAmount()));
            txtBHPayment.setText("Placeholder");
            txtBHNotes.setText(latestTransaction.getNotes());

        }

    }
}