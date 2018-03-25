package com.passinhotv.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;


public class Getting_1_Activity extends AppCompatActivity {
    TextView tx_crie;
    TextView tx_local;
    TextView tx_signup;
    EditText edt_pwd,edt_confirm,edt_avatar;
    ImageView img_avatar;
    Button btn_continue, btn_upload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_getting_1_);
        initView();
    }
    public void initView(){
        tx_crie = findViewById(R.id.tx_crie);
        tx_local = findViewById(R.id.tx_local);
        tx_signup = findViewById(R.id.tx_signup);
        edt_pwd = findViewById(R.id.edt_pwd);
        edt_confirm= findViewById(R.id.edt_confirm);
        edt_avatar= findViewById(R.id.edt_avatar);
        img_avatar = findViewById(R.id.img_avatar);
        btn_continue = findViewById(R.id.btn_continue);
        btn_upload = findViewById(R.id.btn_upload);

        String strCrie = "<font color='white'>Crie uma conta gratuita abaixo ou </font>";
        tx_crie.setText(Html.fromHtml(strCrie), TextView.BufferType.SPANNABLE);
        String strLocal = "<font color='red'>Escolha seu próprio AvatarEsta é uma senha </font><font color='#a7ce39'>local.\n" +
                "</font><font color='red'>Esta senha é local para o seu dispositivo e </font><font color='#a7ce39'>NÃO</font><font color='red'> será carregada na Rede Descentralizada Passinho.</font>";
        tx_local.setText(Html.fromHtml(strLocal), TextView.BufferType.SPANNABLE);
        tx_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Getting_1_Activity.this, Welcome_1_Activity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                Getting_1_Activity.this.finish();
            }
        });
        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Getting_1_Activity.this, Getting_2_Activity.class);
                startActivity(intent);
            }
        });
    }
}
