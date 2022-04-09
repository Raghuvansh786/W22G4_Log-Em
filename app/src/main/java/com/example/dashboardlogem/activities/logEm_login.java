package com.example.dashboardlogem.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dashboardlogem.MainActivity;
import com.example.dashboardlogem.R;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class logEm_login extends AppCompatActivity {

    Button btnMockSignUp, btnLogIn;
    EditText editTextEmail, editTextPassword;
    FirebaseAuth fAuth;

    FirebaseFirestore fStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_em_login);

        btnMockSignUp = findViewById(R.id.btnMockSignUp);
        editTextEmail = findViewById(R.id.editTxtEmail);
        editTextPassword = findViewById(R.id.editTxtPassword);
        btnLogIn = findViewById(R.id.btnLogIn);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();




//        if (fAuth.getCurrentUser() != null) {
//            //Send the user to the next activity.
//            startActivity(new Intent(this, LandingPage.class));
//        }


        btnLogIn.setOnClickListener(
                (View view) -> {
                    String email = editTextEmail.getText().toString();
                    String password = editTextPassword.getText().toString().trim();
                    Log.d("Email", email);
                    Log.d("pass", password);

                    try {


                        if (!email.isEmpty()) {
                            if (!password.isEmpty()) {
                                if (password.length() < 6) {
                                    Toast.makeText(this, "Password should be greater than 6 characters", Toast.LENGTH_SHORT).show();
                                } else {
                                    fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(
                                            (@NonNull Task<AuthResult> task) -> {
                                                if (task.isSuccessful()) {
                                                    Log.d("TAG", "In the sign in method");

                                                    checkUserAccess(fAuth.getCurrentUser().getUid());
                                                    Log.d("TAG", "" + fAuth.getCurrentUser());
//                                                startActivity(new Intent(LogInActivity.this,LandingPage.class));
                                                    Toast.makeText(logEm_login.this, "Logged In Successfully..", Toast.LENGTH_SHORT).show();
                                                    finish();
                                                    startActivity(new Intent(logEm_login.this,MainActivity.class));

                                                } else {
                                                    Log.d("Error", task.getException().getMessage());
                                                    Toast.makeText(logEm_login.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                }

                                            });
                                }
                            } else {
                                Toast.makeText(this, "Please enter a password.", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(this, "Please Enter email address.", Toast.LENGTH_SHORT).show();
                        }
                    }catch(Exception ex){
                        Log.d("abcd",ex.getMessage());
                    }
//                    startActivity(new Intent(logEm_login.this,MainActivity.class));

                });


//        btnMockSignUp.setOnClickListener(
//                (View view) -> {
//                    fAuth.signOut();
//                    startActivity(new Intent(this, SignUpActivity.class));
//                });
    }

    private void checkUserAccess(String uid) {
        DocumentReference df = fStore.collection("Users").document(uid);
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                Log.d("TAG", "" + documentSnapshot.getData());

                if (documentSnapshot.getString("isAdmin") != null) {
                    // Redirect to the admin activity
                    startActivity(new Intent(logEm_login.this, MainActivity.class));
                }

                if (documentSnapshot.getString("isUser") != null) {
                    Log.d("TAG", "onSuccess: Redirecting to the EmpLanding Activity");
                    startActivity(new Intent(logEm_login.this, MainActivity.class));
                }
            }
        });
    }
}