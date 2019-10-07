package com.example.enguerrand_robin_benjamin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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
    }

    public void doRegister(View view) {
        FirebaseDatabaseHelper helper = new FirebaseDatabaseHelper();
        EditText login = findViewById(R.id.login);
        EditText password = findViewById(R.id.password);
        Switch admin = findViewById(R.id.adminSwitch);
        String loginStr = login.getText().toString(), passwordStr = password.getText().toString();
        if (loginStr.isEmpty() || passwordStr.isEmpty())
            Toasty.error(this, "Fill the form please", Toast.LENGTH_SHORT, true).show();
        else
            helper.register(new User(loginStr, passwordStr, admin.isChecked()),
                    (item) -> {
                        Intent intent = new Intent(this, LoginActivity.class);
                        startActivity(intent);
                    },
                    (item) -> Toasty.error(this, "User already exist", Toast.LENGTH_SHORT, true).show()
            );
    }
}
