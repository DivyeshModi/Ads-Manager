package com.example.adsdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.adsmodule.MyAdsManager;

public class AdsActivity extends AppCompatActivity {

    private Activity ac;
    private LinearLayout native_container;
    private RelativeLayout banner_container;
    private Button btn_start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ads);

        ac = AdsActivity.this;
        btn_start = findViewById(R.id.btn_start);
        native_container = findViewById(R.id.native_container);
        banner_container = findViewById(R.id.banner_container);

        MyAdsManager.Load_Native_Banner(ac, banner_container,
                getResources().getString(R.string.fb_native_banner_id),
                getResources().getString(R.string.admob_banner_id),
                getResources().getString(R.string.app_next_id));

        MyAdsManager.Load_Native_Ad(ac, native_container,
                getResources().getString(R.string.fb_native_id),
                getResources().getString(R.string.admob_native_id),
                getResources().getString(R.string.app_next_id));

        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyAdsManager.CreateInterstitial(ac, new Intent(ac, ViewActivity.class),
                        getResources().getString(R.string.fb_intertitial_id),
                        getResources().getString(R.string.admob_intertitial_id),
                        getResources().getString(R.string.app_next_id));
            }
        });
    }
}
