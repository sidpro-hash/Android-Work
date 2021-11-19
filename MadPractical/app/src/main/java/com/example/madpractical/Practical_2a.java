package com.example.madpractical;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class Practical_2a extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical2a);
    }

    public void ValidInput(View view){
        EditText editText = findViewById(R.id.editTextTextPersonName);
        EditText editText1 = findViewById(R.id.editTextTextPassword);
        String username = editText.getText().toString();
        String password = editText1.getText().toString();
        if(username.isEmpty() || password.isEmpty()){
            TextView textView = findViewById(R.id.errorTxtView);
            textView.setText("Username and Password required!");
        }
    }
}