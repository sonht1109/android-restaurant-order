package com.example.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.CreateOrder;
import com.example.R;
import com.example.adapter.ListAdapter;
import com.example.db.SQLiteHelper;
import com.example.model.Disk;

import java.util.List;

public class FrFavorite extends Fragment {

    private RecyclerView rcv;
    private SQLiteHelper sqLiteHelper;
    private List<Disk> disks;
    private ListAdapter adapter;
    private String phone = "";
    private int tableNumber = -1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fr_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcv = view.findViewById(R.id.favorite_rcv);

        sqLiteHelper = new SQLiteHelper(getContext());
        adapter = new ListAdapter((view1, position) -> {
            onItemClick(view1, position);
        });

        getItems();

        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rcv.setAdapter(adapter);
        rcv.setLayoutManager(manager);

        phone = getActivity().getIntent().getExtras().getString("phone");
        tableNumber = getActivity().getIntent().getExtras().getInt("tableNumber");
    }

    private void getItems() {
        disks = sqLiteHelper.getMostFavoriteDisks();
        adapter.setDisks(disks);
    }

    public void onItemClick(View view, int position) {
        if (tableNumber != -1) {
            Disk d = adapter.getDisks().get(position);
            Intent t = new Intent(getActivity(), CreateOrder.class);
            t.putExtra("disk", d);
            t.putExtra("phone", phone);
            t.putExtra("tableNumber", tableNumber);
            startActivity(t);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getItems();
    }
}
