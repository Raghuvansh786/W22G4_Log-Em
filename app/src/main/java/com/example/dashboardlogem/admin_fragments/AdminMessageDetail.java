package com.example.dashboardlogem.admin_fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.dashboardlogem.R;
import com.example.dashboardlogem.admin.AdminDashboard;


public class AdminMessageDetail extends Fragment {
TextView txtMsgSender,txtMsgSubject,txtMsgDesc;
String Sender, Message, Subject;
View view;
Button btnBack;

    public AdminMessageDetail(String sender, String message, String subject) {
        Sender = sender;
        Message = message;
        Subject = subject;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_admin_message_detail, container, false);

        txtMsgSender = view.findViewById(R.id.txtMsgSender);
        txtMsgDesc = view.findViewById(R.id.txtMessageDesc);
        txtMsgSubject = view.findViewById(R.id.txtMsgSubject);

        txtMsgSubject.setText(Subject);
        txtMsgDesc.setText(Message);
        txtMsgSender.setText(Sender);


        btnBack = view.findViewById(R.id.btnBack);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AdminDashboard.class));
            }
        });

        return view;
    }
}