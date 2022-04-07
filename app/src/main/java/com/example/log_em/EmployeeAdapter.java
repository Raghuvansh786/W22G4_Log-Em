package com.example.log_em;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;


public class EmployeeAdapter extends BaseAdapter {
    List<String> empName;
    List<String> empEmail;
    private static final String TAG = "EmployeeAdapter";
    public EmployeeAdapter(List<String> empName, List<String> empEmail) {
        this.empName = empName;
        this.empEmail = empEmail;
    }

    @Override
    public int getCount() {
        Log.d(TAG, "getCount: Size of empName List" + empName.size());
        return empName.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());

            view = layoutInflater.inflate(R.layout.layout_employee,viewGroup,false);
        }
        TextView txtViewName, txtViewEmail;

        txtViewEmail = view.findViewById(R.id.txtViewEmail);
        txtViewName = view.findViewById(R.id.txtViewName);

        txtViewName.setText(empName.get(i));
        txtViewEmail.setText(empEmail.get(i));
    return view;
    }
}
