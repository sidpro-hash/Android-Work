package com.example.madpractical;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class Playvideo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playvideo);

        VideoView videoView1 = findViewById(R.id.video_view1);
        videoView1.setVideoPath("android.resource://"+getPackageName()+"/"+R.raw.gtu_song);
        MediaController mediaController1 = new MediaController(this);
        mediaController1.setAnchorView(videoView1);
        videoView1.setMediaController(mediaController1);

        VideoView videoView2 = findViewById(R.id.video_view2);
        videoView2.setVideoPath("android.resource://"+getPackageName()+"/"+R.raw.jack_sparrow);
        MediaController mediaController2 = new MediaController(this);
        mediaController2.setAnchorView(videoView2);
        videoView2.setMediaController(mediaController2);

    }
}