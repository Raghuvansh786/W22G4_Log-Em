package com.example.log_em;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.log_em.databinding.ActivityAdminLandingBinding;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AdminLanding extends AppCompatActivity {
    ActivityAdminLandingBinding binding;
    Button btnLogOut,btnTimeOff,btnAvailability,btnShowEmps,btnAddSchedule;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    ListView lstViewEmployee;
    private static final String TAG = "AdminLanding";
    List<String> empNames = new ArrayList<>();
    List<String> empEmail = new ArrayList<>();
    List<String> empIds = new ArrayList<>();
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_admin_landing);


        binding = ActivityAdminLandingBinding.inflate(getLayoutInflater());
        View layout = binding.getRoot();
        setContentView(layout);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
//        btnLogOut = binding.btnSignOut;
        btnLogOut =binding.btnSignOut;
        btnAddSchedule = binding.btnAddSchedule;
        btnShowEmps = binding.btnAllEmps;
        btnTimeOff= binding.btnTimeOff;
        lstViewEmployee = binding.lstViewEmps;
        btnAvailability= binding.btnAvailability;
        getData();
//        replaceFragement(new fragmentEmployees(empNames,empEmail));

         btnLogOut.setOnClickListener(
                (View view) ->{
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(AdminLanding.this, LogInActivity.class));
                finish();
        });

        btnAvailability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//
            }
        });
        btnShowEmps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EmployeeAdapter employeeAdapter = new EmployeeAdapter(empNames, empEmail);
                lstViewEmployee.setAdapter(employeeAdapter);
            }
        });
    }
//    private void replaceFragement(Fragment fragment) {
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        fragmentManager.beginTransaction()
//                .replace(R.id.fragmentContainerView, fragment,null)
//                .setReorderingAllowed(true)
//                .addToBackStack("name") // name can be null
//                .commit();
//
//    }

    public void names(List<String> x) {
        Log.d(TAG, "onCreate: The size of empNames List is: " + x.size());
    }

    public void getData() {
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        fStore.collection("Users")
                .whereEqualTo("isUser", "1")
                .get()
                .addOnCompleteListener(
                        (@NonNull Task<QuerySnapshot> task) -> {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
//                                    empIds.add(document.getId());
                                    Log.d(TAG, "getData: The empid "+ document.getId());;
                                    empNames.add(document.getData().get("fullName").toString());
                                    empEmail.add(document.getData().get("email").toString());
                                    Log.d(TAG, document.getId() + " => " + document.getData().get("fullName"));
//                                    names(empNames);
                                }
                            } else {
                                Log.d(TAG, "Error getting documents: ", task.getException());
                            }

                        });
    }
}