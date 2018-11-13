package com.example.best.codeofhubassignmnet;

import android.content.Intent;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.best.codeofhubassignmnet.Commmon.Common;
import com.example.best.codeofhubassignmnet.adapter.PublicPhotesAdapter;
import com.example.best.codeofhubassignmnet.model.FlickerPublicPhotesResponse;
import com.example.best.codeofhubassignmnet.model.Item;
import com.example.best.codeofhubassignmnet.remote.IFlickerApi;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // wait for 1 second and then move for next activity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                // for showing new activity
                startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                // to remove from stack
                finish();


            }
        }, 1000);
    }


}
