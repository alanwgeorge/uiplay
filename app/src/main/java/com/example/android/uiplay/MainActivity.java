package com.example.android.uiplay;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.android.uiplay.v5.ViewPagerFragment;


public class MainActivity extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {
    private static final String TAG = "MainActivity";
    public static final String ARG_SECTION_NUMBER = "section_number";

    private NavigationDrawerFragment navigationDrawerFragment;
    private CharSequence title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        title = getTitle();

        navigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment;

        switch (position) {
            case 0:
                fragment = new com.example.android.uiplay.v1.AccountListFragment();
                break;
            case 1:
                fragment = new com.example.android.uiplay.v2.AccountListFragment();
                break;
            case 2:
                fragment = new com.example.android.uiplay.v3.AccountListFragment();
                break;
            case 3:
                fragment = new com.example.android.uiplay.v4.AccountListFragment();
                break;
            case 4:
                fragment = new ViewPagerFragment();
                break;
            case 5:
                fragment = new com.example.android.uiplay.v6.AccountListFragment();
                break;
            default:
                fragment = new com.example.android.uiplay.v1.AccountListFragment();
        }

        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, position + 1);
        fragment.setArguments(args);

        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                title = getString(R.string.title_section1);
                break;
            case 2:
                title = getString(R.string.title_section2);
                break;
            case 3:
                title = getString(R.string.title_section3);
                break;
            case 4:
                title = getString(R.string.title_section4);
                break;
            case 5:
                title = getString(R.string.title_section5);
                break;
            case 6:
                title = getString(R.string.title_section6);
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(title);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!navigationDrawerFragment.isDrawerOpen()) {
            getMenuInflater().inflate(R.menu.global, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
