package com.example.dashboardlogem.admin_fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.dashboardlogem.R;
import com.example.dashboardlogem.activities.EmployeeAdapter;
import com.example.dashboardlogem.admin.AddEmpSchedule;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class AdminHomeFragment extends Fragment {

    //    ActivityAdminLandingBinding binding;
    Button btnLogOut, btnTimeOff, btnUpdateAvailability, btnShowEmps, btnAddSchedule,btnVTO,btnAvailableRequest;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    ListView lstViewEmployee;
    TextView txtViewAdminTitle,txtViewCompanyTitle;
    View view;
    private static final String TAG = "AdminHomeFragment";
    List<String> empNames = new ArrayList<>();
    List<String> empEmail = new ArrayList<>();
    List<String> empIds = new ArrayList<>();
    List<String> requestedDates = new ArrayList<>();
    List<String> requestedEmps = new ArrayList<>();
    String eName, eEmail;
    List<String> availableDates = new ArrayList<>();
    List<String> availableEmp = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_admin_home, container, false);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        String userID = fAuth.getCurrentUser().getUid();
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_admin_landing);

//        btnLogOut = view.findViewById(R.id.btnSignOut);
        btnAddSchedule = view.findViewById(R.id.btnAddSchedule);
//        btnTimeOff = view.findViewById(R.id.btnTimeOff);
//        btnUpdateAvailability = view.findViewById(R.id.btnAvailability);
        btnShowEmps = view.findViewById(R.id.btnAllEmps);
        btnVTO = view.findViewById(R.id.btnVtoRequest);
        lstViewEmployee = view.findViewById(R.id.lstViewEmps);
        btnAvailableRequest = view.findViewById(R.id.btnAvailablity);
        txtViewAdminTitle = view.findViewById(R.id.txtViewAdminTitle);
        txtViewCompanyTitle=view.findViewById(R.id.txtViewCompanyTitle);
        getData();
        getVtoRequest();
        getAvailableDates();
//        getCurrentUserData(userID);
        DocumentReference df = fStore.collection("Users").document(userID);

        df.get().addOnCompleteListener(
                (@NonNull Task<DocumentSnapshot> task) -> {

                    Log.d("abcd", "onComplete: Reading From the database");
                    DocumentSnapshot document = task.getResult();
                    eName = document.get("fullName").toString();
                    eEmail = document.get("email").toString();
                    Log.d(TAG, "getCurrentUserData: The name of the user is:" +eName);
                    txtViewAdminTitle.setText(eName);
                    txtViewCompanyTitle.setText(eEmail);
                });
//        Log.d(TAG, "onCreateView: The name of the user is"+eName);

//        btnLogOut.setOnClickListener(
//                (View view) -> {
//                    FirebaseAuth.getInstance().signOut();
//                    startActivity(new Intent(view.getContext(), logEm_login.class));
////                    finish();
//                });

        btnAvailableRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EmployeeAdapter employeeAdapter = new EmployeeAdapter(availableEmp,availableDates);
                employeeAdapter.notifyDataSetChanged();
                lstViewEmployee.setAdapter(employeeAdapter);
                Log.d(TAG, "onClick: All employee count"+lstViewEmployee.getCount());
            }
        });
        btnShowEmps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getData();
                EmployeeAdapter employeeAdapter = new EmployeeAdapter(empNames,empEmail);
                employeeAdapter.notifyDataSetChanged();
                lstViewEmployee.setAdapter(employeeAdapter);
                Log.d(TAG, "onClick: All employee count"+lstViewEmployee.getCount());
                lstViewEmployee.setOnItemClickListener(null);
            }
        });

        btnAddSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getData();
                EmployeeAdapter employeeAdapter = new EmployeeAdapter(empNames, empEmail);
//                employeeAdapter.notifyDataSetChanged();
                lstViewEmployee.setAdapter(employeeAdapter);


                lstViewEmployee.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String curEmpName = empNames.get(i);
                        String curEmpEmail = empEmail.get(i);
                        String curEmpId = empIds.get(i);

                        Intent intent = new Intent(view.getContext(), AddEmpSchedule.class);
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

                EmployeeAdapter timeOffRequest = new EmployeeAdapter(requestedEmps,requestedDates);
//                timeOffRequest.notifyDataSetChanged();
                Log.d(TAG, "onClick: Size of requested dated is: "+ requestedDates.size());
                lstViewEmployee.setAdapter(timeOffRequest);

            }
        });
        return view;

    }
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
                                requestedEmps.add(document.getData().get("fullName").toString());
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

    public void getAvailableDates() {
        fStore.collection("Availability")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                availableDates.add(document.getData().get("date").toString());
                                availableEmp.add(document.getData().get("fullName").toString());
                            }
                            Log.d(TAG, "requested date list size"+ availableDates.size());
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

    private void getCurrentUserData(String uid) {

        DocumentReference df = fStore.collection("Users").document(uid);

        df.get().addOnCompleteListener(
                (@NonNull Task<DocumentSnapshot> task) -> {

                    Log.d("abcd", "onComplete: Reading From the database");
                    DocumentSnapshot document = task.getResult();
                    eName = document.get("fullName").toString();
                    eEmail = document.get("email").toString();
                    Log.d(TAG, "getCurrentUserData: The name of the user is:" +eName);

                });
    }
}