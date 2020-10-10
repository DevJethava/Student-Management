package com.android.dev.studentmanagement.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.dev.studentmanagement.Adapter.StudentAdapter;
import com.android.dev.studentmanagement.DataBase.Model.StudentModel;
import com.android.dev.studentmanagement.DataBase.Room.RoomDB;
import com.android.dev.studentmanagement.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    private final String TAG = RegisterActivity.class.getSimpleName();

    EditText etName;
    EditText etEmail;
    EditText etPassword;
    Spinner spCity;
    Button btnSubmit;
    Toolbar toolbar;

    RoomDB roomDB;

    StudentModel model;

    String[] city;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        toolbar = findViewById(R.id.toolbar);
        roomDB = RoomDB.getInstance(this);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }



        Intent intent = getIntent();
        model = (StudentModel) intent.getSerializableExtra("data");

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        spCity = findViewById(R.id.spCity);
        btnSubmit = findViewById(R.id.btnSubmit);

        if (model != null) {

            btnSubmit.setText("Update Data");

            etName.setText(model.getName());
            etEmail.setText(model.getEmail());
            etPassword.setText(model.getPassword());

            city = getResources().getStringArray(R.array.city);
            int pos = -1;

            for (int i = 0; i < city.length; i++) {
                if (city[i].toLowerCase().equals(model.getCity().toLowerCase())) {
                    pos = i;
                    break;
                }
            }

            spCity.setSelection(pos);
        }

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(etName.getText().toString())) {
                    etName.setError("Empty");
                    return;
                }

                if (TextUtils.isEmpty(etEmail.getText().toString())) {
                    etEmail.setError("Empty");
                    return;
                }

                if (TextUtils.isEmpty(etPassword.getText().toString())) {
                    etPassword.setError("Empty");
                    return;
                }

                String city = spCity.getSelectedItem().toString();

                if (model != null) {
                    if (!TextUtils.isEmpty(etName.getText().toString()) && !TextUtils.isEmpty(etEmail.getText().toString()) && !TextUtils.isEmpty(etPassword.getText().toString())) {
                        StudentModel data = new StudentModel();
                        data.setId(model.getId());
                        data.setName(etName.getText().toString());
                        data.setEmail(etEmail.getText().toString());
                        data.setPassword(etPassword.getText().toString());
                        data.setCity(city);

                        roomDB.dataDAO().update(data.getId(), data.getName(), data.getEmail(), data.getPassword(), data.getCity());

                        Toast.makeText(RegisterActivity.this, "Update Success", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                } else {
                    if (!TextUtils.isEmpty(etName.getText().toString()) && !TextUtils.isEmpty(etEmail.getText().toString()) && !TextUtils.isEmpty(etPassword.getText().toString())) {
                        StudentModel model = new StudentModel();
                        model.setName(etName.getText().toString());
                        model.setEmail(etEmail.getText().toString());
                        model.setPassword(etPassword.getText().toString());
                        model.setCity(city);

                        roomDB.dataDAO().insert(model);

                        Toast.makeText(RegisterActivity.this, "Registration Success", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}