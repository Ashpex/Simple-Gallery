package com.example.testgallery.activities.mainActivities.data_favor;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

public class MySharedPreferences {
    private static final String MY_SHARED_PREFERENCES="MY_SHARED_PREFERENCES";
    private Context context;

    public MySharedPreferences(Context context) {
        this.context = context;
    }



    public Set<String> getStringSet(String key){
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_SHARED_PREFERENCES,Context.MODE_PRIVATE);

        return sharedPreferences.getStringSet(key,new HashSet<String>());
    }

    public void deleteListFavor (String key){
        SharedPreferences settings = context.getSharedPreferences(MY_SHARED_PREFERENCES, Context.MODE_PRIVATE);
        settings.edit().remove("PREF_IMG_FAVOR").commit();
    }

    public void putStringSet(String key, Set value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_SHARED_PREFERENCES,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet(key,value);
        editor.apply();
    }
}
