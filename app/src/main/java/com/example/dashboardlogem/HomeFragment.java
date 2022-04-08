package com.example.dashboardlogem;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class HomeFragment extends Fragment {


    TextView txtViewName;
    TextView txtViewEmpNum;
    View view;
    HomeViewModel homeVM;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);


        homeVM = new ViewModelProvider(this).get(HomeViewModel.class);

        view = inflater.inflate(R.layout.fragment_home, container, false);
        txtViewName = view.findViewById(R.id.txtViewName);

        homeVM.generateUsername("Amit Bhattacharya");
        txtViewName.setText(homeVM.nameOfUser);
        txtViewEmpNum = view.findViewById(R.id.txtViewEmpNum);
        txtViewEmpNum.setText("12345");
        return view;
    }
}