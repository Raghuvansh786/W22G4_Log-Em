package com.example.log_em;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.log_em.databinding.ActivityAdminLandingBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdminLanding extends AppCompatActivity {
    ActivityAdminLandingBinding binding;
    Button btnLogOut, btnTimeOff, btnUpdateAvailability, btnShowEmps, btnAddSchedule,btnVTO,btnAvailableRequest;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    ListView lstViewEmployee;
    private static final String TAG = "AdminLanding";
    List<String> empNames = new ArrayList<>();
    List<String> empEmail = new ArrayList<>();
    List<String> empIds = new ArrayList<>();
    List<String> requestedDates = new ArrayList<>();
    List<String> cities  = new ArrayList<>(Arrays.asList("Vancouver","Toronto","Surrey"));
    List<String> proviene = new ArrayList<>(Arrays.asList("British Columbia","Ontario","British Columbia"));
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_admin_landing);


        binding = ActivityAdminLandingBinding.inflate(getLayoutInflater());
        View layout = binding.getRoot();
        setContentView(layout);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        btnLogOut = binding.btnSignOut;
        btnAddSchedule = binding.btnAddSchedule;
        btnTimeOff = binding.btnTimeOff;
        btnUpdateAvailability = binding.btnAvailability;
        btnShowEmps = binding.btnAllEmps;
        btnVTO = binding.btnVtoRequest;
        lstViewEmployee = binding.lstViewEmps;
        btnAvailableRequest = binding.btnAvailability;
        getData();
        getVtoRequest();
        btnLogOut.setOnClickListener(
                (View view) -> {
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(AdminLanding.this, LogInActivity.class));
                    finish();
                });

        btnUpdateAvailability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//
            }
        });
        btnShowEmps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getData();
                EmployeeAdapter employeeAdapter = new EmployeeAdapter(empNames,proviene);
                employeeAdapter.notifyDataSetChanged();
                lstViewEmployee.setAdapter(employeeAdapter);
            }
        });

        btnAddSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getData();
                EmployeeAdapter employeeAdapter = new EmployeeAdapter(empNames, empEmail);
                employeeAdapter.notifyDataSetChanged();
                    lstViewEmployee.setAdapter(employeeAdapter);


                lstViewEmployee.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String curEmpName = empNames.get(i);
                        String curEmpEmail = empEmail.get(i);
                        String curEmpId = empIds.get(i);

                        Intent intent = new Intent(AdminLanding.this, AddEmpSchedule.class);
                        Bundle bundle = new Bundle();

                        bundle.putString("clickedEmpName", curEmpName);
                        bundle.putString("clickedEmpEmail", curEmpEmail);
                        bundle.putString("clickedEmpId", curEmpId);

                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });


            }
        });

        btnVTO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EmployeeAdapter timeOffRequest = new EmployeeAdapter(empNames,requestedDates);
                timeOffRequest.notifyDataSetChanged();
                lstViewEmployee.setAdapter(timeOffRequest);

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
                                    empIds.add(document.getId());
//                                    Log.d(TAG, "getData: The empid " + document.getId());
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

public void getVtoRequest() {
    Log.d(TAG, "getVtoRequest: In the method.");
    fStore.collection("TimeOff")
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            requestedDates.add(document.getData().get("date").toString());
                        }
                        Log.d(TAG, "requested date list size"+ requestedDates.size());
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            Log.d(TAG, "onFailure: Error" + e.getMessage());
        }
    });
}
}