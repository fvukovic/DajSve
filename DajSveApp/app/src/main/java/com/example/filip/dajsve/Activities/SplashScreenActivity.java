package com.example.filip.dajsve.Activities;

import android.content.Intent;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.filip.dajsve.R;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        ImageView slika = (ImageView) findViewById(R.id.splash_screen_logo);
        TextView tekst = (TextView) findViewById(R.id.splash_screen_tekst);


        Thread splashScreenThread = new Thread(){
            @Override
            public void run() {
                try{
                    sleep(2500);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        };
        splashScreenThread.start();


        TranslateAnimation anim = new TranslateAnimation(0,0,0,1200);
        TranslateAnimation anim2 = new TranslateAnimation(0,0,1200,0);
        anim.setDuration(800);
        anim2.setDuration(1000);

        anim.setInterpolator(new DecelerateInterpolator());
        anim.setFillAfter(true);
        anim.setFillEnabled(true);

        slika.startAnimation(anim);
        tekst.startAnimation(anim2);


    }
}
