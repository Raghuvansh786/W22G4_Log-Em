package com.example.log_em;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.log_em.databinding.ActivityLogInBinding;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogInActivity extends AppCompatActivity {
    Button btnMockSignUp, btnLogIn;
    EditText editTextEmail, editTextPassword;
    FirebaseAuth fAuth;
    ActivityLogInBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLogInBinding.inflate(getLayoutInflater());
        View layout = binding.getRoot();
        setContentView(layout);

        btnMockSignUp = binding.btnMockSignUp;
        editTextEmail = binding.editTxtEmail;
        editTextPassword = binding.editTxtPassword;
        btnLogIn = binding.btnLogIn;
        fAuth = FirebaseAuth.getInstance();

//        if (fAuth.getCurrentUser() != null) {
//            //Send the user to the next activity.
//            startActivity(new Intent(this, LandingPage.class));
//        }


        btnLogIn.setOnClickListener(
                (View view) ->{
                    String email = editTextEmail.getText().toString();
                    String password = editTextPassword.getText().toString().trim();
                    Log.d("Email", email);
                    Log.d("pass", password);

                    if(!email.isEmpty()){
                        if(!password.isEmpty()){
                            if(password.length() < 6) {
                                Toast.makeText(this, "Password should be greater than 6 characters", Toast.LENGTH_SHORT).show();
                            }else {
                                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(
                                        (@NonNull Task<AuthResult> task) ->{
                                            if(task.isSuccessful()){
                                                startActivity(new Intent(LogInActivity.this,LandingPage.class));
                                                Toast.makeText(LogInActivity.this, "Logged In Successfully..", Toast.LENGTH_SHORT).show();
                                            }else {
                                                Toast.makeText(LogInActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            }

                                });
                            }
                        }else {
                            Toast.makeText(this, "Please enter a password.", Toast.LENGTH_SHORT).show();
                        }

                    }else {
                        Toast.makeText(this, "Please Enter email address.", Toast.LENGTH_SHORT).show();
                    }



        });




        btnMockSignUp.setOnClickListener(
                (View view) -> {
                    startActivity(new Intent(this, SignUpActivity.class));
                });
    }
}