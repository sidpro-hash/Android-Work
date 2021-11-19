package com.example.madpractical;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Practical8 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical8);
    }

    public void Practical_8a(View view){
        Intent intent = new Intent(this,Practical8a.class);
        startActivity(intent);
    }
    public void Practical_8b(View view){
        Intent intent = new Intent(this,Practical2d.class);
        startActivity(intent);
    }
    public void Practical_8c(View view){
        Intent intent = new Intent(this,Practical8b.class);
        startActivity(intent);
    }
}