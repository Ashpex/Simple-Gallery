package com.ashpex.simplegallery;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapter extends FragmentStateAdapter {


    public ViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new PictureFragment();
            case 1:
                return new SearchFragment();
            case 2:
                return new ShareFragment();
            case 3:
                return new GalleryFragment();
            default:
                return new PictureFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
