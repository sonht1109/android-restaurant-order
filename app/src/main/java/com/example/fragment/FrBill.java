package com.example.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;
import com.example.adapter.OrderListAdapter;
import com.example.adapter.SpinnerDiscountAdapter;
import com.example.db.SQLiteHelper;
import com.example.model.Order;
import com.example.model.Discount;

import java.util.List;

public class FrBill extends Fragment {

    private SQLiteHelper db;
    private OrderListAdapter adapter;
    private RecyclerView rcv;
    private Button btnPay;
    private String phone;
    private int tableNumber;
    private TextView tvTotal, tvTable, tvPhone, tvDiscount;
    private SpinnerDiscountAdapter discountAdapter;
    private Spinner spDiscount;
    private Discount selectedDiscount = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fr_bill, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        super.onViewCreated(view, savedInstanceState);

        phone = getActivity().getIntent().getExtras().getString("phone");
        tableNumber = getActivity().getIntent().getExtras().getInt("tableNumber");
        rcv = view.findViewById(R.id.bill_rcv);
        btnPay = view.findViewById(R.id.bill_btn_pay);
        tvTotal = view.findViewById(R.id.bill_tv_total);
        tvTable = view.findViewById(R.id.bill_tv_table);
        tvPhone = view.findViewById(R.id.bill_tv_phone);
        spDiscount = view.findViewById(R.id.bill_sp_discount);
        tvDiscount = view.findViewById(R.id.bill_tv_discount);

        tvTable.setText(tableNumber + "");
        tvPhone.setText(phone);

        btnPay.setOnClickListener(v -> {
            boolean res = db.pay(phone, tableNumber, selectedDiscount);
            if (res) {
                Toast.makeText(getContext(), "Thank you. We hope you enjoyed the meal", Toast.LENGTH_SHORT).show();
                getBill();
                showDiscount();
                showTotal();
            }
        });

        db = new SQLiteHelper(getContext());
        adapter = new OrderListAdapter();
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rcv.setAdapter(adapter);
        rcv.setLayoutManager(manager);

        getBill();

        getDiscount();
    }

    @Override
    public void onResume() {
        super.onResume();
        getBill();
    }

    private void getBill() {
        List<Order> orders = db.getBill(phone, tableNumber);
        adapter.setOrders(orders);
        if (orders == null || orders.size() == 0) {
            btnPay.setVisibility(View.GONE);
        }
    }

    private float calculateTotal() {
        float res = 0;
        List<Order> orders = adapter.getOrders();
        if (orders != null) {
            for (Order o : orders) {
                res += o.getDisk().getPrice();
            }
        }
        if (selectedDiscount != null) {
            res *= (100 - selectedDiscount.getPercentage()) / 100;
        }
        return res;
    }

    private void showTotal() {
        tvTotal.setText(calculateTotal() + "");
    }

    private void showDiscount() {
        tvDiscount.setText(selectedDiscount != null ? selectedDiscount.getPercentage() + "%" : "0");
    }

    private void getDiscount() {
        List<Discount> discounts = db.getAllDiscount();
        discounts.add(0, null);
        discountAdapter = new SpinnerDiscountAdapter();
        discountAdapter.setDiscounts(discounts);
        spDiscount.setAdapter(discountAdapter);

        spDiscount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedDiscount = discountAdapter.getDiscounts().get(position);
                showDiscount();
                showTotal();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedDiscount = null;
                showDiscount();
                showTotal();
            }
        });
    }
}
