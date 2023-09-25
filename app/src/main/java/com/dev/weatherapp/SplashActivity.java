package com.dev.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dev.weatherapp.auth.LoginActivity;

public class SplashActivity extends AppCompatActivity {
    ImageView ivLogo;
    TextView tvAppName;
    Animation fromTop, fromBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.main));

        ivLogo = findViewById(R.id.ivLogo);
        tvAppName = findViewById(R.id.tvAppName);

        fromTop = AnimationUtils.loadAnimation(this, R.anim.fromtop);
        fromBottom = AnimationUtils.loadAnimation(this, R.anim.frombottom);
        ivLogo.setAnimation(fromTop);
        tvAppName.setAnimation(fromBottom);

        final Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(3000);
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                } catch (InterruptedException e) {
                    Toast.makeText(SplashActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

        };
        thread.start();

    }
}