package com.example;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.db.SQLiteHelper;
import com.example.model.Disk;
import com.example.model.Order;
import com.example.model.Values;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CreateOrder extends AppCompatActivity {

    private TextView tv;
    private EditText eQuantity;
    private Button btn;
    private SQLiteHelper db;
    private final Calendar c = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);

        tv = findViewById(R.id.create_order_tv_food_name);
        eQuantity = findViewById(R.id.create_order_e_quantity);
        btn = findViewById(R.id.create_order_btn_submit);
        db = new SQLiteHelper(this);

        Intent i = getIntent();
        Disk disk = (Disk) i.getSerializableExtra("disk");
        String phone = i.getStringExtra("phone");
        int tableNumber = i.getIntExtra("tableNumber", 0);

        tv.setText(disk.getName());

        btn.setOnClickListener(v -> {
            String quantity = eQuantity.getText().toString();
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String today = dateFormat.format(c.getTime());
                int quantityToInt = Integer.parseInt(quantity);
                if(quantityToInt < 1) {
                    Toast.makeText(this, "Quantity is not valid", Toast.LENGTH_SHORT).show();
                    return;
                }
                long res = db.createOrder(new Order(quantityToInt, tableNumber, Values.ORDER_STATUS_PENDING, disk, today, phone));
                if (res >= 0) {
                    finish();
                } else {
                    Toast.makeText(this, "Error occurred. Please try again", Toast.LENGTH_SHORT).show();
                }
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Quantity must be a number", Toast.LENGTH_SHORT).show();
            }
        });
    }
}