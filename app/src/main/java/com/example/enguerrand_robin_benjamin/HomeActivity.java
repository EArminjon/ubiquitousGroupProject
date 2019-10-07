package com.example.enguerrand_robin_benjamin;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.enguerrand_robin_benjamin.model.User;
import com.google.gson.Gson;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ActionBar bar = getSupportActionBar();
        bar.setTitle("Home");

        Gson gson = new Gson();
        User user = gson.fromJson(getIntent().getStringExtra("user"), User.class);
        System.out.println(user.toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.mybutton) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
