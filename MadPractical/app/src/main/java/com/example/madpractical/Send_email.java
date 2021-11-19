package com.example.madpractical;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Send_email extends AppCompatActivity {
    EditText email, subject, message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_email);

        email = findViewById(R.id.email);
        subject = findViewById(R.id.subject);
        message = findViewById(R.id.email_message);
        Button send_sms = findViewById(R.id.send_btn);

        send_sms.setOnClickListener(v -> {
            String email_text = message.getText().toString().trim();
            String subject_text = subject.getText().toString().trim();
            String Email = email.getText().toString().trim();
            if (email_text.isEmpty() || subject_text.isEmpty() || Email.isEmpty()) {
                Toast.makeText(Send_email.this, "All Fields required!", Toast.LENGTH_LONG).show();
            } else {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + Email));
                    intent.putExtra(Intent.EXTRA_SUBJECT, subject_text);
                    intent.putExtra(Intent.EXTRA_TEXT, email_text);
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(Send_email.this, "Error!", Toast.LENGTH_LONG).show();
                    Log.d("Sms Error", e.toString());
                }
            }
        });
    }

}