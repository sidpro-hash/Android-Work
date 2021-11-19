package com.example.madpractical;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_action_bar);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setTitle(" Mad Practical");
        getSupportActionBar().setSubtitle(" by siddharth gabu");
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);
    }

    public void Practical_1(View view){
        Intent intent = new Intent(this,Practical1.class);
        startActivity(intent);
    }
    public void Practical_2(View view){
        Intent intent = new Intent(this,Practical2.class);
        startActivity(intent);
    }
    public void Practical_4(View view){
        Intent intent = new Intent(this,Practical4.class);
        startActivity(intent);
    }
    public void Practical_5(View view){
        Intent intent = new Intent(this,Practical5.class);
        startActivity(intent);
    }
    public void Practical_6(View view){
        Intent intent = new Intent(this,Practical6.class);
        startActivity(intent);
    }
    public void Practical_7(View view){
        Intent intent = new Intent(this,Practical7.class);
        startActivity(intent);
    }
    public void Practical_8(View view){
        Intent intent = new Intent(this,Practical8.class);
        startActivity(intent);
    }
    public void bluetooth(View view){
        Intent intent = new Intent(this,Discover_Bluetooth.class);
        startActivity(intent);
    }
    public void wifi_strength(View view){
        Intent intent = new Intent(this,Wifi_Signal.class);
        startActivity(intent);
    }
    public void Practical_9(View view){
        Intent intent = new Intent(this,About.class);
        startActivity(intent);
    }
}