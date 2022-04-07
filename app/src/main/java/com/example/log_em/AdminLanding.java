package com.example.log_em;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.log_em.databinding.ActivityAdminLandingBinding;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AdminLanding extends AppCompatActivity {

    Button btnLogOut;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    private static final String TAG = "AdminLanding";
    List<String> empNames = new ArrayList<>();
    List<String> empEmail = new ArrayList<>();

    ActivityAdminLandingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAdminLandingBinding.inflate(getLayoutInflater());
        View layout = binding.btnSignOut;
         try {
             setContentView(layout);
         }catch (Exception e) {
             e.printStackTrace();
             Log.d(TAG, "onCreate: " +e.getMessage());
         }
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        btnLogOut = binding.btnSignOut;
        getData();
//        replaceFragement(new fragmentEmployees(empNames,empEmail));


        btnLogOut.setOnClickListener(
                (View view) ->{
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(AdminLanding.this, LogInActivity.class));
                finish();
//                    Log.d(TAG, "onCreate: Clicked on logout button");
//                    Log.d(TAG, "onCreate: Size of empName List" + empNames.size());
//                    replaceFragement(new fragmentEmployees(empNames,empEmail));
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragmentContainerView, fragmentEmployees.class,null)
                            .setReorderingAllowed(true)
                            .addToBackStack("name") // name can be null
                            .commit();

        });
    }

    private void replaceFragement(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, fragment)
                .setReorderingAllowed(true)
                .addToBackStack("name") // name can be null
                .commit();

    }

    public void names(List<String> x){
        Log.d(TAG, "onCreate: The size of empNames List is: " + x.size());
    }

    public void getData(){
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        fStore.collection("Users")
                .whereEqualTo("isUser", "1")
                .get()
                .addOnCompleteListener(
                        (@NonNull Task<QuerySnapshot> task)-> {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    empNames.add(document.getData().get("fullName").toString());
                                    empEmail.add(document.getData().get("email").toString());
                                    Log.d(TAG, document.getId() + " => " + document.getData().get("fullName"));
                                    names(empNames);
                                }
                            } else {
                                Log.d(TAG, "Error getting documents: ", task.getException());
                            }

                        });
    }
}