package ca.on.conestogac.alj.studentassistancemanagerandroid.ui.Home;

import android.content.SharedPreferences;
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
import androidx.preference.PreferenceManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import ca.on.conestogac.alj.studentassistancemanagerandroid.Assignment;
import ca.on.conestogac.alj.studentassistancemanagerandroid.R;
import ca.on.conestogac.alj.studentassistancemanagerandroid.SAMApplication;
import ca.on.conestogac.alj.studentassistancemanagerandroid.Transaction;

public class HomeFragment extends Fragment {

    private HomeViewModel mViewModel;
    private Button btnTest;
    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    private TextView txtMMEventName;
    private TextView txtMMDueDate;
    private TextView txtMMDuration;
    private TextView txtMMEDescription;
    private TextView txtMMTDate, txtMMAmount, txtMMPayment, txtMMNotes;
    private Assignment assignment;
    private Transaction transaction;
    private String currency;
    private boolean darkTheme;

    private DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm aa", Locale.getDefault());
    private DateFormat df2 = new SimpleDateFormat("dd/MM/yy", Locale.getDefault());
    private SharedPreferences sp;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        PreferenceManager.setDefaultValues(getActivity(), R.xml.root_preferences, false);
        sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        currency = sp.getString("currencyType", "$");



        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        txtMMEventName = view.findViewById(R.id.txtMMEventName);
        txtMMDueDate = view.findViewById(R.id.txtMMDueDate);
        txtMMDuration = view.findViewById(R.id.txtMMDuration);
        txtMMEDescription = view.findViewById(R.id.txtMMEDescription);
        txtMMTDate = view.findViewById(R.id.txtMMTDate);
        txtMMAmount = view.findViewById(R.id.txtMMAmount);
        txtMMPayment = view.findViewById(R.id.txtMMPayment);
        txtMMNotes = view.findViewById(R.id.txtMMNotes);

        populate();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        // TODO: Use the ViewModel

    }

    @Override
    public void onResume() {
        populate();
        super.onResume();
    }

    private void populate(){
        assignment = ((SAMApplication) getActivity().getApplication()).getNextAssignment();
        txtMMEventName.setText(assignment.getName());
        if (assignment.getName() != "No Assignments Found") {
            txtMMDueDate.setText(df.format(assignment.getDueDate()));
            txtMMDuration.setText(assignment.getDuration() + " Hours");
        } else {
            txtMMDueDate.setText("");
            txtMMDuration.setText("");
        }
        txtMMEDescription.setText(assignment.getDesc());

        transaction = ((SAMApplication) getActivity().getApplication()).getLastTransaction();
        Long date = transaction.getDate();
        if (date != 0 ) {
            txtMMTDate.setText(df.format(transaction.getDate()));
            txtMMPayment.setText(df2.format(((SAMApplication) getActivity().getApplication()).getPaymentType(transaction.getPaymentType())));
        }
        txtMMAmount.setText(currency + String.format("%.2f", transaction.getAmount()));
        txtMMNotes.setText(transaction.getNotes());
    }
}