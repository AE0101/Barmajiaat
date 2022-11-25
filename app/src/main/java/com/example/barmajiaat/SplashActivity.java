package com.example.barmajiaat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

// this Handler code is responsible for delaying the Intent code, which transition the screen to the Login activity, but without the Handler delay,
// the screen would transition so fast, and the Splash screen wouldn't even show up to the user.
        new Handler().postDelayed(() -> {

// the startActivity is the command which gives the order to transition to the screen with the Intent Class.
// (Intent) is the Class name which transition between one screen to another the (SplashActivity.this) means that it's this Activity you are currently in, after that we separated this with a "," and then we commanded it to go to the other screen / activity we created, which is the login screen.
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
// without the finish(); code, the user could press back to return to the splash screen, which isn't good design of course, so the finish command closes it and remove it from the RAM.
            finish();
        },2000);



    }
}