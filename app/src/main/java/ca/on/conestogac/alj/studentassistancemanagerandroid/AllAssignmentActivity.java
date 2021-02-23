package ca.on.conestogac.alj.studentassistancemanagerandroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class AllAssignmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_assignment);

        CardView newCard = new CardView(this);
        TextView aName = new TextView(this);
        TextView aDue = new TextView(this);
        LinearLayout aLayout = new LinearLayout(this);
        aLayout.setOrientation(LinearLayout.VERTICAL);

        aName.setText("Capstone");
        aDue.setText("Tuesday");
        aLayout.addView(aName);
        aLayout.addView(aDue);

        newCard.addView(aLayout);
        newCard.setCardElevation(10);
        newCard.setPadding(10,10,10,10);
        newCard.setRadius(15);
        newCard.setContentPadding(10, 10, 10, 10);


        LinearLayout ll = (LinearLayout)findViewById(R.id.AALayout);
        ll.addView(newCard);

        newCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
