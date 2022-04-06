package com.example.log_em;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.log_em.databinding.ActivityLandingPageBinding;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class LandingPage extends AppCompatActivity {

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    TextView txtViewMonth;
    ImageView imgViewNext, imgViewBack;
    Button btnLogOut;
    ActivityLandingPageBinding binding;
    CompactCalendarView compactCalendar;
    private static final String TAG = "Landing";
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLandingPageBinding.inflate(getLayoutInflater());
        View layout = binding.getRoot();
        setContentView(layout);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        btnLogOut = binding.btnLogOut;
        compactCalendar = binding.compactCalendarSchedule;
        txtViewMonth =binding.txtViewMonth;
        imgViewBack = binding.imgViewPrev;
        imgViewNext = binding.imgViewNext;
        txtViewMonth.setText(dateFormatForMonth.format(compactCalendar.getFirstDayOfCurrentMonth()));


        Log.d("NEW", "Entered in the new activity" + TAG);


        // Getting the Current User id
        String userID = fAuth.getCurrentUser().getUid();
        Log.d(TAG, "onCreate: Current User ID:  " + userID);

        compactCalendar.shouldSelectFirstDayOfMonthOnScroll(false);
        compactCalendar.setUseThreeLetterAbbreviation(true);

//        getSchedule(userID);

        DocumentReference df = fStore.collection("Users").document(userID);

        df.get().addOnCompleteListener(
                (@NonNull Task<DocumentSnapshot> task) -> {

                    Log.d("abcd", "onComplete: Reading From the database");
                    DocumentSnapshot document = task.getResult();
                    List<String> schedule = (List<String>) document.get("schedule");
                    Log.d("list", "onComplete: Schedule List " + schedule);
                    Log.d(TAG, "getSchedule: Schedule List Size: " + schedule.size());

                    for (int i = 0; i < schedule.size(); i++) {

                        String[] parts = schedule.get(i).split("/");

                        int month = Integer.parseInt(parts[0]);
                        int day = Integer.parseInt(parts[1]);
                        int year = Integer.parseInt(parts[2]);

                        Calendar calendar = Calendar.getInstance();
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, (month-1));
                        calendar.set(Calendar.DAY_OF_MONTH, day);

                        long milliTime = calendar.getTimeInMillis();
                        Log.d(TAG, "onCreate: " + milliTime);

                        Event ev1 = new Event(Color.GREEN,milliTime);
                        compactCalendar.addEvent(ev1);
//                        String x = String.valueOf(calendarViewSchedule.getDate());

//                        Log.d(TAG, "onCreate: Current date from calendar view " + x);

                    }
                });

        imgViewNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                compactCalendar.scrollRight();
                txtViewMonth.setText(dateFormatForMonth.format(compactCalendar.getFirstDayOfCurrentMonth()));
            }
        });

        imgViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                compactCalendar.scrollLeft();
                txtViewMonth.setText(dateFormatForMonth.format(compactCalendar.getFirstDayOfCurrentMonth()));
            }
        });

        btnLogOut.setOnClickListener((View view) -> {

            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, LogInActivity.class));
            finish();

        });
    }

    private void getSchedule(String uid) {

        DocumentReference df = fStore.collection("Users").document(uid);

        df.get().addOnCompleteListener(
                (@NonNull Task<DocumentSnapshot> task) -> {

                    Log.d("abcd", "onComplete: Reading From the database");
                    DocumentSnapshot document = task.getResult();
                    List<String> schedule = (List<String>) document.get("schedule");
                    Log.d("list", "onComplete: Schedule List " + schedule);
                    Log.d(TAG, "getSchedule: Schedule List Size: " + schedule.size());

                    for (int i = 0; i < schedule.size(); i++) {

                        String[] Date = schedule.get(i).split("/");
//                    Log.d(TAG, "getSchedule: Size of date array is:" + Date.length);
                        String month = Date[0];
                        String date = Date[1];
                        String Year = Date[2];

                        Log.d(TAG, "getSchedule: Date: "
                                + date + " Month: " + month + " Year: " + Year);
                    }
                });
//        Log.d(TAG, "getSchedule: New List size: "+ mSchedule.size());
////        + "\n" +"List first Element:" + mSchedule.get(1));
//        return mSchedule;

    }
}


//   private  List<String> getSchedule(String uid) {
//              List<String> mSchedule = new ArrayList<>();
//        DocumentReference df = fStore.collection("Users").document(userID);
//
//        df.get().addOnCompleteListener(
//                (@NonNull Task<DocumentSnapshot> task) -> {
//
//                    Log.d("abcd", "onComplete: Reading From the database");
//                    DocumentSnapshot document = task.getResult();
//                    List<String> schedule = (List<String>) document.get("schedule");
//                    Log.d("list", "onComplete: Schedule List "+ schedule);
//                    Log.d(TAG, "getSchedule: Schedule List Size: " + schedule.size());
//
//                    for( int i=0; i < schedule.size(); i++) {
//                        Log.d(TAG, "getSchedule: In the for Loop");
//                        mSchedule.add(schedule.get(i));
//                    }
//
//                    Log.d(TAG, "getSchedule: Size of new String: "+ mSchedule.size());
//                });
//        Log.d(TAG, "getSchedule: Size of new String2: "+ mSchedule.size());
//
//    }
//}