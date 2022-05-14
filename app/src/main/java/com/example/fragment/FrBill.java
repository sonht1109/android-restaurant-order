package com.example.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;
import com.example.adapter.OrderListAdapter;
import com.example.db.SQLiteHelper;
import com.example.model.Order;

import java.util.ArrayList;
import java.util.List;

public class FrBill extends Fragment {

    private SQLiteHelper db;
    private OrderListAdapter adapter;
    private RecyclerView rcv;
    private Button btnPay;
    private String phone;
    private int tableNumber;
    private TextView tvTotal;

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
        btnPay.setOnClickListener(v -> {
            boolean res = db.pay(phone, tableNumber);
            if (res) {
                adapter.setOrders(new ArrayList<>());
                adapter.notifyDataSetChanged();
                Toast.makeText(getContext(), "Thank you. We hope you enjoyed the meal", Toast.LENGTH_SHORT).show();
            }
        });

        db = new SQLiteHelper(getContext());
        adapter = new OrderListAdapter();
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rcv.setAdapter(adapter);
        rcv.setLayoutManager(manager);

        getBill();

        tvTotal.setText("Your bill: " + getTotal() + " at table " + tableNumber);
    }

    @Override
    public void onResume() {
        super.onResume();
        getBill();
    }

    private void getBill() {
        List<Order> orders = db.getBill(phone, tableNumber);
        adapter.setOrders(orders);
        if(orders == null || orders.size() == 0) {
            btnPay.setVisibility(View.GONE);
        }
    }

    private float getTotal() {
        float res = 0;
        List<Order> orders = adapter.getOrders();
        if (orders != null) {
            for (Order o : orders) {
                res += o.getDisk().getPrice();
            }
        }
        return res;
    }
}
