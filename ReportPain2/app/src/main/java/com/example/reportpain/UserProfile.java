package com.example.reportpain;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


public class UserProfile extends AppCompatActivity {

    private Toolbar toolbar;

    TextView upUsername;
    TextView upFullname;
    TextView upDOB;
    TextView upWeight;
    TextView upHeight;
    TextView upBMI;
    Button editUP;

    protected void onCreate(Bundle savedInstanceState){
        setTheme(R.style.UserProfile);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);

        toolbar = findViewById(R.id.toolbar);//replaces the actionbar
        setSupportActionBar(toolbar);//sets the toolbar

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        upUsername = (TextView) findViewById(R.id.upUsername);
        upFullname = (TextView) findViewById(R.id.upFullname);
        upDOB = (TextView) findViewById(R.id.upDateOfBirth);
        upWeight = (TextView) findViewById(R.id.upWeight);
        upHeight = (TextView) findViewById(R.id.upHeight);
        upBMI = (TextView) findViewById(R.id.upBMI);
        editUP = (Button) findViewById(R.id.edit);

        setUserProfile();
        //upUsername.setText(user.getUsername());
        //Log.d("Test", user.getUsername());

        editUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent edit = new Intent (UserProfile.this, EditUserProfile.class);
                startActivity(edit);
            }
        });

    }
    public void setUserProfile(){
        User user = new User();
        upFullname.setText(user.getFullname());
        upUsername.setText("@"+user.getUsername());
        upDOB.setText(user.getDateOfBirth());
        upWeight.setText(user.getWeight()+"kg");
        upHeight.setText(user.getHeight()+"m");
        upBMI.setText(user.getBmi());

        Log.d("3", user.getFullname());
    }
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id == android.R.id.home){
            Intent ha = new Intent (UserProfile.this, HumanAnatomy.class);
            startActivity(ha);
        }
        return super.onOptionsItemSelected(item);
    }

}
