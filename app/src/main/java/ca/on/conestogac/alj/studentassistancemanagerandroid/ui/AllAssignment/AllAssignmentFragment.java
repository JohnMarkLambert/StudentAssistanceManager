package ca.on.conestogac.alj.studentassistancemanagerandroid.ui.AllAssignment;

import androidx.appcompat.app.ActionBar;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ca.on.conestogac.alj.studentassistancemanagerandroid.Assignment;
import ca.on.conestogac.alj.studentassistancemanagerandroid.AssignmentDetailsActivity;
import ca.on.conestogac.alj.studentassistancemanagerandroid.CreateEventActivity;
import ca.on.conestogac.alj.studentassistancemanagerandroid.R;
import ca.on.conestogac.alj.studentassistancemanagerandroid.SAMApplication;

public class AllAssignmentFragment extends Fragment {

    private AllAssignmentViewModel mViewModel;
    private Button btnAACreateEvent;
    private List<Assignment> a;

    private DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm", Locale.getDefault());

    public static AllAssignmentFragment newInstance() {
        return new AllAssignmentFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.all_assignment_fragment, container, false);

        btnAACreateEvent = view.findViewById(R.id.btnAACreateEvent);
        btnAACreateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), CreateEventActivity.class);
                intent.putExtra("aId", 0);
                startActivity(intent);
            }
        });

        LinearLayout ll = (LinearLayout)view.findViewById(R.id.AALayout);

        try {
            a = ((SAMApplication) getActivity().getApplication()).getAllAssignments();
        } catch (Exception ex) {

        }

        for (Assignment assignment : a) {
            CardView newCard = new CardView(getActivity().getApplication());
            TextView aName = new TextView(getActivity().getApplication());
            TextView aDue = new TextView(getActivity().getApplication());
            LinearLayout aLayout = new LinearLayout(getActivity().getApplication());
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
                    Intent intent = new Intent(getActivity().getApplication(), AssignmentDetailsActivity.class);
                    intent.putExtra("aId", assignment.getId());
                    startActivity(intent);
                }
            });
        }

       return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AllAssignmentViewModel.class);
        // TODO: Use the ViewModel
    }

}