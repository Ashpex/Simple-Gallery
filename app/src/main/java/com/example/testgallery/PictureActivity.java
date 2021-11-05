package com.example.testgallery;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class PictureActivity extends AppCompatActivity {
    ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);

        imageView = findViewById(R.id.imgPicture);
        Intent intent = getIntent();
        int src = intent.getIntExtra("imgSrc", 0);
        imageView.setImageResource(src);
    }
}