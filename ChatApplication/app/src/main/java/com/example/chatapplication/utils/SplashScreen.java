package com.example.chatapplication.utils;

/**
 * Created by Antonio on 28/10/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chatapplication.R;
import com.example.chatapplication.user.LoginActivity;


public class SplashScreen extends AppCompatActivity {
    int i = 0;
    String banderaTerminos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        iniciarAnimacion();
    }

    public void  iniciarAnimacion(){

        SharedPreferences prefe=getSharedPreferences("datos", Context.MODE_PRIVATE);
        banderaTerminos = prefe.getString("terminos","");

        final ProgressBar progress=(ProgressBar)findViewById(R.id.simpleProgressBar);

        progress.setIndeterminate(false);
        progress.setProgress(0);


        final int totalProgressTime = 50;
        final Thread t = new Thread() {
            @Override
            public void run() {
                int jumpTime = 0;

                while(jumpTime <= totalProgressTime) {
                    try {
                        sleep(200);
                        jumpTime += 2;
                        progress.setProgress(jumpTime);

                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                   if(jumpTime == 50){
                        Intent intencio = new Intent(getApplicationContext(), LoginActivity.class);
                        finish();
                        startActivity(intencio);
                    }

                }


            }
        };
        t.start();


    }

}
