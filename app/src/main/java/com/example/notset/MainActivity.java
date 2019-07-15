package com.example.notset;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private ViewPager myViewPager;
    private TabLayout myTabLayout;
    private TabsAccessAdapter myTabsAccessAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar)findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Notset");

        myViewPager = (ViewPager)findViewById(R.id.main_tabs_pager);
        myTabsAccessAdapter = new TabsAccessAdapter(getSupportFragmentManager());
        myViewPager.setAdapter(myTabsAccessAdapter);

        myTabLayout = (TabLayout)findViewById(R.id.main_tabs);
        myTabLayout.setupWithViewPager(myViewPager);

    }

}
