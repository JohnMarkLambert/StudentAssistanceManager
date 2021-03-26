package ca.on.conestogac.alj.studentassistancemanagerandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AllAssignmentActivity extends AppCompatActivity {

    private Button btnAACreateEvent;
    private List<Assignment> a;

    private DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm",Locale.getDefault());

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_assignment);

        btnAACreateEvent = findViewById(R.id.btnAACreateEvent);
        btnAACreateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CreateEventActivity.class);
                intent.putExtra("aId", 0);
                startActivity(intent);
            }
        });

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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        boolean result = true;
        Intent intent;

        switch (item.getItemId()) {
            case R.id.menuHome:
                intent = new Intent(getApplicationContext(), MainActivity.class);
                //intent.putExtra("darkTheme", darkTheme);
                startActivity(intent);
                break;
            case R.id.menuCalendar:
                intent = new Intent(getApplicationContext(), CalendarActivity.class);
                //intent.putExtra("darkTheme", darkTheme);
                startActivity(intent);
                break;
            case R.id.menuBudget:
                intent = new Intent(getApplicationContext(), BudgetHomeActivity.class);
                //intent.putExtra("darkTheme", darkTheme);
                startActivity(intent);
                break;
//            case R.id.menuSettings:
//                intent = new Intent(getApplicationContext(), SettingsActivity.class);
//                //intent.putExtra("darkTheme", darkTheme);
//                startActivity(intent);
//                break;
            default:
                result = super.onOptionsItemSelected(item);
                break;
        }
        return result;
    }
}
