package ca.on.conestogac.alj.studentassistancemanagerandroid.ui.Goals;

import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ca.on.conestogac.alj.studentassistancemanagerandroid.Category;
import ca.on.conestogac.alj.studentassistancemanagerandroid.R;
import ca.on.conestogac.alj.studentassistancemanagerandroid.SAMApplication;
import ca.on.conestogac.alj.studentassistancemanagerandroid.SetGoalActivity;

public class GoalsFragment extends Fragment {

    private GoalsViewModel mViewModel;
    private Button btnNewGoal;
    private LinearLayout ll;

    public static GoalsFragment newInstance() {
        return new GoalsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.goals_fragment, container, false);

        //Add new goal button
        btnNewGoal = view.findViewById(R.id.btnNewGoal);
        btnNewGoal.setOnClickListener(new View.OnClickListener() {
            Intent intent;

            @Override
            public void onClick(View v) {
                intent = new Intent(getActivity().getApplicationContext(), SetGoalActivity.class);
                //intent.putExtra("darkTheme", darkTheme);
                intent.putExtra("cId", 0);
                startActivity(intent);
            }
        });

        ll = (LinearLayout) view.findViewById(R.id.llShowGoals);

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(GoalsViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onResume() {
        super.onResume();

        ll.removeAllViews();

        List<Category> categories = new ArrayList<>();
        categories = ((SAMApplication) getActivity().getApplication()).getAllCategory();

        for (Category c : categories) {
            CardView newCard = new CardView(getActivity().getApplicationContext());
            TextView cAmount = new TextView(getActivity().getApplicationContext());
            TextView cName = new TextView(getActivity().getApplicationContext());
            LinearLayout gLayout = new LinearLayout(getActivity().getApplicationContext());

            String addName = c.getName();
            String addAmount = Double.toString(c.getGoal());
            cName.setText(addName);
            cAmount.setText("$" + addAmount);
            gLayout.setOrientation(LinearLayout.VERTICAL);
            gLayout.addView(cName);
            gLayout.addView(cAmount);

            cName.setTypeface(null, Typeface.BOLD);
            cName.setTextSize(getResources().getDimension(R.dimen.card_title));
            cAmount.setTextSize(getResources().getDimension(R.dimen.card_text));

            newCard.setBackground(getResources().getDrawable(R.drawable.card_bg));
            newCard.addView(gLayout);
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
                    Intent intent = new Intent(getActivity().getApplicationContext(), SetGoalActivity.class);
                    intent.putExtra("cId", c.getId());
                    startActivity(intent);
                }
            });
        }
    }
}
