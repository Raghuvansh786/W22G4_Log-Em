package com.example.dashboardlogem;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dashboardlogem.activities.logEm_login;
import com.example.dashboardlogem.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    EditText editTextFullName, editTextEmail, editTextPassword, editTextAdminCode;
    CheckBox chkBoxIsAdmin;
    Button btnSignUp, btnMockSignIn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    FirebaseFirestore fStore;

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
            btnSignUp = findViewById(R.id.btnSignUp);
            editTextPassword = binding.editTxtPassword;
            editTextEmail = binding.editTxtEmail;
            chkBoxIsAdmin = binding.chkBoxIsAdmin;
            editTextAdminCode = binding.edtTxtAdminCode;
            fAuth = FirebaseAuth.getInstance();
            fStore = FirebaseFirestore.getInstance();
            progressBar = binding.progressBar;
            btnSignUp = binding.btnSignUp;
            btnMockSignIn = binding.btnMockLogIn;
            if (fAuth.getCurrentUser() != null) {
                //Send the user to the next activity.
                startActivity(new Intent(SignUpActivity.this, MainActivity.class));
            }

            Log.d("TAG", "Admin is not checked yet.");

            chkBoxIsAdmin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (compoundButton.isChecked()) {
                        Log.d("TAG", "Admin is Clicked.");
                        editTextAdminCode.setVisibility(View.VISIBLE);
                    } else {
                        editTextAdminCode.setVisibility(View.INVISIBLE);
                    }
                }
            });


            btnSignUp.setOnClickListener(
                    (View view) -> {
                        String fullName = editTextFullName.getText().toString();
                        String email = editTextEmail.getText().toString();
                        String password = editTextPassword.getText().toString();
                        String AdminCode = editTextAdminCode.getText().toString();


                        Log.d("button", "Button is clicked");
                        Log.d("fullName", fullName);
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
                                        progressBar.setVisibility(View.VISIBLE);
                                        fAuth.createUserWithEmailAndPassword(email, password).
                                                addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<AuthResult> task) {

                                                        if (task.isSuccessful()) {
                                                            String user = fAuth.getCurrentUser().getUid();
                                                            DocumentReference df = fStore.collection("Users").document(user);

                                                            Map<String, Object> userInfo = new HashMap<>();

                                                            userInfo.put("fullName", fullName);
                                                            userInfo.put("email", email);
                                                            if (AdminCode.length() == 4 && AdminCode.equalsIgnoreCase("1111")) {
                                                                userInfo.put("isAdmin", "1");
                                                            } else {
                                                                userInfo.put("isUser", "1");
                                                            }
                                                            df.set(userInfo);
                                                            Toast.makeText(SignUpActivity.this, "User Created..",
                                                                    Toast.LENGTH_SHORT).show();
                                                            startActivity(new Intent(SignUpActivity.this, logEm_login.class));
                                                            finish();
                                                        } else {
                                                            Toast.makeText(SignUpActivity.this, "Error Occurred." +
                                                                    task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
//
//                                        if (AdminCode.length() == 4 && AdminCode.equalsIgnoreCase("1111")) {
//
//
//                                        } else {
//
//                                            Toast.makeText(this, "Admin code is Exactly 4 digits. Please enter again.", Toast.LENGTH_SHORT).show();
//                                        }

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
                        startActivity(new Intent(SignUpActivity.this, logEm_login.class));
                    });

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("error", e.getMessage());
        }


    }
}






