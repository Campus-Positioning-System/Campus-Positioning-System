package com.example.campus_positioning_system.Activitys;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.campus_positioning_system.Fragments.SettingsFragment;
import com.example.campus_positioning_system.R;

public class SettingsActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {

        System.out.println("On Create Settings Activity");
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(android.R.id.content, new SettingsFragment()).commit();
        }
    }

    @Override
    public void onBackPressed() {
        System.out.println("User wants to go back from Room list");
        System.out.println("Navigating from Room List back to Main");
        finish();
    }
}
