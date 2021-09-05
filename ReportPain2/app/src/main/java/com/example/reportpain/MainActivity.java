package com.example.reportpain;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends Activity {

    //duration of the splash screen
    private final int splash_length = 1000;

    public void onCreate (Bundle wait) {
        setTheme(R.style.SplashTheme);
        super.onCreate(wait);
        setContentView(R.layout.activity_main);


        //delays the splash screen for the given time limit
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                Intent login = new Intent(MainActivity.this, Login.class);
                startActivity(login);
            }
        }, splash_length);

    }
}