package com.example.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.R;
import com.example.model.Disk;
import com.example.model.Values;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ItemViewHolder> {

    private List<Disk> disks;
    private ItemListener itemListener;

    public ListAdapter() {
    }

    public List<Disk> getDisks() {
        return disks;
    }

    public void setDisks(List<Disk> disks) {
        this.disks = disks;
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
        Disk d = disks.get(position);
        if (d == null) return;
        holder.tvName.setText(d.getName());
        holder.tvPrice.setText(String.valueOf(d.getPrice()));
        holder.imageView.setImageResource(Values.FOOD_IMAGES[d.getImage()]);
    }

    @Override
    public int getItemCount() {
        if (disks == null) return 0;
        return disks.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName, tvPrice;
        private ImageView imageView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.item_view_name);
            tvPrice = itemView.findViewById(R.id.item_view_price);
            imageView = itemView.findViewById(R.id.item_view_img);

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
