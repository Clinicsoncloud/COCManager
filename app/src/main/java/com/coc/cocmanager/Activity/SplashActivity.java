package com.coc.cocmanager.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.coc.cocmanager.R;

/**
 * created by ketan 23-03-2020
 */

public class SplashActivity extends AppCompatActivity {

    private Context context = SplashActivity.this;
    private Handler splashHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        
        initializeData();
    }

    private void initializeData() {
        splashHandler = new Handler();
        splashHandler.postDelayed(() -> {
            final Intent mainIntent = new Intent(context, Loginctivity.class);
            startActivity(mainIntent);
            finish();
        }, 2000);
    }
}
