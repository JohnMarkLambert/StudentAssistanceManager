package ca.on.conestogac.alj.studentassistancemanagerandroid.ui.AllTransaction;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ca.on.conestogac.alj.studentassistancemanagerandroid.AddTransactionActivity;
import ca.on.conestogac.alj.studentassistancemanagerandroid.R;
import ca.on.conestogac.alj.studentassistancemanagerandroid.SAMApplication;
import ca.on.conestogac.alj.studentassistancemanagerandroid.Transaction;
import ca.on.conestogac.alj.studentassistancemanagerandroid.TransactionDetailActivity;

public class AllTransactionFragment extends Fragment {

    private AllTransactionViewModel mViewModel;
    private Button btnNewTransaction;
    private List<Transaction> transactions;
    private Spinner dateSpinner;
    private String sortBy;
    private LinearLayout ll;
    private View view;

    public static AllTransactionFragment newInstance() {
        return new AllTransactionFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.all_transaction_fragment, container, false);

        try {
            transactions = ((SAMApplication) getActivity().getApplication()).getAllTransactions();
        } catch(Exception ex){

        }

        btnNewTransaction = view.findViewById(R.id.btnATNewTrans);
        dateSpinner = (Spinner) view.findViewById(R.id.assignDateSpinner);
        ll = (LinearLayout) view.findViewById(R.id.llShowTransactions);
        populateSpinner();

        btnNewTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), AddTransactionActivity.class);
                intent.putExtra("tId", 0);
                startActivity(intent);
            }
        });

        dateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sortBy = dateSpinner.getSelectedItem().toString();
                showTransactions(sortBy);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                showTransactions("all");
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AllTransactionViewModel.class);
        // TODO: Use the ViewModel
    }

    private void populateSpinner() {
        List<String> checking = new ArrayList<>();
        checking.add("all");
        if (transactions != null) {
            for (Transaction t : transactions) {
                String tDate = new SimpleDateFormat("MM/yyyy").format(new Date(t.getDate()));
                if (!checking.contains(tDate)) {
                    checking.add(tDate);
                }
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, checking);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dateSpinner.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void showTransactions(String month) {
        ll.removeAllViews();
        if (transactions != null) {
            for (Transaction t : transactions) {

                String checkDate = new SimpleDateFormat("MM/yyyy").format(new Date(t.getDate()));
                if (month == "all" | month.equals(checkDate)) {
                    CardView newCard = new CardView(getActivity().getApplicationContext());
                    TextView tAmount = new TextView(getActivity().getApplicationContext());
                    TextView tDate = new TextView(getActivity().getApplicationContext());
                    //TextView tPayment = new TextView(this);
                    LinearLayout tLayout = new LinearLayout(getActivity().getApplicationContext());

                    String addDate = new SimpleDateFormat("dd/MM/yy").format(new Date(t.getDate()));
                    String addAmount = Double.toString(t.getAmount());
                    tDate.setText(addDate);
                    tAmount.setText("$" + addAmount);

                    tDate.setTypeface(null, Typeface.BOLD);
                    tDate.setTextSize(getResources().getDimension(R.dimen.card_title));
                    tAmount.setTextSize(getResources().getDimension(R.dimen.card_text));

                    //tPayment.setText(((SAMApplication) getApplication()).getPaymentType(t.getPaymentType()));
                    tLayout.setOrientation(LinearLayout.VERTICAL);
                    tLayout.addView(tDate);
                    tLayout.addView(tAmount);
                    //tLayout.addView(tPayment);

                    newCard.setBackground(getResources().getDrawable(R.drawable.card_bg));
                    newCard.addView(tLayout);
                    newCard.setCardElevation(10);
                    newCard.setPadding(10, 10, 10, 10);
                    newCard.setRadius(15);
                    newCard.setContentPadding(10, 10, 10, 10);

                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(0,0,0, (int) getResources().getDimension(R.dimen.padding));
                    newCard.setLayoutParams(lp);

                    ll.addView(newCard);

                    newCard.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity().getApplicationContext(), TransactionDetailActivity.class);
                            intent.putExtra("tId", t.getId());
                            startActivity(intent);
                        }
                    });
                }
            }
        }


    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            transactions = ((SAMApplication) getActivity().getApplication()).getAllTransactions();
        } catch(Exception ex){

        }

        btnNewTransaction = view.findViewById(R.id.btnATNewTrans);
        dateSpinner = (Spinner) view.findViewById(R.id.assignDateSpinner);
        ll = (LinearLayout) view.findViewById(R.id.llShowTransactions);
        populateSpinner();
    }
}