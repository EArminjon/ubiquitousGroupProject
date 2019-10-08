package com.example.enguerrand_robin_benjamin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.enguerrand_robin_benjamin.model.User;
import com.google.gson.Gson;

import es.dmoral.toasty.Toasty;

public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("Login");
    }

    public void doLogin(View view) {
        FirebaseDatabaseHelper helper = new FirebaseDatabaseHelper();
        EditText login = findViewById(R.id.login);
        EditText password = findViewById(R.id.password);
        String loginStr = login.getText().toString(), passwordStr = password.getText().toString();
        if (loginStr.isEmpty() || passwordStr.isEmpty())
            Toasty.error(this, "Fill the form please", Toast.LENGTH_SHORT, true).show();
        else
            helper.login(new User(loginStr, passwordStr, false),
                    (item) -> {
                        User user = (User) item;
                        Intent intent;
                        if (user.admin) {
                            intent = new Intent(this, HomeAdminActivity.class);
                        } else {
                            intent = new Intent(this, HomeActivity.class);
                        }
                        Gson gson = new Gson();
                        String myJson = gson.toJson(item);
                        intent.putExtra("user", myJson);
                        startActivity(intent);
                    },
                    (item) -> {
                        String msg = (String) item;
                        Toasty.error(this, msg, Toast.LENGTH_SHORT, true).show();
                    }
            );
    }
}
