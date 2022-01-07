package com.example.testgallery.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.testgallery.R;
import com.example.testgallery.models.Category;
import com.example.testgallery.models.Image;
import com.example.testgallery.utility.ListTransInterface;

import java.util.ArrayList;
import java.util.List;

public class ImageSelectAdapter extends RecyclerView.Adapter<ImageSelectAdapter.ImageSelectHolder> {

    private ListTransInterface listTransInterface;
    private List<Image> listImages;
    private Context context;
    private List<Category> listCategory;
    private Intent intent;
    private ArrayList<String> listPath ;
    private ArrayList<String> listThumb ;
    public ImageSelectAdapter(Context context) {
        this.context = context;
    }

    public void setListTransInterface(ListTransInterface listTransInterface) {
        this.listTransInterface = listTransInterface;
    }

    public void setListCategory(List<Category> listCategory) {
        this.listCategory = listCategory;
    }

    public void setData(List<Image> listImages) {
        this.listImages = listImages;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ImageSelectHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_picture, parent, false);

        return new ImageSelectHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageSelectHolder holder, @SuppressLint("RecyclerView") int position) {
        Image image = listImages.get(position);
        if (image == null) {
            return;
        }

        // set ảnh cho imgPhoto bằng thư viện Glide
        Glide.with(context).load(image.getThumb()).into(holder.imgPhoto);

        holder.imgPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if (holder.imgPhoto.getImageAlpha() == 100) {
                        holder.imgPhoto.setImageAlpha(255);
                        listTransInterface.removeList(image);
                    } else if (holder.imgPhoto.getImageAlpha() == 255) {
                        holder.imgPhoto.setImageAlpha(100);
                        listTransInterface.addList(image);
                    }
            }
        });

    }

    @Override
    public int getItemCount() {
        if (listImages != null)
            return listImages.size();
        return 0;
    }

    public class ImageSelectHolder extends RecyclerView.ViewHolder {
        private ImageView imgPhoto;

        public ImageSelectHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.imgPhoto);
        }
    }


    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object){
        // Remove view
        container.removeView((View) object);
    }
}