package com.passinhotv.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Welcome_Activity extends AppCompatActivity {
    TextView tx_first, tx_second, tx_third, tx_done;
    Button btn_continue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_welcome);
        initView();
    }
    public void initView(){
        tx_done = findViewById(R.id.tx_done);
        tx_first = findViewById(R.id.tx_welcom_first);
        tx_second = findViewById(R.id.tx_welcom_sec);
        tx_third = findViewById(R.id.tx_welcom_third);
        btn_continue = findViewById(R.id.btn_continue);
        String strDone = "<font color='#007aff'>Passinho.Tv</font> é diferente - Sua conta é armazenada com segurança em seu dispositivo.";
        String strFirst = "Eu entendo que minhas informações são seguras em segurança neste dispositivo, <font color='red'>e não por uma empresa</font>.";
        String strSec = "Eu entendo que, se esse aplicativo for movido para outro dispositivo ou excluído, minha conta <font color='#007aff'>Passinho.Tv</font> só pode ser recuperada com a <font color='#a7ce39'>Frase de backup.</font>";
        String strThird = "Gostaria de ajudar a melhorar a plataforma <font color='#007aff'>Passinho.Tv</font> enviando estatísticas de uso anônimo para os desenvolvedores.";
        tx_done.setText(Html.fromHtml(strDone), TextView.BufferType.SPANNABLE);
        tx_first.setText(Html.fromHtml(strFirst), TextView.BufferType.SPANNABLE);
        tx_second.setText(Html.fromHtml(strSec), TextView.BufferType.SPANNABLE);
        tx_third.setText(Html.fromHtml(strThird), TextView.BufferType.SPANNABLE);
        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Welcome_Activity.this, MainFlowActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
                Welcome_Activity.this.finish();
            }
        });
    }
}
