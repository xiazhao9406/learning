package com.example.designdemo.mvp;

import com.example.designdemo.bean.Account;
import com.example.designdemo.callback.MCallBack;

public class MVPPresenter {
    private MVPModel mvpModel;
    private IMVPView mvpView;

    public MVPPresenter(IMVPView mvpView) {
        this.mvpView = mvpView;
        mvpModel = new MVPModel();
    }

    public void getData(String userInput) {
        mvpModel.getAccountData(userInput, new MCallBack() {
            @Override
            public void onSuccess(Account account) {
                mvpView.showSuccessPage(account);
            }

            @Override
            public void onFailed() {
                mvpView.showFailedPage();
            }
        });
    }
}
