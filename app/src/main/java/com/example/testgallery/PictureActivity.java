package com.example.testgallery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class PictureActivity extends AppCompatActivity {
    ImageView imageView;
    ImageView imgViewBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_picture);

        imageView = findViewById(R.id.imgPicture);
        imgViewBack = findViewById(R.id.imgViewBack);
        Intent intent = getIntent();
        int src = intent.getIntExtra("imgSrc", 0);
        imageView.setImageResource(src);

        imgViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}