package com.example.log_em;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.log_em.databinding.ActivityTimeOffBinding;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class timeOff extends AppCompatActivity {
ActivityTimeOffBinding binding;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String eName, eEmail, date;
    CalendarView calendar;
    Button btnCancel,btnConfirm;

    private static final String TAG = "timeOff";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityTimeOffBinding.inflate(getLayoutInflater());
        View layout = binding.getRoot();
        setContentView(layout);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        btnCancel = binding.btnCancel;
        btnConfirm= binding.btnConfirm;
        calendar = binding.calendarView;

        String userID = fAuth.getCurrentUser().getUid();
        getData(userID);


        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                String curDate = "", Year,Month = " ";
                if(dayOfMonth < 9) {
                     curDate = "0"+(dayOfMonth);
                }else {
                    curDate= String.valueOf(dayOfMonth);
                }
                if((month+1) < 9) {
                    Month = "0"+(month+1);
                }else {
                    Month= String.valueOf(month+1);
                }
                 Year = String.valueOf(year);
                 date = Month + "/" + curDate + "/" + Year;
                        binding.txtViewSelectedDate.setText(date);
                Log.e("date", date);
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DocumentReference df = fStore.collection("TimeOff").document(userID);
                Map<String, Object> userInfo = new HashMap<>();

                userInfo.put("fullName", eName);
                userInfo.put("email",eEmail);
                userInfo.put("date", date);
                df.set(userInfo);
                startActivity(new Intent(timeOff.this, EmpLanding.class));
                finish();
            }
        });


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(timeOff.this, EmpLanding.class));
                finish();
            }
        });
    }


    private void getData(String uid) {

        DocumentReference df = fStore.collection("Users").document(uid);

        df.get().addOnCompleteListener(
                (@NonNull Task<DocumentSnapshot> task) -> {

                    Log.d("abcd", "onComplete: Reading From the database");
                    DocumentSnapshot document = task.getResult();
                    List<String> schedule = (List<String>) document.get("schedule");
                    eName = document.get("fullName").toString();
                    eEmail = document.get("email").toString();
                    binding.txtViewEmpName.setText(eName);
                    binding.txtViewEmpEmail.setText(eEmail);

                    Log.d(TAG, "getData: The Name of the Employee is " + eName);
                    Log.d(TAG, "getData: The email is " + eEmail);
                });
    }
}