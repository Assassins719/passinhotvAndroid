package com.passinhotv.android.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.passinhotv.android.R;

public class ProfileActivity extends AppCompatActivity {
    Button btn_back;
    ImageView img_logo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_profile);
        initUI();
    }
    public void initUI(){
        btn_back = findViewById(R.id.btn_back);
        img_logo = findViewById(R.id.img_logo);
        btn_back.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                ProfileActivity.this.finish();
            }
        });
        img_logo.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {

            }
        });
    }
}
