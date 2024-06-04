package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.SQLiteConnector.SQLiteConnector;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Login extends AppCompatActivity {

    EditText editTextUsername, editTextPassword;
    Button buttonLogin;
    SQLiteConnector db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        db = new SQLiteConnector(this);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();

                if (db.checkUser(username, hashPassword(password))) {
                    Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login.this, Home.class);
                    intent.putExtra("username", username);
                    startActivity(intent);
                } else {
                    Toast.makeText(Login.this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void register(View view) {
        Intent intent = new Intent(Login.this, Register.class);
        startActivity(intent);
    }

    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
