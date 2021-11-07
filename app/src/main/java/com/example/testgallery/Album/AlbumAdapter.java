package com.example.testgallery.Album;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testgallery.ListAlbumFragment;
import com.example.testgallery.R;

import java.util.List;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>{
    private List<Album> mlistAlbums;
    public AlbumAdapter(List<Album> listAlbums){
        mlistAlbums = listAlbums;
    }
    @NonNull
    @Override
    public AlbumAdapter.AlbumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_album, parent, false);


        return new AlbumAdapter.AlbumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumAdapter.AlbumViewHolder holder, int position) {
        holder.onBind(mlistAlbums.get(position));
    }

    @Override
    public int getItemCount() {
        return mlistAlbums.size();
    }

    class AlbumViewHolder extends RecyclerView.ViewHolder {
        private ImageView img_album;
        private TextView txtName_album;
        private TextView txtCount_item_album;
        public AlbumViewHolder(@NonNull View itemView) {
            super(itemView);
            img_album = itemView.findViewById(R.id.img_album);
            txtName_album = itemView.findViewById(R.id.txtName_album);
            txtCount_item_album = itemView.findViewById(R.id.txtCount_item_album);
        }
        public void onBind(Album ref) {
            img_album.setImageResource(ref.getImg().getResource());
            txtName_album.setText(ref.getName());
            txtCount_item_album.setText(String.valueOf(ref.getList().size()) + " items");

            img_album.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    ListAlbumFragment myFragment = new ListAlbumFragment(ref);
                    activity.getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.anim_open_fragment, R.anim.anim_close_fragment)
                            .replace(R.id.frlayout, myFragment, "fragment_list_album").addToBackStack(null).commit();
                }
            });

        }
    }
}
