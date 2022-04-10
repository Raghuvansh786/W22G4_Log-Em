package com.example.dashboardlogem.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.dashboardlogem.EarningsFragment;
import com.example.dashboardlogem.HomeFragment;
import com.example.dashboardlogem.NotificationsFragment;
import com.example.dashboardlogem.R;
import com.example.dashboardlogem.ScheduleFragment;
import com.example.dashboardlogem.admin_fragments.AdminAnnouncementFragment;
import com.example.dashboardlogem.admin_fragments.AdminFullMenuFragment;
import com.example.dashboardlogem.admin_fragments.AdminHomeFragment;
import com.example.dashboardlogem.admin_fragments.AdminTimeOffFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class AdminDashboard extends AppCompatActivity {


    BottomNavigationView bnv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        getSupportFragmentManager().beginTransaction().replace(R.id.adminFrameContainer, new AdminHomeFragment()).commit();


        bnv=(BottomNavigationView) findViewById(R.id.adminBottomNavBar);
        bnv.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment temp = null;
                switch(item.getItemId()){
                    case R.id.home : temp = new AdminHomeFragment();
                        break;
                    case R.id.timeOffRequests: temp = new AdminTimeOffFragment();
                        break;
                    case R.id.announcements: temp = new AdminAnnouncementFragment();
                        break;
                    case R.id.fullMenu: temp = new AdminFullMenuFragment();
                        break;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.adminFrameContainer, temp).commit();
                return true;
            }
        });
    }
}