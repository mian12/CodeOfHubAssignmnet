package com.example.best.codeofhubassignmnet.FullPhoto;

import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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
        //PhotoViewAttacher photoViewAttacher=new PhotoViewAttacher(imageView_FullImage);

        String imageUrl = getIntent().getExtras().getString("fullImageLink");

        Picasso.get()
                .load(imageUrl)
                .into(imageView_FullImage);



       // photoViewAttacher.update();





    }
}
