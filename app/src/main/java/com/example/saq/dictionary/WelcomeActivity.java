package com.example.saq.dictionary;

import android.content.Intent;
import android.drm.DrmStore;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;


public class WelcomeActivity extends AppCompatActivity {

    private TextView tv;
    private static int splash =3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        tv= (TextView) this.findViewById(R.id.welcome_screen_tv);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent home=new Intent("com.example.saq.dictionary.MainActivity");
                startActivity(home);
                finish();
            }
        },splash);

    }
}
