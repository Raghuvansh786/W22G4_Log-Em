package com.example.dashboardlogem;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dashboardlogem.interfaces.EmployeeDao;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HomeFragment extends Fragment {


    TextView txtViewName;
    TextView txtViewEmpNum;
    TextView txtViewShiftTime;
    Button btnClockIn;
    Button viewSchedule;
    ListView listView;
    ArrayList<String> scheduleList = new ArrayList<>();
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    View view;
    HomeViewModel homeVM;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        HomeViewModel homeViewModel =
//                new ViewModelProvider(this).get(HomeViewModel.class);
        view = inflater.inflate(R.layout.fragment_home, container, false);

        listView = view.findViewById(R.id.listViewSchedule);

        btnClockIn = view.findViewById(R.id.btnClockIn);
        homeVM = new ViewModelProvider(this).get(HomeViewModel.class);
//        homeVM.setData();
        txtViewName = view.findViewById(R.id.txtViewName);
        txtViewEmpNum = view.findViewById(R.id.txtViewEmpNum);
//        txtViewShiftTime = view.findViewById(R.id.txtViewShiftTime);
        viewSchedule = view.findViewById(R.id.btnViewSchedule);

        Employee emp = new Employee("12321","Amit Bhattacharya","Hey","3:00pm","5:00pm");
        Employee emp2 = new Employee("12321","Raghuvansh Raj","Hey","3:00pm","5:00pm");
        Employee emp3 = new Employee("12321","Jeff Rodricks","Hey","3:00pm","5:00pm");

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        String userID = fAuth.getCurrentUser().getUid();
        Log.d("logInDebugM", "onCreate: Current User ID:  " + userID);

        DocumentReference df = fStore.collection("Users").document(userID);


        df.get().addOnCompleteListener(
                (@NonNull Task<DocumentSnapshot> task) -> {

                    Log.d("abcd", "onComplete: Reading From the database");
                    DocumentSnapshot document = task.getResult();
                    scheduleList = (ArrayList<String>) document.get("schedule");


                    ArrayAdapter scheduleAdapter = new ArrayAdapter(view.getContext(), android.R.layout.simple_list_item_1,scheduleList);
                    listView.setAdapter(scheduleAdapter);

                    txtViewName.setText(document.get("fullName").toString());
                    txtViewEmpNum.setText(userID);});




//        txtViewEmpNum.setText(homeVM.getEmployeeNumber());
//        txtViewShiftTime.setText(homeVM.getShiftTime());

//        String clockinTime = LocalTime;

        viewSchedule.setOnClickListener((View view) ->{


            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.FrameContainer, new ScheduleFragment()).commit();

        });

        btnClockIn.setOnClickListener((View view)-> {

//            clockinTime= LocalTime;
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.FrameContainer, new ClockIn()).commit();


        });
        return view;
    }

}