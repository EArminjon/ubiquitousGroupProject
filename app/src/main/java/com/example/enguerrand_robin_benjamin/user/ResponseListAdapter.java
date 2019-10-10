package com.example.enguerrand_robin_benjamin.user;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.enguerrand_robin_benjamin.R;
import com.example.enguerrand_robin_benjamin.model.Quizz;
import com.example.enguerrand_robin_benjamin.model.QuizzResponse;

import java.util.ArrayList;
import java.util.List;

public class ResponseListAdapter extends BaseAdapter {

    private final List<QuizzResponse> listProduct = new ArrayList<>();

    ResponseListAdapter() {
    }

    void update(List<QuizzResponse> listProduct) {
        this.listProduct.clear();
        this.listProduct.addAll(listProduct);
        notifyDataSetChanged();
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
            viewProduct = View.inflate(parent.getContext(), R.layout.answer_response, null);
        } else viewProduct = convertView;


        QuizzResponse product = (QuizzResponse) getItem(position);
        ((TextView) viewProduct.findViewById(R.id.responseName)).setText(product.name);
        ((CheckBox) viewProduct.findViewById(R.id.answer)).setChecked(product.choosed);
        return viewProduct;
    }
}