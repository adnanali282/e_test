package com.workouts.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.workouts.R;
import com.workouts.preference.PrefManager;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashscreenActivity extends AppCompatActivity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 2500;
    private PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        prefManager = new PrefManager(this);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = null;
                if (!prefManager.isFirstTimeLaunch()) {
                    prefManager.setFirstTimeLaunch(false);
                    i = new Intent(SplashscreenActivity.this, MainActivity.class);
                } else {
                    i = new Intent(SplashscreenActivity.this, IntroActivity.class);
                    i.setAction("not_info");
                }
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}
