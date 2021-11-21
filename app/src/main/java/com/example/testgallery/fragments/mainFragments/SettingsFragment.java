package com.example.testgallery.fragments.mainFragments;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.UiModeManager;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import androidx.appcompat.widget.Toolbar;
import androidx.preference.Preference;

import com.example.testgallery.R;

public class SettingsFragment extends PreferenceFragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.setting);
        //LoadSettings();
    }

    private void LoadSettings(){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getContext());
        boolean checkNight = sp.getBoolean("nightMode",false);
        if(checkNight){
        }
        else{
        }


    }
}