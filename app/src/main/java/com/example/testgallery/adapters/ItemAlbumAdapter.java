package com.example.testgallery.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.testgallery.activities.mainActivities.PictureActivity;
import com.example.testgallery.R;



import java.util.ArrayList;

public class ItemAlbumAdapter extends RecyclerView.Adapter<ItemAlbumAdapter.ItemAlbumViewHolder> {
    private ArrayList<String> album;
    private Context context;

    public ItemAlbumAdapter(ArrayList<String> album) {
        this.album = album;
    }


    @NonNull
    @Override
    public ItemAlbumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_picture, parent, false);

        return new ItemAlbumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAlbumViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.onBind(album.get(position), position);

    }

    @Override
    public int getItemCount() {
        return album.size();
    }



    class ItemAlbumViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgPhoto;
        private Context context;
        public ItemAlbumViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            imgPhoto = itemView.findViewById(R.id.imgPhoto);
        }
        public void onBind(String img, int pos) {
            // set ảnh cho imgPhoto bằng thư viện Glide
            Glide.with(context).load(img).into(imgPhoto);
            imgPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, PictureActivity.class);
                    intent.putStringArrayListExtra("data_list_path", album);
                    intent.putStringArrayListExtra("data_list_thumb", album);
                    intent.putExtra("pos", pos);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }
    }
}
