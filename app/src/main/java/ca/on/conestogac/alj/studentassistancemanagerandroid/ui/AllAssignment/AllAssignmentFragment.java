package ca.on.conestogac.alj.studentassistancemanagerandroid.ui.AllAssignment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    private Spinner spinner;
    private LinearLayout ll;

    private DateFormat df = new SimpleDateFormat("dd/MM/yy hh:mm aa", Locale.getDefault());

    public static AllAssignmentFragment newInstance() {
        return new AllAssignmentFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.all_assignment_fragment, container, false);

        ll = view.findViewById(R.id.AALayout);

        spinner = view.findViewById(R.id.assignDateSpinner);
        btnAACreateEvent = view.findViewById(R.id.btnAACreateEvent);
        btnAACreateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), CreateEventActivity.class);
                intent.putExtra("aId", 0);
                startActivity(intent);
            }
        });

        try {
            a = ((SAMApplication) getActivity().getApplication()).getAllAssignments();
        } catch (Exception ex) {

        }

        populateSpinner();

        populateAssignments("All");

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String sortBy = spinner.getSelectedItem().toString();
                populateAssignments(sortBy);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                populateAssignments("all");
            }
        });


       return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AllAssignmentViewModel.class);
        
    }

    private void populateSpinner() {
        List<String> checking = new ArrayList<>();
        checking.add("All");
        checking.add("Completed");
        if (a != null) {
            for (Assignment assignment : a) {
                String tDate = new SimpleDateFormat("dd/MM/yy").format(new Date(assignment.getDueDate()));
                if (!checking.contains(tDate)) {
                    checking.add(tDate);
                }
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, checking);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void populateAssignments(String selector) {
        ll.removeAllViews();
        for (Assignment assignment : a) {
            String aDate = new SimpleDateFormat("dd/MM/yy").format(new Date(assignment.getDueDate()));
            boolean add = false;
            if (selector == "All") {
                add = true;
            } else if (selector == "Completed" && assignment.isNotified()) {
                add = true;
            } else if (selector.equals(aDate) && !assignment.isNotified()) {
                add = true;
            }

            if (add) {
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
                newCard.setPadding(10, 10, 10, 10);
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
        }
    }

}