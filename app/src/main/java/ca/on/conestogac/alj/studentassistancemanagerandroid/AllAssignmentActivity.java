package ca.on.conestogac.alj.studentassistancemanagerandroid;

import android.content.Intent;
import android.os.Bundle;

import android.view.MenuItem;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AllAssignmentActivity extends AppCompatActivity {

    private Button btnAACreateEvent;

    private DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm",Locale.getDefault());

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_assignment);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        btnAACreateEvent = findViewById(R.id.btnAACreateEvent);
        btnAACreateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateEventActivity.class);
                startActivity(intent);
            }
        });

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
            Date date = new Date(assignment.getDueDate());
            aDue.setText(df.format(date));
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
