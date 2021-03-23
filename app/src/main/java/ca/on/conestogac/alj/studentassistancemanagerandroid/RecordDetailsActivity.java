package ca.on.conestogac.alj.studentassistancemanagerandroid;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;

public class RecordDetailsActivity extends AppCompatActivity {

    private String date;
    private Switch switchChart;
    private LinearLayout ll;
    private TextView txtMonth;
    private List<Record> records;
    private List<Integer> colours;
    private PieChartView pcvReport;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recorddetails);

        date = getIntent().getExtras().getString("rId");
        txtMonth = findViewById(R.id.txtReportDate);
        txtMonth.setText(date);

        pcvReport = (PieChartView)findViewById(R.id.MRChart);
        switchChart = (Switch)findViewById(R.id.switchReportPie);

        colours = new ArrayList<>();
        colours.add(getColor(R.color.purple_200));
        colours.add(getColor(R.color.teal_200));
        colours.add(getColor(R.color.orange));
        colours.add(getColor(R.color.green));
        colours.add(getColor(R.color.blue));
        colours.add(getColor(R.color.grey));
        colours.add(getColor(R.color.red));

        try{
            records = ((SAMApplication) getApplication()).getMonthlyRecord(date);
        } catch (Exception ex) {

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
            txtGoalName.setBackgroundColor(colours.get(num));
            if (num == colours.size()-1) {
                num = 0;
            } else {
                num++;
            }
            txtGoalAmount.setText("Goal Target: $" + record.getGoalAmount());
            txtTotalAmount.setText("Total Spent: $" + record.getAmountSpent());
            txtDiff.setText("Difference: $" + record.getDifference());
            if (record.getDifference() > 0) {
                txtDiff.setTextColor(getResources().getColor(R.color.red, null));
            } else if (record.getDifference() <= 0) {
                txtDiff.setTextColor(getResources().getColor(R.color.green, null));
            }

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
        String title;
        List<SliceValue> pieData = new ArrayList<>();
        for (Record record: records) {
            if (mode) {
                value = record.getAmountSpent();
            } else {
                value = record.getGoalAmount();
            }
            pieData.add(new SliceValue((float)value, colours.get(num)).setLabel(record.getGoalName()
            + ": $" + value));
            num++;
            if (num >= colours.size()){
                num = 0;
            }
        }
        PieChartData pieChartData = new PieChartData(pieData);
        pieChartData.setHasLabels(true).setValueLabelTextSize(20);
        if (mode) {
            pieChartData.setHasCenterCircle(true).setCenterText1("Totals");
        }
        else {
            pieChartData.setHasCenterCircle(true).setCenterText1("Goals");
        }
        pcvReport.setPieChartData(pieChartData);
    }

}