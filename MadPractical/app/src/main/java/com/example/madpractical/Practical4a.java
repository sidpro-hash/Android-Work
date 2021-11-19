package com.example.madpractical;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Practical4a extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical4a);

        Intent intent = getIntent();
        String data = intent.getStringExtra("Data");
        TextView textview = findViewById(R.id.data_text);
        if (!data.isEmpty()) textview.setText(data);
    }
}