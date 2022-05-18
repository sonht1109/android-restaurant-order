package com.example.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;
import com.example.adapter.OrderListAdapter;
import com.example.db.SQLiteHelper;
import com.example.model.Disk;
import com.example.model.Order;
import com.example.model.Values;

import java.util.List;

public class FrOrder extends Fragment implements OrderListAdapter.OrderItemListener {

    private SQLiteHelper db;
    private OrderListAdapter adapter;
    private RecyclerView rcv;
    private String phone;
    private int tableNumber;
    private Button btnConfirmOrders;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fr_order, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        phone = getActivity().getIntent().getExtras().getString("phone");
        tableNumber = getActivity().getIntent().getExtras().getInt("tableNumber");
        rcv = view.findViewById(R.id.order_rcv);
        btnConfirmOrders = view.findViewById(R.id.order_btn_confirm_orders);

        db = new SQLiteHelper(getContext());
        adapter = new OrderListAdapter();
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rcv.setAdapter(adapter);
        rcv.setLayoutManager(manager);

        adapter.setOrderItemListener(this);
        getOrders();

        btnConfirmOrders.setOnClickListener(v -> {
            boolean res = db.confirmOrders(phone, tableNumber);
            if (res == true) {
                getOrders();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getOrders();
    }

    private void getOrders() {
        List<Order> orders = db.getCurrentOrders(phone, tableNumber);
        adapter.setOrders(orders);
        updateConfirmButtonVisible();
    }

    @Override
    public void onConfirm(int position) {
    }

    @Override
    public void onCancel(int position) {
        int res = db.deleteOrder(adapter.getOrders().get(position).getId());
        if (res >= 0) {
            adapter.getOrders().remove(position);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onUpdateQuantity(int position, int value) {
        Order o = adapter.getOrders().get(position);
        if (o.getQuantity() + value > 0) {
            o.setQuantity(o.getQuantity() + value);
            int res = db.updateOrderByQuantity(o);
            if (res > 0) {
                adapter.getOrders().set(position, o);
                adapter.notifyItemChanged(position);
            }
        }
    }

    private boolean canConfirmOrders() {
        boolean res = false;
        List<Order> orders = adapter.getOrders();
        for (Order o : orders) {
            if (o.getStatus() == Values.ORDER_STATUS_PENDING) {
                res = true;
                break;
            }
        }
        return res;
    }

    private void updateConfirmButtonVisible() {
        btnConfirmOrders.setVisibility(canConfirmOrders() ? View.VISIBLE : View.GONE);
    }
}
