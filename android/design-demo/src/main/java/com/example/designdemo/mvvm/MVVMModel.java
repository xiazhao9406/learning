package com.example.designdemo.mvvm;

import com.example.designdemo.bean.Account;
import com.example.designdemo.callback.MCallBack;

import java.util.Random;

public class MVVMModel {
    public void getAccountData(String name, MCallBack callBack) {
        Random randomBoolean = new Random();
        Boolean isSuccess = randomBoolean.nextBoolean();
        Account account = new Account(name, 100);
        if (isSuccess) {
            callBack.onSuccess(account);
        } else {
            callBack.onFailed();
        }
    }
}
