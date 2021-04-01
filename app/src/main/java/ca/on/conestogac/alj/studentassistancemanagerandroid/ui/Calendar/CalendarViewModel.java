package ca.on.conestogac.alj.studentassistancemanagerandroid.ui.Calendar;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CalendarViewModel extends ViewModel {
    private MutableLiveData<String> mText;
    public CalendarViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Calendar fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

}


