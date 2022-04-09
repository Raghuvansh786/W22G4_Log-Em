package com.example.log_em;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MessageTemplate extends Fragment {
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    View view;
    EditText editTxtTitle,editTxtMessage;
    Button btnCancelMessage,btnSendMessage;
    String userName,userID, subject, message;
    private static final String TAG = "MessageTemplate";


    public MessageTemplate(String userName, String userID) {
        this.userName = userName;
        this.userID = userID;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_message_template, container, false);

        editTxtTitle = view.findViewById(R.id.editTxtSubject);
        editTxtMessage = view.findViewById(R.id.editTxtMessage);


        btnCancelMessage = view.findViewById(R.id.btnCancelMessage);
        btnSendMessage = view.findViewById(R.id.btnSendMessage);

        btnSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 subject = editTxtTitle.getText().toString();
                 message = editTxtMessage.getText().toString();

                 if(subject.isEmpty() || message.isEmpty()){
                     Toast.makeText(getActivity(), "Please Enter the Message or Title.", Toast.LENGTH_SHORT).show();
                 }else {
                     addMessage(userID,userName,subject,message);
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
        userInfo.put("fullName",userName);
        userInfo.put("Title",subject);
        userInfo.put("Message",message);

        df.set(userInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getActivity(), "Added the Message", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Error while sending Message:" +e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        startActivity(new Intent(getActivity(), EmpLanding.class));

    }
}