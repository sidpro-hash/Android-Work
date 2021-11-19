package com.example.madpractical;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Practical5 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical5);
    }

    public void Practical_5a(View view){
        Intent intent = new Intent(this,Practical5a.class);
        startActivity(intent);
    }
    public void Practical_5b(View view){
        Intent intent = new Intent(this,Practical5b.class);
        startActivity(intent);
    }
}