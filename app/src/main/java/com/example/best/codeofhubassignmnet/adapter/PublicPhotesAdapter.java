package com.example.best.codeofhubassignmnet.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.best.codeofhubassignmnet.FullPhoto.SeePhotoActivity;
import com.example.best.codeofhubassignmnet.R;
import com.example.best.codeofhubassignmnet.model.FlickerPublicPhotesResponse;
import com.example.best.codeofhubassignmnet.model.Item;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PublicPhotesAdapter extends RecyclerView.Adapter<PublicPhotesAdapter.MyViewHolder> {


    private Context context;
    private List<Item> items;
    private static final int MAX_WIDTH = 400;
    private static final int MAX_HEIGHT = 400;

    public PublicPhotesAdapter(Context context, List<Item> items) {
        this.context = context;
        this.items = items;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_public_photes, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        try {


//            String name= courses.get(position).getTitle();
//
//
//            ArrayList<Instructors> instructors=courses.get(position).getInstructors();
//            Log.e("size", instructors.size()+"helloeee");
//            final String teacherName= instructors.get(0).getName();
//            final String bio= instructors.get(0).getBio();
//            final String dp= instructors.get(0).getImage();

            // Picasso.get().load("http://i.imgur.com/DvpvklR.png").into(imageView);

            final int temp = i;
            Picasso.get()
                    .load(items.get(i).getMedia().getM())
                    .resize(MAX_WIDTH, MAX_HEIGHT)
                    .centerCrop()
                    .into(myViewHolder.imageViewPublicPhoto);


            myViewHolder.row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //  Toast.makeText(context, ""+teacherName, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, SeePhotoActivity.class);
                    intent.putExtra("fullImageLink", items.get(temp).getMedia().getM());

                    context.startActivity(intent);
                }
            });

        } catch (Exception e) {
            Log.e("err", e.getMessage());
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageViewPublicPhoto;
        private View row;

        MyViewHolder(View view) {
            super(view);
            row = view;
            imageViewPublicPhoto = view.findViewById(R.id.imageViewPublicPhoto);

        }
    }
}
