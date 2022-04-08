package com.example.dashboardlogem;

import android.util.Log;
import android.widget.TextView;

import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    String nameOfUser;
    public HomeViewModel() {
        Log.i("ViewModel", "HomeViewModel is created");
        nameOfUser="";
    }

    public void generateUsername(String name){
        nameOfUser = name;
    }
}
