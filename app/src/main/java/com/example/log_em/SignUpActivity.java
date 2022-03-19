package com.example.log_em;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.log_em.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {
    EditText editTextFullName, editTextEmail, editTextPassword;
    Button btnSignUp, btnMockSignIn;
    FirebaseAuth fAuth;


    ActivitySignUpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
//        View layout = binding.getRoot();
        setContentView(R.layout.activity_sign_up);

//        editTextFullName = binding.editTxtFullName;
        editTextFullName = findViewById(R.id.editTxtFullName);
        editTextEmail = binding.editTxtEmail;
        editTextPassword = binding.editTxtPassword;
        fAuth = FirebaseAuth.getInstance();
        ProgressBar progressBar = binding.progressBar;
        btnSignUp = binding.btnSignUp;
        btnMockSignIn = binding.btnMockLogIn;
        String fullName = editTextFullName.getText().toString();
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();
        Log.d("fullName", fullName);

        if (fAuth.getCurrentUser() != null) {
            //Send the user to the next activity.
            startActivity(new Intent(this, LandingPage.class));
        }
        try {

//    btnSignUp.setOnClickListener(
//            (View view) -> {
//                Log.d("button", "Button is clicked");
//                Log.d("fullName", fullName);
//                if (!fullName.isEmpty()) {
//                    Log.d("fullName", "Full Name is checked");
//                    if (!email.isEmpty()) {
//                        Log.d("email", "Email is checked");
//                        if (!password.isEmpty()) {
//                            Log.d("password", "password is checked");
//                            if (password.length() < 6) {
//                                Toast.makeText(this, "Please Enter a password greater than 6 characters.",
//                                        Toast.LENGTH_SHORT).show();
//                            } else {
//                                progressBar.setVisibility(view.VISIBLE);
//
//                                fAuth.createUserWithEmailAndPassword(email, password).
//                                        addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                                            @Override
//                                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                                if (task.isSuccessful()) {
//                                                    Toast.makeText(SignUpActivity.this, "User Created..",
//                                                            Toast.LENGTH_SHORT).show();
//                                                    startActivity(new Intent(SignUpActivity.this, LandingPage.class));
//                                                } else {
//                                                    Toast.makeText(SignUpActivity.this, "Error Occured." +
//                                                            task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//                                                }
//                                            }
//                                        });
//                            }
//
//
//                        } else {
//                            Toast.makeText(this, "Please Enter a valid password.", Toast.LENGTH_SHORT).show();
//                        }
//                    } else {
//                        Toast.makeText(this, "Please enter a valid email.", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    Toast.makeText(this, "Please Enter full Name.", Toast.LENGTH_SHORT).show();
//                }
//
//
//            });

        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }

        btnMockSignIn.setOnClickListener(
                (View view) -> {
        Log.d("Btn", "Log In clicked");
                    startActivity(new Intent(SignUpActivity.this, LogInActivity.class));
                });

    }
}