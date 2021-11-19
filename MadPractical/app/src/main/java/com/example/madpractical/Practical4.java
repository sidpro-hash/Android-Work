package com.example.madpractical;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Practical4 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical4);

        EditText contact, name, email, city;
        contact = findViewById(R.id.contact_id);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        city = findViewById(R.id.city);

        // save button to insert data
        Button save_btn = findViewById(R.id.save_btn);

        save_btn.setOnClickListener(v -> {
            String Name = name.getText().toString();
            String Contact = contact.getText().toString();
            String City = city.getText().toString();
            String Email = email.getText().toString();

            if (Name.isEmpty() || Contact.isEmpty() || City.isEmpty() || Email.isEmpty()) {
                Toast.makeText(Practical4.this, "Every field is required!", Toast.LENGTH_LONG).show();
            } else {
                StudentDbHelper helper = new StudentDbHelper(getApplicationContext());
                SQLiteDatabase db = helper.getWritableDatabase();
                helper.addInfo(Contact, Name, Email, City, db);
                helper.close();
                name.setText("");
                contact.setText("");
                email.setText("");
                city.setText("");
                Toast.makeText(Practical4.this, "Student Info saved Successfully", Toast.LENGTH_LONG).show();
            }
        });


        // view button to read data
        Button view_btn = findViewById(R.id.view_btn);

        view_btn.setOnClickListener(v -> {

            StudentDbHelper helper = new StudentDbHelper(getApplicationContext());
            SQLiteDatabase db = helper.getReadableDatabase();
            Cursor cursor = helper.readInfo(db);

            String info = "";

            while (cursor.moveToNext()) {
                String id = cursor.getString(cursor.getColumnIndex(Studentinfo.CONTACT));
                String name1 = cursor.getString(cursor.getColumnIndex(Studentinfo.NAME));
                String City = cursor.getString(cursor.getColumnIndex(Studentinfo.CITY));
                String Email = cursor.getString(cursor.getColumnIndex(Studentinfo.EMAIL));
                info = info + "\n\nID: " + id + "\nName: " + name1 + "\nEmail: " + Email + "\nCity: " + City;
            }

            Intent intent = new Intent(Practical4.this, Practical4a.class);
            intent.putExtra("Data", info);
            startActivity(intent);
        });

        // delete data by id
        Button delete_btn = findViewById(R.id.delete_btn);

        delete_btn.setOnClickListener(this::btn_showMessage);

    }

    public void btn_showMessage(View view) {
        final AlertDialog.Builder alert = new AlertDialog.Builder(Practical4.this);

        // get layout
        View dialog_layout_view = getLayoutInflater().inflate(R.layout.dilog_layout, null);

        EditText txt_inputText = (EditText) dialog_layout_view.findViewById(R.id.txt_input);
        Button btn_cancel = (Button) dialog_layout_view.findViewById(R.id.btn_cancel);
        Button btn_okay = (Button) dialog_layout_view.findViewById(R.id.btn_okay);

        // insert layout into dialog box
        alert.setView(dialog_layout_view);
        // create dialog box
        final AlertDialog alertDialog = alert.create();
        // around area : false
        alertDialog.setCanceledOnTouchOutside(false);

        // on cancel
        btn_cancel.setOnClickListener(v -> alertDialog.dismiss());
        // on okay
        btn_okay.setOnClickListener(v -> {
            StudentDbHelper helper = new StudentDbHelper(getApplicationContext());
            SQLiteDatabase db = helper.getWritableDatabase();
            String Id = txt_inputText.getText().toString();
            if (!Id.isEmpty()) {
                helper.deleteInfo(Id, db);
                helper.close();
                txt_inputText.setText("");
                Toast.makeText(Practical4.this, "Student Info Deleted Successfully", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(Practical4.this, "Student ID is required!", Toast.LENGTH_LONG).show();
            }
            alertDialog.dismiss();
        });

        alertDialog.show();
    }

}