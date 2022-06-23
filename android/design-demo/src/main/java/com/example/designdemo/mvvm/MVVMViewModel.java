package com.example.designdemo.mvvm;

import android.app.Application;
import android.view.View;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.designdemo.BR;
import com.example.designdemo.bean.Account;
import com.example.designdemo.callback.MCallBack;
import com.example.designdemo.databinding.ActivityMvvmactivityBinding;

public class MVVMViewModel extends BaseObservable {

    private String result;
    private MVVMModel mvvmModel;
    private ActivityMvvmactivityBinding binding;
    private String userInput;


    public MVVMViewModel(Application application) {
        mvvmModel = new MVVMModel();
    }

    public MVVMViewModel(Application application, ActivityMvvmactivityBinding binding) {
        mvvmModel = new MVVMModel();
        this.binding = binding;
    }

    @Bindable
    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
        notifyPropertyChanged(BR.result);
    }

    public void getData(View view) {
        mvvmModel.getAccountData(userInput, new MCallBack() {
            @Override
            public void onSuccess(Account account) {
                String info = account.getName() + "|" + account.getLevel();
                setResult(info);
            }

            @Override
            public void onFailed() {
                setResult("failed ");
            }
        });
    }

    @Bindable
    public String getUserInput() {
        return userInput;
    }

    public void setUserInput(String userInput) {
        this.userInput = userInput;
        notifyPropertyChanged(BR.userInput);
    }
}
