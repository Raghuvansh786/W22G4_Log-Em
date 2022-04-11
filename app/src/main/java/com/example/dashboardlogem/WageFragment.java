package com.example.dashboardlogem;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.dashboardlogem.admin.AdminDashboard;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class WageFragment extends Fragment {
    String empName, empEmail,eId;
    View view;
    EditText editTxtWage;
    TextView txtViewName, txtViewEmail;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    Button btnCancelWage, btnConfirmWage;


    public WageFragment(String empName, String empEmail, String eId) {
        this.empName = empName;
        this.empEmail = empEmail;
        this.eId = eId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_wage, container, false);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        txtViewName = view.findViewById(R.id.txtViewEName);
        txtViewEmail = view.findViewById(R.id.txtViewEEmail);
        editTxtWage = view.findViewById(R.id.editTxtEWage);
        btnConfirmWage = view.findViewById(R.id.btnConfirmWage);
        btnCancelWage = view.findViewById(R.id.btnCancelWage);
        txtViewEmail.setText(empEmail);
        txtViewName.setText(empName);

        btnConfirmWage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String wage = editTxtWage.getText().toString();

                if (wage.isEmpty()) {
                    Toast.makeText(getActivity(), "Please enter wage before confirming", Toast.LENGTH_SHORT).show();
                } else {
                    DocumentReference df = fStore.collection("Users").document(eId);
                    Map<String, Object> userInfo = new HashMap<>();
                    userInfo.put("Wage", wage);

                    df.update(userInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getActivity(), "Successfully added the wage for: " + empName, Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getActivity(),AdminDashboard.class));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "Error Occurred: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });

        btnCancelWage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AdminDashboard.class));
            }
        });


        return view;
    }
}