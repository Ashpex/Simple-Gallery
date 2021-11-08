package com.example.testgallery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class PictureActivity extends AppCompatActivity {
    ImageView imageView;
    ImageView imgViewBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);

        imageView = findViewById(R.id.imgPicture);
        imgViewBack = findViewById(R.id.imgViewBack);
        Intent intent = getIntent();
        String thumb = intent.getStringExtra("imgSrc");
        Glide.with(this).load(thumb).into(imageView);

//        imageView.setImageResource(src);

        imgViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}