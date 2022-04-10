package com.example.dashboardlogem;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EarningsFragment#} factory method to
 * create an instance of this fragment.
 */
public class EarningsFragment extends Fragment {


    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    Map<String, String> punchInfo = new HashMap<>();
    String clockIn;
    String clockOut;
    View view;
    String clockI;
    String clockO;
    TextView txtViewMoneyEarned;
    double earnings;
        double totalEarnings;
    Map<String, Double> earn = new HashMap<>();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_earnings, container, false);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        txtViewMoneyEarned = view.findViewById(R.id.txtViewMoneyEarned);
        String userID = fAuth.getCurrentUser().getUid();
        Log.d("logInDebugM", "onCreate: Current User ID:  " + userID);

        DocumentReference df = fStore.collection("Users").document(userID);
//
        df.get().addOnCompleteListener(
                (@NonNull Task<DocumentSnapshot> task) -> {
//
                    Log.d("abcd", "onComplete: Reading From the database");
                    try {
                        DocumentSnapshot document = task.getResult();
                        punchInfo = (Map<String, String>) document.get("time");
                        earnings = (double) document.get("earnings");

                        clockIn = punchInfo.get("clockInTime");
                        clockOut = punchInfo.get("clockOutTime");
                        Log.d("clockInTme",clockIn);
                        Log.d("clockOutTime",clockOut);
                        clockI = clockIn.toString();
                        clockO = clockOut.toString();
                        Log.d("clockInTme",clockI);
                        Log.d("clockOutTime",clockO);
                        double wage = calculateWage(clockI, clockO);
                        totalEarnings = earnings+wage;
                        Log.d("wage", "The wage is : "+String.format("%2f",wage));
                        txtViewMoneyEarned.setText(String.format("%.2f",wage));
                    } catch (Exception e) {
                        Log.d("clockInRead", e.getMessage());
                    }
                });
//

                        Log.d("wage", "The total wage1223 is : "+String.format("%2f",totalEarnings));


//                    txtViewName.setText(document.get("fullName").toString());
//                    txtViewEmpNum.setText(userID);});


        return view;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    double calculateWage(String cin, String cout){

        double wage =0.0;
        double hourlyWage = 15.20;
        LocalTime inTime = LocalTime.parse(cin);
        LocalTime outTime = LocalTime.parse(cout);

        Duration workTime = Duration.between(inTime, outTime);
        Log.d("wrkTime", "Duration : "+workTime.toMinutes());
        double workTInHours = workTime.toMinutes()*0.0167;
        Log.d("wrkTime", "Duration : "+workTInHours);

        wage = hourlyWage * workTInHours;

        Log.d("aww", "The wage is : "+wage);
        return wage;

    }
}