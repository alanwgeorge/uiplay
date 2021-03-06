package com.example.android.uiplay;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.example.android.uiplay.databinding.ActivityMainBinding;
import com.example.android.uiplay.expandablelist.ItemFragment;
import com.example.android.uiplay.expandabletoolbar.DetailActivity;
import com.example.android.uiplay.v5.ViewPagerFragment;

import timber.log.Timber;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Timber.d("onCreate()");
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        drawerLayout = binding.drawerLayout;

        setSupportActionBar(binding.toolbar);
        binding.navigationDrawer.setNavigationItemSelectedListener(this);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, binding.drawerLayout, binding.toolbar, R.string.openDrawer, R.string.closeDrawer){
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
                syncState();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
                syncState();
            }
        };

        binding.drawerLayout.setDrawerListener(actionBarDrawerToggle);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } else {
            Timber.e("action bar is null!");
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment;
        String title;

        switch (item.getItemId()) {
            case R.id.navigation_item_1:
                fragment = new com.example.android.uiplay.v1.AccountListFragment();
                title = getString(R.string.title_section1);
                break;
            case R.id.navigation_item_2:
                fragment = new com.example.android.uiplay.v2.AccountListFragment();
                title = getString(R.string.title_section2);
                break;
            case R.id.navigation_item_3:
                fragment = new com.example.android.uiplay.v3.AccountListFragment();
                title = getString(R.string.title_section3);
                break;
            case R.id.navigation_item_4:
                fragment = new com.example.android.uiplay.v4.AccountListFragment();
                title = getString(R.string.title_section4);
                break;
            case R.id.navigation_item_5:
                fragment = new ViewPagerFragment();
                title = getString(R.string.title_section5);
                break;
            case R.id.navigation_item_6:
                fragment = new com.example.android.uiplay.v6.AccountListFragment();
                title = getString(R.string.title_section6);
                break;
            case R.id.navigation_item_7:
                fragment = ItemFragment.newInstance();
                title = getString(R.string.title_section7);
                break;
            case R.id.navigation_item_8:
                drawerLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        drawerLayout.closeDrawer(GravityCompat.START);
                    }
                }, 300);
                Intent intent = new Intent(this, DetailActivity.class);
                ActivityCompat.startActivity(this, intent, null);
                return true;
            default:
                fragment = new com.example.android.uiplay.v1.AccountListFragment();
                title = getString(R.string.title_section1);
        }

        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();

        drawerLayout.closeDrawer(GravityCompat.START);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(title);

        return true;
    }
}
