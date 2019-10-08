package com.example.enguerrand_robin_benjamin;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.enguerrand_robin_benjamin.model.Quizz;
import com.example.enguerrand_robin_benjamin.model.User;
import com.google.gson.Gson;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ActionBar bar = getSupportActionBar();
        bar.setTitle("Home");

        Gson gson = new Gson();
        User user = gson.fromJson(getIntent().getStringExtra("user"), User.class);


        FirebaseDatabaseHelper helper = new FirebaseDatabaseHelper();
        helper.getAllQuizz(param -> {
            List<Quizz> items = (List<Quizz>) param;
            System.out.println(param);
            QuizzListAdapter productListViewAdapter = new QuizzListAdapter(items);
            ListView listView = findViewById(R.id.quizzList);
            listView.setAdapter(productListViewAdapter);
        }, System.out::println);
    }
}
