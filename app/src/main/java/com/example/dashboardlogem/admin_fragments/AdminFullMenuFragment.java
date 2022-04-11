package com.example.dashboardlogem.admin_fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.dashboardlogem.Employee;
import com.example.dashboardlogem.R;
import com.example.dashboardlogem.WageFragment;
import com.example.dashboardlogem.activities.EmployeeAdapter;
import com.example.dashboardlogem.activities.logEm_login;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class AdminFullMenuFragment extends Fragment {
    ListView lstViewEmp;
    Button btnLogout;
    View view;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    List<String > empId = new ArrayList<>();
    List<String> empNames = new ArrayList<>();
    private static final String TAG = "AdminFullMenuFragment";
    List<String> empEmail = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_admin_full_menu, container, false);
        btnLogout = view.findViewById(R.id.btnLogout);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        lstViewEmp = view.findViewById(R.id.listViewEmp);
//        getData();

        try {
            fStore.collection("Users")
                    .whereEqualTo("isUser", "1")
                    .get()
                    .addOnCompleteListener(
                            (@NonNull Task<QuerySnapshot> task) -> {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
//                                    Log.d(TAG, "getData: The empid " + document.getId());
                                        empId.add(document.getId());
                                        empNames.add(document.getData().get("fullName").toString());
                                        empEmail.add(document.getData().get("email").toString());
                                        Log.d(TAG, document.getId() + " => " + document.getData().get("fullName"));
//                                    names(empNames);
                                        EmployeeAdapter employees = new EmployeeAdapter(empNames, empEmail);
                                        Log.d(TAG, "onCreateView: The count of the Adapter is:" + employees.getCount());
                                        lstViewEmp.setAdapter(employees);

                                    }
                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            });

        }catch (Exception e) {
            Log.d(TAG, "onCreateView: Error"+ e.getMessage());
        }

        lstViewEmp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String CNAme = empNames.get(i);
                String CEmail = empEmail.get(i);
                String Cid = empId.get(i);

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView0, new WageFragment(CNAme,CEmail,Cid)).commit();
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(), logEm_login.class));
            }
        });

        return view;
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


}


