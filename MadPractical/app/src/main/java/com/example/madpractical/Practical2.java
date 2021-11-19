package com.example.madpractical;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Practical2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical2);
    }

    public void Practical_2a(View view){
        Intent intent = new Intent(this,Practical_2a.class);
        startActivity(intent);
    }
    public void Practical_2b(View view){
        Intent intent = new Intent(this,Practical_2b.class);
        startActivity(intent);
    }
    public void Practical_2c(View view){
        Intent intent = new Intent(this,Practical_2c.class);
        startActivity(intent);
    }
    public void Practical_2d(View view){
        Intent intent = new Intent(this,Practical2d.class);
        startActivity(intent);
    }
}