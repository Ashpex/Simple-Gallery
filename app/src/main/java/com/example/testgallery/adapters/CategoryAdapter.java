package com.example.testgallery.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testgallery.R;
import com.example.testgallery.models.Category;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>{
    private Context context;
    private List<Category> listCategory;

    public CategoryAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Category> listCategory){
        this.listCategory = listCategory;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = listCategory.get(position);
        if (category == null)
            return;

        holder.tvNameCategory.setText(category.getNameCategory());

        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3);
        holder.rcvPictures.setLayoutManager(gridLayoutManager);

        ImageAdapter girlAdapter = new ImageAdapter(context.getApplicationContext());
        girlAdapter.setData(category.getListGirl());
        girlAdapter.setListCategory(listCategory);
        holder.rcvPictures.setAdapter(girlAdapter);


    }

    @Override
    public int getItemCount() {
        if (listCategory != null){
            return listCategory.size();
        }
        return 0;
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder{
        private TextView tvNameCategory;
        private RecyclerView rcvPictures;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNameCategory = itemView.findViewById(R.id.tvNameCategory);
            rcvPictures = itemView.findViewById(R.id.rcvPictures);
        }
    }
}
