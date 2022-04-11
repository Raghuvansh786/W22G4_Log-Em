package com.example.dashboardlogem;

import android.content.Intent;
import android.icu.util.LocaleData;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
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
import android.widget.Toast;

import com.example.dashboardlogem.activities.logEm_login;
import com.example.dashboardlogem.interfaces.EmployeeDao;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HomeFragment extends Fragment {


    TextView txtViewName;
    TextView txtViewEmpNum;
    TextView txtViewShiftTime;
    Button btnClockIn;
    Button btnClockOut;
    Button viewSchedule;
    Button btnLogOut;
    Button btnUpdateAvailability;
    ListView listView;
    Button viewTimeOff;
    ArrayList<String> scheduleList = new ArrayList<>();
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    View view;
    HomeViewModel homeVM;
    String clockInTime;
    String clockOutTime;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        HomeViewModel homeViewModel =
//                new ViewModelProvider(this).get(HomeViewModel.class);
        view = inflater.inflate(R.layout.fragment_home, container, false);

        listView = view.findViewById(R.id.listViewSchedule);
        viewTimeOff = view.findViewById(R.id.btnReqTo);
        btnClockIn = view.findViewById(R.id.btnClockIn);
        btnClockOut = view.findViewById(R.id.btnClockOut);
        btnUpdateAvailability = view.findViewById(R.id.btnUpdateAvailability);
        btnLogOut = view.findViewById(R.id.btnLogOutEmp);
        btnClockOut.setEnabled(false);
        homeVM = new ViewModelProvider(this).get(HomeViewModel.class);
//        homeVM.setData();
        txtViewName = view.findViewById(R.id.txtViewName);
        txtViewEmpNum = view.findViewById(R.id.txtViewEmpNum);
//        txtViewShiftTime = view.findViewById(R.id.txtViewShiftTime);
        viewSchedule = view.findViewById(R.id.btnViewSchedule);


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
                    if(scheduleList!=null){

                    ArrayAdapter scheduleAdapter = new ArrayAdapter(view.getContext(), android.R.layout.simple_list_item_1,scheduleList);
                    listView.setAdapter(scheduleAdapter);
                    }


                    txtViewName.setText(document.get("fullName").toString());
                    txtViewEmpNum.setText(document.get("email").toString());});



        viewTimeOff.setOnClickListener((View view) ->{

            startActivity(new Intent(view.getContext(),timeOff.class));
        });

        btnLogOut.setOnClickListener((View view) ->{

            startActivity(new Intent(view.getContext(), logEm_login.class));
        });

//        txtViewEmpNum.setText(homeVM.getEmployeeNumber());
//        txtViewShiftTime.setText(homeVM.getShiftTime());

//        String clockinTime = LocalTime;

        btnUpdateAvailability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),updateAvailability.class));
            }
        });
        viewSchedule.setOnClickListener((View view) ->{


            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.FrameContainer, new ScheduleFragment()).commit();

        });

        btnClockIn.setOnClickListener((View view)-> {

            try{

                DateTimeFormatter d = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            String localD = LocalDate.now().format(d);
            Log.d("dateRightNow","Local time : "+localD);
            if(checkClockIn(scheduleList, localD)){
            LocalTime localTime = LocalTime.now();
            DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("hh:mm:ss");
            clockInTime= String.valueOf(localTime.truncatedTo(ChronoUnit.MINUTES));
//            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.FrameContainer, new ClockIn()).commit();
            Toast.makeText(view.getContext(), "You clocked in at : "+clockInTime, Toast.LENGTH_SHORT).show();
            btnClockIn.setEnabled(false);
            btnClockOut.setEnabled(true);
            }else {
                Log.d("dateRightNow","Local time : "+localD);
                Toast.makeText(view.getContext(), "You dont have any assigned shifts on : "+localD, Toast.LENGTH_SHORT).show();
            }
            }catch(Exception e){
                Log.d("ghef",e.getMessage());
            }


        });
        btnClockOut.setOnClickListener((View view)-> {


                DocumentReference dd = fStore.collection("Users").document(userID);
                Map<String, Object> userInfo = new HashMap<>();
                Map<String, String> punchInfo = new HashMap<>();
                LocalTime localTime = LocalTime.now();
                DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("hh:mm:ss");
                clockOutTime= String.valueOf(localTime.truncatedTo(ChronoUnit.MINUTES));

//                Log.d("conn"                        )

                punchInfo.put("clockInTime", clockInTime);
                punchInfo.put("clockOutTime",clockOutTime);

                userInfo.put("time", punchInfo);

                dd.update(userInfo);

//            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.FrameContainer, new ClockIn()).commit();
                Toast.makeText(view.getContext(), "You clocked out at : "+clockOutTime, Toast.LENGTH_SHORT).show();
//                Log.d("dateRightNow","Local time : "+localD);

                btnClockOut.setEnabled(false);
                btnClockIn.setEnabled(true);





        });
        return view;
    }

    boolean checkClockIn(List<String> schedule, String localDate){

        if(schedule != null){
        if(schedule.contains(localDate)){
            return true;
        }

        }else {
            Toast.makeText(view.getContext(), "You are not assigned for any shifts today", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

}