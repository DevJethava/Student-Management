package com.android.dev.studentmanagement.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.dev.studentmanagement.DataBase.Model.StudentModel;
import com.android.dev.studentmanagement.DataBase.Room.RoomDB;
import com.android.dev.studentmanagement.R;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private final String TAG = LoginActivity.class.getSimpleName();

    EditText etEmail;
    EditText etPassword;
    Button btnLogin;
    Button btnRegister;
    Toolbar toolbar;

    RoomDB roomDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        roomDB = RoomDB.getInstance(this);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(etEmail.getText().toString())) {
                    etEmail.setError("Empty");
                    return;
                }

                if (TextUtils.isEmpty(etPassword.getText().toString())) {
                    etPassword.setError("Empty");
                    return;
                }

                if (!TextUtils.isEmpty(etEmail.getText().toString()) && !TextUtils.isEmpty(etPassword.getText().toString())) {

                    List<StudentModel> models = roomDB.dataDAO().getLogin(etEmail.getText().toString(), etPassword.getText().toString());

                    if (models != null) {
                        if (models.size() == 1) {
                            Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, ListActivity.class);
                            startActivity(intent);
                        }

                        if (models.size() <= 0) {
                            Toast.makeText(LoginActivity.this, "invalid Email AND Password", Toast.LENGTH_SHORT).show();
                            etEmail.setError("Invalid");
                            etPassword.setError("Invalid");
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "invalid Email AND Password", Toast.LENGTH_SHORT).show();
                        etEmail.setError("Invalid");
                        etPassword.setError("Invalid");
                    }

                }

            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

    }
}