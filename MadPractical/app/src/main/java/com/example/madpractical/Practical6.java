package com.example.madpractical;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Practical6 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical6);

        EditText id = findViewById(R.id.contact_id);
        EditText name = findViewById(R.id.name);
        EditText email = findViewById(R.id.email);
        Button save_btn = findViewById(R.id.save_btn);
        Button view_btn = findViewById(R.id.view_btn);
        DAOstudent dao = new DAOstudent();

        save_btn.setOnClickListener(v -> {
            String Id = id.getText().toString();
            String Name = name.getText().toString();
            String Email = email.getText().toString();
            if (Id.isEmpty() || Name.isEmpty() || Email.isEmpty()) {
                Toast.makeText(this, "All Fields required!", Toast.LENGTH_LONG).show();
            } else {
                Student student = new Student(Id, Name, Email);
                // save user details in SharedPreferences
                SharedPreferences preferences = getSharedPreferences("student_info", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("id", Id);
                editor.putString("name", Name);
                editor.putString("email", Email);
                editor.apply();
                dao.add(student).addOnSuccessListener(suc -> {
                    Toast.makeText(this, "Record is inserted", Toast.LENGTH_LONG).show();
                    id.setText("");
                    name.setText("");
                    email.setText("");
                }).addOnFailureListener(er -> {
                    Toast.makeText(this, er.getMessage(), Toast.LENGTH_LONG).show();
                });
            }
        });

        // get data from preferences
        SharedPreferences preferences = getSharedPreferences("student_info", MODE_PRIVATE);
        String Id = preferences.getString("id", "");
        String Name = preferences.getString("name", "");
        String Email = preferences.getString("email", "");
        id.setText(Id);
        name.setText(Name);
        email.setText(Email);

        view_btn.setOnClickListener(v -> {
            Intent intent = new Intent(this, Practical6a.class);
            startActivity(intent);
        });
    }
}