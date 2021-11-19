package com.example.madpractical;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Login_message extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_message);

        Intent intent = getIntent();
        String message = intent.getStringExtra("Username");
        TextView textview = findViewById(R.id.username_textview);
        textview.setText(message);
        String message1 = intent.getStringExtra("Password");
        TextView textView = findViewById(R.id.password_textview);
        textView.setText(message1);
    }
}