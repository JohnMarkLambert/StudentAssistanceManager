package ca.on.conestogac.alj.studentassistancemanagerandroid.ui.Calendar;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ca.on.conestogac.alj.studentassistancemanagerandroid.AllAssignmentActivity;
import ca.on.conestogac.alj.studentassistancemanagerandroid.Assignment;
import ca.on.conestogac.alj.studentassistancemanagerandroid.CreateEventActivity;
import ca.on.conestogac.alj.studentassistancemanagerandroid.R;
import ca.on.conestogac.alj.studentassistancemanagerandroid.SAMApplication;

public class CalendarFragment extends Fragment {

    private CalendarViewModel mViewModel;

    private Button btnCreateEvent;
    private CalendarView calendar;
    private SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    private String selectedDate;

    private Button btnShowAll;
    private TextView txtCEventName;
    private TextView txtCDueDate;
    private TextView txtCDuration;
    private TextView txtCDescription;

    private List<Assignment> assignments;
    private Assignment displayAssignment;

    private DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm", Locale.getDefault());
    private Date dueDate;

    public static CalendarFragment newInstance() {
        return new CalendarFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_calendar, container, false);


        sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());

        assignments = ((SAMApplication) getActivity().getApplication()).getAllAssignments();

        btnCreateEvent = view.findViewById(R.id.btnCreateEvent);

        calendar = view.findViewById(R.id.calendarView);
        txtCEventName = view.findViewById(R.id.txtMMEventName);
        txtCDueDate = view.findViewById(R.id.txtMMDueDate);
        txtCDuration = view.findViewById(R.id.txtMMDuration);
        txtCDescription = view.findViewById(R.id.txtMMDescription);

        if (!assignments.isEmpty()) {
            for (Assignment a : assignments) {
                if (a.isComplete() == false) {
                    displayAssignment = a;
                    break;
                }
            }

            txtCEventName.setText(displayAssignment.getName());
            dueDate = new Date((long) displayAssignment.getDueDate() );
            txtCDueDate.setText(df.format(dueDate));
            txtCDuration.setText(displayAssignment.getDuration() + " Hours");
            txtCDescription.setText(displayAssignment.getDesc());
        }

        btnCreateEvent.setOnClickListener(new View.OnClickListener() {
            Intent intent;
            @Override
            public void onClick(View v) {
                intent = new Intent(getActivity().getApplicationContext(), CreateEventActivity.class);
                intent.putExtra("aId", 0);
                //intent.putExtra("darkTheme", darkTheme);
                startActivity(intent);

            }
        });


        btnShowAll = view.findViewById(R.id.btnShowAll);

        btnShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), AllAssignmentActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CalendarViewModel.class);
        // TODO: Use the ViewModel
    }


}