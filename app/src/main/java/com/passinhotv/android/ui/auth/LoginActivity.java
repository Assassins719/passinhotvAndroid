package com.passinhotv.android.ui.auth;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.passinhotv.android.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);
        initView();
    }
    public void initView(){

    }
}
