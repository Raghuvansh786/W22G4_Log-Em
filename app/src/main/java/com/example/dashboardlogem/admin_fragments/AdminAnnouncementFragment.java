package com.example.dashboardlogem.admin_fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.dashboardlogem.R;
import com.example.dashboardlogem.activities.EmployeeAdapter;
import com.example.dashboardlogem.admin.AdminDashboard;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminAnnouncementFragment extends Fragment {
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    View view;
    EditText editTxtTitle, editTxtMessage;
    Button btnCancelMessage, btnSendMessage;
    String userName, userID, subject, message;
    ListView lstViewMessages;
    List<String> empIds = new ArrayList<>();
    List<String> empNames = new ArrayList<>();
    List<String> msgTitle = new ArrayList<>();
    List<String> msgDesc = new ArrayList<>();
    private static final String TAG = "MessageTemplate";
    String eName, eEmail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_admin_announcement, container, false);

        editTxtTitle = view.findViewById(R.id.editTxtSubject);
        editTxtMessage = view.findViewById(R.id.editTxtMessage);


        btnCancelMessage = view.findViewById(R.id.btnCancelMessage);
        btnSendMessage = view.findViewById(R.id.btnSendMessage);
        lstViewMessages = view.findViewById(R.id.lstViewMessages);


        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        String userID = fAuth.getCurrentUser().getUid();

        getCurrentUserData(userID);
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
                EmployeeAdapter messages = new EmployeeAdapter(empNames, msgTitle);
                lstViewMessages.setAdapter(messages);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: Error Occurred: " + e.getMessage());
            }
        });


        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                subject = editTxtTitle.getText().toString();
                message = editTxtMessage.getText().toString();

                if (subject.isEmpty() || message.isEmpty()) {
                    Toast.makeText(getActivity(), "Please Enter the Message or Title.", Toast.LENGTH_SHORT).show();
                } else {
                    addMessage(userID, eName, subject, message);
                    editTxtMessage.setText(null);
                    editTxtTitle.setText(null);
                }
            }
        });
        return view;

    }

    private void addMessage(String userID, String userName, String subject, String message) {
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        DocumentReference df = fStore.collection("Messages").document(userID);
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("fullName", userName);
        userInfo.put("Title", subject);
        userInfo.put("Message", message);

        df.set(userInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getActivity(), "Added the Message", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Error while sending Message:" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        startActivity(new Intent(getActivity(), AdminDashboard.class));
    }

    private void getCurrentUserData(String uid) {

        DocumentReference df = fStore.collection("Users").document(uid);

        df.get().addOnCompleteListener(
                (@NonNull Task<DocumentSnapshot> task) -> {

                    Log.d("abcd", "onComplete: Reading From the database");
                    DocumentSnapshot document = task.getResult();
                    List<String> schedule = (List<String>) document.get("schedule");
                    eName = document.get("fullName").toString();
                    eEmail = document.get("email").toString();

                });
    }
}