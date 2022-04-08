package com.example.dashboardlogem;

import android.util.Log;
import android.widget.TextView;

import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    String nameOfUser;
    String employeeNumber;
    String shiftTime;

    public String getNameOfUser() {
        return nameOfUser;
    }

    public void setNameOfUser(String nameOfUser) {
        this.nameOfUser = nameOfUser;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getShiftTime() {
        return shiftTime;
    }

    public void setShiftTime(String shiftTime) {
        this.shiftTime = shiftTime;
    }

    public HomeViewModel(String nameOfUser, String employeeNumber, String shiftTime) {
        this.nameOfUser = nameOfUser;
        this.employeeNumber = employeeNumber;
        this.shiftTime = shiftTime;
    }

    public HomeViewModel() {
        Log.i("ViewModel", "HomeViewModel is created");
        this.nameOfUser="";
        this.employeeNumber="";
        this.shiftTime="";
    }

    public void generateUsername(String name){
        nameOfUser = name;
    }
}
