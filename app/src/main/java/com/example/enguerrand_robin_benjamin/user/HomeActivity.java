package com.example.enguerrand_robin_benjamin.user;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.enguerrand_robin_benjamin.FirebaseDatabaseHelper;
import com.example.enguerrand_robin_benjamin.QuizzListAdapter;
import com.example.enguerrand_robin_benjamin.R;
import com.example.enguerrand_robin_benjamin.admin.EditQuizzActivity;
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

            listView.setOnItemClickListener((adapter, v, position, id) -> {
                Quizz quizz = (Quizz) adapter.getItemAtPosition(position);

                Intent intent = new Intent(this, PlayQuizz.class);
                Gson gson1 = new Gson();
                String myJson = gson1.toJson(quizz);
                intent.putExtra("quizz", myJson);
                myJson = gson1.toJson(user);
                intent.putExtra("user", myJson);
                startActivity(intent);
            });


            listView.setAdapter(productListViewAdapter);
        }, System.out::println);
    }
}
