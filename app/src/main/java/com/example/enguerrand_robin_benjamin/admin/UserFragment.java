package com.example.enguerrand_robin_benjamin.admin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.enguerrand_robin_benjamin.FirebaseDatabaseHelper;
import com.example.enguerrand_robin_benjamin.R;
import com.example.enguerrand_robin_benjamin.model.User;
import com.google.gson.Gson;

import java.util.List;

public class UserFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_admin_user_list, container, false);

        FirebaseDatabaseHelper helper = new FirebaseDatabaseHelper();
        helper.getAllUser(param -> {
            List<User> items = (List<User>) param;
            UserListAdapter productListViewAdapter = new UserListAdapter(items);
            ListView listView = rootView.findViewById(R.id.userList);

//            listView.setOnItemClickListener((adapter, v, position, id) -> {
//                User user = (User) adapter.getItemAtPosition(position);
//
//                Intent intent = new Intent(getActivity(), EditQuizzActivity.class);
//                Gson gson1 = new Gson();
//                String myJson = gson1.toJson(user);
//                intent.putExtra("quizz", myJson);
//                startActivity(intent);
//            });

            listView.setAdapter(productListViewAdapter);
        }, System.out::println);
        return rootView;
    }
}
