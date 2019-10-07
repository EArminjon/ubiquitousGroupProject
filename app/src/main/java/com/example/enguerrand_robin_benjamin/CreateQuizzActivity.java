package com.example.enguerrand_robin_benjamin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewParent;
import android.widget.EditText;
import android.widget.LinearLayout;

public class CreateQuizzActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_quizz);
    }

    public void addQuestion(View view) {
        LinearLayout linearLayout = findViewById(R.id.inputList);
        LayoutInflater inflater = LayoutInflater.from(this);
        View child = inflater.inflate(R.layout.question, null);
        linearLayout.addView(child);
    }

    public void addResponse(View view) {
        View parent = (View)view.getParent();
        LinearLayout linearLayout = parent.findViewById(R.id.reponsesList);
        EditText editText = new EditText(this);
        editText.setHint("response...");
        linearLayout.addView(editText);
    }
}
