package com.slpproject.slp_project;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    NavigationView nav;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    TextView textView;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView=findViewById(R.id.textView12);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbQuery.loadData(new MyCompleteListener() {
                    @Override
                    public void onSuccess() {
                        Intent in=new Intent(MainActivity.this,create_test.class);
                        startActivity(in);

                    }

                    @Override
                    public void onFailure() {
//                        Toast.makeText(MainActivity.this,text:"something went wrong ! please try again",Toast.LENGTH_SHORT).show();

                    }
                });
//                Intent in=new Intent(MainActivity.this,create_test.class);
//                startActivity(in);
            }

        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        nav = (NavigationView)findViewById(R.id.nav_view);
        drawerLayout =(DrawerLayout) findViewById(R.id.drawer);

        toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        nav.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId())
            {
                case R.id.nav_home :
                    Toast.makeText(MainActivity.this, "Home Panel is Open", Toast.LENGTH_LONG).show();
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;

                case R.id.nav_profile :
                Toast.makeText(MainActivity.this, "Profile Panel is Open", Toast.LENGTH_LONG).show();
                drawerLayout.closeDrawer(GravityCompat.START);
                    Intent i = new Intent(MainActivity.this, MyProfileActivity.class);
                    startActivity(i);
                break;

                case R.id.nav_settings :
                Toast.makeText(MainActivity.this, "Settings Panel is Open", Toast.LENGTH_LONG).show();
                drawerLayout.closeDrawer(GravityCompat.START);
                break;

                case R.id.nav_logout :
                    Toast.makeText(MainActivity.this, "Logout Panel is Open", Toast.LENGTH_LONG).show();
                    drawerLayout.closeDrawer(GravityCompat.START);
                    Intent intent = new Intent(MainActivity.this, Login.class);
                    startActivity(intent);
                    break;

                case R.id.nav_share :
                    Toast.makeText(MainActivity.this, "Share Panel is Open", Toast.LENGTH_LONG).show();
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;

                case R.id.nav_Contact_us :
                    Toast.makeText(MainActivity.this, "Contact Panel is Open", Toast.LENGTH_LONG).show();
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;

                case R.id.nav_rate_us :
                    Toast.makeText(MainActivity.this, "Rate us Panel is Open", Toast.LENGTH_LONG).show();
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;
            }

            return true;
        });




    }




}