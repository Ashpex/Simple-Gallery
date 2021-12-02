package com.example.testgallery.activities.mainActivities;


import android.content.Intent;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testgallery.R;
import com.example.testgallery.activities.subActivities.ItemAlbumMultiSelectActivity;
import com.example.testgallery.activities.subActivities.MultiSelectImage;
import com.example.testgallery.adapters.ItemAlbumAdapter;


import java.util.ArrayList;

public class ItemAlbumActivity extends AppCompatActivity {
    private ArrayList<String> myAlbum;
    private RecyclerView ryc_album;
    private RecyclerView ryc_list_album;
    private Intent intent;
    private String album_name;
    Toolbar toolbar_item_album;
    private SearchView searchView;
    private static int REQUEST_CODE_CHOOSE = 55;

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_CHOOSE) {
            finish();
        }
    }

    private void setRyc() {
        album_name = intent.getStringExtra("name");
        ryc_list_album.setLayoutManager(new GridLayoutManager(this, 3));
        ryc_list_album.setAdapter(new ItemAlbumAdapter(myAlbum));
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
                switch (id) {
                    case R.id.album_item_search:
                        eventSearch(menuItem);
                        break;

                    case R.id.menuChoose:
                        Intent intent_mul = new Intent(ItemAlbumActivity.this, ItemAlbumMultiSelectActivity.class);
                        intent_mul.putStringArrayListExtra("data_1", myAlbum);
                        intent_mul.putExtra("name_1", album_name);
                        startActivityForResult(intent_mul, REQUEST_CODE_CHOOSE);
                        break;
                    case R.id.album_item_slideshow:
                        slideShowEvents();
                        break;
                }

                return true;
            }
        });
    }

    private void eventSearch(@NonNull MenuItem item) {
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setQueryHint("Type to search");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                ArrayList<String> listImageSearch = new ArrayList<>();
                for (String image : myAlbum) {
                    if (image.toLowerCase().contains(s)) {
                        listImageSearch.add(image);
                    }
                }

                if (listImageSearch.size() != 0) {
                    ryc_list_album.setAdapter(new ItemAlbumAdapter(listImageSearch));
                    synchronized (ItemAlbumActivity.this) {
                        ItemAlbumActivity.this.notifyAll();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Searched image not found", Toast.LENGTH_LONG).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        item.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                ryc_list_album.setAdapter(new ItemAlbumAdapter(myAlbum));
                synchronized (ItemAlbumActivity.this) {
                    ItemAlbumActivity.this.notifyAll();
                }
                return true;
            }
        });
    }

    private void slideShowEvents() {
        Intent intent = new Intent(ItemAlbumActivity.this, SlideShowActivity.class);
        intent.putStringArrayListExtra("data_slide", myAlbum);
        intent.putExtra("name", album_name);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ItemAlbumActivity.this.startActivity(intent);
    }

    private void setData() {
        myAlbum = intent.getStringArrayListExtra("data");
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void mappingControls() {
        ryc_list_album = findViewById(R.id.ryc_list_album);
        toolbar_item_album = findViewById(R.id.toolbar_item_album);

    }
}