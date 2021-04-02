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

import ca.on.conestogac.alj.studentassistancemanagerandroid.AddTransactionActivity;
import ca.on.conestogac.alj.studentassistancemanagerandroid.AllTransactionActivity;
import ca.on.conestogac.alj.studentassistancemanagerandroid.CreateEventActivity;
import ca.on.conestogac.alj.studentassistancemanagerandroid.R;

public class BudgetFragment extends Fragment {

    private BudgetViewModel mViewModel;
    private Button btnAddTransaction, btnShowAllTransaction;
    public static BudgetFragment newInstance() {
        return new BudgetFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_budget, container, false);

        btnAddTransaction = view.findViewById(R.id.btnAddTransaction);
        btnShowAllTransaction = view.findViewById(R.id.btnShowAllTransaction);

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

        btnShowAllTransaction.setOnClickListener(new View.OnClickListener() {
            Intent intent;
            @Override
            public void onClick(View view) {
                intent = new Intent(getActivity().getApplicationContext(), AllTransactionActivity.class);
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

}