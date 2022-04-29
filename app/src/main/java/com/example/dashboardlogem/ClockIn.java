package com.example.dashboardlogem;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClockIn extends Fragment {

    EditText editTxtDate;
    TextView txtViewTime;
    Button btnConfirmPunch, btnCancelPunch;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    View view;
    String time, Date, enteredDate, userId, Title, dbKey;
    List<String> ScheduledDates;
    Boolean empty;
    private static final String TAG = "ClockInFragment";

    public ClockIn(String time, String date, List<String> scheduledDates, String UserID, String title, String DbKey) {
        this.time = time;
        Date = date;
        ScheduledDates = scheduledDates;
        userId = UserID;
        Title = title;
        dbKey = DbKey;
    }

    public ClockIn() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_clock_in, container, false);
        editTxtDate = view.findViewById(R.id.editTxtDate);
        txtViewTime = view.findViewById(R.id.txtViewTime);
        btnCancelPunch = view.findViewById(R.id.btnCancelPunch);
        btnConfirmPunch = view.findViewById(R.id.btnConfirmPunch);
//            getTimeField(userId);
        txtViewTime.setText(time);

        btnConfirmPunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enteredDate = editTxtDate.getText().toString();
                if (Date.equalsIgnoreCase(enteredDate)) {
                    Log.d(TAG, "onClick: Date Checked Successfully...");
                    checkMatchingDates(ScheduledDates, enteredDate, time, dbKey);

                } else {
                    Log.d(TAG, "onClick:Entered Date is: " + enteredDate);
                    Toast.makeText(getActivity(), "Incorrect Date:You can only Clock In for today.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        btnCancelPunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.FrameContainer, new HomeFragment()).commit();
                Toast.makeText(getActivity(), "Cancelled and Redirecting to Home Page", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    public void checkMatchingDates(List<String> dates, String eDate, String time, String dbKEY) {
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();


        DocumentReference df = fStore.collection("Users").document(userId);
        Map<String, Object> userInfo = new HashMap<>();
        Map<String, String> punchInfo = new HashMap<>();

        Log.d(TAG, "checkMatchingDates: In the Method");
        for (int i = 0; i < dates.size(); i++) {
            Log.d(TAG, "checkMatchingDates: In the Method");
            if (dates.get(i).equalsIgnoreCase(eDate)) {
                getTimeField(df);
                punchInfo.put("Date", eDate);
                punchInfo.put(dbKEY, time);

                userInfo.put("time", punchInfo);
                df.update(userInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "onSuccess: Data Added to database Successfully");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: Error while adding data" + e.getMessage());
                    }
                });
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.FrameContainer, new HomeFragment()).commit();

                Toast.makeText(getActivity(), "Punch Created.", Toast.LENGTH_SHORT).show();
                return;
            } else {
                Log.d(TAG, "checkMatchingDates: The date entered is: " + eDate);
                Toast.makeText(getActivity(), "You are not Scheduled for Today.", Toast.LENGTH_SHORT).show();
                return;
            }
        }
    }

    public void getTimeField(DocumentReference df) {
        df.get().addOnCompleteListener(
                (@NonNull Task<DocumentSnapshot> task) -> {
                    DocumentSnapshot document = task.getResult();
                    if (document.get("time") != null) {
                        Log.d(TAG, "getTimeField: The data present is: " + document.getData().get("date"));
//                        scheduledDates = (List<String>) document.get("schedule");
                    } else {
                        Log.d(TAG, "getTimeField: The field is null");
                    }
//
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: Error Occurred: " + e.getMessage());
            }
        });
    }


}