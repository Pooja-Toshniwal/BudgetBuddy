package com.who.budgetbuddy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
public class Splashscreen extends AppCompatActivity {
    private Animation random;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);


        TextView logo=findViewById(R.id.splash);
        random= AnimationUtils.loadAnimation(Splashscreen.this,R.anim.float_button);
        logo.startAnimation(random);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent next;
                next=new Intent(Splashscreen.this,MainActivity.class);
                startActivity(next);
                finish();
            }
        },2000);


    }
}