package com.example.testgallery.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.testgallery.activities.mainActivities.ItemAlbumActivity;
import com.example.testgallery.R;
import com.example.testgallery.activities.mainActivities.PictureActivity;
import com.example.testgallery.activities.mainActivities.SlideShowActivity;
import com.example.testgallery.models.Album;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder> {
    private List<Album> mListAlbums;
    private Context context;
    private BottomSheetDialog bottomSheetDialog;
    public AlbumAdapter(List<Album> mListAlbums, Context context) {
        this.mListAlbums = mListAlbums;
        this.context = context;
    }

    public void setData(List<Album> mListAlbums) {
        this.mListAlbums = mListAlbums;
        notifyDataSetChanged();
    }
    public void notifyData() {
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
        holder.onBind(mListAlbums.get(position), position);
    }

    @Override
    public int getItemCount() {
        if (mListAlbums != null) {
            return mListAlbums.size();
        }
        return 0;
    }

    class AlbumViewHolder extends RecyclerView.ViewHolder {
        private final ImageView img_album;
        private final TextView txtName_album;
        private final TextView txtCount_item_album;
        private Context context;
        private LinearLayout layout_bottom_delete;
        private LinearLayout layout_bottom_slide_show;

        public AlbumViewHolder(@NonNull View itemView) {
            super(itemView);
            img_album = itemView.findViewById(R.id.img_album);
            txtName_album = itemView.findViewById(R.id.txtName_album);
            txtCount_item_album = itemView.findViewById(R.id.txtCount_item_album);
            context = itemView.getContext();
        }

        public void onBind(Album ref, int pos) {
            bindData(ref);

            img_album.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ItemAlbumActivity.class);
                    ArrayList<String> list = new ArrayList<>();
                    for(int i=0;i<ref.getList().size();i++) {
                        list.add(ref.getList().get(i).getThumb());
                    }

                    intent.putStringArrayListExtra("data", list);
                    intent.putExtra("name", ref.getName());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });

            img_album.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    openBottomDialog();
                    layout_bottom_slide_show.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            slideShowEvents(ref);
                        }
                    });
                    layout_bottom_delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            deleteEvents(ref, pos);
                        }
                    });
                    return true;
                }
            });

        }

        private void bindData(Album ref) {
            txtName_album.setText(ref.getName());
            txtCount_item_album.setText(String.valueOf(ref.getList().size()) + " items");
            Glide.with(context).load(ref.getImg().getThumb()).into(img_album);
        }

        private void slideShowEvents(@NonNull Album ref) {
            Intent intent = new Intent(context, SlideShowActivity.class);
            ArrayList<String> list = new ArrayList<>();
            for(int i=0;i<ref.getList().size();i++) {
                list.add(ref.getList().get(i).getThumb());
            }
            intent.putStringArrayListExtra("data_slide", list);
            intent.putExtra("name", ref.getName());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            bottomSheetDialog.cancel();
            context.startActivity(intent);
        }
        private void deleteEvents(Album ref, int pos) {
            for(int i=0;i<ref.getList().size();i++) {
                Uri targetUri = Uri.parse("file://" + ref.getList().get(i).getPath());
                File file = new File(targetUri.getPath());
                if (file.exists()){
                    file.delete();
                }
            }
            mListAlbums.remove(pos);
            notifyDataSetChanged();
            bottomSheetDialog.cancel();
        }
        private void openBottomDialog() {
            View viewDialog = LayoutInflater.from(context).inflate(R.layout.layout_bottom_sheet_album, null);
            layout_bottom_delete = viewDialog.findViewById(R.id.layout_bottom_delete);
            layout_bottom_slide_show = viewDialog.findViewById(R.id.layout_bottom_slide_show);

            bottomSheetDialog = new BottomSheetDialog(context);
            bottomSheetDialog.setContentView(viewDialog);
            bottomSheetDialog.show();
        }
    }
}
