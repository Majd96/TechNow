package com.majd.technow.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.majd.technow.AnalyticsApplication;
import com.majd.technow.R;
import com.majd.technow.adapters.ViewPagerAdapter;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    ViewPager viewPager;
    TabLayout tabLayout;
    Tracker mTracker;
    final static private String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
    }

    private void setupViewPager(ViewPager viewPager) {
        //defines the number of tabs by setting up the appropriate fragments and tab name
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ChoiceFragment(), "Main");
        adapter.addFragment(new SavedArticlesFragment(), "Saved Articles");
        viewPager.setAdapter(adapter);
    }

    public void theVegre(View view) {
        Intent intent = new Intent(this, ArticlesActicity.class);
        intent.putExtra("title", "TheVerge");
        intent.putExtra("websiteName", "verge");
        startActivity(intent);
    }


    public void techRadar(View view) {
        Intent intent = new Intent(this, ArticlesActicity.class);
        intent.putExtra("title", "TechRadar");
        intent.putExtra("websiteName", "TechRadar");
        startActivity(intent);
    }

    public void engadget(View view) {
        Intent intent = new Intent(this, ArticlesActicity.class);
        intent.putExtra("title", "Engadget");
        intent.putExtra("websiteName", "Engadget");
        startActivity(intent);
    }

    public void techCrunch(View view) {
        Intent intent = new Intent(this, ArticlesActicity.class);
        intent.putExtra("title", "TechCrunch");
        intent.putExtra("websiteName", "TechCrunch");
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "Setting screen name: " + "MainActivity");
        mTracker.setScreenName("MainActivity");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }
}
