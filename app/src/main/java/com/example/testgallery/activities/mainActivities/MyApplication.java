package com.example.testgallery.activities.mainActivities;

import android.app.Application;

import com.example.testgallery.activities.mainActivities.data_favor.DataLocalManager;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DataLocalManager.init(getApplicationContext());
    }
}
