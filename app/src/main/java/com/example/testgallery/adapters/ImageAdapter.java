package com.example.testgallery.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.testgallery.activities.mainActivities.PictureActivity;
import com.example.testgallery.R;
import com.example.testgallery.models.Image;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private List<Image> listImages;
    private Context context;

    public ImageAdapter(Context context) {
        this.context = context;
    }

    public ImageAdapter() {
    }

    public void setData(List<Image> listImages) {
        this.listImages = listImages;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_picture, parent, false);

        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Image image = listImages.get(position);
        if (image == null) {
            return;
        }

        // set ảnh cho imgPhoto bằng thư viện Glide
        Glide.with(context).load(image.getThumb()).into(holder.imgPhoto);

        holder.imgPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PictureActivity.class);
                intent.putExtra("imgSrc", image.getThumb());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        if (listImages != null)
            return listImages.size();
        return 0;
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgPhoto;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.imgPhoto);
        }
    }

}

