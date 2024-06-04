package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class Home extends AppCompatActivity {

    TextView textViewWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        textViewWelcome = findViewById(R.id.textViewWelcome);

        Intent intent = getIntent();
        String username = intent.getStringExtra("username");

        textViewWelcome.setText("Welcome, " + username + "!");
    }
}
