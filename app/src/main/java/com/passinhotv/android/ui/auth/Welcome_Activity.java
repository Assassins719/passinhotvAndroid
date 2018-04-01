package com.passinhotv.android.ui.auth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.passinhotv.android.GlobalVar;
import com.passinhotv.android.MainFlowActivity;
import com.passinhotv.android.R;

public class Welcome_Activity extends AppCompatActivity implements View.OnClickListener {
    TextView tx_first, tx_second, tx_third, tx_done;
    CheckBox chk_first, chk_second, chk_third;
    Button btn_continue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_welcome);
        initView();
    }

    public void initView() {
        tx_done = findViewById(R.id.tx_done);
        tx_first = findViewById(R.id.tx_welcom_first);
        tx_second = findViewById(R.id.tx_welcom_sec);
        tx_third = findViewById(R.id.tx_welcom_third);
        tx_first.setOnClickListener(this);
        tx_second.setOnClickListener(this);
        tx_third.setOnClickListener(this);

        chk_first = findViewById(R.id.chk_first);
        chk_second = findViewById(R.id.chk_second);
        chk_third = findViewById(R.id.chk_third);

        btn_continue = findViewById(R.id.btn_continue);

        String strDone = "<font color='#007aff'>Passinho.Tv</font> é diferente - Sua conta é armazenada com segurança em seu dispositivo.";
        String strFirst = "Eu entendo que minhas informações são seguras em segurança neste dispositivo, <font color='red'>e não por uma empresa</font>.";
        String strSec = "Eu entendo que, se esse aplicativo for movido para outro dispositivo ou excluído, minha conta <font color='#007aff'>Passinho.Tv</font> só pode ser recuperada com a <font color='#a7ce39'>Frase de backup.</font>";
        String strThird = "Gostaria de ajudar a melhorar a plataforma <font color='#007aff'>Passinho.Tv</font> enviando estatísticas de uso anônimo para os desenvolvedores.";
        tx_done.setText(Html.fromHtml(strDone), TextView.BufferType.SPANNABLE);
        tx_first.setText(Html.fromHtml(strFirst), TextView.BufferType.SPANNABLE);
        tx_second.setText(Html.fromHtml(strSec), TextView.BufferType.SPANNABLE);
        tx_third.setText(Html.fromHtml(strThird), TextView.BufferType.SPANNABLE);
        btn_continue.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                if(chk_first.isChecked() && chk_second.isChecked()) {
                    Intent intent = new Intent(Welcome_Activity.this, MainFlowActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    Welcome_Activity.this.finish();
                    saveDataToLoacal();
                }else{
                    Toast.makeText(getApplicationContext(),"Please accept terms.",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void saveDataToLoacal(){
        SharedPreferences myPreferences= PreferenceManager.getDefaultSharedPreferences(Welcome_Activity.this);
        SharedPreferences.Editor myEditor = myPreferences.edit();
        myEditor.putString(GlobalVar.KEY_INTENT_PASSWORD, "");
        myEditor.putString(GlobalVar.KEY_INTENT_PRIVATE, "");
        myEditor.commit();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tx_welcom_first:
                chk_first.setChecked(!chk_first.isChecked());
                break;
            case R.id.tx_welcom_sec:
                chk_second.setChecked(!chk_second.isChecked());
                break;
            case R.id.tx_welcom_third:
                chk_third.setChecked(!chk_third.isChecked());
                break;

        }
    }
}
