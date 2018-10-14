package com.daoyu.niuqun.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;

import com.daoyu.niuqun.R;
import com.daoyu.niuqun.ui.user.LoginActivity;

public class SplashActivity extends BaseActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        handler.sendEmptyMessageDelayed(1, 1000);
    }

    private void goToLogin()
    {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void handlerLogicMessage(Message msg) {
        super.handlerLogicMessage(msg);
        goToLogin();
    }
}
