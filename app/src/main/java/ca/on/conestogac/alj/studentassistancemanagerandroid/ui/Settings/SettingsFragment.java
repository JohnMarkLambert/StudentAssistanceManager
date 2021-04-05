package ca.on.conestogac.alj.studentassistancemanagerandroid.ui.Settings;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ca.on.conestogac.alj.studentassistancemanagerandroid.R;
import ca.on.conestogac.alj.studentassistancemanagerandroid.SettingsActivity;

public class SettingsFragment extends Fragment {

    private SettingsViewModel mViewModel;
    private static final String TITLE_TAG = "settingsActivityTitle";

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.settings_fragment, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);
        // TODO: Use the ViewModel
    }

}