package com.example.chahat.anotode;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 1000;

    public static final String MyPREFERENCES = "MyPrefs" ;

    SharedPreferences sharedPreferences;
    String Name,id,FEmail=null;

    FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        firebaseAuth = FirebaseAuth.getInstance();


        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity

                if (firebaseAuth.getCurrentUser()!=null)
                {
                    String name = firebaseAuth.getCurrentUser().getDisplayName();
                    String email = firebaseAuth.getCurrentUser().getEmail();

                    finish();
                    Intent i = new Intent(getApplicationContext(), InboxActivity.class);
                    startActivity(i);

                }
                else{

                    finish();
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                }

            }
        }, SPLASH_TIME_OUT);
    }


}
