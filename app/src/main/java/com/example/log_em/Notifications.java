package com.example.log_em;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.log_em.databinding.ActivityNotificationsBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Notifications extends AppCompatActivity {

    ActivityNotificationsBinding binding;
    ListView lstViewMessages;
    FirebaseAuth fAuth;
    List<String> empIds = new ArrayList<>();
    List<String> empNames = new ArrayList<>(Arrays.asList());
    List<String> msgTitle = new ArrayList<>(Arrays.asList());
    List<String> msgDesc = new ArrayList<>();
    FirebaseFirestore fStore;
    private static final String TAG = "Notifications";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityNotificationsBinding.inflate(getLayoutInflater());
        View layout = binding.getRoot();
        setContentView(layout);
        lstViewMessages = binding.lstViewNotifications;

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        fStore.collection("Messages").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        empIds.add(document.getId());
                        empNames.add(document.getData().get("fullName").toString());
                        msgTitle.add(document.getData().get("Title").toString());
                        msgDesc.add(document.getData().get("Message").toString());
                        Log.d(TAG, document.getId() + " => " + document.getData().get("fullName"));
                        Log.d(TAG, "onComplete: The size of list is "+ msgTitle.size());
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
                addData(empNames,msgTitle);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "onFailure: Error Occurred: " + e.getMessage());
            }
        });



//        getData();

//        notificationsAdapter adapter = new notificationsAdapter(empNames, msgTitle);
//        lstViewMessages.setAdapter(adapter);
//        Log.d(TAG, "onCreate: Items in list view"+ lstViewMessages.getCount());




//        setContentView(R.layout.activity_notifications);
    }

    public void addData(List<String> name, List<String> title) {
        notificationsAdapter adapter = new notificationsAdapter(name, title);
        binding.lstViewNotifications.setAdapter(adapter);
        Log.d(TAG, "onCreate: Items in list view"+ lstViewMessages.getCount());

    }

}