package com.example.testgallery.fragments.mainFragments;

import static androidx.core.content.ContextCompat.getSystemService;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatDelegate;

import android.preference.Preference;
import android.preference.SwitchPreference;
import android.widget.Toast;

import com.example.testgallery.R;

public class SettingsFragment extends PreferenceFragment {


    public android.preference.SwitchPreference switchNight;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.setting);
        onResume();
    }


    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("nightMode")) {
            boolean nightMode = sharedPreferences.getBoolean("nightMode", false);
            if (nightMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        switchNight = (SwitchPreference) findPreference("nightMode");

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        boolean nightMode = preferences.getBoolean("nightMode", false);

        if (nightMode) {
            Toast.makeText(getActivity(),"Night mode is on", Toast.LENGTH_SHORT).show();
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            Toast.makeText(getActivity(),"Night mode is off", Toast.LENGTH_SHORT).show();
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }
}