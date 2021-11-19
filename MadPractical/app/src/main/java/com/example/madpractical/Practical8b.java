package com.example.madpractical;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Practical8b extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical8b);
    }

    public void Practical_audio(View view) {
        Intent intent = new Intent(this, Play_audio.class);
        startActivity(intent);
    }

    public void Practical_video(View view) {
        Intent intent = new Intent(this, Playvideo.class);
        startActivity(intent);
    }

    public void Practical_sms(View view) {
        Intent intent = new Intent(this, Send_message.class);
        startActivity(intent);
    }

    public void Practical_email(View view) {
        Intent intent = new Intent(this, Send_email.class);
        startActivity(intent);
    }
}