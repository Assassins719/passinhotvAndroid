package com.passinhotv.android;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.passinhotv.android.ui.auth.EnvironmentManager;
import com.passinhotv.android.util.AppUtil;
import com.passinhotv.android.util.PrefsUtil;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(SplashActivity.this, StartActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                SplashActivity.this.finish();
            }
        },3000);
        AppUtil appUtil = new AppUtil(this);
        EnvironmentManager.init(new PrefsUtil(this), appUtil);
    }
}
