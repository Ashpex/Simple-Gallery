package com.example.testgallery.activities.mainActivities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testgallery.R;
import com.example.testgallery.adapters.ItemAlbumAdapter;
import com.example.testgallery.fragments.mainFragments.ChangePassFragment;
import com.example.testgallery.models.Album;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class ItemAlbumActivity extends AppCompatActivity {
    private ArrayList<String> myAlbum;
    private RecyclerView ryc_album;
    private RecyclerView ryc_list_album;
    private Intent intent;
    private String album_name;
    Toolbar toolbar_item_album;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_album);
        intent = getIntent();
        mappingControls();
        setData();
        setRyc();
        events();


    }



    private void events() {

        // Toolbar events
        toolbar_item_album.inflateMenu(R.menu.menu_top_item_album);
        toolbar_item_album.setTitle(album_name);

        // Show back button
        toolbar_item_album.setNavigationIcon(R.drawable.abc_ic_ab_back_material);
        toolbar_item_album.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Toolbar options
        toolbar_item_album.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id){
                    case R.id.album_item_search:
                        Toast.makeText(ItemAlbumActivity.this, "Tìm kiếm", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.album_item_add:
                        Toast.makeText(ItemAlbumActivity.this, "Thêm ảnh vào album", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.album_item_delete:
                        Toast.makeText(ItemAlbumActivity.this, "Xóa", Toast.LENGTH_SHORT).show();
                        break;
                }

                return true;
            }
        });

    }





    private void setData() {
        myAlbum = intent.getStringArrayListExtra("data");
    }

    private void setRyc() {
        album_name = intent.getStringExtra("name");
        ryc_list_album.setLayoutManager(new GridLayoutManager(this, 3));
        ryc_list_album.setAdapter(new ItemAlbumAdapter(myAlbum));
    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    private void mappingControls() {
        ryc_list_album = findViewById(R.id.ryc_list_album);
        toolbar_item_album = findViewById(R.id.toolbar_item_album);
    }
}
