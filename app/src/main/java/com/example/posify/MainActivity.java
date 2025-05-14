package com.example.posify;



import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

//import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.posify.items.ItemsStorage;

public class MainActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ImageView logo = findViewById(R.id.app_logo);
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);

        //animation
        logo.startAnimation(fadeIn);


        //Default Intent
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent intent = new Intent(MainActivity.this, ItemsStorage.class);
            startActivity(intent);
            finish();
        }, 1300);
    }


}