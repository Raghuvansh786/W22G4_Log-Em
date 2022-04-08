package com.example.dashboardlogem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bnv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().replace(R.id.FrameContainer, new HomeFragment()).commit();


        bnv=(BottomNavigationView) findViewById(R.id.bottomNavBar);
        bnv.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment temp = null;
                switch(item.getItemId()){
                    case R.id.home : temp = new HomeFragment();
                        break;
                    case R.id.schedule : temp = new ScheduleFragment();
                        break;
                    case R.id.earnings : temp = new EarningsFragment();
                        break;
                    case R.id.notifications : temp = new NotificationsFragment();
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.FrameContainer, temp).commit();
                return true;
            }
        });
    }
}