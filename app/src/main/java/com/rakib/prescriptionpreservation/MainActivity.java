package com.rakib.prescriptionpreservation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ImageView logo;
    private TextView logoText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        logo = findViewById(R.id.welcomeLogo);
        logoText = findViewById(R.id.welcomeText);
        logo.startAnimation(AnimationUtils.loadAnimation(this, R.anim.lefttoright));
        logoText.startAnimation(AnimationUtils.loadAnimation(this, R.anim.righttoleft));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this, DashboardActivity.class));
                finish();
            }
        },2000);
    }



}
