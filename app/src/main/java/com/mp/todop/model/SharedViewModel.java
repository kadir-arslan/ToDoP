package com.mp.todop.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<Task> selectedItem = new MutableLiveData<Task>();


    public  void selectedItem(Task task){
        selectedItem.setValue(task);
    }

    public LiveData<Task> getSelectedItem() {
    return selectedItem;
    }
    }

