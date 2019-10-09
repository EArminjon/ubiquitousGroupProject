package com.example.enguerrand_robin_benjamin.admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.enguerrand_robin_benjamin.FirebaseDatabaseHelper;
import com.example.enguerrand_robin_benjamin.QuizzListAdapter;
import com.example.enguerrand_robin_benjamin.R;
import com.example.enguerrand_robin_benjamin.model.Quizz;
import com.google.gson.Gson;

import java.util.List;

public class QuizzFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_admin_quizz_list, container, false);


        FirebaseDatabaseHelper helper = new FirebaseDatabaseHelper();
        helper.getAllQuizz(param -> {
            List<Quizz> items = (List<Quizz>) param;
            QuizzListAdapter productListViewAdapter = new QuizzListAdapter(items);
            ListView listView = rootView.findViewById(R.id.quizzList);

            listView.setOnItemClickListener((adapter, v, position, id) -> {
                Quizz quizz = (Quizz) adapter.getItemAtPosition(position);

                Intent intent = new Intent(getActivity(), EditQuizzActivity.class);
                Gson gson1 = new Gson();
                String myJson = gson1.toJson(quizz);
                intent.putExtra("quizz", myJson);
                startActivity(intent);
            });

            listView.setAdapter(productListViewAdapter);
        }, System.out::println);
        return rootView;
    }
}
