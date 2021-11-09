package com.example.testgallery.activities.mainActivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testgallery.R;
import com.example.testgallery.adapters.ImageAdapter;
import com.example.testgallery.adapters.SlideShowAdapter;
import com.example.testgallery.models.Image;
import com.example.testgallery.utility.GetAllPhotoFromGallery;

import java.util.List;

public class CreateAlbumActivity extends AppCompatActivity {
    private ImageView img_back_create_album;
    private ImageView menu_album;
    private EditText edtTitleAlbum;
    private RecyclerView rycAddAlbum;
    private List<Image> listImage;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_album);

        mappingControls();
        event();
    }

    private void event() {
        img_back_create_album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        setViewRyc();
        menu_album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                action();
            }
        });
    }

    private void action() {
        Intent intent = new Intent(this, SlideShowActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void setViewRyc() {
        listImage = GetAllPhotoFromGallery.getAllImageFromGallery(this);
        ImageAdapter imageAdapter = new ImageAdapter(this);
        imageAdapter.setData(listImage);
        rycAddAlbum.setLayoutManager(new GridLayoutManager(this, 4));
        rycAddAlbum.setAdapter(imageAdapter);
    }

    private void mappingControls() {
        img_back_create_album = findViewById(R.id.img_back_create_album);
        menu_album = findViewById(R.id.menu_album);
        edtTitleAlbum = findViewById(R.id.edtTitleAlbum);
        rycAddAlbum = findViewById(R.id.rycAddAlbum);
    }
}
