package com.complaints.jd.h2h;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainTabActivity extends AppCompatActivity implements  Bidding.OnFragmentInteractionListener{
    ViewPager viewPager;
    TabLayout tabLayout;
    Boolean value;
    String comid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tab);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Crop Production Viewer");
        setSupportActionBar(toolbar);
        SharedPreferences sharedPreferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
        comid = sharedPreferences.getString("COMID",null);
        value = sharedPreferences.getBoolean("ISGUEST",false);
       // Toast.makeText(getApplicationContext(),comid,Toast.LENGTH_SHORT).show();
       // Toast.makeText(getApplicationContext(),value.toString(),Toast.LENGTH_SHORT).show();

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        if(value) {

            viewPagerAdapter.addFragment(new CropProductionFragment(comid), "Crop Production");
            viewPagerAdapter.addFragment(new UserAccFragment(comid), "User Account");
        }
        else {
            viewPagerAdapter.addFragment(new CropProductionFragment(comid), "Crop Production");
            //viewPagerAdapter.addFragment(new Bidding(),"My Bidding");
            viewPagerAdapter.addFragment(new UserAccFragment(comid), "User Account");
        }

        viewPager.setAdapter(viewPagerAdapter);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();

        return super.onOptionsItemSelected(item);
    }

}
