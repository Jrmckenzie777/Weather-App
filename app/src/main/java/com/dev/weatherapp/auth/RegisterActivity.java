package com.dev.weatherapp.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dev.weatherapp.R;
import com.dev.weatherapp.database.DatabaseHelper;
import com.dev.weatherapp.model.Users;

public class RegisterActivity extends AppCompatActivity {
    EditText etName, etEmail, etPassword;
    AppCompatButton btnRegister;
    LinearLayout llLogin;
    ImageView ivBack;
    String name, email, password;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.main));

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnRegister = findViewById(R.id.btnRegister);
        llLogin = findViewById(R.id.llLogin);
        ivBack = findViewById(R.id.ivBack);
        databaseHelper = new DatabaseHelper(this);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        llLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValidated()){
                    Users users = new Users(name, email, password);
                    databaseHelper.register(users);
                    showMessage("Successfully Registered");
                    finish();
                }
            }
        });

    }

    private Boolean isValidated(){
        name = etName.getText().toString().trim();
        email = etEmail.getText().toString().trim();
        password = etPassword.getText().toString().trim();

        if (name.isEmpty()){
            showMessage("Please enter name");
            return false;
        }
        if (email.isEmpty()){
            showMessage("Please enter email");
            return false;
        }
        if (!(Patterns.EMAIL_ADDRESS).matcher(email).matches()) {
            showMessage("Please enter email in correct format");
        }
        if (password.isEmpty()){
            showMessage("Please enter password");
            return false;
        }

        return true;
    }

    private void showMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}