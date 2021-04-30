package com.example.CalTracker.login;

import android.content.Intent;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

import com.example.CalTracker.R;

public class SplashActivity extends LoginActivity {
    private final long SPLASH_DISP_TIME = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
// TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_splash);

        class TimerTaskHande extends TimerTask{
            @Override
            public void run() {
                // TODO Auto-generated method stub
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }
        Timer timer = new Timer();
        timer.schedule(new TimerTaskHande(), SPLASH_DISP_TIME);
    }

}
