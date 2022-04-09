package com.example.dashboardlogem;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class HomeViewModel extends ViewModel {

    String nameOfUser;
    String employeeNumber;
    String shiftTime;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

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


    public void setData(){
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        String userID = fAuth.getCurrentUser().getUid();
        Log.d("logInDebugM", "onCreate: Current User ID:  " + userID);

        DocumentReference df = fStore.collection("Users").document(userID);


        df.get().addOnCompleteListener(
                (@NonNull Task<DocumentSnapshot> task) -> {

                    Log.d("abcd", "onComplete: Reading From the database");
                    DocumentSnapshot document = task.getResult();
                    List<String> schedule = (List<String>) document.get("schedule");
                    setEmployeeNumber(userID);
                    setNameOfUser(document.get("fullName").toString());


                    Log.d("loginDebug", "getData: The Name of the Employee is " + getNameOfUser());
                    Log.d("loginDebug", "getData: The emp Num is " + getEmployeeNumber());
                });

        Log.d("anfr",getNameOfUser());
    }
}
