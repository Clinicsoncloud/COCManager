package com.coc.cocmanager.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.coc.cocmanager.R;
import com.coc.cocmanager.Utils.Constants;
import com.coc.cocmanager.Utils.Utils;

/**
 * created by ketan 23-03-2020
 */

public class SplashActivity extends AppCompatActivity {

    private Handler splashHandler;
    private SharedPreferences prefInfo;
    private Context context = SplashActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        
        initializeData();
    }

    private void initializeData() {

        prefInfo = getSharedPreferences(Utils.PREFERENCE_PERSONAL,MODE_PRIVATE);

        splashHandler = new Handler();
        splashHandler.postDelayed(() -> {
            if(prefInfo.getBoolean(Constants.Fields.LOGGED,false)) {
                final Intent mainIntent = new Intent(context, MainActivity.class);
                startActivity(mainIntent);
            }else{
                final Intent mainIntent = new Intent(context, Loginctivity.class);
                startActivity(mainIntent);
            }
            finish();
        }, 2000);
    }
}
