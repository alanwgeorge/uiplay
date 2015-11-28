package com.example.android.uiplay;


import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.example.android.uiplay.databinding.ActivityMain2Binding;
import com.example.android.uiplay.expandablelist.ItemFragment;
import com.example.android.uiplay.v1.AccountListFragment;
import com.example.android.uiplay.v5.ViewPagerFragment;

import timber.log.Timber;


public class MainActivity2 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static final String ARG_SECTION_NUMBER = "section_number";

    private ActivityMain2Binding binding;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Timber.d("onCreate()");
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main2);

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
        int position;

        switch (item.getItemId()) {
            case R.id.navigation_item_1:
                fragment = new AccountListFragment();
                title = getString(R.string.title_section1);
                position = 1;
                break;
            case R.id.navigation_item_2:
                fragment = new com.example.android.uiplay.v2.AccountListFragment();
                title = getString(R.string.title_section2);
                position = 2;
                break;
            case R.id.navigation_item_3:
                fragment = new com.example.android.uiplay.v3.AccountListFragment();
                title = getString(R.string.title_section3);
                position = 3;
                break;
            case R.id.navigation_item_4:
                fragment = new com.example.android.uiplay.v4.AccountListFragment();
                title = getString(R.string.title_section4);
                position = 4;
                break;
            case R.id.navigation_item_5:
                fragment = new ViewPagerFragment();
                title = getString(R.string.title_section5);
                position = 5;
                break;
            case R.id.navigation_item_6:
                fragment = new com.example.android.uiplay.v6.AccountListFragment();
                title = getString(R.string.title_section6);
                position = 6;
                break;
            case R.id.navigation_item_7:
                fragment = ItemFragment.newInstance();
                title = getString(R.string.title_section7);
                position = 7;
                break;
            default:
                fragment = new AccountListFragment();
                title = getString(R.string.title_section1);
                position = 1;
        }

        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, position + 1);
        fragment.setArguments(args);

        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();

        binding.drawerLayout.closeDrawer(GravityCompat.START);

        binding.collapsingToolbar.setTitle(title);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            binding.collapsingToolbar.setExpandedTitleColor(getResources().getColor(android.R.color.transparent, this.getTheme()));
        } else {
            //noinspection deprecation
            binding.collapsingToolbar.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
        }

        item.setChecked(true);

        return true;
    }
}
