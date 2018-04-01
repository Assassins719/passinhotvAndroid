package com.passinhotv.android.ui.auth;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.passinhotv.android.GlobalVar;
import com.passinhotv.android.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import me.originqiu.library.EditTag;

public class Getting_4_Activity extends AppCompatActivity {

    Button btn_continue,btn_clear;
    EditExtend et_seeds;
    EditExtend et_selected;
    TextView tx_back;
    public List<String> mSelectedSeeds = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_getting_4_);
        initView();
    }
    public void initView(){
        btn_continue = findViewById(R.id.btn_continue);
        btn_clear = findViewById(R.id.btn_clear);
        tx_back = findViewById(R.id.tx_back);
        et_selected = findViewById(R.id.edit_tag_view_selected);
        et_selected.setEditable(false);
        et_seeds = findViewById(R.id.edit_tag_view);
        et_seeds.setEditable(false);
        List<String> tempSeeds = new ArrayList<String>(GlobalVar.mSeeds);
        Random rand = new Random();
        for(int i =0; i < GlobalVar.mSeeds.size(); i ++){
            int nIndex = rand.nextInt(100) % tempSeeds.size();
            String strTemp = tempSeeds.get(nIndex);
            et_seeds.addTag(strTemp);
            tempSeeds.remove(nIndex);
        }

        et_seeds.setSelectedCallback(new EditExtend.SelectedCallback(){
            @Override
            public void onSelected(String strSelected) {
                mSelectedSeeds.add(strSelected);
                et_selected.addTag(strSelected);
                btn_clear.setVisibility(View.VISIBLE);
                CheckSeeds();
            }
        });
        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btn_clear.setVisibility(View.GONE);
                mSelectedSeeds.clear();
                et_seeds.reset();
                et_selected.clear();
            }
        });
        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Getting_4_Activity.this, Welcome_Activity.class);
                startActivity(intent);
            }
        });
        tx_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Getting_4_Activity.this.finish();
            }
        });
    }
    public void CheckSeeds(){

    }
}
