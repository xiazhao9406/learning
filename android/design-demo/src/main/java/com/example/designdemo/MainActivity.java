package com.example.designdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.designdemo.demo.DemoActivity;
import com.example.designdemo.mvc.MVCActivity;
import com.example.designdemo.mvp.MVPActivity;
import com.example.designdemo.mvvm.MVVMActivity;
import com.example.designdemo.normal.NormalActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initial();
    }

    private void initial() {
        findViewById(R.id.btn_start).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(MainActivity.this, DemoActivity.class);
        MainActivity.this.startActivity(intent);
    }
}