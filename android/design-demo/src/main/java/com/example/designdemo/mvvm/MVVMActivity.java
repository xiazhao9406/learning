package com.example.designdemo.mvvm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.example.designdemo.R;
import com.example.designdemo.databinding.ActivityMvvmactivityBinding;

public class MVVMActivity extends AppCompatActivity {
    private ActivityMvvmactivityBinding binding;
    private MVVMViewModel mvvmViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_mvvmactivity);
        mvvmViewModel = new MVVMViewModel(getApplication(), binding);
        binding.setViewModel(mvvmViewModel);
    }
}