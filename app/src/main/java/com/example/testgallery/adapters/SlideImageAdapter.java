package com.example.testgallery.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.testgallery.R;
import com.example.testgallery.utility.PictureInterface;
import com.github.chrisbanes.photoview.PhotoView;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;

public class SlideImageAdapter extends PagerAdapter {
    private ArrayList<String> imageListThumb;
    private ArrayList<String> imageListPath;
    private PictureInterface pictureInterface;
    private Context context;
    private ImageView imgPicture;
    private boolean flag = false;

    public void setPictureInterface(PictureInterface pictureInterface) {
        this.pictureInterface = pictureInterface;
    }

    public void setData(ArrayList<String> imageListThumb, ArrayList<String> imageListPath) {
        this.imageListPath = imageListPath;
        this.imageListThumb = imageListThumb;
        notifyDataSetChanged();
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return imageListPath.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {

        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_slide_picture, container, false);
        imgPicture = view.findViewById(R.id.imgPicture);
        Glide.with(context).load(imageListPath.get(position)).into(imgPicture);
        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        imgPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pictureInterface.actionShow(flag);
                if(flag)
                    flag = false;
                else
                    flag = true;
            }
        });
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ViewPager viewPager = (ViewPager) container;
        View view = (View) object;
        viewPager.removeView(view);
    }
}

