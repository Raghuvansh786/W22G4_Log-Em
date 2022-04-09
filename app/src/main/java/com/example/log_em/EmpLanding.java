package com.example.log_em;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.log_em.databinding.ActivityEmpLandingBinding;
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

public class EmpLanding extends AppCompatActivity {
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    ActivityEmpLandingBinding binding;
    Button btnLogOut, btnTimeOff, btnAvailability,btnSchdule,btnClockIn,btnClockOut,btnCreateMsg,btnNotifications;
    private static final String TAG = "EmpLanding";
    List<String> schedule = new ArrayList<>();
    String eName, eEmail;
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityEmpLandingBinding.inflate(getLayoutInflater());
        View layout = binding.getRoot();
        setContentView(layout);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        btnLogOut = binding.btnSignOut;
        btnSchdule = binding.btnSchedule;
        btnTimeOff = binding.btnTimeOff;
        btnAvailability = binding.btnAvailability;
        btnClockIn = binding.btnClockIn;
        btnClockOut=binding.btnClockOut;
        btnCreateMsg= binding.btnCreateMessage;
        btnNotifications = binding.btnNotifications;

        // Getting the Current User id
        String userID = fAuth.getCurrentUser().getUid();
        getSchduledDates(userID);
        getData(userID);

        btnCreateMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    replaceFragement(new MessageTemplate(eName,userID));
            }
        });

        btnNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EmpLanding.this,Notifications.class));
                finish();
            }
        });

        btnClockIn.setOnClickListener(new View.OnClickListener() {

            final String Title = "Clock In";
            final String dbkey = "inTime";
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat date = new SimpleDateFormat("MM/dd/yyyy");
                SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                String time =  format.format(calendar.getTime());
                String Date = date.format(calendar.getTime());
                replaceFragement(new ClockInFragment(time,Date,schedule,userID,Title,dbkey));
                Log.d(TAG, "onClick: Current Time is: "+ time);
                Log.d(TAG, "onClick: Today's Date is: "+ Date);
            }
        });

        btnSchdule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EmpLanding.this, LandingPage.class));
                finish();
            }
        });

        btnLogOut.setOnClickListener(
                (View view) -> {
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(EmpLanding.this, LogInActivity.class));
                    finish();
                });

        btnAvailability.setOnClickListener(
                (View view) -> {
                    startActivity(new Intent(EmpLanding.this, updateAvailability.class));
                    finish();

                });


        btnTimeOff.setOnClickListener(
                (View view) -> {

                    startActivity(new Intent(EmpLanding.this, timeOff.class));
                    finish();
                });
    }

    private void replaceFragement(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, fragment,null)
                .setReorderingAllowed(true)
                .addToBackStack("name") // name can be null
                .commit();

    }

    public void getSchduledDates(String uId) {
        DocumentReference df = fStore.collection("Users").document(uId);

        df.get().addOnCompleteListener(
                (@NonNull Task<DocumentSnapshot> task) -> {

                    Log.d("abcd", "onComplete: Reading From the database");
                    DocumentSnapshot document = task.getResult();
                    schedule = (List<String>) document.get("schedule");
                    Log.d(TAG, "getSchduledDates: The size is: "+ schedule.size());
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

                });
    }

}