package com.example.testgallery.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.testgallery.fragments.mainFragments.AlbumFragment;
import com.example.testgallery.fragments.mainFragments.FavoriteFragment;
import com.example.testgallery.fragments.mainFragments.PhotoFragment;
import com.example.testgallery.fragments.mainFragments.ShareFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {


    public ViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new PhotoFragment();
            case 1:
                return new AlbumFragment();
            case 2:
                return new ShareFragment();
            case 3:
                return new FavoriteFragment();
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }


}
