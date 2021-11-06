package com.example.testgallery.album;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testgallery.R;

import image.Image;

public class ListAlbumAdapter extends RecyclerView.Adapter<ListAlbumAdapter.ListAlbumViewHolder> {
    private Album album;
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
        private ImageView imgGirl;
        public ListAlbumViewHolder(@NonNull View itemView) {
            super(itemView);
            imgGirl = itemView.findViewById(R.id.imgGirl);
        }
        public void onBind(Image img) {
            imgGirl.setImageResource(img.getResource());
        }
    }
}
