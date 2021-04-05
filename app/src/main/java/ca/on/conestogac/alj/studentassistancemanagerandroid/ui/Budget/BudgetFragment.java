package ca.on.conestogac.alj.studentassistancemanagerandroid.ui.Budget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import ca.on.conestogac.alj.studentassistancemanagerandroid.AddTransactionActivity;
import ca.on.conestogac.alj.studentassistancemanagerandroid.AllTransactionActivity;
import ca.on.conestogac.alj.studentassistancemanagerandroid.R;

public class BudgetFragment extends Fragment {

    private BudgetViewModel mViewModel;
    private Button btnAddTransaction, btnShowAllTransaction;
    public static BudgetFragment newInstance() {
        return new BudgetFragment();
    }
    private Switch swtchangeChart;

    private SharedPreferences sp;
    private String currency;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_budget, container, false);

        sp = this.getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        currency = sp.getString("currencyType", "$");

//        if (sp.getBoolean("themeType", false)) {
//            //Dark Theme
//            setTheme();
//        } else {
//            //Light Theme
//            setTheme();
//        }

        btnAddTransaction = view.findViewById(R.id.btnAddTransaction);
        btnShowAllTransaction = view.findViewById(R.id.btnShowAllTransaction);
        swtchangeChart = view.findViewById(R.id.swtBudgetHome);

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