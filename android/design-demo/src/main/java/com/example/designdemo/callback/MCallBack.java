package com.example.designdemo.callback;

import com.example.designdemo.bean.Account;

public interface MCallBack {
    void onSuccess(Account account);
    void onFailed();
}
