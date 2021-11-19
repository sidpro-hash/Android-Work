package com.example.madpractical;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Practical7 extends AppCompatActivity {
    BroadcastReceiverExample broad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical7);

        // BroadcastReceiver
        TextView brod_text = findViewById(R.id.brod_text);
        broad = new BroadcastReceiverExample(brod_text);
        // if we register this BroadcastReceiver by putting it in manifest file then it's statically register.
        // but here we have done dynamically
        registerReceiver(broad,new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        registerReceiver(broad,new IntentFilter(Intent.ACTION_BOOT_COMPLETED));
        registerReceiver(broad,new IntentFilter(Intent.ACTION_POWER_CONNECTED));
        registerReceiver(broad,new IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED));
        registerReceiver(broad,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        // Notification
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("Mad Practical","Mad Practical", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        Button notify = findViewById(R.id.notify);
        notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // create notification
                NotificationCompat.Builder builder = new NotificationCompat.Builder(Practical7.this,"Mad Practical");
                builder.setContentTitle("MAD Practical");
                builder.setContentText("Alert: You are viewing Practical 7!");
                builder.setSmallIcon(R.drawable.ic_stat_name);
                builder.setAutoCancel(true);

                // notify user
                NotificationManagerCompat managerCompat = NotificationManagerCompat.from(Practical7.this);
                managerCompat.notify(1,builder.build());
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(broad);
    }
}