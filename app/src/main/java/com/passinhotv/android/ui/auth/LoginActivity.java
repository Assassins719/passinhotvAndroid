package com.passinhotv.android.ui.auth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.passinhotv.android.R;

public class LoginActivity extends AppCompatActivity {

    TextView tx_login;
    EditText ed_pwd;
    Button btn_continue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);
        initView();
    }
    public void initView(){
        tx_login = findViewById(R.id.tx_login);
        ed_pwd = findViewById(R.id.edt_pwd);
        btn_continue = findViewById(R.id.btn_continue);
        tx_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, Getting_1_Activity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                LoginActivity.this.finish();
            }
        });
        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ed_pwd.getText().equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Please enter password.",Toast.LENGTH_SHORT).show();
                }
                else{

                }
            }
        });
    }
}
