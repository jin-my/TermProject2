package com.example.termproject2.ui.add;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class addViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public addViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is add fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}