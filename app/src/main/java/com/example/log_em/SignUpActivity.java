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
    ProgressBar progressBar;

    ActivitySignUpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        View layout = binding.getRoot();
//        setContentView(R.layout.activity_sign_up);
        setContentView(layout);

        try {
        editTextFullName = binding.editTxtFullName;
        btnSignUp=  findViewById(R.id.btnSignUp);
        editTextPassword = binding.editTxtPassword;
          editTextEmail = binding.editTxtEmail;
        fAuth = FirebaseAuth.getInstance();
         progressBar = binding.progressBar;
        btnSignUp = binding.btnSignUp;
        btnMockSignIn = binding.btnMockLogIn;
        if (fAuth.getCurrentUser() != null) {
            //Send the user to the next activity.
            startActivity(new Intent(SignUpActivity.this, LandingPage.class));
        }

            btnSignUp.setOnClickListener(
            (View view) -> {
                String fullName = editTextFullName.getText().toString();
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();
                Log.d("button", "Button is clicked");
                Log.d("fullName", fullName );
                Log.d("email", email);
                if (!fullName.isEmpty()) {
                    Log.d("fullName", "Full Name is checked");
                    if (!email.isEmpty()) {
                        Log.d("email", "Email is checked");
                        if (!password.isEmpty()) {
                            Log.d("password", "password is checked");
                            if (password.length() < 6) {
                                Toast.makeText(this, "Please Enter a password greater than 6 characters.",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                progressBar.setVisibility(view.VISIBLE);

                                fAuth.createUserWithEmailAndPassword(email, password).
                                        addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                            @Override
                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                if (task.isSuccessful()) {
                                                    Toast.makeText(SignUpActivity.this, "User Created..",
                                                            Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(SignUpActivity.this, LandingPage.class));
                                                } else {
                                                    Toast.makeText(SignUpActivity.this, "Error Occured." +
                                                            task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }


                        } else {
                            Toast.makeText(this, "Please Enter a valid password.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "Please enter a valid email.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Please Enter full Name.", Toast.LENGTH_SHORT).show();
                }


            });


            btnMockSignIn.setOnClickListener(
                    (View view) -> {
                        Log.d("Btn", "Log In clicked");
                        startActivity(new Intent(SignUpActivity.this, LogInActivity.class));
                    });

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("error",e.getMessage());
        }


    }
}






