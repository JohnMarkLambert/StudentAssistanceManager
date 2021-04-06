package ca.on.conestogac.alj.studentassistancemanagerandroid;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

public class RecordDetailsActivity extends AppCompatActivity {

    private String date;
    private Switch switchChart;
    private LinearLayout ll;
    private TextView txtMonth;
    private List<Record> records;
    private PieChartView pcvReport;
    private List<Integer> colors;

    private SharedPreferences sp;
    private String currency;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recorddetails);

        PreferenceManager.setDefaultValues(this, R.xml.root_preferences, false);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        currency = sp.getString("currencyType", "$");

//        if (sp.getBoolean("themeType", false)) {
//            //Dark Theme
//            setTheme();
//        } else {
//            //Light Theme
//            setTheme();
//        }


        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        date = getIntent().getExtras().getString("rId");
        txtMonth = findViewById(R.id.txtReportDate);
        txtMonth.setText(date);

        pcvReport = (PieChartView)findViewById(R.id.MRChart);
        switchChart = (Switch)findViewById(R.id.switchReportPie);
        ll = (LinearLayout)findViewById(R.id.LLReportGoals);

        colors = new ArrayList<>();

        try{
            records = ((SAMApplication) getApplication()).getMonthlyRecord(date);
        } catch (Exception ex) {

        }

        for (Record r: records) {
            Random random = new Random();
            int color = Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
            colors.add(color);
        }

        switchChart.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               generateChart(isChecked);
            }
        });


        populateReport();
        generateChart(false);

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

    public void populateReport(){
        int num = 0;
        for (Record record: records) {
            CardView newCard = new CardView(this);
            TextView txtGoalName = new TextView(this);
            TextView txtGoalAmount = new TextView(this);
            TextView txtTotalAmount = new TextView(this);
            TextView txtDiff = new TextView(this);
            LinearLayout newLL = new LinearLayout(this);

            txtGoalName.setText("Goal: " + record.getGoalName());
            txtGoalName.setTextSize(20);
            txtGoalName.setBackgroundColor(colors.get(num));
            txtGoalName.setTextColor(getResources().getColor(R.color.white));
            if (num == colors.size()-1) {
                num = 0;
            } else {
                num++;
            }
            txtGoalAmount.setText("Goal Target: " + currency + String.format("%.2f", record.getGoalAmount()));
            txtTotalAmount.setText("Total Spent: " + currency + String.format("%.2f", record.getAmountSpent()));
            txtDiff.setText("Difference: " + currency + String.format("%.2f", record.getDifference()));
            if (record.getDifference() > 0) {
                txtDiff.setTextColor(getResources().getColor(R.color.red, null));
            } else if (record.getDifference() <= 0) {
                txtDiff.setTextColor(getResources().getColor(R.color.green, null));
            }
            newLL.setOrientation(LinearLayout.VERTICAL);
            newLL.addView(txtGoalName);
            newLL.addView(txtGoalAmount);
            newLL.addView(txtTotalAmount);
            newLL.addView(txtDiff);

            newCard.addView(newLL);

            ll.addView(newCard);
        }
    }

    public void generateChart(boolean mode){
        int num = 0;
        double value;
        List<SliceValue> pieData = new ArrayList<>();
        for (Record record: records) {
            if (mode) {
                value = record.getAmountSpent();
            } else {
                value = record.getGoalAmount();
            }

            pieData.add(new SliceValue((float)value, colors.get(num)).setLabel(record.getGoalName()
            + ": " + currency + value));
            num++;
            if (num >= colors.size()){
                num = 0;
            }
        }
        PieChartData pieChartData = new PieChartData(pieData);
        pieChartData.setHasLabelsOnlyForSelected(true).setValueLabelTextSize(16);
        if (mode) {
            pieChartData.setHasCenterCircle(true).setCenterText1("Totals");
        }
        else {
            pieChartData.setHasCenterCircle(true).setCenterText1("Goals");
        }
        pcvReport.setPieChartData(pieChartData);
    }

}
