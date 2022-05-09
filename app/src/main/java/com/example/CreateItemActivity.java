package com.example;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.adapter.SpinnerImageAdapter;
import com.example.db.SQLiteHelper;
import com.example.model.Item;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CreateItemActivity extends AppCompatActivity {

    private Spinner spAuthor, spPublisher;
    private EditText eTitle, eShort;
    private Button btnAdd, btnCancel, btnDelete;
    private SQLiteHelper sql;
    private Item item;
    private TextView tv;
    private RatingBar ratingBar;
//    public static final int[] IMAGES = {R.drawable.ca1, R.drawable.ca2, R.drawable.ca3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_item);

        eTitle = findViewById(R.id.add_e_title);
        btnAdd = findViewById(R.id.btn_add);
        btnCancel = findViewById(R.id.btn_cancel);
        btnDelete = findViewById(R.id.btn_delete);
        tv = findViewById(R.id.add_tv_title);
        spAuthor = findViewById(R.id.add_sp_author);
        spPublisher = findViewById(R.id.add_sp_publisher);
        ratingBar = findViewById(R.id.add_rating);
        eShort = findViewById(R.id.add_e_b_short);

        sql = new SQLiteHelper(this);

        spAuthor.setAdapter(new ArrayAdapter<String>(this,
                R.layout.category_view_holder,
                getResources().getStringArray(R.array.book_author)));

        spPublisher.setAdapter(new ArrayAdapter<String>(this,
                R.layout.category_view_holder,
                getResources().getStringArray(R.array.book_publisher)));

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAdd();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCancel();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDelete();
            }
        });

        Intent intent = getIntent();
        item = (Item) intent.getSerializableExtra("item");
        if (item == null) {
            btnDelete.setVisibility(View.GONE);
            btnAdd.setText("Add");
            tv.setText("Add a new item");
        } else {
            setItemIntoForm();
            btnDelete.setVisibility(View.VISIBLE);
            btnAdd.setText("Update");
            tv.setText("Update item");
        }
    }

    private void onAdd() {
        String title = eTitle.getText().toString();
        String author = spAuthor.getSelectedItem().toString();
        String bShort = eShort.getText().toString();
        String publisher = spPublisher.getSelectedItem().toString();
        float rate = ratingBar.getRating();
        Item i = new Item(title, author, bShort, publisher, rate);
        if (i != null) {
            if (item != null) {
                i.setId(item.getId());
                sql.updateItem(i);
            } else {
                sql.createItem(i);
            }
            finish();
        }
    }

    private void onCancel() {
        finish();
    }

    private void onDelete() {
        sql.deleteItem(item.getId());
        finish();
    }

    private void setItemIntoForm() {
        eTitle.setText(item.getTitle());
        eShort.setText(item.getbShort());
        ratingBar.setRating(item.getRate());

        for (int i = 0; i < spAuthor.getCount(); i++) {
            if (item.getAuthor().equals(spAuthor.getItemAtPosition(i))) {
                spAuthor.setSelection(i);
                break;
            }
        }

        for (int i = 0; i < spPublisher.getCount(); i++) {
            if (item.getPublisher().equals(spPublisher.getItemAtPosition(i))) {
                spPublisher.setSelection(i);
                break;
            }
        }
    }
}