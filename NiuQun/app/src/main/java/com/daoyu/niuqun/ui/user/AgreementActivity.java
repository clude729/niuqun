package com.daoyu.niuqun.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.daoyu.chat.SealUserInfoManager;
import com.daoyu.chat.SealUserInfoManager.ResultCallback;
import com.daoyu.niuqun.R;
import com.daoyu.niuqun.constant.HttpConstant;
import com.daoyu.niuqun.ui.MyBaseActivity;
import com.daoyu.niuqun.util.Logger;

/**
 * 显示web内容
 */
public class AgreementActivity extends MyBaseActivity
{

    public static final String WEB_TYPE = "web_type";

    public static final String WEB_URL = "web_url";

    //隐私协议
    public static final int WEB_PROVISION = 0;

    private static final String TAG = "AgreementActivity";

    private WebView webView;

    private String url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agreement);
        setTranslucentStatus();
        initView();
        loadContent();
    }

    private void initView()
    {
        updataStartImageView(R.mipmap.icon_back);
        webView = findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setDefaultTextEncodingName("UTF-8");
        Intent intent = getIntent();
        int type = intent.getIntExtra(WEB_TYPE, 0);
        switch (type)
        {
            case WEB_PROVISION:
                updataTitle(getResources().getString(R.string.app_privacy));
                url = HttpConstant.REG_PROVISION;
                break;
            default:
                break;
        }
    }

    private void loadContent()
    {
        if (!TextUtils.isEmpty(url))
        {
            SealUserInfoManager.getInstance().getWebContent(url, new ResultCallback<String>()
            {
                @Override
                public void onSuccess(String s)
                {
                    showContent(s);
                }

                @Override
                public void onError(String errString)
                {
                    Logger.d(TAG, "loadContent, error: " + errString);
                }
            });
        }
    }

    private void showContent(String content)
    {
        if (TextUtils.isEmpty(content))
        {
            Logger.d(TAG, "showContent, content is null, return!");
            return;
        }
        Logger.d(TAG, "showContent, success!");
        webView.loadData(content, HttpConstant.WEB_TYPE, null);
    }

    @Override
    public void onStartImageViewClicked(View view) {
        super.onStartImageViewClicked(view);
        onBackPressed();
    }
}
