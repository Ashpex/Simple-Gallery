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
import android.view.View;
import android.view.ViewGroup;
import android.app.UiModeManager;

import com.example.testgallery.R;
import com.example.testgallery.fragments.mainFragments.SettingsFragment;

public class SettingsActivity extends AppCompatActivity {

    private Toolbar toolbar_settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        toolbarEvents();

        getFragmentManager().beginTransaction()
                .replace(R.id.settingsFragment, new SettingsFragment())
                .commit();

    }

    private void toolbarEvents(){
        toolbar_settings = (Toolbar)findViewById(R.id.toolbar_settings);
        toolbar_settings.setNavigationIcon(R.drawable.abc_ic_ab_back_material);
        toolbar_settings.setTitle("Settings");
        toolbar_settings.inflateMenu(R.menu.menu_top_settings);
        toolbar_settings.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}