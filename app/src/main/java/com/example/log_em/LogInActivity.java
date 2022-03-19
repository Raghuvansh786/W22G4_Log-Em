package com.example.log_em;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.log_em.databinding.ActivityLogInBinding;

public class LogInActivity extends AppCompatActivity {
Button btnMockSignUp;
    ActivityLogInBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLogInBinding.inflate(getLayoutInflater());
        View layout =  binding.getRoot();
        setContentView(layout);

        btnMockSignUp = binding.btnMockSignUp;

        btnMockSignUp.setOnClickListener(
                (View view) ->{

                    startActivity(new Intent(this,SignUpActivity.class));
        });
    }
}