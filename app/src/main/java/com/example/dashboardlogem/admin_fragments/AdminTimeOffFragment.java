package com.example.dashboardlogem.admin_fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextClock;
import android.widget.TextView;

import com.example.dashboardlogem.R;
import com.example.dashboardlogem.admin.AdminDashboard;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminTimeOffFragment} factory method to
 * create an instance of this fragment.
 */
public class AdminTimeOffFragment extends Fragment {

//    ActivityTimeOffBinding binding;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String eName, eEmail, date;
    CalendarView calendar;
    Button btnCancel,btnConfirm;
    TextView selectedDate;


    private static final String TAG = "timeOff";
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_admin_time_off, container, false);
        super.onCreate(savedInstanceState);
//        View layout = binding.getRoot();

//        setContentView(layout);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        btnCancel = view.findViewById(R.id.btnCancel);
        btnConfirm= view.findViewById(R.id.btnConfirm);
        calendar = view.findViewById(R.id.calendarView);

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
                selectedDate = view.findViewById(R.id.txtViewSelectedDate);
                selectedDate.setText(date);
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
                startActivity(new Intent(view.getContext(), AdminDashboard.class));

            }
        });


        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), AdminDashboard.class));

            }
        });

        return view;
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
                    TextView empName = view.findViewById(R.id.txtViewEmpName);
                    empName.setText(eName);
                    TextView empEmail = view.findViewById(R.id.txtViewEmpEmail);
                    empEmail.setText(eEmail);

                    Log.d(TAG, "getData: The Name of the Employee is " + eName);
                    Log.d(TAG, "getData: The email is " + eEmail);
                });
    }
}