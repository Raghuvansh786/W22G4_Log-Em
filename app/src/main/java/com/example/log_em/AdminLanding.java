package com.example.log_em;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.log_em.databinding.ActivityAdminLandingBinding;
import com.google.firebase.auth.FirebaseAuth;

public class AdminLanding extends AppCompatActivity {

    Button btnLogOut;
    FirebaseAuth fAuth;

    ActivityAdminLandingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAdminLandingBinding.inflate(getLayoutInflater());
        View layout = binding.getRoot();
        setContentView(layout);
        fAuth = FirebaseAuth.getInstance();
        btnLogOut = binding.btnSignOut;

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(AdminLanding.this, LogInActivity.class));
                finish();
            }
        });
    }
}