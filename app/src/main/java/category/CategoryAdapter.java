package category;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testgallery.R;

import java.util.List;

import image.ImageAdapter;

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

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
        holder.rcvPictures.setLayoutManager(linearLayoutManager);

        ImageAdapter girlAdapter = new ImageAdapter(context.getApplicationContext());
        girlAdapter.setData(category.getListGirl());
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
