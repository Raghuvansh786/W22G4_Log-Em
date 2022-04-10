package com.example.dashboardlogem;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotificationsFragment#} factory method to
 * create an instance of this fragment.
 */
public class NotificationsFragment extends Fragment {

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    View view;
    String message;
    String nameOfSender;
    ListView listViewMessages;
    private static final String TAG = "NotificationsFragment";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_notifications, container, false);
        ArrayList<String> messages = new ArrayList<>();
        listViewMessages = view.findViewById(R.id.listViewMessages);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        String userID = fAuth.getCurrentUser().getUid();
        Log.d("logInDebugM", "onCreate: Current User ID:  " + userID);

        DocumentReference df = fStore.collection("Users").document(userID);

        fStore.collection("Messages")
                .whereEqualTo("fullName", "Admin")
                .get()
                .addOnCompleteListener(
                        (@NonNull Task<QuerySnapshot> task) -> {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {

//                                    Log.d(TAG, "getData: The empid " + document.getId());
                                    message = document.getData().get("Message").toString();
                                    nameOfSender = document.getData().get("fullName").toString();
                                        Log.d("mSS",""+message);
                                    messages.add(message);
                                    if(messages.size() == 0){
                                        Toast.makeText(view.getContext(),"You dont have any messages",Toast.LENGTH_SHORT).show();
                                        String mess="You dont have any messages yet";
                                        messages.add(mess);
                                    }
                                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(view.getContext(),android.R.layout.simple_list_item_1, messages);
                                    listViewMessages.setAdapter(arrayAdapter);
                                    Log.d(TAG, document.getId() + " => " + document.getData().get("fullName"));
//                                    names(empNames);
                                }
                            } else {
                                Log.d(TAG, "Error getting documents: ", task.getException());
                            }
                        });
        return view;
    }
}