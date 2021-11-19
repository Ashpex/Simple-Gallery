package com.example.testgallery.fragments.mainFragments;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testgallery.R;
import com.example.testgallery.activities.mainActivities.ItemAlbumActivity;
import com.example.testgallery.activities.mainActivities.data_favor.DataLocalManager;
import com.example.testgallery.adapters.CategoryAdapter;
import com.example.testgallery.adapters.ImageAdapter;
import com.example.testgallery.adapters.ItemAlbumAdapter;
import com.example.testgallery.models.Category;
import com.example.testgallery.models.Image;
import com.example.testgallery.utility.GetAllPhotoFromGallery;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FavoriteFragment extends Fragment {
    private RecyclerView recyclerView;
    private ImageAdapter imageAdapter;
    private List<Category> listImg;
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
        imageListPath = DataLocalManager.getListImg();
        imageList = getListImgFavor(imageListPath);

        setRyc();


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        FavoriteFragment.MyAsyncTask myAsyncTask = new FavoriteFragment.MyAsyncTask();
        myAsyncTask.execute();
    }

    private void setRyc() {

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));

        recyclerView.setAdapter(new ItemAlbumAdapter(new ArrayList<>(imageListPath)));

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
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            recyclerView.setAdapter(new ItemAlbumAdapter(new ArrayList<>(imageListPath)));
        }
    }
}
