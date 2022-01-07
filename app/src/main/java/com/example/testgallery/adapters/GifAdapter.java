package com.example.testgallery.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.testgallery.R;
import com.example.testgallery.activities.mainActivities.PictureActivity;
import com.example.testgallery.activities.subActivities.GifShowActivity;
import com.example.testgallery.models.Category;
import com.example.testgallery.models.GIF;
import com.example.testgallery.models.Image;

import java.util.ArrayList;
import java.util.List;

public class GifAdapter extends RecyclerView.Adapter<GifAdapter.ImageViewHolder> {

    private List<GIF> listGif;
    private Context context;
    public GifAdapter(Context context) {
        this.context = context;
    }


    public GifAdapter() {
    }

    public void setData(List<GIF> listGif) {
        this.listGif = listGif;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_picture, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, @SuppressLint("RecyclerView") int position) {
        GIF image = listGif.get(position);
        if (image == null) {
            return;
        }
        Glide.with(context).load(image.getPathPre()).into(holder.imgPhoto);

        holder.imgPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> list = new ArrayList<>();
                list.addAll(listGif.get(position).getListImage());
                Intent intent = new Intent(context, GifShowActivity.class);
                intent.putStringArrayListExtra("list", list);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (listGif != null)
            return listGif.size();
        return 0;
    }



    public class ImageViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgPhoto;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.imgPhoto);
        }
    }


    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object){

        // Remove view
        container.removeView((View) object);
    }

}

