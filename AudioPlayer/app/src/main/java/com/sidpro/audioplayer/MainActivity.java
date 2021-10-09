package com.sidpro.audioplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<String> arrayList;
    ArrayAdapter arrayAdapter;
    MediaPlayer mediaPlayer;
    SeekBar seekBar;
    ImageView play, pause, next, prev;
    TextView playerPosition, playerDuration, scroll_text;

    Handler handler = new Handler();
    Runnable runnable;
    int Pid = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Assign variable
        play = findViewById(R.id.play);
        pause = findViewById(R.id.pause);
        next = findViewById(R.id.next);
        prev = findViewById(R.id.prev);
        scroll_text = findViewById(R.id.scrollTxt);
        seekBar = findViewById(R.id.seek_bar);
        playerPosition = findViewById(R.id.player_position);
        playerDuration = findViewById(R.id.player_duration);
        listView = findViewById(R.id.song_list);
        arrayList = new ArrayList<>();

        // get song names
        Field[] fields = R.raw.class.getFields();
        // add name to list
        for (int i = 0; i < fields.length; ++i) {
            arrayList.add(fields[i].getName());
        }

        arrayAdapter = new ArrayAdapter(this, R.layout.simple_list, arrayList);
        listView.setAdapter(arrayAdapter);

        // on click to song name
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Pid = position;
                start_MediaPlayer(Pid);
                play.setVisibility(View.GONE);
                pause.setVisibility(View.VISIBLE);
            }
        });
        // on play
        play.setOnClickListener(v -> {
            if (Pid == -1) {
                Pid = 0;
                start_MediaPlayer(Pid);
            }
            play.setVisibility(View.GONE);
            pause.setVisibility(View.VISIBLE);
            mediaPlayer.start();
        });
        // on pause
        pause.setOnClickListener(v -> {
            pause.setVisibility(View.GONE);
            play.setVisibility(View.VISIBLE);
            mediaPlayer.pause();
        });
        // on next
        next.setOnClickListener(v -> {
            if (Pid == -1) {
                Pid = 1;
                start_MediaPlayer(Pid);
            } else {
                Pid = Math.abs(Pid + 1) % arrayList.size();
                start_MediaPlayer(Pid);
            }
            play.setVisibility(View.GONE);
            pause.setVisibility(View.VISIBLE);
        });
        // on prev
        prev.setOnClickListener(v -> {
            if (Pid == -1) {
                Pid = arrayList.size() - 1;
                start_MediaPlayer(Pid);
            } else {
                Pid = (Pid - 1) > -1 ? (Pid - 1) : (arrayList.size() - 1);
                start_MediaPlayer(Pid);
            }
            play.setVisibility(View.GONE);
            pause.setVisibility(View.VISIBLE);
        });
    }

    // media player
    private void start_MediaPlayer(int position) {
        // free player
        if (mediaPlayer != null) mediaPlayer.release();
        // get id of song
        int Id = getResources().getIdentifier(arrayList.get(position), "raw", getPackageName());
        // set Text
        scroll_text.setText(arrayList.get(position));
        scroll_text.setSelected(true);
        // Initialize media player
        mediaPlayer = MediaPlayer.create(MainActivity.this, Id);

        // Initialize runnable
        runnable = new Runnable() {
            @Override
            public void run() {
                // set progress on seek bar
                seekBar.setProgress(mediaPlayer.getCurrentPosition());
                // Handler post delay for 0.5 second
                handler.postDelayed(this, 500);
            }
        };
        // Get duration of media player
        int duration = mediaPlayer.getDuration();
        // Convert millisecond to minute and second
        String Duration = convertFormat(duration);
        // set duration text
        playerDuration.setText(Duration);
        // Start media player
        mediaPlayer.start();
        // Set max on seek bar
        seekBar.setMax(mediaPlayer.getDuration());
        // control using seekbar
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) mediaPlayer.seekTo(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        // Start handler
        handler.postDelayed(runnable, 0);
    }

    @SuppressLint("DefaultLocale")
    private String convertFormat(int duration) {
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(duration),
                TimeUnit.MILLISECONDS.toSeconds(duration) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)));
    }
}