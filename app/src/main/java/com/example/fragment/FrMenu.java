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
import com.example.model.Discount;
import com.example.model.Disk;
import com.example.model.Values;

import java.util.List;

public class FrMenu extends Fragment {

    private RecyclerView rcvFood, rcvDrink;
    private SQLiteHelper sqLiteHelper;
    private List<Disk> foods, drinks;
    private ListAdapter adapterFood, adapterDrink;
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
        rcvFood = view.findViewById(R.id.today_rcv_food);
        rcvDrink = view.findViewById(R.id.today_rcv_drink);

        sqLiteHelper = new SQLiteHelper(getContext());
        adapterFood = new ListAdapter((view1, position) -> {
            onFoodClick(view1, position);
        });
        adapterDrink = new ListAdapter((view1, position) -> {
            onDrinkClick(view1, position);
        });

        getItems();

        LinearLayoutManager managerFood = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rcvFood.setAdapter(adapterFood);
        rcvFood.setLayoutManager(managerFood);

        LinearLayoutManager managerDrink = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rcvDrink.setAdapter(adapterDrink);
        rcvDrink.setLayoutManager(managerDrink);

        phone = getActivity().getIntent().getExtras().getString("phone");
        tableNumber = getActivity().getIntent().getExtras().getInt("tableNumber");

//        sqLiteHelper.createDisk(new Disk(0, 1200, "Súp cua dăm bông", Values.EnumDiskType.FOOD));
//        sqLiteHelper.createDisk(new Disk(1, 300, "Trứng ốp", Values.EnumDiskType.FOOD));
//        sqLiteHelper.createDisk(new Disk(2, 340, "Đậu hũ hạnh nhân", Values.EnumDiskType.FOOD));
//        sqLiteHelper.createDisk(new Disk(3, 970, "Măng chua", Values.EnumDiskType.FOOD));
//        sqLiteHelper.createDisk(new Disk(4, 232, "Mochi mâm xôi", Values.EnumDiskType.FOOD));
//        sqLiteHelper.createDisk(new Disk(5, 570, "Sushi trứng chim", Values.EnumDiskType.FOOD));
//
//        sqLiteHelper.createDisk(new Disk(0, 20, "Coca", Values.EnumDiskType.DRINK));
//        sqLiteHelper.createDisk(new Disk(1, 20, "Pepsi", Values.EnumDiskType.DRINK));
//        sqLiteHelper.createDisk(new Disk(2, 15, "Mirinda", Values.EnumDiskType.DRINK));
//        sqLiteHelper.createDisk(new Disk(3, 10, "Aquafina", Values.EnumDiskType.DRINK));
//        sqLiteHelper.createDisk(new Disk(4, 30, "Sochu", Values.EnumDiskType.DRINK));
//
//        sqLiteHelper.createDiscount(new Discount("GIAM10", 20));
//        sqLiteHelper.createDiscount(new Discount("GIAM20", 20));
//        sqLiteHelper.createDiscount(new Discount("GIAM30", 30));
//        sqLiteHelper.createDiscount(new Discount("GIAM50", 50));

    }

    private void getItems() {
        foods = sqLiteHelper.getAllDisks(Values.EnumDiskType.FOOD);
        drinks = sqLiteHelper.getAllDisks(Values.EnumDiskType.DRINK);

        adapterFood.setDisks(foods);
        adapterDrink.setDisks(drinks);
    }

    public void onFoodClick(View view, int position) {
        if (tableNumber != -1) {
            Disk d = adapterFood.getDisks().get(position);
            Intent t = new Intent(getActivity(), CreateOrder.class);
            t.putExtra("disk", d);
            t.putExtra("phone", phone);
            t.putExtra("tableNumber", tableNumber);
            startActivity(t);
        }
    }

    public void onDrinkClick(View view, int position) {
        if (tableNumber != -1) {
            Disk d = adapterDrink.getDisks().get(position);
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
