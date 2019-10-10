package com.example.enguerrand_robin_benjamin.user;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.enguerrand_robin_benjamin.R;
import com.example.enguerrand_robin_benjamin.model.Quizz;
import com.example.enguerrand_robin_benjamin.model.User;
import com.google.gson.Gson;

public class PlayQuizz extends AppCompatActivity {
    Quizz quizz;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_quizz);

        Gson gson = new Gson();
        quizz = gson.fromJson(getIntent().getStringExtra("quizz"), Quizz.class);
        user = gson.fromJson(getIntent().getStringExtra("user"), User.class);

        ActionBar bar = getSupportActionBar();
        bar.setTitle("Quizz: " + quizz.name);
    }
}
