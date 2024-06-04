package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.SQLiteConnector.SQLiteConnector;
import com.example.myapplication.model.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Register extends AppCompatActivity {

    EditText editTextUsername, editTextEmail, editTextPassword;
    Button buttonRegister;
    SQLiteConnector db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonRegister = findViewById(R.id.buttonRegister);
        db = new SQLiteConnector(this);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString();
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();

                if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(Register.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else {
                    String hashedPassword = hashPassword(password);
                    User user = new User(username, email, hashedPassword);
                    db.addUser(user);
                    Toast.makeText(Register.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Register.this, Login.class);
                    startActivity(intent);
                }
            }
        });
    }

    public void login(View view) {
        Intent intent = new Intent(Register.this, Login.class);
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
