package com.example.testgallery.activities.mainActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceManager;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.ViewGroup;
import android.app.UiModeManager;

import com.example.testgallery.R;

public class SettingsActivity extends PreferenceActivity {
    private UiModeManager uiModeManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setting);
        uiModeManager = (UiModeManager) getSystemService(UI_MODE_SERVICE);
        LoadSettings();
    }

    private void LoadSettings(){
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        boolean checkNight = sp.getBoolean("nightMode",false);
        if(checkNight){
            uiModeManager.setNightMode(UiModeManager.MODE_NIGHT_YES);
        }
        else{
            uiModeManager.setNightMode(UiModeManager.MODE_NIGHT_NO);
        }



    }

}