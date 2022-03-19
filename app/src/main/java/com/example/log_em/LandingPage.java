package com.example.log_em;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class LandingPage extends AppCompatActivity {

    Button btnLogOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        btnLogOut = findViewById(R.id.btnLogOut);

        btnLogOut.setOnClickListener((View view) ->{

            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, LogInActivity.class));
            finish();

        });
    }
}