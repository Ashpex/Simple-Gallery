package com.example.testgallery.activities.mainActivities;

import android.Manifest;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import com.example.testgallery.R;
import com.example.testgallery.adapters.ViewPagerAdapter;
import com.example.testgallery.fragments.subFragments.ItemAlbumFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    ViewPager2 viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar();
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_nav);
        viewPager = findViewById(R.id.view_pager);
        setUpViewPager();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                removeFragment();
                switch (item.getItemId()) {
                    case R.id.picture:

                        viewPager.setCurrentItem(0);
                        break;

                    case R.id.search:
                        viewPager.setCurrentItem(1);
                        break;

                    case R.id.share:
                        viewPager.setCurrentItem(2);
                        break;

                    case R.id.gallery:
                        viewPager.setCurrentItem(3);
                        break;
                }
                return true;
            }
        });

        requestPermission();
    }

    private void setUpViewPager() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), getLifecycle());
        viewPager.setAdapter(viewPagerAdapter);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
                removeFragment();
            }

            @Override
            public void onPageSelected(int position) {
                removeFragment();
                switch (position){
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.picture).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.search).setChecked(true);
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.share).setChecked(true);
                        break;
                    case 3:
                        bottomNavigationView.getMenu().findItem(R.id.gallery).setChecked(true);
                        break;
                }
            }
        });
    }

    private void requestPermission(){
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
//                Toast.makeText(MainActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(MainActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }
        };

        TedPermission.create()
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
                .check();
    }

    public  void removeFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        ItemAlbumFragment fragment = (ItemAlbumFragment) getSupportFragmentManager().findFragmentByTag("fragment_list_album");
        if(fragment != null) {
            fragmentTransaction.setCustomAnimations(R.anim.anim_open_fragment, R.anim.anim_close_fragment).remove(fragment);
            fragmentTransaction.commit();
        }
    }
}