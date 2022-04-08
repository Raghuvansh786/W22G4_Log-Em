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
    TextView txtViewShiftTime;

    View view;
    HomeViewModel homeVM;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);


        homeVM = new ViewModelProvider(this).get(HomeViewModel.class);
        setUser(homeVM);
        view = inflater.inflate(R.layout.fragment_home, container, false);
        txtViewName = view.findViewById(R.id.txtViewName);
        txtViewEmpNum = view.findViewById(R.id.txtViewEmpNum);
        txtViewShiftTime = view.findViewById(R.id.txtViewShiftTime);

        txtViewName.setText(homeVM.getNameOfUser());
        txtViewEmpNum.setText(homeVM.getEmployeeNumber());
        txtViewShiftTime.setText(homeVM.getShiftTime());
        return view;
    }

    public void setUser(HomeViewModel hm){
        hm.setNameOfUser("Alborz Johnson");
        hm.setEmployeeNumber("0001234");
        hm.setShiftTime("9:00pm - 10:00am");
    }
}