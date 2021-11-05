package girl;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ashpex.simplegallery.PictureActivity;
import com.ashpex.simplegallery.R;

import java.util.List;

public class GirlAdapter extends RecyclerView.Adapter<GirlAdapter.GirlViewHolder> {

    private List<Girl> listGirl;

    public void setData(List<Girl> listGirl) {
        this.listGirl = listGirl;
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
        Girl girl = listGirl.get(position);
        if (girl == null) {
            return;
        }

        holder.imgGirl.setImageResource(girl.getResource());

        holder.imgGirl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.context, PictureActivity.class);
                intent.putExtra("imgSrc", girl.getResource());
                holder.context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (listGirl != null)
            return listGirl.size();
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

