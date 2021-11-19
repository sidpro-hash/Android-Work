package com.example.madpractical;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class Send_message extends AppCompatActivity {

    private EditText message, phone;
    private Button send_sms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);

        message = findViewById(R.id.phone_message);
        phone = findViewById(R.id.phone);
        send_sms = findViewById(R.id.send_btn);

        send_sms.setOnClickListener(v -> {

            if (ContextCompat.checkSelfPermission(Send_message.this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                sendMessage();
            } else {
                ActivityCompat.requestPermissions(Send_message.this, new String[]{Manifest.permission.SEND_SMS}, 100);
            }
        });

    }

    private void sendMessage() {
        String sms_text = message.getText().toString().trim();
        String phone_no = phone.getText().toString().trim();
        if (sms_text.isEmpty() || phone_no.isEmpty()) {
            Toast.makeText(Send_message.this, "All Fields required!", Toast.LENGTH_LONG).show();
        } else {
            try {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(phone_no, null, sms_text, null, null);
                Toast.makeText(Send_message.this, "Sent success!", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(Send_message.this, "Error!", Toast.LENGTH_LONG).show();
                Log.d("Sms Error", e.toString());
            }
        }
    }
}