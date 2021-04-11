package ca.on.conestogac.alj.studentassistancemanagerandroid.ui.Reports;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.List;

import ca.on.conestogac.alj.studentassistancemanagerandroid.R;
import ca.on.conestogac.alj.studentassistancemanagerandroid.Record;
import ca.on.conestogac.alj.studentassistancemanagerandroid.RecordDetailsActivity;
import ca.on.conestogac.alj.studentassistancemanagerandroid.SAMApplication;

public class ReportsFragment extends Fragment {

    private ReportsViewModel mViewModel;
    private List<Record> records;
    private LinearLayout ll;

    private SharedPreferences sp;
    private String currency;

    public static ReportsFragment newInstance() {
        return new ReportsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reports, container, false);

        PreferenceManager.setDefaultValues(getActivity(), R.xml.root_preferences, false);
        sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
        currency = sp.getString("currencyType", "$");

        try {
            records = ((SAMApplication) getActivity().getApplication()).getAllRecords();
        } catch (Exception ex) {

        }

        ll = (LinearLayout)view.findViewById(R.id.LLRecords);

        showRecords();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ReportsViewModel.class);
        // TODO: Use the ViewModel
    }

    public void showRecords(){
        if (records != null){
            List<String> dates = new ArrayList<>();
            for (Record record: records) {
                if (!dates.contains(record.getDate())){
                    dates.add(record.getDate());
                }
            }
            for (String date: dates){
                double goal = 0.0;
                double total = 0.0;
                double diff = 0.0;
                for (Record record: records) {
                    if (record.getDate().equals(date)) {
                        goal += record.getGoalAmount();
                        total += record.getAmountSpent();
                        diff += record.getDifference();
                    }
                }
                CardView newCard = new CardView(getActivity().getApplicationContext());
                TextView txtDate = new TextView(getActivity().getApplicationContext());
                TextView goalTitle = new TextView(getActivity().getApplicationContext());
                TextView txtGoal = new TextView(getActivity().getApplicationContext());
                TextView totalTitle = new TextView(getActivity().getApplicationContext());
                TextView txtTotal = new TextView(getActivity().getApplicationContext());
                TextView diffTitle = new TextView(getActivity().getApplicationContext());
                TextView txtDiff = new TextView(getActivity().getApplicationContext());
                LinearLayout newLL = new LinearLayout(getActivity().getApplicationContext());
                LinearLayout ll1 = new LinearLayout(getActivity().getApplicationContext());
                LinearLayout ll2 = new LinearLayout(getActivity().getApplicationContext());
                LinearLayout ll3 = new LinearLayout(getActivity().getApplicationContext());
                newLL.setOrientation(LinearLayout.VERTICAL);
                ll1.setOrientation(LinearLayout.HORIZONTAL);
                ll2.setOrientation(LinearLayout.HORIZONTAL);
                ll3.setOrientation(LinearLayout.HORIZONTAL);

                txtDate.setText(date);
                goalTitle.setText("Goal: ");
                txtGoal.setText(currency + String.format("%.2f", goal));
                totalTitle.setText("Total: ");
                txtTotal.setText(currency + String.format("%.2f", total));
                diffTitle.setText("Difference: ");
                txtDiff.setText(currency + String.format("%.2f", diff));
                if (diff > 0) {
                    txtDiff.setTextColor(getResources().getColor(R.color.red, null));
                } else if (diff <= 0) {
                    txtDiff.setTextColor(getResources().getColor(R.color.green, null));
                }

                txtDate.setTypeface(null, Typeface.BOLD);
                txtDate.setTextSize(getResources().getDimension(R.dimen.card_title));
                goalTitle.setTextSize(getResources().getDimension(R.dimen.card_text));
                txtGoal.setTextSize(getResources().getDimension(R.dimen.card_text));
                totalTitle.setTextSize(getResources().getDimension(R.dimen.card_text));
                txtTotal.setTextSize(getResources().getDimension(R.dimen.card_text));
                diffTitle.setTextSize(getResources().getDimension(R.dimen.card_text));
                txtDiff.setTextSize(getResources().getDimension(R.dimen.card_text));

                newLL.addView(txtDate);
                ll1.addView(goalTitle);
                ll1.addView(txtGoal);
                ll2.addView(totalTitle);
                ll2.addView(txtTotal);
                ll3.addView(diffTitle);
                ll3.addView(txtDiff);
                newLL.addView(ll1);
                newLL.addView(ll2);
                newLL.addView(ll3);
                newCard.addView(newLL);

                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(0,0,0, (int) getResources().getDimension(R.dimen.padding));
                newCard.setLayoutParams(lp);
                newCard.setBackground(getResources().getDrawable(R.drawable.card_bg));

                ll.addView(newCard);

                newCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity().getApplicationContext(), RecordDetailsActivity.class);
                        intent.putExtra("rId", date);
                        startActivity(intent);
                    }
                });
            }
        }
    }

}