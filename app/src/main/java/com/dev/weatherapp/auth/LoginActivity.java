package com.dev.weatherapp.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dev.weatherapp.MainActivity;
import com.dev.weatherapp.R;
import com.dev.weatherapp.database.DatabaseHelper;
import com.dev.weatherapp.model.HelperClass;
import com.dev.weatherapp.model.Users;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    EditText etEmail, etPassword;
    AppCompatButton btnLogin;
    LinearLayout llRegister;
    String email, password;
    DatabaseHelper databaseHelper;
    Boolean checkDetails = false;
    List<Users> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.main));

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        llRegister = findViewById(R.id.llRegister);
        databaseHelper = new DatabaseHelper(this);

        llRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValidated()) {
                    list = databaseHelper.getAllUsers();
                    for (Users users : list) {
                        if (email.equals(users.getEmail()) && password.equals(users.getPassword())) {
                            checkDetails = true;
                            showMessage("Successfully Login");
                            HelperClass.users = users;
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                            break;
                        }
                    }
                    if (!checkDetails) {
                        showMessage("Wrong Credentials...\nPlease check email or password");
                    }

                }
            }
        });

    }

    private Boolean isValidated() {
        email = etEmail.getText().toString().trim();
        password = etPassword.getText().toString().trim();

        if (email.isEmpty()) {
            showMessage("Please enter email");
            return false;
        }
        if (!(Patterns.EMAIL_ADDRESS).matcher(email).matches()) {
            showMessage("Please enter email in correct format");
        }
        if (password.isEmpty()) {
            showMessage("Please enter password");
            return false;
        }

        return true;
    }

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}