package com.example.testgallery.activities.mainActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.testgallery.R;
import com.example.testgallery.adapters.SlideShowAdapter;
import com.example.testgallery.models.Image;
import com.example.testgallery.utility.GetAllPhotoFromGallery;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.List;

public class SlideShowActivity extends AppCompatActivity {
    private SliderView sliderView;
    private ImageView img_back_slide_show;
    private ImageView anim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_show);

        mappingControls();
        event();

    }

    private void event() {
        addListAnim();
        setUpSlider();

        img_back_slide_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        anim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sliderView.setSliderTransformAnimation(SliderAnimations.GATETRANSFORMATION);
            }
        });
    }

    private void addListAnim() {
    }

    private void setUpSlider() {
        List<Image> imageList = GetAllPhotoFromGallery.getAllImageFromGallery(getApplicationContext());
        SlideShowAdapter slideShowAdapter = new SlideShowAdapter();
        slideShowAdapter.setData(imageList);
        sliderView.setSliderAdapter(slideShowAdapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.ZOOMOUTTRANSFORMATION);
        sliderView.startAutoCycle();
    }

    private void mappingControls() {
        sliderView = findViewById(R.id.sliderView);
        img_back_slide_show = findViewById(R.id.img_back_slide_show);
        anim = findViewById(R.id.anim);
    }
}