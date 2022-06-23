package com.example.designdemo.demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.widget.Toast;

import com.example.designdemo.R;
import com.example.designdemo.bean.Account;
import com.example.designdemo.databinding.ActivityDemoBinding;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DemoActivity extends AppCompatActivity {
    private Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDemoBinding binding
                = DataBindingUtil.setContentView(this, R.layout.activity_demo);
        account = new Account("Test", 100);
        binding.setAccount(account);
        binding.setActivity(this);
    }

    public void onclick(Account account, long timestamp) {
        Toast.makeText(this, "clicked " + account.getLevel() + " @ " + getTime(timestamp), Toast.LENGTH_LONG).show();
        int level = account.getLevel();
        account.setLevel(level + 1);
    }

    private String getTime(long timestamp) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy' 'HH:mm:ss:S");
        return simpleDateFormat.format(timestamp);
    }
}