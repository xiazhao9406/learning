package com.example.designdemo.mvp;

import com.example.designdemo.bean.Account;

public interface IMVPView {
    String getUserInput();
    void showSuccessPage(Account account);
    void showFailedPage();
}
