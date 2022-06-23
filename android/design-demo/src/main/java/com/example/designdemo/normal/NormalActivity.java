package com.example.designdemo.normal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.designdemo.R;
import com.example.designdemo.bean.Account;
import com.example.designdemo.callback.MCallBack;

import java.util.Random;

public class NormalActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText mEtAccount;
    private TextView mTvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal);
        initView();
    }

    private void initView() {
        mEtAccount = findViewById(R.id.et_account);
        mTvResult = findViewById(R.id.tv_result);
        findViewById(R.id.btn_getAccount).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String name = getUserInput();
        getAccountData(name, new MCallBack() {
            @Override
            public void onSuccess(Account account) {
                showSuccessPage(account);
            }

            @Override
            public void onFailed() {
                showFailedPage();
            }
        });
    }

    private String getUserInput() {
        return mEtAccount.getText().toString();
    }

    private void showSuccessPage(Account account) {
        mTvResult.setText("account: " + account.getName() + " | " + "level: " + account.getLevel());
    }

    private void showFailedPage() {
        mTvResult.setText("failed ");
    }

    private void getAccountData(String name, MCallBack callBack) {
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