package com.example.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

        db = new SQLiteHelper(getContext());
        adapter = new OrderListAdapter();
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rcv.setAdapter(adapter);
        rcv.setLayoutManager(manager);

        adapter.setOrderItemListener(this);
        getOrders();
    }

    @Override
    public void onResume() {
        super.onResume();
        getOrders();
    }

    private void getOrders() {
        List <Order> orders = db.getCurrentOrders(phone, tableNumber);
        adapter.setOrders(orders);
    }

    @Override
    public void onConfirm(int position) {
        Order selectedOrder = adapter.getOrders().get(position);
        selectedOrder.setStatus(Values.ORDER_STATUS_ACCEPTED);
        int res = db.updateOrder(selectedOrder);
        if(res >= 0) {
            adapter.getOrders().set(position, selectedOrder);
            adapter.notifyItemChanged(position);
        }
    }

    @Override
    public void onCancel(int position) {
        int res = db.deleteOrder(adapter.getOrders().get(position).getId());
        if(res >= 0) {
            adapter.getOrders().remove(position);
            adapter.notifyDataSetChanged();
        }
    }
}
