package com.example;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.model.Values;

public class LoginActivity extends AppCompatActivity {

    private EditText ePhone;
    private Button btnSubmit;
    private Spinner sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ePhone = findViewById(R.id.login_e_phone);
        sp = findViewById(R.id.login_sp_table);
        btnSubmit = findViewById(R.id.login_btn_submit);

        ArrayAdapter<Integer> tableNumberAdapter = new ArrayAdapter<>(this, R.layout.table_number_holder, Values.TABLE_NUMBERS);
        sp.setAdapter(tableNumberAdapter);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                String phone = ePhone.getText().toString();
                Integer tableNumber = (Integer) sp.getSelectedItem();
                intent.putExtra("phone", phone);
                intent.putExtra("tableNumber", tableNumber);
                startActivity(intent);
            }
        });
    }
}