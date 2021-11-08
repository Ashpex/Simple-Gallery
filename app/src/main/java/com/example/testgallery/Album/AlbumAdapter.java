package com.example.testgallery.Album;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.testgallery.ListAlbumFragment;
import com.example.testgallery.R;

import java.util.List;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder> {
    private List<Album> mlistAlbums;
    private Context context;

    public AlbumAdapter(List<Album> listAlbums) {
        mlistAlbums = listAlbums;
    }

    public AlbumAdapter(Context context) {
        this.context = context;
    }

    public AlbumAdapter(List<Album> mlistAlbums, Context context) {
        this.mlistAlbums = mlistAlbums;
        this.context = context;
    }

    public void setData(List<Album> mlistAlbums) {
        this.mlistAlbums = mlistAlbums;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AlbumAdapter.AlbumViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_album, parent, false);


        return new AlbumAdapter.AlbumViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlbumAdapter.AlbumViewHolder holder, int position) {
//        holder.onBind(mlistAlbums.get(position));

        Album album = mlistAlbums.get(position);
        if (album == null) {
            return;
        }

        Glide.with(context).load(album.getImg().getThumb()).into(holder.img_album);

        holder.txtName_album.setText(album.getName());
        holder.txtCount_item_album.setText(String.valueOf(album.getList().size()) + " items");

        holder.img_album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(context, PictureActivity.class);
//                intent.putExtra("imgSrc", image.getThumb());
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent);

                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                ListAlbumFragment myFragment = new ListAlbumFragment(album);
                activity.getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.anim_open_fragment, R.anim.anim_close_fragment)
                        .replace(R.id.frlayout, myFragment, "fragment_list_album").addToBackStack(null).commit();

            }
        });
    }

    @Override
    public int getItemCount() {
        if (mlistAlbums != null) {
            return mlistAlbums.size();
        }
        return 0;
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
//            code cũ của Nguyên
//            img_album.setImageResource(ref.getImg().getResource()); // comment de test chuong trinh
//            txtName_album.setText(ref.getName());
//            txtCount_item_album.setText(String.valueOf(ref.getList().size()) + " items");

//            img_album.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
//                    ListAlbumFragment myFragment = new ListAlbumFragment(ref);
//                    activity.getSupportFragmentManager().beginTransaction()
//                            .setCustomAnimations(R.anim.anim_open_fragment, R.anim.anim_close_fragment)
//                            .replace(R.id.frlayout, myFragment, "fragment_list_album").addToBackStack(null).commit();
//                }
//            });

        }
    }
}
