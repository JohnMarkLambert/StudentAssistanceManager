package ca.on.conestogac.alj.studentassistancemanagerandroid.ui.Budget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import ca.on.conestogac.alj.studentassistancemanagerandroid.AddTransactionActivity;
import ca.on.conestogac.alj.studentassistancemanagerandroid.Category;
import ca.on.conestogac.alj.studentassistancemanagerandroid.R;
import ca.on.conestogac.alj.studentassistancemanagerandroid.SAMApplication;
import ca.on.conestogac.alj.studentassistancemanagerandroid.Transaction;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

public class BudgetFragment extends Fragment {

    private BudgetViewModel mViewModel;
    private Button btnAddTransaction;
    private TextView txtBHDate, txtBHAmount, txtBHPayment, txtBHNotes;

    public static BudgetFragment newInstance() {
        return new BudgetFragment();
    }

    private Transaction transaction;
    private List<Integer> colours;
    private PieChartView pcvReport;
    private List<Category> goals;

    private SharedPreferences sp;
    private String currency;

    private DateFormat df = new SimpleDateFormat("dd/MM/yy", Locale.getDefault());

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
        pcvReport = (PieChartView) view.findViewById(R.id.BHChart);

        txtBHDate = view.findViewById(R.id.txtMMTDate);
        txtBHAmount = view.findViewById(R.id.txtMMAmount);
        txtBHPayment = view.findViewById(R.id.txtMMPayment);
        txtBHNotes = view.findViewById(R.id.txtMMNotes);

        transaction = ((SAMApplication) getActivity().getApplication()).getLastTransaction();
        Long date = transaction.getDate();
        if (date != 0) {
            txtBHDate.setText(df.format(transaction.getDate()));
            txtBHPayment.setText(((SAMApplication) getActivity().getApplication()).getPaymentType(transaction.getPaymentType()));
        }
        txtBHAmount.setText(currency + String.valueOf(transaction.getAmount()));
        txtBHNotes.setText(transaction.getNotes());

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

        goals = ((SAMApplication) getActivity().getApplication()).getAllCategory();
        if (!goals.isEmpty()) {
            generateChart();
        }


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

            txtBHAmount.setText(currency + String.valueOf(latestTransaction.getAmount()));
            txtBHPayment.setText(((SAMApplication) getActivity().getApplication()).getPaymentType(latestTransaction.getPaymentType()));
            txtBHNotes.setText(latestTransaction.getNotes());

        }

    }

    public void generateChart() {
        int num = 0;
        double value;
        List<SliceValue> pieData = new ArrayList<>();
        for (Category goal : goals) {
            value = goal.getGoal();
            Random random = new Random();
            int color = Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
            pieData.add(new SliceValue((float) value, color).setLabel(goal.getName()
                    + ": " + currency + value));

        }
        PieChartData pieChartData = new PieChartData(pieData);
        pieChartData.setHasLabelsOnlyForSelected(true).setValueLabelTextSize(16);

        pieChartData.setHasCenterCircle(true).setCenterText1("Goals");
        pcvReport.setPieChartData(pieChartData);
    }
}