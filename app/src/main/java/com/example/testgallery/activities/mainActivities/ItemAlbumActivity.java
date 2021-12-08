package com.example.testgallery.activities.mainActivities;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.Explode;
import androidx.transition.Transition;

import com.example.testgallery.R;
import com.example.testgallery.activities.mainActivities.data_favor.DataLocalManager;
import com.example.testgallery.activities.subActivities.ItemAlbumMultiSelectActivity;
import com.example.testgallery.activities.subActivities.MultiSelectImage;
import com.example.testgallery.adapters.ItemAlbumAdapter;
import com.example.testgallery.adapters.ItemAlbumAdapter2;
import com.example.testgallery.adapters.ItemAlbumAdapter3;


import java.io.File;
import java.util.ArrayList;

public class ItemAlbumActivity extends AppCompatActivity {
    private ArrayList<String> myAlbum;
    private String path_folder ;
    private RecyclerView ryc_album;
    private RecyclerView ryc_list_album;
    private Intent intent;
    private String album_name;
    Toolbar toolbar_item_album;
    private ItemAlbumAdapter itemAlbumAdapter;
    private ItemAlbumAdapter2 itemAlbumAdapter2;
    private ItemAlbumAdapter3 itemAlbumAdapter3;
    private int spanCount;
    private int isScret;
    private static int REQUEST_CODE_CHOOSE = 55;
    private static int REQUEST_CODE_ADD = 56;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_album);
        intent = getIntent();
        setUpSpanCount();
        mappingControls();
        setData();
        setRyc();
        events();
    }

    private void setUpSpanCount() {
        SharedPreferences sharedPref = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        spanCount = sharedPref.getInt("span_count", 3);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_ADD) {
            ArrayList<String> resultList = data.getStringArrayListExtra("list_result");
            if(resultList !=null) {
                myAlbum.addAll(resultList);
                spanAction();
            }
        }
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_CHOOSE) {
            int isMoved = data.getIntExtra("move", 0);
            if(isMoved == 1) {
                ArrayList<String> resultList = data.getStringArrayListExtra("list_result");
                if (resultList != null) {
                    myAlbum.remove(resultList);
                    spanAction();
                }
            }
        }
    }

    private void spanAction() {
        if(spanCount == 1) {
            itemAlbumAdapter3.setData(myAlbum);
            ryc_list_album.setAdapter(itemAlbumAdapter3);
        }
        else if(spanCount == 2) {
            itemAlbumAdapter2.setData(myAlbum);
            ryc_list_album.setAdapter(itemAlbumAdapter3);
        }
        else{
            itemAlbumAdapter.setData(myAlbum);
            ryc_list_album.setAdapter(itemAlbumAdapter);
        }
    }

    private void setRyc() {
        album_name = intent.getStringExtra("name");
        ryc_list_album.setLayoutManager(new GridLayoutManager(this, spanCount));
        itemAlbumAdapter = new ItemAlbumAdapter(myAlbum);
        if(spanCount == 1)
            ryc_list_album.setAdapter(new ItemAlbumAdapter3(myAlbum));
        else if(spanCount == 2)
            ryc_list_album.setAdapter(new ItemAlbumAdapter2(myAlbum));
        else
            ryc_list_album.setAdapter(new ItemAlbumAdapter(myAlbum));
    }

    private void animationRyc() {
        switch(spanCount) {
            case 1:
                Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.anim_layout_ryc_1);
                ryc_list_album.setAnimation(animation1);
            case 2:
                Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_layout_ryc_1);
                ryc_list_album.setAnimation(animation2);
                break;
            case 3:
                Animation animation3 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_layout_ryc_2);
                ryc_list_album.setAnimation(animation3);
                break;
            case 4:
                Animation animation4 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_layout_ryc_3);
                ryc_list_album.setAnimation(animation4);
                break;
        }
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
                    case R.id.change_span_count:
                        spanCountEvent();
                        break;
                    case R.id.menuChoose:
                        Intent intent_mul = new Intent(ItemAlbumActivity.this, ItemAlbumMultiSelectActivity.class);
                        intent_mul.putStringArrayListExtra("data_1", myAlbum);
                        intent_mul.putExtra("name_1", album_name);
                        intent_mul.putExtra("path_folder", path_folder);
                        startActivityForResult(intent_mul, REQUEST_CODE_CHOOSE);
                        break;
                    case R.id.album_item_slideshow:
                        slideShowEvents();
                        break;
                    case R.id.menu_add_image:
                        Intent intent_add = new Intent(ItemAlbumActivity.this, AddImageToAlbumActivity.class);
                        intent_add.putStringArrayListExtra("list_image", myAlbum);
                        intent_add.putExtra("path_folder", path_folder);
                        intent_add.putExtra("name_folder", album_name);
                        startActivityForResult(intent_add, REQUEST_CODE_ADD);
                        break;
                }

                return true;
            }
        });
        if(isScret == 1)
        hideMenu();
    }

    private void hideMenu() {
        toolbar_item_album.getMenu().findItem(R.id.menu_add_image).setVisible(false);
    }

    private void spanCountEvent() {
        if(spanCount == 1){
            spanCount++;
            ryc_list_album.setLayoutManager(new GridLayoutManager(this, spanCount));
            ryc_list_album.setAdapter(itemAlbumAdapter2);
        }

        else if(spanCount < 4 && spanCount > 1) {
            spanCount++;
            ryc_list_album.setLayoutManager(new GridLayoutManager(this, spanCount));
            ryc_list_album.setAdapter(itemAlbumAdapter);
        }
        else if(spanCount == 4) {

            spanCount = 1;
            ryc_list_album.setLayoutManager(new LinearLayoutManager(this));
            ryc_list_album.setAdapter(itemAlbumAdapter3);

        }


        animationRyc();
        SharedPreferences sharedPref = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("span_count", spanCount);
        editor.commit();
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
        path_folder = intent.getStringExtra("path_folder");
        isScret = intent.getIntExtra("isSecret", 0);
        itemAlbumAdapter2 = new ItemAlbumAdapter2(myAlbum);
        itemAlbumAdapter3 = new ItemAlbumAdapter3(myAlbum);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        MyAsyncTask myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute();
    }

    private void mappingControls() {
        ryc_list_album = findViewById(R.id.ryc_list_album);
        toolbar_item_album = findViewById(R.id.toolbar_item_album);
    }

    public class MyAsyncTask extends AsyncTask<Void, Integer, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            for(int i=0;i<myAlbum.size();i++) {
                File file = new File(myAlbum.get(i));
                if(!file.exists()) {
                    myAlbum.remove(i);
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            itemAlbumAdapter.notifyDataSetChanged();
        }
    }

}