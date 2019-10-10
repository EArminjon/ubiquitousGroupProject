package com.example.enguerrand_robin_benjamin.admin;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.enguerrand_robin_benjamin.FirebaseDatabaseHelper;
import com.example.enguerrand_robin_benjamin.R;
import com.example.enguerrand_robin_benjamin.model.User;
import com.google.gson.Gson;

import es.dmoral.toasty.Toasty;

public class UserViewActivity extends AppCompatActivity {
    User user;
    boolean delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view);
        ActionBar bar = getSupportActionBar();
        bar.setTitle("User");

        Gson gson = new Gson();
        user = gson.fromJson(getIntent().getStringExtra("user"), User.class);
        delete = getIntent().getBooleanExtra("delete", false);


        if (user.scores != null && !user.scores.isEmpty()) {
            QuizzScoreListAdapter productListViewAdapter = new QuizzScoreListAdapter(user.scores);
            ListView listView = findViewById(R.id.quizzScoreList);

//            listView.setOnItemClickListener((adapter, v, position, id) -> {
//                Quizz quizz = (Quizz) adapter.getItemAtPosition(position);
//
//                Intent intent = new Intent(this, EditQuizzActivity.class);
//                Gson gson1 = new Gson();
//                String myJson = gson1.toJson(quizz);
//                intent.putExtra("quizz", myJson);
//                startActivity(intent);
//            });

            listView.setAdapter(productListViewAdapter);
        }
    }

    public void deleteThisUser(View view) {
        if (!delete) {
            Toasty.error(this, getString(R.string.delete_self_warning), Toast.LENGTH_SHORT, true).show();
            return;
        }
        FirebaseDatabaseHelper helper = new FirebaseDatabaseHelper();
        helper.deleteThisUser(this.user, item -> finish(), item -> {
            String msg = (String) item;
            Toasty.error(this, msg, Toast.LENGTH_SHORT, true).show();
        });
    }
}
