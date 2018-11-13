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

import com.example.best.codeofhubassignmnet.FullPhoto.FullMyPhotoActivity;
import com.example.best.codeofhubassignmnet.FullPhoto.SeePhotoActivity;
import com.example.best.codeofhubassignmnet.R;
import com.example.best.codeofhubassignmnet.model.Item;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GalleryPhotesAdapter extends RecyclerView.Adapter<GalleryPhotesAdapter.MyViewHolder> {


    private Context context;
    private List<String> url;
    private static final int MAX_WIDTH = 400;
    private static final int MAX_HEIGHT = 400;

    public GalleryPhotesAdapter(Context context, List<String> url) {
        this.context = context;
        this.url = url;
    }

    @NonNull
    @Override
    public GalleryPhotesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new GalleryPhotesAdapter.MyViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_public_photes, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryPhotesAdapter.MyViewHolder myViewHolder, int i) {
        try {

            final int temp = i;
            Picasso.get()
                    .load(url.get(i))
                    .resize(MAX_WIDTH, MAX_HEIGHT)
                    .centerCrop()
                    .into(myViewHolder.imageViewPublicPhoto);

            myViewHolder.row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, FullMyPhotoActivity.class);
                    intent.putExtra("fullImageLink", url.get(temp));

                    context.startActivity(intent);
                }
            });

        } catch (Exception e) {
            Log.e("err", e.getMessage());
        }

    }

    @Override
    public int getItemCount() {
        return url.size();
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
