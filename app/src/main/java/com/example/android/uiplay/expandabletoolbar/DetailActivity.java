package com.example.android.uiplay.expandabletoolbar;

import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MenuItem;
import android.view.View;

import com.example.android.uiplay.R;
import com.example.android.uiplay.databinding.ActivityDetailBinding;

public class DetailActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private ActivityDetailBinding binding;
    private DetailsController detailsController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DetailAdapter detailAdapter = new DetailAdapter();
        detailsController = new DetailsController(detailAdapter);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        binding.setController(detailsController);

        setSupportActionBar(binding.toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
        }

        final DrawerArrowDrawable arrowDrawable = new DrawerArrowDrawable(this);
        final DrawerArrowDrawable hamburgerDrawable = new DrawerArrowDrawable(this);

        arrowDrawable.setProgress(1.0f);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            arrowDrawable.setColor(getResources().getColor(android.R.color.primary_text_dark, getTheme()));
            hamburgerDrawable.setColor(getResources().getColor(android.R.color.primary_text_dark, getTheme()));
        } else {
            //noinspection deprecation
            arrowDrawable.setColor(getResources().getColor(android.R.color.primary_text_dark));
            //noinspection deprecation
            hamburgerDrawable.setColor(getResources().getColor(android.R.color.primary_text_dark));
        }

        hamburgerDrawable.setDirection(DrawerArrowDrawable.ARROW_DIRECTION_END);
        binding.backArrow.setImageDrawable(arrowDrawable);
        binding.hamburgerIcon.setImageDrawable(hamburgerDrawable);

        binding.navigationDrawer.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, binding.drawerLayout, binding.toolbar, R.string.openDrawer, R.string.closeDrawer) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                hamburgerDrawable.setProgress(slideOffset);
            }
        };

        binding.drawerLayout.setDrawerListener(actionBarDrawerToggle);
        binding.collapsingToolbar.setTitle("Alan's Checking");
        binding.hamburgerIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleNavigationDrawer();
            }
        });
        binding.backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.recyclerview.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerview.setItemAnimator(new DefaultItemAnimator());
        detailAdapter.registerAdapterDataObserver(new DetailAdapterDataObserver());
        binding.recyclerview.setAdapter(detailAdapter);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        detailsController.addDetail();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        item.setChecked(true);
        binding.drawerLayout.closeDrawer(GravityCompat.END);
        return true;
    }

    public void toggleNavigationDrawer() {
        if (binding.drawerLayout.isDrawerOpen(binding.navigationDrawer)) {
            binding.drawerLayout.closeDrawer(binding.navigationDrawer);
        } else {
            binding.drawerLayout.openDrawer(binding.navigationDrawer);
        }
    }
}
