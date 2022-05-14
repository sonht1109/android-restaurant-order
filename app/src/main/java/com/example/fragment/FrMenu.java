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

public class FrMenu extends Fragment implements ListAdapter.ItemListener {

    private RecyclerView rcv;
    private SQLiteHelper sqLiteHelper;
    private List<Disk> disks;
    private ListAdapter adapter;
    private String phone = "";
    private int tableNumber = -1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fr_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcv = view.findViewById(R.id.today_rcv);

        sqLiteHelper = new SQLiteHelper(getContext());
        adapter = new ListAdapter();

        getItems();

        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rcv.setAdapter(adapter);
        rcv.setLayoutManager(manager);

        adapter.setItemListener(this);

        phone = getActivity().getIntent().getExtras().getString("phone");
        tableNumber = getActivity().getIntent().getExtras().getInt("tableNumber");

//        sqLiteHelper.createDisk(new Disk(0, 1200, "Súp cua dăm bông"));
//        sqLiteHelper.createDisk(new Disk(1, 300, "Trứng ốp"));
//        sqLiteHelper.createDisk(new Disk(2, 1200, "Đậu hũ hạnh nhân"));
//        sqLiteHelper.createDisk(new Disk(3, 1200, "Măng chua"));
//        sqLiteHelper.createDisk(new Disk(4, 1200, "Mochi mâm xôi"));
//        sqLiteHelper.createDisk(new Disk(5, 1200, "Sushi trứng chim"));
    }

    private void getItems() {
        disks = sqLiteHelper.getAllDisks();
        adapter.setDisks(disks);
    }

    @Override
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
