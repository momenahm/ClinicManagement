package com.example.me.doctorsclinics;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.NavigationView.OnNavigationItemSelectedListener;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout mdrawerlayout;
    private ActionBarDrawerToggle mtoggle;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mdrawerlayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mtoggle = new ActionBarDrawerToggle(this, mdrawerlayout, R.string.open, R.string.close);
        mdrawerlayout.addDrawerListener(mtoggle);
        mtoggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


          navigationView = (NavigationView) findViewById(R.id.navigationv);
        navigationView.setNavigationItemSelectedListener(new OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch((item.getItemId()))
                {
                    case R.id.Add_patient:
                         String docname = getIntent().getStringExtra("KEYY");
                        Intent intent = new Intent(MainActivity.this,AddActivity.class);
                        intent.putExtra("KEYYY",docname);
                        startActivity(intent);
                        item.setChecked(true);
                        break;
                    case R.id.delete_patient:
                        String docnamee = getIntent().getStringExtra("KEYY");
                        Intent deleteintent = new Intent(MainActivity.this,DeleteActivity.class);
                        deleteintent.putExtra("KEYYYY",docnamee);
                        startActivity(deleteintent);
                        item.setChecked(true);
                        break;
                    case R.id.ViewPatients:
                      Intent dspatient = new Intent(MainActivity.this,DisplayPatient.class);
                        String docnameee = getIntent().getStringExtra("KEYY");
                       dspatient.putExtra("VIEW",docnameee);
                        startActivity(dspatient);
                        item.setChecked(true);
                        break;
                    case R.id.Logout:
                        Intent logoutintent = new Intent(MainActivity.this,LoginActivity.class);
                        logoutintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(logoutintent);
                        finish();



                }
                return false;
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mtoggle.onOptionsItemSelected(item)){return  true;}
        {
            return super.onOptionsItemSelected(item);
        }
    }



}
