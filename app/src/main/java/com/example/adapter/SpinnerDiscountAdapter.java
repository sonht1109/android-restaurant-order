package com.example.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.R;
import com.example.model.Discount;

import java.util.List;

public class SpinnerDiscountAdapter extends BaseAdapter {

    private List<Discount> discounts;

    public SpinnerDiscountAdapter() {

    }

    public List<Discount> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(List<Discount> discounts) {
        this.discounts = discounts;
    }

    @Override
    public int getCount() {
        return discounts != null ? discounts.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return discounts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner_view_holder, parent, false);
        TextView textView = view.findViewById(R.id.spinner_discount_view_holder);
        textView.setText(discounts.get(position) != null ? discounts.get(position).getCode() : "--");
        return view;
    }
}
