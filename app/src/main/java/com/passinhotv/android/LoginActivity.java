package com.passinhotv.android;

import android.passinhotv.com.passinhotv.R;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import ss.com.bannerslider.banners.Banner;
import ss.com.bannerslider.banners.DrawableBanner;
import ss.com.bannerslider.banners.RemoteBanner;
import ss.com.bannerslider.views.BannerSlider;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);
        initView();
    }
    public void initView(){
        BannerSlider bannerSlider = (BannerSlider) findViewById(R.id.desc_slider);
        List<Banner> banners=new ArrayList<>();
        //add banner using image url
//        banners.add(new RemoteBanner("Put banner image url here ..."));
        //add banner using resource drawable
        banners.add(new DrawableBanner(R.drawable.slider_login_1));
        banners.add(new DrawableBanner(R.drawable.slider_login_2));
        banners.add(new DrawableBanner(R.drawable.slider_login_3));
        banners.add(new DrawableBanner(R.drawable.slider_login_4));
        bannerSlider.setBanners(banners);
    }
}
