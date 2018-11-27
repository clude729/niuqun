package com.daoyu.niuqun.ui;

import java.io.IOException;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;

import com.archie.netlibrary.okhttp.listener.DisposeDataListener;
import com.archie.netlibrary.okhttp.request.RequestParams;
import com.daoyu.chat.SealUserInfoManager;
import com.daoyu.chat.db.Friend;
import com.daoyu.chat.server.pinyin.CharacterParser;
import com.daoyu.niuqun.R;
import com.daoyu.niuqun.bean.AccountInfo;
import com.daoyu.niuqun.bean.ReuserInfo;
import com.daoyu.niuqun.constant.HttpConstant;
import com.daoyu.niuqun.constant.MessageConstant;
import com.daoyu.niuqun.constant.SharePreferenceConstant;
import com.daoyu.niuqun.request.RequestCenter;
import com.daoyu.niuqun.response.LoginResponse;
import com.daoyu.niuqun.ui.chat.PhoneMainActivity;
import com.daoyu.niuqun.ui.user.LoginActivity;
import com.daoyu.niuqun.util.DataBaseHelper;
import com.daoyu.niuqun.util.Logger;
import com.daoyu.niuqun.util.SharePreferenceManager;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

public class SplashActivity extends MyBaseActivity
{

    private static final String TAG = "SplashActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setTranslucentStatus(true);
        startDataBase();
        toLogin();
    }

    //初始化数据库
    private void startDataBase()
    {
        DataBaseHelper myDbHelper = new DataBaseHelper(this);
        try
        {
            myDbHelper.createDataBase();
        }
        catch (IOException ioe)
        {
            Logger.d(TAG, "getQ, IOException: " + ioe);
        }
    }

    private void toLogin()
    {
        String mobile = SharePreferenceManager.getCacheMobile();
        String password = SharePreferenceManager.getKeyCachedPassword();
        String userId = SharePreferenceManager.getKeyCachedUserid();
        if (TextUtils.isEmpty(mobile) || TextUtils.isEmpty(password) || TextUtils.isEmpty(userId))
        {
            Logger.d(TAG, "need go to LoginActivity!");
            goToLogin();
            return;
        }
        RequestParams requestParams = new RequestParams();
        requestParams.put("mobile", mobile);
        requestParams.put("password", password);
        RequestCenter.requesetLoginData(requestParams, new DisposeDataListener()
        {
            @Override
            public void onSuccess(Object responseObj)
            {
                Logger.d(TAG, "toLogin onSuccess!");
                if (null != responseObj && responseObj instanceof LoginResponse)
                {
                    Message msg = handler.obtainMessage();
                    msg.what = MessageConstant.LOGIN;
                    msg.obj = responseObj;
                    handler.sendMessage(msg);
                }
                else
                {
                    handler.sendEmptyMessage(MessageConstant.SPLASH_TO_LOGIN);
                }
            }

            @Override
            public void onFailure(Object responseObj)
            {
                Logger.d(TAG, "toLogin onFailure: " + responseObj.toString());
                showToast(getResources().getString(R.string.http_client_false));
                handler.sendEmptyMessage(MessageConstant.SPLASH_TO_LOGIN);
            }
        });
    }

    private void goToLogin()
    {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void goToMain(String token)
    {
        if (!TextUtils.isEmpty(token))
        {
            RongIM.connect(token, new RongIMClient.ConnectCallback()
            {
                @Override
                public void onSuccess(String s)
                {
                    SealUserInfoManager.getInstance().openDB();
                    goToMain();
                }

                @Override
                public void onError(RongIMClient.ErrorCode errorCode)
                {
                    Logger.e(TAG, "RongIM.connect, onError: " + errorCode);
                }

                @Override
                public void onTokenIncorrect()
                {
                    goToLogin();
                }
            });
        }
        else
        {
            goToLogin();
        }
    }

    private void goToMain()
    {
        Intent intent = new Intent(this, PhoneMainActivity.class);
        startActivity(intent);
        finish();
    }

    private void loginMessage(Message msg)
    {
        LoginResponse loginResponse = (LoginResponse) msg.obj;
        String code = loginResponse.getCode();
        if (HttpConstant.SUCCESS.equals(code))
        {
            AccountInfo accountInfo = loginResponse.getData();
            if (null != accountInfo)
            {
                Logger.d(TAG, "account: " + accountInfo.toString());
                String userId = accountInfo.getUser_id();
                String token = accountInfo.getToken();
                SharePreferenceManager.setKeyCachedUserid(userId);
                SharePreferenceManager.setKeyStringValue(SharePreferenceConstant.IM_TOKEN, token);
                ReuserInfo reuserInfo = accountInfo.getReuser();
                if (null != reuserInfo)
                {
                    SharePreferenceManager.setKeyCachedReUserid(reuserInfo.getUser_id());
                    App app = (App) getApplication();
                    app.setReuserInfo(reuserInfo);
                }
                goToMain(token);
                return;
            }
        }
        goToLogin();
    }

    @Override
    protected void handlerLogicMessage(Message msg)
    {
        super.handlerLogicMessage(msg);
        switch (msg.what)
        {
            case MessageConstant.LOGIN:
                loginMessage(msg);
                break;
            case MessageConstant.SPLASH_TO_LOGIN:
                goToLogin();
                break;
            default:
                break;
        }
    }
}
