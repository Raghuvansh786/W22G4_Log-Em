package com.example.dashboardlogem.admin_fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.dashboardlogem.R;
import com.example.dashboardlogem.activities.logEm_login;
import com.google.firebase.auth.FirebaseAuth;


public class AdminFullMenuFragment extends Fragment {

    Button btnLogout;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_admin_full_menu, container, false);
        btnLogout = view.findViewById(R.id.btnLogout);


        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(), logEm_login.class));
            }
        });

        return view;
    }
}