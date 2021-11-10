package com.example.testgallery.activities.mainActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;

import com.example.testgallery.R;
import com.example.testgallery.adapters.SlideShowAdapter;
import com.example.testgallery.models.Image;
import com.example.testgallery.utility.GetAllPhotoFromGallery;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class SlideShowActivity extends AppCompatActivity {
    private SliderView sliderView;
    private ImageView img_back_slide_show;
    private Toolbar toolbar_slide;
    private List<SliderAnimations> effect = new ArrayList<SliderAnimations>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_show);

        mappingControls();
        event();

    }

    private void event() {
        addListAnim();
        setUpSlider(0);
        setUpToolBar();
        img_back_slide_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void setUpToolBar() {
        toolbar_slide.inflateMenu(R.menu.menu_effect_slide);
        toolbar_slide.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                switch (id){
                    case R.id.menu_effect1:
                        setUpSlider(0);
                        break;
                    case R.id.menu_effect2:
                        setUpSlider(1);
                        break;
                    case R.id.menu_effect3:
                        setUpSlider(2);
                        break;
                }
                return true;
            }
        });
    }

    private void addListAnim() {
        effect.add(SliderAnimations.HORIZONTALFLIPTRANSFORMATION);
        effect.add(SliderAnimations.ZOOMOUTTRANSFORMATION);
        effect.add(SliderAnimations.DEPTHTRANSFORMATION);
    }

    private void setUpSlider(int i) {
        List<Image> imageList = GetAllPhotoFromGallery.getAllImageFromGallery(getApplicationContext());
        SlideShowAdapter slideShowAdapter = new SlideShowAdapter();
        slideShowAdapter.setData(imageList);
        sliderView.setSliderAdapter(slideShowAdapter);
        sliderView.startAutoCycle();
        sliderView.setSliderTransformAnimation(effect.get(i));

    }

    private void mappingControls() {
        sliderView = findViewById(R.id.sliderView);
        img_back_slide_show = findViewById(R.id.img_back_slide_show);
        toolbar_slide = findViewById(R.id.toolbar_slide);
    }
}