package com.example.testgallery.activities.mainActivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testgallery.R;
import com.example.testgallery.adapters.ItemAlbumAdapter;
import com.example.testgallery.fragments.mainFragments.ChangePassFragment;
import com.example.testgallery.models.Album;

import java.util.ArrayList;

public class ItemAlbumActivity extends AppCompatActivity {
    private ArrayList<String> myAlbum;
    private RecyclerView ryc_album;
    private TextView txtName_list_album;
    private ImageButton btnBack;
    private RecyclerView ryc_list_album;
    private Intent intent;
    private  ImageButton btnAddPhoto;
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
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }

    private void setData() {
        myAlbum = intent.getStringArrayListExtra("data");
    }

    private void setRyc() {
        txtName_list_album.setText(intent.getStringExtra("name"));
        ryc_list_album.setLayoutManager(new GridLayoutManager(this, 3));
        ryc_list_album.setAdapter(new ItemAlbumAdapter(myAlbum));
    }

    private void mappingControls() {
        ryc_list_album = findViewById(R.id.ryc_list_album);
        btnBack = findViewById(R.id.btnBack);
        txtName_list_album = findViewById(R.id.txtName_list_album);
        btnAddPhoto = findViewById(R.id.ic_add_photo);
    }
}
