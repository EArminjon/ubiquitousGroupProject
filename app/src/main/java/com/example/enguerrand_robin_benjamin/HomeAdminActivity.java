package com.example.enguerrand_robin_benjamin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.enguerrand_robin_benjamin.model.Quizz;
import com.example.enguerrand_robin_benjamin.model.User;
import com.google.gson.Gson;

import java.util.List;

public class HomeAdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        ActionBar bar = getSupportActionBar();
        bar.setTitle("Home - Admin");

        Gson gson = new Gson();
        User user = gson.fromJson(getIntent().getStringExtra("user"), User.class);


        FirebaseDatabaseHelper helper = new FirebaseDatabaseHelper();
        helper.getAllQuizz(param -> {
            List<Quizz> items = (List<Quizz>) param;
            QuizzListAdapter productListViewAdapter = new QuizzListAdapter(items);
            ListView listView = findViewById(R.id.quizzList);

            listView.setOnItemClickListener((adapter, v, position, id) -> {
                Quizz quizz = (Quizz) adapter.getItemAtPosition(position);

                Intent intent = new Intent(HomeAdminActivity.this, EditQuizzActivity.class);
                Gson gson1 = new Gson();
                String myJson = gson1.toJson(quizz);
                intent.putExtra("quizz", myJson);
                startActivity(intent);
            });

            listView.setAdapter(productListViewAdapter);
        }, System.out::println);
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

    public void doCreateNewQuizz(View view) {
        Intent intent = new Intent(this, CreateQuizzActivity.class);
        startActivity(intent);
    }
}


//        Quizz quizz = new Quizz("Les jeux",
//                Arrays.asList(
//                        new QuizzQuestion("aimes-tu mario ?", Arrays.asList("oui", "non")),
//                        new QuizzQuestion("aimes-tu pitch ?", Arrays.asList("oui", "non")),
//                        new QuizzQuestion("aimes-tu luigi ?", Arrays.asList("oui", "non")),
//                        new QuizzQuestion("aimes-tu bob ?", Arrays.asList("oui", "non")),
//                        new QuizzQuestion("aimes-tu gotaga ?", Arrays.asList("oui", "non"))
//                )
//        );
//        helper.insertThisQuizz(quizz, param -> {
//            System.out.println(param);
//        }, param -> {
//            System.out.println(param);
//        });