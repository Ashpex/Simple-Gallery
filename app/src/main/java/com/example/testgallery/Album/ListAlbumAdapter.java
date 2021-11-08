package com.example.testgallery.Album;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.testgallery.PictureActivity;
import com.example.testgallery.R;

import image.Image;

public class ListAlbumAdapter extends RecyclerView.Adapter<ListAlbumAdapter.ListAlbumViewHolder> {
    private Album album;
    private Context context;

    public ListAlbumAdapter(Context context) {
        this.context = context;
    }

    public ListAlbumAdapter(Album album) {
        this.album = album;
    }
    @NonNull
    @Override
    public ListAlbumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_picture, parent, false);

        return new ListAlbumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAlbumViewHolder holder, int position) {
        holder.onBind(album.getList().get(position));
    }

    @Override
    public int getItemCount() {
        return album.getList().size();
    }

    class ListAlbumViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgPhoto;
        private Context context;
        public ListAlbumViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            imgPhoto = itemView.findViewById(R.id.imgPhoto);
        }
        public void onBind(Image img) {
            // set ảnh cho imgPhoto bằng thư viện Glide
            Glide.with(context).load(img.getThumb()).into(imgPhoto);
            imgPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, PictureActivity.class);
                    intent.putExtra("imgSrc", img.getThumb());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }
    }
}
