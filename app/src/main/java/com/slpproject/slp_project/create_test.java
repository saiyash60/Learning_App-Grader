package com.slpproject.slp_project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.slpproject.slp_project.ui.account.accountFragment;
import com.slpproject.slp_project.ui.home.HomeFragment;
import com.slpproject.slp_project.ui.leaderboard.leaderboardFragment;

public class create_test extends AppCompatActivity {

    NavigationView nav;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    private Toolbar toolbar;


    private BottomNavigationView bottomNavigationView;

    private NavController navController;
    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_test);

        drawerLayout = findViewById(R.id.my_drawer);
        nav = findViewById(R.id.nav_view);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Subjects");
        navController = Navigation.findNavController(this, R.id.main_fragment);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);
        FragmentManager manager = getSupportFragmentManager();
//
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.home) {
                    toolbar.setTitle("Subjects");
                    FragmentTransaction transaction = manager.beginTransaction();
                    fragment = new HomeFragment();
                    transaction.replace(R.id.main_fragment, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                    return true;
                }else if (item.getItemId() == R.id.account){
                    toolbar.setTitle("My Account");
                    FragmentTransaction transaction = manager.beginTransaction();
                    fragment = new accountFragment();
                    transaction.replace(R.id.main_fragment, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                    return true;
                }else if (item.getItemId() == R.id.leaderboard){
                    toolbar.setTitle("Leaderboard");
                    FragmentTransaction transaction = manager.beginTransaction();
                    fragment = new leaderboardFragment();
                    transaction.replace(R.id.main_fragment, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                    return true;
                }
                return false;
            }
        });


        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        nav.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_home:
                    Toast.makeText(create_test.this, "Home Panel is Open", Toast.LENGTH_LONG).show();
                    drawerLayout.closeDrawer(GravityCompat.START);
                    Intent intent = new Intent(create_test.this, MainActivity.class);
                    startActivity(intent);
                    break;

                case R.id.nav_profile:
                    Toast.makeText(create_test.this, "Profile Panel is Open", Toast.LENGTH_LONG).show();
                    drawerLayout.closeDrawer(GravityCompat.START);
                    Intent i = new Intent(create_test.this, MyProfileActivity.class);
                    startActivity(i);
                    break;

                case R.id.nav_settings:
                    Toast.makeText(create_test.this, "Settings Panel is Open", Toast.LENGTH_LONG).show();
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;

                case R.id.nav_logout:
                    Toast.makeText(create_test.this, "Logout Panel is Open", Toast.LENGTH_LONG).show();
                    drawerLayout.closeDrawer(GravityCompat.START);
                    Intent in = new Intent(create_test.this, Login.class);
                    startActivity(in);
                    break;

                case R.id.nav_share:
                    Toast.makeText(create_test.this, "Share Panel is Open", Toast.LENGTH_LONG).show();
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;

                case R.id.nav_Contact_us:
                    Toast.makeText(create_test.this, "Contact Panel is Open", Toast.LENGTH_LONG).show();
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;

                case R.id.nav_rate_us:
                    Toast.makeText(create_test.this, "Rate us Panel is Open", Toast.LENGTH_LONG).show();
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;
            }

            return true;
        });
    }


}