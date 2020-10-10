package com.android.dev.studentmanagement.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.dev.studentmanagement.Adapter.StudentAdapter;
import com.android.dev.studentmanagement.DataBase.Model.StudentModel;
import com.android.dev.studentmanagement.DataBase.Room.RoomDB;
import com.android.dev.studentmanagement.R;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    EditText etSearch;
    ImageView ivClear;
    RecyclerView rvList;
    Toolbar toolbar;

    RoomDB roomDB;

    StudentAdapter adapter;

    List<StudentModel> modelList;
    List<StudentModel> searchList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        toolbar = findViewById(R.id.toolbar);
        etSearch = findViewById(R.id.etSearch);
        rvList = findViewById(R.id.rvList);
        ivClear = findViewById(R.id.ivClear);

        roomDB = RoomDB.getInstance(this);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        rvList.setLayoutManager(new LinearLayoutManager(this));
        rvList.setHasFixedSize(true);

        modelList = roomDB.dataDAO().getAll();
        adapter = new StudentAdapter(this, modelList);
        rvList.setAdapter(adapter);

        ivClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etSearch.setText("");
                modelList = roomDB.dataDAO().getAll();
                adapter = new StudentAdapter(ListActivity.this, modelList);
                rvList.setAdapter(adapter);
            }
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                filter(charSequence.toString());

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void filter(String search) {
        searchList = new ArrayList<>();
        searchList.clear();

        for (StudentModel model : modelList) {
            if (model.getName().toLowerCase().contains(search.toLowerCase())) {
                searchList.add(model);
            }
        }

        adapter.filterList(searchList);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        modelList.clear();
        modelList.addAll(roomDB.dataDAO().getAll());
        adapter.notifyDataSetChanged();
        super.onResume();
    }
}