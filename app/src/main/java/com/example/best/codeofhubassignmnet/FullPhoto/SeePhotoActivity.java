package com.example.best.codeofhubassignmnet.FullPhoto;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.best.codeofhubassignmnet.R;
import com.squareup.picasso.Picasso;


public class SeePhotoActivity extends AppCompatActivity {

    ImageView imageView_FullImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_photo);

        getSupportActionBar().setTitle("Full Image");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        imageView_FullImage = findViewById(R.id.imageViewFullPhoto);
        // getting intent result
        String imageUrl = getIntent().getExtras().getString("fullImageLink");

        // used for gettng image from web server
        Picasso.get()
                .load(imageUrl)
                .into(imageView_FullImage);


    }
}
