package com.example.log_em;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;

import com.example.log_em.databinding.ActivityLandingPageBinding;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LandingPage extends AppCompatActivity {

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    Button btnLogOut;
    ActivityLandingPageBinding binding;
    CalendarView calendarViewSchedule;
    private static final String TAG = "Landing";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLandingPageBinding.inflate(getLayoutInflater());
        View layout = binding.getRoot();
        setContentView(layout);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        btnLogOut = binding.btnLogOut;
        calendarViewSchedule = binding.calendarViewSchedule;

        Log.d("NEW","Entered in the new activity"+ TAG);



        // Getting the Current User id
        String userID = fAuth.getCurrentUser().getUid();
        Log.d(TAG, "onCreate: Current User ID:  " + userID);

        getSchedule(userID);



        btnLogOut.setOnClickListener((View view) ->{

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
                Log.d("list", "onComplete: Schedule List "+ schedule);

        });
    }


//    private  List<String> getSchedule(String uid) {
//        List<String> schedule = Arrays.asList();
//        DocumentReference df = fStore.collection("Users").document(uid);
//
//        df.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//
//                Log.d("abcd", "onComplete: Reading From the database");
//                DocumentSnapshot document = task.getResult();
//                List<String> schedule = (List<String>) document.get("schedule");
//                Log.d("list", "onComplete: Schedule List "+ schedule);
//            }
//        });
//        Log.d(TAG, "getSchedule: List size" + schedule.);
//        return schedule;
//
//    }
}