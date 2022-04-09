package com.example.log_em;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.log_em.databinding.ActivityAddEmpScheduleBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddEmpSchedule extends AppCompatActivity {

    ActivityAddEmpScheduleBinding binding;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String eName, eEmail, date;
    CalendarView calendar;
    Button btnCancel,btnConfirm;
    List<String> scheduledDates = new ArrayList<>();

    private static final String TAG = "AddEmpSchedule";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddEmpScheduleBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        Bundle bundle = getIntent().getExtras();
        String curEmpName = bundle.getString("clickedEmpName","No Value");
        String curEmpEmail = bundle.getString("clickedEmpEmail","No Value");
        String curEmpId = bundle.getString("clickedEmpId","No Value");


        binding.txtViewEmpName.setText(curEmpName);
        binding.txtViewEmpEmail.setText(curEmpEmail);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        btnCancel = binding.btnCancel;
        btnConfirm= binding.btnConfirm;
        calendar = binding.calendarView;


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
                scheduledDates.add(date);
                binding.txtViewSelectedDate.setText(date);
                Log.e("date", date);
            }
        });


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddEmpSchedule.this, AdminLanding.class));
                finish();
            }
        });


        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DocumentReference df = fStore.collection("Users").document(curEmpId);

                Map<String, Object> userInfo = new HashMap<>();
                userInfo.put("schedule",scheduledDates);
                df.update(userInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(AddEmpSchedule.this, "Added the Schedule Successfully.",
                                 Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddEmpSchedule.this, AdminLanding.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddEmpSchedule.this, "Error Occured: "+ e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }
}