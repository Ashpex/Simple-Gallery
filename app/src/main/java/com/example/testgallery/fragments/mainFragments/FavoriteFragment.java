package com.example.testgallery.fragments.mainFragments;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testgallery.R;
import com.example.testgallery.activities.mainActivities.ItemAlbumActivity;
import com.example.testgallery.activities.mainActivities.SlideShowActivity;
import com.example.testgallery.activities.mainActivities.data_favor.DataLocalManager;
import com.example.testgallery.adapters.CategoryAdapter;
import com.example.testgallery.adapters.ItemAlbumAdapter;

import com.example.testgallery.models.Category;
import com.example.testgallery.models.Image;
import com.example.testgallery.utility.GetAllPhotoFromGallery;


import java.io.File;
import java.util.ArrayList;

import java.util.List;

public class FavoriteFragment extends Fragment {
    private RecyclerView recyclerView;

    private List<String> imageListPath;
    private List<Image> imageList;
    private androidx.appcompat.widget.Toolbar toolbar_favor;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container,false);
        context = view.getContext();
        recyclerView = view.findViewById(R.id.favor_category);
        toolbar_favor = view.findViewById(R.id.toolbar_favor);

        // Toolbar events
        toolbar_favor.inflateMenu(R.menu.menu_top_favor);
        toolbar_favor.setTitle("Favorite");

        toolbar_favor.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id){
                    case R.id.album_item_search:
                        eventSearch(menuItem);
                        break;
                    case R.id.album_item_slideshow:
                        slideShowEvents();
                        break;
                }

                return true;
            }
        });

        imageListPath = DataLocalManager.getListImg();
        imageList = getListImgFavor(imageListPath);

        setRyc();


        return view;
    }

    private void setRyc() {

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));

        recyclerView.setAdapter(new ItemAlbumAdapter(new ArrayList<>(imageListPath)));

    }

    private void eventSearch(@NonNull MenuItem item) {
        androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) item.getActionView();
        searchView.setQueryHint("Type to search");
        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                ArrayList<String> listImageSearch = new ArrayList<>();
                for (String image : imageListPath) {
                    if (image.toLowerCase().contains(s)) {
                        listImageSearch.add(image);
                    }
                }

                if (listImageSearch.size() != 0) {
                    recyclerView.setAdapter(new ItemAlbumAdapter(listImageSearch));
                    synchronized (FavoriteFragment.this) {
                        FavoriteFragment.this.notifyAll();
                    }
                } else {
                    Toast.makeText(getContext(), "Searched image not found", Toast.LENGTH_LONG).show();
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
                recyclerView.setAdapter(new ItemAlbumAdapter(new ArrayList<>(imageListPath)));
                synchronized (FavoriteFragment.this) {
                    FavoriteFragment.this.notifyAll();
                }
                return true;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        FavoriteFragment.MyAsyncTask myAsyncTask = new FavoriteFragment.MyAsyncTask();
        myAsyncTask.execute();
    }

    private List<Image> getListImgFavor(List<String> imageListUri) {
        List<Image> listImageFavor = new ArrayList<>();
        List<Image> imageList = GetAllPhotoFromGallery.getAllImageFromGallery(context);
        for (int i = 0; i < imageList.size(); i++) {
            for (String st: imageListUri) {
                if(imageList.get(i).getPath().equals(st)){
                    listImageFavor.add(imageList.get(i));
                }
            }
        }

        return listImageFavor;
    }
    public class MyAsyncTask extends AsyncTask<Void, Integer, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            imageListPath = DataLocalManager.getListImg();
            for(int i=0;i<imageListPath.size();i++) {
                File file = new File(imageListPath.get(i));
                if(!file.exists()) {
                    imageListPath.remove(i);
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            recyclerView.setAdapter(new ItemAlbumAdapter(new ArrayList<>(imageListPath)));
        }
    }

    private void slideShowEvents() {
        Intent intent = new Intent(getView().getContext(), SlideShowActivity.class);

        ArrayList<String> list = new ArrayList<>(imageListPath.size());
        list.addAll(imageListPath);
        intent.putStringArrayListExtra("data_slide", list);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getView().getContext().startActivity(intent);
    }
}