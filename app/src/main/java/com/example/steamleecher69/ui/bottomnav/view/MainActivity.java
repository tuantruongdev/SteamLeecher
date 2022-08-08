package com.example.steamleecher69.ui.bottomnav.view;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.steamleecher69.R;
import com.example.steamleecher69.ui.main.view.MainFragment;
import com.example.steamleecher69.ui.saved.view.SavedFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private Fragment mainFragment, detailFragment, activeFragment;
    private FragmentManager fragmentManager;
    private BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigation = findViewById(R.id.navigation);
        mainFragment = new MainFragment();
        detailFragment = new SavedFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.frame_container, mainFragment, "Main")
                .add(R.id.frame_container, detailFragment, "Detail")
                .commit();
        fragmentManager.beginTransaction().hide(mainFragment).hide(detailFragment).commit();
        activeFragment = mainFragment;
        loadFragment(fragmentManager, mainFragment);

        bind();
    }


    private void bind() {
        navigation.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    getSupportActionBar().setTitle(R.string.app_title);
                    loadFragment(fragmentManager, mainFragment);
                    return true;
                case R.id.navigation_details:
                    getSupportActionBar().setTitle(R.string.wishlist);
                    loadFragment(fragmentManager, detailFragment);
                    return true;
            }
            return false;
        });
    }

    private void loadFragment(FragmentManager fm, Fragment targetFragment) {
        Log.d(TAG, "loadFragment: switch fragment");
        fm.beginTransaction().hide(activeFragment).show(targetFragment).commit();
        activeFragment = targetFragment;
    }
}
