package com.example.enguerrand_robin_benjamin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

import com.example.enguerrand_robin_benjamin.model.User;

import es.dmoral.toasty.Toasty;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().setTitle("Register");

        findViewById(R.id.submit).setVisibility(View.VISIBLE);
        findViewById(R.id.progressBar).setVisibility(View.GONE);
    }

    public void doRegister(View view) {
        Button button = findViewById(R.id.submit);
        ProgressBar bar = findViewById(R.id.progressBar);

        FirebaseDatabaseHelper helper = new FirebaseDatabaseHelper();
        EditText login = findViewById(R.id.login);
        EditText password = findViewById(R.id.password);
        Switch admin = findViewById(R.id.adminSwitch);
        String loginStr = login.getText().toString(), passwordStr = password.getText().toString();
        if (loginStr.isEmpty() || passwordStr.isEmpty())
            Toasty.error(this, "Fill the form please", Toast.LENGTH_SHORT, true).show();
        else {
            button.setVisibility(View.GONE);
            bar.setVisibility(View.VISIBLE);
            helper.register(new User(loginStr, passwordStr, admin.isChecked()),
                    (item) -> {
                        Intent intent = new Intent(this, LoginActivity.class);
                        button.setVisibility(View.VISIBLE);
                        bar.setVisibility(View.GONE);
                        startActivity(intent);
                    },
                    (item) -> {
                        bar.setVisibility(View.GONE);
                        button.setVisibility(View.VISIBLE);
                        Toasty.error(this, "User already exist", Toast.LENGTH_SHORT, true).show();
                    }
            );
        }
    }
}
