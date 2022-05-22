package com.xiazhao.mytimer;

import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "MainActivityTag";

    private TimePicker timePicker;
    private Button addButton;
    private RecyclerView recyclerView;
    private TimerAdapter timerAdapter;
    private MediaPlayer mediaPlayer;
    private AlertDialog alertDialog;
    private boolean isPlaying;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()");
        setContentView(R.layout.activity_main);
        initial();
    }

    private void initial() {
        isPlaying = false;
        mediaPlayer = MediaPlayer.create(this, R.raw.sound);
        mediaPlayer.setLooping(true);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Confirm to stop sound.");
        alertDialogBuilder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (isPlaying) {
                    mediaPlayer.pause();
                    mediaPlayer.seekTo(0);
                }
                alertDialog.dismiss();
                isPlaying = false;
            }
        });
        alertDialog = alertDialogBuilder.create();

        timePicker = findViewById(R.id.timer);
        timePicker.setIs24HourView(true);
        if (Config.debug) {
            timePicker.setHour(0);
            timePicker.setMinute(3);
        } else {
            timePicker.setHour(1);
            timePicker.setMinute(0);
        }

        recyclerView = findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        timerAdapter = new TimerAdapter(new Handler());
        recyclerView.setAdapter(timerAdapter);
        timerAdapter.setTimeUpListener(new TimeUpListener() {
            @Override
            public void timeUp() {
                Log.i(TAG, "timeUp()");
                if (!isPlaying) {
                    mediaPlayer.start();
                }
                isPlaying = true;
                alertDialog.show();
            }
        });

        addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimerItem timerItem = new TimerItem(timePicker.getHour() * 60 + timePicker.getMinute());
                timerAdapter.addTimer(timerItem);
            }
        });
    }
}