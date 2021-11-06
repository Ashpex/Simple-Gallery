package image;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testgallery.PictureActivity;
import com.example.testgallery.R;

import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.GirlViewHolder> {

    private List<Image> listImage;

    public void setData(List<Image> listImage) {
        this.listImage = listImage;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GirlViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_picture, parent, false);


        return new GirlViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GirlViewHolder holder, int position) {
        Image image = listImage.get(position);
        if (image == null) {
            return;
        }

        holder.imgGirl.setImageResource(image.getResource());

        holder.imgGirl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.context, PictureActivity.class);
                intent.putExtra("imgSrc", image.getResource());
                holder.context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (listImage != null)
            return listImage.size();
        return 0;
    }

    public class GirlViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgGirl;
        private Context context;

        public GirlViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            imgGirl = itemView.findViewById(R.id.imgGirl);
        }
    }

}

