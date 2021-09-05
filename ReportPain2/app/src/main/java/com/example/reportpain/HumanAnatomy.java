package com.example.reportpain;

import android.app.Activity;
import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.navigation.NavigationView;

public class HumanAnatomy extends AppCompatActivity {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;


    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.HumanAnatomy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.human_anatomy);

        toolbar = findViewById(R.id.toolbar);//replaces the actionbar
        setSupportActionBar(toolbar);//sets the toolbar

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);//adds a home button to actionbar
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);//changes the home icon

        navigationView();


    }


    public void navigationView(){
        drawerLayout = findViewById(R.id.drawer_layout);//opens drawer
        navigationView = findViewById(R.id.navigationView);//navigations within drawer

        navigationView.setNavigationItemSelectedListener
                (new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                        switch (menuItem.getItemId()){
                            /*
                             * Add all navigation menus here and use "drawer_menu"
                             * to add your menu
                             */
                            case R.id.user: {
                                menuItem.setChecked(true);
                                drawerLayout.closeDrawers();
                                Intent upIntent = new Intent(HumanAnatomy.this, UserProfile.class);
                                startActivity(upIntent);
                                return true;
                            }
                            case R.id.human_A: {
                                menuItem.setChecked(true);
                                drawerLayout.closeDrawers();
                                return true;
                            }
                            case R.id.daily_D: {
                                menuItem.setChecked(true);
                                drawerLayout.closeDrawers();
                                Intent pddIntent = new Intent(HumanAnatomy.this, PersonalDD.class);
                                startActivity(pddIntent);
                                return true;
                            }
                            case R.id.analyt: {
                                menuItem.setChecked(true);
                                drawerLayout.closeDrawers();
                                return true;
                            }
                            case R.id.logout: {
                                menuItem.setChecked(true);
                                drawerLayout.closeDrawers();
                                Intent logout = new Intent(HumanAnatomy.this, Login.class);
                                startActivity(logout);
                                return true;
                            }

                        }
                        return false;
                    }
                });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        /*
         * The home button that opens the drawer
         */
        switch(item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
