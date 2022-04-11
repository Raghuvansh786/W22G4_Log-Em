package com.example.dashboardlogem.admin_fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.dashboardlogem.R;
import com.example.dashboardlogem.activities.EmployeeAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AdminMessages extends Fragment {
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    View view;
    ListView lstViewMessage;
    List<String> empIds = new ArrayList<>();
    List<String> empNames = new ArrayList<>();
    List<String> msgTitle = new ArrayList<>();
    List<String> msgDesc = new ArrayList<>();
    private static final String TAG = "AdminMessages";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        view = inflater.inflate(R.layout.fragment_admin_messages, container, false);
        lstViewMessage = view.findViewById(R.id.lstViewMessages);
        getMessages();
        EmployeeAdapter messages = new EmployeeAdapter(empNames, msgTitle);
        messages.notifyDataSetChanged();
        lstViewMessage.setAdapter(messages);


        lstViewMessage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String message = msgDesc.get(i);
                String messageTitle = msgTitle.get(i);
                String sender  = empNames.get(i);

                getActivity().getSupportFragmentManager().beginTransaction().
                        replace(R.id.fragmentContainer,
                                new AdminMessageDetail(sender,message,messageTitle)).commit();
                lstViewMessage.setVisibility(View.INVISIBLE);
            }
        });
        return view;
    }

    public void getMessages() {

        fStore.collection("Messages").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        empIds.add(document.getId());
                        empNames.add(document.getData().get("fullName").toString());
                        msgTitle.add(document.getData().get("Title").toString());
                        msgDesc.add(document.getData().get("Message").toString());
                        Log.d(TAG, document.getId() + " => " + document.getData().get("fullName"));
                        Log.d(TAG, "onComplete: The size of list is " + msgTitle.size());
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
//
//                EmployeeAdapter messages = new EmployeeAdapter(empNames, msgTitle);
////        Log.d(TAG, "onCreateView: The count of the Adapter is:");
//                lstViewMessage = view.findViewById(R.id.lstViewMessages);
//                lstViewMessage.setAdapter(messages);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: Error Occurred: " + e.getMessage());
            }

        });
    }
}