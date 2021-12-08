package com.example.testgallery.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.testgallery.R;
import com.example.testgallery.activities.mainActivities.PictureActivity;

import java.util.ArrayList;
import java.util.List;

public class ItemAlbumAdapter2 extends RecyclerView.Adapter<ItemAlbumAdapter2.ItemAlbumViewHolder> {
    private ArrayList<String> album;
    private ImageView imgPhoto;
    private static int REQUEST_CODE_PIC = 10;
    public ItemAlbumAdapter2(ArrayList<String> album) {
        this.album = album;
    }

    public void setData(ArrayList<String> album) {
        this.album = album;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ItemAlbumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_picture_album_span2, parent, false);

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



    public class ItemAlbumViewHolder extends RecyclerView.ViewHolder {
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

                    ((Activity)context).startActivityForResult(intent,REQUEST_CODE_PIC);
                }
            });
        }
    }
}
