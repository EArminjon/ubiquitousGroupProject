package com.example.enguerrand_robin_benjamin;

import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.enguerrand_robin_benjamin.model.Quizz;

import java.util.List;

public class QuizzListAdapter extends BaseAdapter {

    private final List<Quizz> listProduct;

    QuizzListAdapter(List<Quizz> listProduct) {
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
            viewProduct = View.inflate(parent.getContext(), R.layout.quizz_row, null);
        } else viewProduct = convertView;


        Quizz product = (Quizz) getItem(position);
        ((TextView) viewProduct.findViewById(R.id.title)).setText(product.name);
        ((TextView) viewProduct.findViewById(R.id.questionNumber)).setText(String.valueOf(product.questions.size()));
        return viewProduct;
    }
}