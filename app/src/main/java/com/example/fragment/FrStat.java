package com.example.fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.CreateItemActivity;
import com.example.R;
import com.example.adapter.ListAdapter;
import com.example.db.SQLiteHelper;
import com.example.model.Item;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FrStat extends Fragment {

    private SearchView searchView;
    private Button btnSearch;
    private Spinner spAuthor;
    private RecyclerView rcv;
    private SQLiteHelper sqLiteHelper;
    private ListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fr_stat, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchView = view.findViewById(R.id.stat_search);
        btnSearch = view.findViewById(R.id.stat_btn_search);
        rcv = view.findViewById(R.id.stat_rcv);
        spAuthor = view.findViewById(R.id.stat_sp_author);

        spAuthor.setAdapter(new ArrayAdapter<String>(getContext(),
                R.layout.category_view_holder,
                getResources().getStringArray(R.array.book_author_all)));

        sqLiteHelper = new SQLiteHelper(getContext());

        adapter = new ListAdapter();
        adapter.setItems(new ArrayList<>());
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rcv.setLayoutManager(manager);
        rcv.setAdapter(adapter);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSearch();
            }
        });

        adapter.setItemListener(new ListAdapter.ItemListener() {
            @Override
            public void onItemClick(View view, int position) {
                Item i = adapter.getItems().get(position);
                Intent t = new Intent(getActivity(), CreateItemActivity.class);
                t.putExtra("item", i);
                startActivity(t);
            }
        });

        String[] args = {"rate desc"};
        String sql = "select * from book order by ?";
        List<Item> items = sqLiteHelper.searchItems(sql, args);
        adapter.setItems(items);
    }

    private void onSearch() {
        String title = searchView.getQuery().toString();
        String author = spAuthor.getSelectedItem().toString();

        if (author.equals("All")) {
            String[] args = {"%" + title + "%", "rate desc"};
            String sql = "select * from book where title like ? order by ?";
            List<Item> items = sqLiteHelper.searchItems(sql, args);
            adapter.setItems(items);
        } else {
            String[] args = {"%" + title + "%", author, "rate desc"};
            String sql = "select * from book where title like ? and author like ? order by ?";
            List<Item> items = sqLiteHelper.searchItems(sql, args);
            adapter.setItems(items);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
