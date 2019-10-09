package com.example.enguerrand_robin_benjamin.admin;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.enguerrand_robin_benjamin.R;
import com.example.enguerrand_robin_benjamin.model.User;

import java.util.List;

public class UserListAdapter extends BaseAdapter {

    private final List<User> listProduct;

    public UserListAdapter(List<User> listProduct) {
        this.listProduct = listProduct;
    }


    @Override
    public int getCount() {
        return listProduct.size();
    }

    @Override
    public Object getItem(int position) {
        return listProduct.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View viewProduct;
        if (convertView == null) {
            viewProduct = View.inflate(parent.getContext(), R.layout.user_row, null);
        } else viewProduct = convertView;


        User product = (User) getItem(position);
        ((TextView) viewProduct.findViewById(R.id.nameInput)).setText(product.name);
        ((TextView) viewProduct.findViewById(R.id.passwordInput)).setText(product.password);
        return viewProduct;
    }
}