package com.example.designdemo.mvp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.designdemo.R;
import com.example.designdemo.bean.Account;
import com.example.designdemo.mvc.MVCModel;

public class MVPActivity extends AppCompatActivity implements View.OnClickListener, IMVPView{

    private EditText mEtAccount;
    private TextView mTvResult;
    private MVPPresenter mvpPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal);
        initView();
    }

    private void initView() {
        mEtAccount = findViewById(R.id.et_account);
        mTvResult = findViewById(R.id.tv_result);
        mvpPresenter = new MVPPresenter(this);
        findViewById(R.id.btn_getAccount).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        mvpPresenter.getData(getUserInput());
    }

    @Override
    public String getUserInput() {
        return mEtAccount.getText().toString();
    }

    @Override
    public void showSuccessPage(Account account) {
        mTvResult.setText("account: " + account.getName() + " | " + "level: " + account.getLevel());
    }

    @Override
    public void showFailedPage() {
        mTvResult.setText("failed ");
    }
}