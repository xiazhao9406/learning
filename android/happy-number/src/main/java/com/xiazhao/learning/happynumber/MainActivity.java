package com.xiazhao.learning.happynumber;


import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {
     static boolean isPlay = true;
     MediaPlayer mediaPlayer;
     Button music_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        music_btn = findViewById(R.id.btn_music);
        playMusic();
    }

    private void playMusic() {
        mediaPlayer = MediaPlayer.create(this, R.raw.main_music);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    public void OnPlay(View v) {
        startActivity(new Intent(MainActivity.this, SelectActivity.class));
    }

    public void OnAbout(View v) {
        startActivity(new Intent(MainActivity.this, AboutAcitivity.class));
    }

    public void OnMusic(View v) {
        if (isPlay == true) {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                music_btn.setBackgroundResource(R.drawable.btn_music2);
                isPlay = false;
            }
        } else {
            playMusic();
            music_btn.setBackgroundResource(R.drawable.btn_music1);
            isPlay = true;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (isPlay) {
            playMusic();
        }
    }


}