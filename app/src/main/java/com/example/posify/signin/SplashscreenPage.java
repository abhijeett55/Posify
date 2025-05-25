package com.example.posify.signin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;



import com.example.posify.R;


@SuppressLint("CustomSplashScreen")
public class SplashscreenPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.splashscreen_page);


        ImageView logo = findViewById(R.id.app_logo);
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);

        logo.startAnimation(fadeIn);


        //Default Intent
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent intent = new Intent(SplashscreenPage.this, LoginPage.class);
            startActivity(intent);
            finish();
        }, 1300);
    }


}
