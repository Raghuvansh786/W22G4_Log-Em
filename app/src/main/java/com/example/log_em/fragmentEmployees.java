package com.example.log_em;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.List;

public class fragmentEmployees extends Fragment {
    ListView lstViewEmployee;
    private static final String TAG = "fragmentEmployees";
    List<String> empName;
    List<String> empEmail;
    View view;

    public fragmentEmployees(List<String> empName, List<String> empEmail) {
        this.empName = empName;
        this.empEmail = empEmail;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_employees, container, false);
        EmployeeAdapter employeeAdapter = new EmployeeAdapter(empName, empEmail);

        lstViewEmployee = view.findViewById(R.id.lstViewEmployees);
        lstViewEmployee.setAdapter(employeeAdapter);
        Log.d(TAG, "onCreateView: List View Size " + lstViewEmployee.getCount());
        return view;

    }
}