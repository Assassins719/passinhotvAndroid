package com.passinhotv.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Welcome_2_Activity extends AppCompatActivity {
    EditText edt_pwd, edt_confirm;
    TextView tx_login, tx_local;
    Button btn_continue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_welcome_2_);
        initView();
    }
    public void initView(){
        tx_login = findViewById(R.id.tx_login);
        tx_local = findViewById(R.id.tx_local);
        btn_continue = findViewById(R.id.btn_continue);

        String strLocal = "<font color='red'>Escolha seu próprio AvatarEsta é uma senha </font><font color='#a7ce39'>local.\n" +
                "</font><font color='red'>Esta senha é local para o seu dispositivo e </font><font color='#a7ce39'>NÃO</font><font color='red'> será carregada na Rede Descentralizada Passinho.</font>";
        tx_local.setText(Html.fromHtml(strLocal), TextView.BufferType.SPANNABLE);
        edt_pwd = findViewById(R.id.edt_pwd);
        edt_confirm = findViewById(R.id.edt_confirm);
        tx_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Welcome_2_Activity.this, Getting_1_Activity.class);
                startActivity(intent);
                setResult(999);
                Welcome_2_Activity.this.finish();
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
            }
        });
        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Welcome_2_Activity.this, Welcome_Activity.class);
                startActivity(intent);
            }
        });
    }
}
