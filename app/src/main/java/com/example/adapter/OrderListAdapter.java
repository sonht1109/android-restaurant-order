package com.example.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;
import com.example.db.SQLiteHelper;
import com.example.model.Disk;
import com.example.model.Order;
import com.example.model.Values;

import java.util.ArrayList;
import java.util.List;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ItemViewHolder> {

    private List<Order> orders;
    private OrderItemListener orderItemListener;

    public OrderListAdapter() {
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
        notifyDataSetChanged();
    }

    public void setOrderItemListener(OrderItemListener orderItemListener) {
        this.orderItemListener = orderItemListener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item_view_holder,
                parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Order o = orders.get(position);
        if (o == null) return;
        holder.tvName.setText(o.getDisk().getName());
        holder.tvPriceAndQuantity.setText(o.getDisk().getPrice() + " x " + o.getQuantity());
        if (o.getStatus() == Values.ORDER_STATUS_PENDING) {
            holder.btnConfirm.setOnClickListener(v -> {
                orderItemListener.onConfirm(position);
            });
            holder.btnCancel.setOnClickListener(v -> {
                orderItemListener.onCancel(position);
            });
        } else {
            holder.btnConfirm.setVisibility(View.GONE);
            holder.btnCancel.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        if (orders == null) return 0;
        return orders.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName, tvPriceAndQuantity;
        private Button btnConfirm, btnCancel;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.order_item_view_name);
            tvPriceAndQuantity = itemView.findViewById(R.id.order_item_view_price_and_quantity);
            btnConfirm = itemView.findViewById(R.id.order_item_btn_confirm);
            btnCancel = itemView.findViewById(R.id.order_item_btn_cancel);
        }
    }

    public interface OrderItemListener {
        void onConfirm(int position);
        void onCancel(int position);
    }
}
