package com.xiazhao.mytimer;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "MainActivityTag";

    private TimePicker timePicker;
    private Button addButton;
    private RecyclerView recyclerView;
    private TimerAdapter timerAdapter;
    private Button deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()");
        setContentView(R.layout.activity_main);
        initial();
    }

    private void initial() {
        timePicker = findViewById(R.id.timer);
        timePicker.setIs24HourView(true);
        timePicker.setHour(1);
        timePicker.setMinute(0);

        recyclerView = findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        timerAdapter = new TimerAdapter(new Handler());
        recyclerView.setAdapter(timerAdapter);

        addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimerItem timerItem = new TimerItem(timePicker.getHour(), timePicker.getMinute());
                timerAdapter.addTimer(timerItem);
            }
        });

        deleteButton = findViewById(R.id.delete_button);
    }
}