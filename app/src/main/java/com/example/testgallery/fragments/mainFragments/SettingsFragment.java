package com.example.testgallery.fragments.mainFragments;

import static androidx.core.content.ContextCompat.getSystemService;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatDelegate;

import android.preference.Preference;
import android.preference.SwitchPreference;
import android.util.Log;

import com.example.testgallery.R;

import java.util.Locale;

public class SettingsFragment extends PreferenceFragment {


    public android.preference.SwitchPreference switchNight;
    public android.preference.SwitchPreference switchLanguage;
    public static SharedPreferences prefs;
    public String selectedLanguage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.setting);
        switchNight = (SwitchPreference) findPreference("nightMode");
        switchLanguage = (SwitchPreference) findPreference("changeLanguage");

        switchNight.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {

                boolean checked = ((SwitchPreference) preference)
                        .isChecked();
                if(checked){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
                else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
                return true;
            }
        });

        switchLanguage.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                boolean checked = ((SwitchPreference) preference)
                        .isChecked();


                if(checked){
                    selectedLanguage = "vi-rVN";
                }
                else{
                    selectedLanguage = "en";
                }

                Log.d("Simple-Gallery","SettingsFragment onClickListener()");
                Locale locale = new Locale(selectedLanguage);
                Locale.setDefault(locale);
                Resources res = getResources();
                Configuration config = res.getConfiguration();
                config.setLocale(locale);
                getContext().createConfigurationContext(config);
                return true;
            }
        });

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

        if(key.equals("changeLanguage")){
            boolean changeLanguage = sharedPreferences.getBoolean("changeLanguage", false);

            if(changeLanguage){
                selectedLanguage = "vi-rVN";
            }

            else{
                selectedLanguage = "en";
            }
            Log.d("Simple-Gallery","SettingsFragment onPreferenceChange()");
            Locale locale = new Locale(selectedLanguage);
            Locale.setDefault(locale);
            Resources res = getResources();
            Configuration config = res.getConfiguration();
            config.setLocale(locale);
            getContext().createConfigurationContext(config);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        switchNight = (SwitchPreference) findPreference("nightMode");

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        boolean nightMode = preferences.getBoolean("nightMode", false);

        if (nightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        switchLanguage = (SwitchPreference) findPreference("changeLanguage");
        boolean changeLanguage = preferences.getBoolean("changeLanguage", false);

        if(changeLanguage){
            selectedLanguage = "vi-rVN";
        }

        else{
            selectedLanguage = "en";
        }
        Log.d("Simple-Gallery","SettingsFragment onResume()");
        Locale locale = new Locale(selectedLanguage);
        Locale.setDefault(locale);
        Resources res = getContext().getResources();
        Configuration config = res.getConfiguration();
        config.setLocale(locale);
        config.locale = locale;
        getContext().createConfigurationContext(config);

    }


}