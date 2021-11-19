package com.example.madpractical;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Practical1 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical1);
        Log.d("lifecycle", "onCreate invoked");
        /* Implicit intent */
        // it is used to communicate between two different apps
        // For example, if you want to show the user a Web page or website,
        // you can use an implicit intent to request that another capable
        // app show a specified Web page or website.
        ImageView imgClick = (ImageView) findViewById(R.id.imageView);
        imgClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Practical1.this, "You clicked on collegeek.com", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://collegeek.com"));
                startActivity(intent);
            }
        });
    }

    /* Control events */
    public void ValidInput(View view) {
        EditText editText = findViewById(R.id.editTextTextPersonName);
        EditText editText1 = findViewById(R.id.editTextTextPassword);
        String username = editText.getText().toString();
        String password = editText1.getText().toString();
        if (username.isEmpty() || password.isEmpty()) {
            TextView textView = findViewById(R.id.errorTxtView);
            textView.setText("Username and Password required!");
        } else {
            /* Explicit intent */
            // use an explicit intent to start a component in your own app, because you
            // know the class name of the activity or service you want to start.
            // For example, you might start a new activity within your app in response
            // to a user action, or start a service to download a file in the background.
            Intent intent = new Intent(this, Login_message.class);
            intent.putExtra("Username", username);
            intent.putExtra("Password", password);
            startActivity(intent);
        }
    }

    /* Android Activity Lifecycle */
    // onCreate is above
    @Override
    protected void onStart() {
        super.onStart();
        Log.d("lifecycle", "onStart invoked");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("lifecycle", "onResume invoked");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("lifecycle", "onPause invoked");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("lifecycle", "onStop invoked");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("lifecycle", "onRestart invoked");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("lifecycle", "onDestroy invoked");
    }
}