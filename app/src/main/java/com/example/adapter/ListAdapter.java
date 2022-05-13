package com.example.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;
import com.example.model.Item;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ItemViewHolder> {

    private List<Item> items;
    private ItemListener itemListener;

    public ListAdapter() {
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public void setItemListener(ItemListener itemListener) {
        this.itemListener = itemListener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_holder, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item item = items.get(position);
        if (item == null) return;
        holder.tvTitle.setText(item.getTitle());
        holder.tvAuthor.setText(item.getAuthor());
    }

    @Override
    public int getItemCount() {
        if (items == null) return 0;
        return items.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle, tvAuthor;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.item_view_title);
            tvAuthor = itemView.findViewById(R.id.item_view_author);

            itemView.setOnClickListener(v -> {
                if (itemView != null) {
                    itemListener.onItemClick(itemView, getAdapterPosition());
                }
            });
        }
    }

    public interface ItemListener {
        void onItemClick(View view, int position);
    }
}
