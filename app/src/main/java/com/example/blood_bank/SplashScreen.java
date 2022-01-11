package com.example.blood_bank;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {
    private ImageView logo;
    private TextView slogan, tytle;
    Animation anim_top, anim_bottom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splashscreen);
        logo = findViewById(R.id.logo);
        slogan = findViewById(R.id.slogan);
        tytle = findViewById(R.id.tytle);
        anim_top = AnimationUtils.loadAnimation(this, R.anim.top_anim);
        anim_bottom = AnimationUtils.loadAnimation(this, R.anim.bottom_anim);
        logo.setAnimation(anim_top);
        tytle.setAnimation(anim_bottom);
        slogan.setAnimation(anim_bottom);
        int SPLASH_SCREEN = 4300;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreen.this, register_activity.class));
                finish();
            }
        }, SPLASH_SCREEN);

    }
}
