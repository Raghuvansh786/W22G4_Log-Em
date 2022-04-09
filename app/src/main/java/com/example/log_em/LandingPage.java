package com.example.log_em;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.log_em.databinding.ActivityLandingPageBinding;
import com.github.sundeepk.compactcalendarview.CompactCalendarView;
import com.github.sundeepk.compactcalendarview.domain.Event;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class LandingPage extends AppCompatActivity {
    String Date = "04/09/2022";
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    TextView txtViewMonth;
    ImageView imgViewNext, imgViewBack;
    Button btnLogOut;
    ActivityLandingPageBinding binding;
    CompactCalendarView compactCalendar;
    List<String> schedule = new ArrayList<>();
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
        txtViewMonth = binding.txtViewMonth;
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
                    schedule = (List<String>) document.get("schedule");
//                    Log.d("list", "onComplete: Schedule List " + schedule);
//                    Log.d(TAG, "getSchedule: Schedule List Size: " + schedule.size());

                    if(schedule==null) {
                        Toast.makeText(this, "You have no Schedule Yet.", Toast.LENGTH_SHORT).show();
                    }else {

                        for (int i = 0; i < schedule.size(); i++) {

                            if (schedule.get(i).equalsIgnoreCase(Date)) {
                                Log.d(TAG, "onCreate: The following date matched with the given date" + schedule.get(i));
                            } else {
                                Log.d(TAG, "onCreate: No dates matched..");
                            }

                            String[] parts = schedule.get(i).split("/");

                            int month = Integer.parseInt(parts[0]);
                            int day = Integer.parseInt(parts[1]);
                            int year = Integer.parseInt(parts[2]);

                            Calendar calendar = Calendar.getInstance();
                            calendar.set(Calendar.YEAR, year);
                            calendar.set(Calendar.MONTH, (month - 1));
                            calendar.set(Calendar.DAY_OF_MONTH, day);

                            long milliTime = calendar.getTimeInMillis();
                            Log.d(TAG, "onCreate: " + milliTime);

                            Event ev1 = new Event(Color.GREEN, milliTime);
                            compactCalendar.addEvent(ev1);
//                        String x = String.valueOf(calendarViewSchedule.getDate());

//                        Log.d(TAG, "onCreate: Current date from calendar view " + x);

                        }
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

        btnLogOut.setOnClickListener((
                View view) ->

        {

            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, LogInActivity.class));
            finish();

        });
    }
}
