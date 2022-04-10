package com.example.dashboardlogem;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ScheduleFragment#} factory method to
 * create an instance of this fragment.
 */
public class ScheduleFragment extends Fragment {

    String Date = "04/09/2022";
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    TextView txtViewMonth;
    ImageView imgViewNext, imgViewBack;
    Button btnLogOut;
    CompactCalendarView compactCalendar;
    List<String> schedule = new ArrayList<>();
    private static final String TAG = "Landing";
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
    private SimpleDateFormat dateFormatForMonth = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_schedule, container, false);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
//        btnLogOut = view.findViewById(R.id.btnLogOut);
        compactCalendar = view.findViewById(R.id.compactCalendarSchedule);
        txtViewMonth = view.findViewById(R.id.txtViewMonth);
        imgViewBack = view.findViewById(R.id.imgViewPrev);
        imgViewNext = view.findViewById(R.id.imgViewNext);
        txtViewMonth.setText(dateFormatForMonth.format(compactCalendar.getFirstDayOfCurrentMonth()));

//
        Log.d("NEW", "Entered in the new activity" + TAG);
//
//
//        // Getting the Current User id
        try{

        }catch(Exception ex){
            Log.d("abcd", ex.getMessage());

        }
        String userID = fAuth.getCurrentUser().getUid();
        Log.d("logInDebugM", "onCreate: Current User ID:  " + userID);

        compactCalendar.shouldSelectFirstDayOfMonthOnScroll(false);
        compactCalendar.setUseThreeLetterAbbreviation(true);

//        fAuth.getSchedule(userID);

        DocumentReference df = fStore.collection("Users").document(userID);

        df.get().addOnCompleteListener(
                (@NonNull Task<DocumentSnapshot> task) -> {

                    Log.d("abcd", "onComplete: Reading From the database");
                    try{

                        DocumentSnapshot document = task.getResult();
                        schedule = (List<String>) document.get("schedule");
                        Log.d("list", "onComplete: Schedule List " + schedule);
                        Log.d(TAG, "getSchedule: Schedule List Size: " + schedule.size());

                        if(schedule==null) {
                        Toast.makeText(getActivity(), "You have no Schedule Yet.", Toast.LENGTH_SHORT).show();
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
//                            String x = String.valueOf(calendarViewSchedule.getDate());

//                            Log.d(TAG, "onCreate: Current date from calendar view " + x);

                                }
                        }

                    }catch(Exception e){
                        Log.d("abcd",e.getMessage());
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

//        btnLogOut.setOnClickListener((
//                View view) ->
//
//        {
//
//            FirebaseAuth.getInstance().signOut();
//            startActivity(new Intent(this, LogInActivity.class));
//            finish();
//
//        });

        return view;
    }
}