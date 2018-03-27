package com.passinhotv.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Arrays;

import me.originqiu.library.EditTag;

public class Welcome_1_Activity extends AppCompatActivity {
    TextView tx_login;
    Button btn_contiue;
    EditText edt_address;
    EditTag et_words;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_welcome_1_);
        initView();
    }
    public void initView(){
        tx_login = findViewById(R.id.tx_login);
        btn_contiue = findViewById(R.id.btn_continue);
        et_words = findViewById(R.id.edit_tag_view);
        edt_address = findViewById(R.id.edt_address);
        tx_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Welcome_1_Activity.this, Getting_1_Activity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                Welcome_1_Activity.this.finish();
            }
        });
        btn_contiue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Welcome_1_Activity.this, Welcome_2_Activity.class);
                startActivityForResult(intent,999);
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==999){
            this.finish();
        }
    }
}
