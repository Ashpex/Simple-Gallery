package com.example.testgallery.activities.mainActivities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import android.widget.Adapter;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testgallery.R;
import com.example.testgallery.adapters.ItemAlbumAdapter;
import com.example.testgallery.models.Image;


import java.util.ArrayList;
import java.util.List;

public class ItemAlbumActivity extends AppCompatActivity {
    private ArrayList<String> myAlbum;
    private RecyclerView ryc_album;
    private RecyclerView ryc_list_album;
    private Intent intent;
    private String album_name;
    Toolbar toolbar_item_album;
    private SearchView searchView;


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
                        Toast.makeText(ItemAlbumActivity.this, "Search", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.album_item_add:
                        Toast.makeText(ItemAlbumActivity.this, "Add images to album", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.album_item_delete:
                        Toast.makeText(ItemAlbumActivity.this, "Delete", Toast.LENGTH_SHORT).show();
                        break;

                    case R.id.album_item_slideshow:
                        slideShowEvents();
                        break;
                }

                return true;
            }
        });

    }



    private void slideShowEvents() {
        Intent intent = new Intent(ItemAlbumActivity.this, SlideShowActivity.class);
        intent.putStringArrayListExtra("data_slide", myAlbum);
        intent.putExtra("name",album_name);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ItemAlbumActivity.this.startActivity(intent);
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