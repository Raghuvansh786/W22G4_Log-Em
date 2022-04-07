package com.example.log_em;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.log_em.databinding.FragmentEmployeesBinding;

import java.util.List;

public class fragmentEmployees extends Fragment {
    ListView lstViewEmployee;
    private static final String TAG = "fragmentEmployees";
    List<String> empName;
    List<String> empEmail;
    FragmentEmployeesBinding binding;

    public fragmentEmployees(List<String> empName, List<String> empEmail) {
        this.empName = empName;
        this.empEmail = empEmail;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentEmployeesBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
//        lstViewEmployee = binding.lstViewEmployees;
        Log.d(TAG, "onCreateView: Size of empName list is" + empName.size());
//        EmployeeAdapter employeeAdapter = new EmployeeAdapter(empName,empEmail);
//        lstViewEmployee.setAdapter(employeeAdapter);
//        return  view;

        try {
            return inflater.inflate(R.layout.fragment_employees, container, false);
            // ... rest of body of onCreateView() ...
        } catch (Exception e) {
            Log.e(TAG, "onCreateView", e);
            throw e;
        }
//        return inflater.inflate(R.layout.fragment_employees, container, false);


    }
}