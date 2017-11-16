package com.majd.technow.ui;

import android.content.ContentValues;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;


import com.majd.technow.R;
import com.majd.technow.adapters.ViewPagerAdapter;

public class ArticlesActicity extends AppCompatActivity {
    Toolbar toolbar;
    ViewPager viewPager;
    TabLayout tabLayout;
    String title;
    String websiteName;
    TopFragment topFragment;
    RecentFragment recentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles);
        title = getIntent().getStringExtra("title");
        websiteName = getIntent().getStringExtra("websiteName");
        topFragment = new TopFragment();
        recentFragment = new RecentFragment();
        topFragment.setWebsite(websiteName);
        recentFragment.setWebsite(websiteName);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        //defines the number of tabs by setting up the appropriate fragments and tab name
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(topFragment, "Top Articles");
        adapter.addFragment(recentFragment, "recently Articles");
        viewPager.setAdapter(adapter);
    }

    public void saveArticle(View view) {
        ImageButton imageButton = (ImageButton) findViewById(R.id.imageButton);
        imageButton.setBackgroundColor(Color.parseColor("#ffffff"));
        if (topFragment.isVisible()) {

        } else if (recentFragment.isVisible()) {

        }


    }


}
