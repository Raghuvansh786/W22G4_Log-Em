package com.example.log_em;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class notificationsAdapter extends BaseAdapter {
    List<String> empName;
    List<String> msgSubject;
    private static final String TAG = "notificationsAdapter";
    public notificationsAdapter(List<String> empName, List<String> msgSubject) {
        this.empName = empName;
        this.msgSubject = msgSubject;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
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

        if (view == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());

            view = layoutInflater.inflate(R.layout.notificationsview, viewGroup, false);
        }
        TextView txtViewName, txtViewSubject;

        txtViewName = view.findViewById(R.id.txtViewSenderName);
        txtViewSubject = view.findViewById(R.id.txtViewSubject);
        Log.d(TAG, "getView: "+ empName.size());
        txtViewName.setText(empName.get(i));
        txtViewSubject.setText(msgSubject.get(i));
        return view;
    }
}
