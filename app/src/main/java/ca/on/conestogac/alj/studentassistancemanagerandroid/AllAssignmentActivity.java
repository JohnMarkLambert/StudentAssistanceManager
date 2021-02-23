package ca.on.conestogac.alj.studentassistancemanagerandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class AllAssignmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_assignment);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        List<Assignment> a = new ArrayList<>();
        LinearLayout ll = (LinearLayout)findViewById(R.id.AALayout);

        try {
            a = ((SAMApplication) getApplication()).getAllAssignments();
        } catch (Exception ex) {

        }

        for (Assignment assignment : a) {
            CardView newCard = new CardView(this);
            TextView aName = new TextView(this);
            TextView aDue = new TextView(this);
            LinearLayout aLayout = new LinearLayout(this);
            aLayout.setOrientation(LinearLayout.VERTICAL);

            aName.setText(assignment.getName());
            Date date = new Date(assignment.getDueDate()*1000);
            aDue.setText(date.toString());
            aLayout.addView(aName);
            aLayout.addView(aDue);

            newCard.addView(aLayout);
            newCard.setCardElevation(10);
            newCard.setPadding(10,10,10,10);
            newCard.setRadius(15);
            newCard.setContentPadding(10, 10, 10, 10);

            ll.addView(newCard);

            newCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), AssignmentDetailsActivity.class);
                    intent.putExtra("aId", assignment.getId());
                    startActivity(intent);
                }
            });
        }
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
