package com.example.enguerrand_robin_benjamin.admin;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.enguerrand_robin_benjamin.R;
import com.example.enguerrand_robin_benjamin.model.User;
import com.google.gson.Gson;

public class HomeAdminActivity extends AppCompatActivity {
    User user;

    public void doCreateNewQuizz(View view) {
        Intent intent = new Intent(this, CreateQuizzActivity.class);
        startActivity(intent);
    }

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter pagerAdapter;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Gson gson = new Gson();
        outState.putString("user", gson.toJson(user));
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Gson gson = new Gson();
        user = gson.fromJson(savedInstanceState.getString("user"), User.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        ActionBar bar = getSupportActionBar();
        bar.setTitle("Home - Admin");

        if (savedInstanceState == null) {
            Gson gson = new Gson();
            user = gson.fromJson(getIntent().getStringExtra("user"), User.class);
        } else {
            Gson gson = new Gson();
            user = gson.fromJson(savedInstanceState.getString("user"), User.class);
        }

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = findViewById(R.id.pager);
        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(), user);
        mPager.setAdapter(pagerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.mybutton) finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0)
            super.onBackPressed();
        else
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
    }
}

class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
    private static final int NUM_PAGES = 2;
    private User currentUser;

    ScreenSlidePagerAdapter(FragmentManager fm, User currentUser) {
        super(fm);

        this.currentUser = currentUser;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0)
            return new QuizzFragment();
        else {
            Fragment fragment = new UserFragment();
            Bundle args = new Bundle();
            Gson gson = new Gson();
            String myJson = gson.toJson(currentUser);
            args.putString("user", myJson);
            fragment.setArguments(args);
            return fragment;
        }
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }
}