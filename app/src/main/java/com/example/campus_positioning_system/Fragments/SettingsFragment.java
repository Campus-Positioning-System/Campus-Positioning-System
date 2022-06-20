package com.example.campus_positioning_system.Fragments;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;

import android.content.Intent;
import android.os.Bundle;

import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.preference.PreferenceFragmentCompat;

import com.example.campus_positioning_system.Activitys.MainActivity;
import com.example.campus_positioning_system.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }

    public void onBackPressed() {
        System.out.println("User wants to go back from Settings");

        NavHostFragment navHostFragment = (NavHostFragment) MainActivity.getSupportFragmentManagerMain().findFragmentById(R.id.nav_host_fragment);
        NavController navController = navHostFragment.getNavController();
        navController.navigate(R.id.mainFragment);
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        //MainActivity.setOnlyNavigateOnceTrue();

        System.out.println("Navigating from Room List back to Main");
    }
}