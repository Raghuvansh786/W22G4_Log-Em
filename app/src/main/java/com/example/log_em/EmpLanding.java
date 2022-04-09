package com.example.log_em;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.log_em.databinding.ActivityEmpLandingBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class EmpLanding extends AppCompatActivity {
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    ActivityEmpLandingBinding binding;
    Button btnLogOut, btnTimeOff, btnAvailability,btnSchdule;
    private static final String TAG = "EmpLanding";
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


}