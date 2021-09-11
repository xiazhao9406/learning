package com.xiazhao.learning.happynumber;


import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

public class SelectActivity extends Activity {
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        if (MainActivity.isPlay) {
            playMusic();
        }
    }

    private void playMusic() {
        mediaPlayer = MediaPlayer.create(this, R.raw.number_music);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
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
        if (MainActivity.isPlay) {
            playMusic();
        }
    }

    public void onOne(View v) {
        startActivity(new Intent(this, OneActivity.class));
    }
    public void onTwo(View v) {
        startActivity(new Intent(this, TwoActivity.class));
    }
    public void onThree(View v) {
        startActivity(new Intent(this, ThreeActivity.class));
    }
    public void onFour(View v) {
        startActivity(new Intent(this, FourActivity.class));
    }
    public void onFive(View v) {
        startActivity(new Intent(this, FiveActivity.class));
    }
    public void onSix(View v) {
        startActivity(new Intent(this, SixActivity.class));
    }
    public void onSeven(View v) {
        startActivity(new Intent(this, SevenActivity.class));
    }
    public void onEight(View v) {
        startActivity(new Intent(this, EightActivity.class));
    }
    public void onNine(View v) {
        startActivity(new Intent(this, NineActivity.class));
    }
    public void onZero(View v) {
        startActivity(new Intent(this, ZeroActivity.class));
    }
}