package com.example.posify.logs;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.posify.MainActivity;
import com.example.posify.R;

public class Splashscreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.splashscreen);
        ImageView logo = findViewById(R.id.app_logo);
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);

        //animation
        logo.startAnimation(fadeIn);


        //Default Intent
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent intent = new Intent(Splashscreen.this, MainActivity.class);
            startActivity(intent);
            finish();
        }, 1300);
    }

}