package com.example.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
        holder.tvPrice.setText(o.getDisk().getPrice() + " x " + o.getQuantity());
        holder.tvQuantity.setText(o.getQuantity() + "");
        if (o.getStatus() == Values.ORDER_STATUS_PENDING) {
            holder.btnCancel.setOnClickListener(v -> {
                orderItemListener.onCancel(position);
            });
            holder.btnMinus.setOnClickListener(v -> {
                orderItemListener.onUpdateQuantity(position, -1);
            });
            holder.btnPlus.setOnClickListener(v -> {
                orderItemListener.onUpdateQuantity(position, 1);
            });
        } else {
            holder.layoutBottom.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        if (orders == null) return 0;
        return orders.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName, tvPrice, tvQuantity;
        private Button btnCancel, btnMinus, btnPlus;
        private LinearLayout layoutBottom;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.order_item_view_name);
            tvPrice = itemView.findViewById(R.id.order_item_view_price);
            btnCancel = itemView.findViewById(R.id.order_item_btn_cancel);
            btnMinus = itemView.findViewById(R.id.order_item_btn_minus);
            btnPlus = itemView.findViewById(R.id.order_item_btn_plus);
            tvQuantity = itemView.findViewById(R.id.order_item_tv_quantity);
            layoutBottom = itemView.findViewById(R.id.order_item_layout_bottom);
        }
    }

    public interface OrderItemListener {
        void onConfirm(int position);
        void onCancel(int position);
        void onUpdateQuantity(int position, int value);
    }
}
