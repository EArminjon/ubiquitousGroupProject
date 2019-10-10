package com.example.enguerrand_robin_benjamin.admin;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.enguerrand_robin_benjamin.R;
import com.example.enguerrand_robin_benjamin.model.Quizz;
import com.example.enguerrand_robin_benjamin.model.QuizzScore;

import java.util.List;

public class QuizzScoreListAdapter extends BaseAdapter {

    private final List<QuizzScore> listProduct;

    public QuizzScoreListAdapter(List<QuizzScore> listProduct) {
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
            viewProduct = View.inflate(parent.getContext(), R.layout.score_row, null);
        } else viewProduct = convertView;


        QuizzScore product = (QuizzScore) getItem(position);
        ((TextView) viewProduct.findViewById(R.id.title)).setText(product.name);
        ((TextView) viewProduct.findViewById(R.id.questionScore)).setText(String.format(parent.getContext().getString(R.string.quizz_score), product.score, product.numberOfQuestion));
        return viewProduct;
    }
}