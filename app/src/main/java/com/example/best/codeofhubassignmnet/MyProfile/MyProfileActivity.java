package com.example.best.codeofhubassignmnet.MyProfile;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.example.best.codeofhubassignmnet.Commmon.Common;
import com.example.best.codeofhubassignmnet.R;


public class MyProfileActivity extends AppCompatActivity {

    EditText userName, realName, userId;

    Button logOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        userName = findViewById(R.id.userName_edittext);
        realName = findViewById(R.id.realName);
        userId = findViewById(R.id.userId);

        logOut = findViewById(R.id.logout);


        try {

            userName.setText(Common.userName);
            realName.setText(Common.realName);
            userId.setText(Common.userId);

        } catch (Exception e) {
            e.getMessage();
        }


        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(MyProfileActivity.this)
                        .setMessage("Are you sure you want to logout?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finishAffinity();
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();


            }
        });


    }
}
