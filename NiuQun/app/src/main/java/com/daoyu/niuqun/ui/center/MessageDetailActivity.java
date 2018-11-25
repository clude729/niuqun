package com.daoyu.niuqun.ui.center;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.daoyu.chat.server.network.http.HttpException;
import com.daoyu.chat.server.response.AppMessageResponse;
import com.daoyu.chat.server.utils.NToast;
import com.daoyu.chat.server.widget.LoadDialog;
import com.daoyu.chat.ui.activity.BaseActivity;
import com.daoyu.niuqun.R;
import com.daoyu.niuqun.bean.AppMessage;
import com.daoyu.niuqun.constant.HttpConstant;
import com.daoyu.niuqun.constant.IntentConstant;
import com.daoyu.niuqun.constant.ResponseConstant;
import com.daoyu.niuqun.util.Logger;

/**
 * 系统信息详情
 */
public class MessageDetailActivity extends BaseActivity
{

    private static final String TAG = "MessageDetailActivity";
    
    private TextView tvTitle;
    
    private TextView tvTime;
    
    private WebView webView;
    
    private String msgId = "";
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);
        initView();
        initData();
    }
    
    private void initView()
    {
        setTitle(R.string.app_system_message);
        tvTitle = findViewById(R.id.tv_web_title);
        tvTime = findViewById(R.id.tv_web_time);
        webView = findViewById(R.id.webview);
        WebSettings webSettings = webView.getSettings();
        webSettings.setDefaultTextEncodingName("UTF-8");
    }
    
    private void initData()
    {
        Intent intent = getIntent();
        msgId = intent.getStringExtra(IntentConstant.MESSAGE_ID);
        LoadDialog.show(mContext);
        request(ResponseConstant.APP_MESSAGE_DETAIL, true);
    }

    @Override
    public Object doInBackground(int requestCode, String id) throws HttpException
    {
        switch (requestCode)
        {
            case ResponseConstant.APP_MESSAGE_DETAIL:
                return action.getAppMessageById(msgId);
            default:
                break;
        }
        return super.doInBackground(requestCode, id);
    }

    @Override
    public void onSuccess(int requestCode, Object result)
    {
        switch (requestCode)
        {
            case ResponseConstant.APP_MESSAGE_DETAIL:
                LoadDialog.dismiss(mContext);
                if (result instanceof AppMessageResponse)
                {
                    AppMessageResponse response = (AppMessageResponse) result;
                    if (HttpConstant.SUCCESS.equals(response.getCode()))
                    {
                        AppMessage message = response.getData();
                        if (null != message)
                        {
                            String title = message.getTitle();
                            String time = message.getAdd_time();
                            String content = message.getContent();
                            if (!TextUtils.isEmpty(time))
                            {
                                tvTime.setText(time);
                                tvTime.setVisibility(View.VISIBLE);
                            }
                            else
                            {
                                tvTime.setVisibility(View.GONE);
                            }

                            if (!TextUtils.isEmpty(title))
                            {
                                tvTitle.setText(title);
                                tvTitle.setVisibility(View.VISIBLE);
                            }
                            else
                            {
                                tvTitle.setVisibility(View.GONE);
                            }
                            if (!TextUtils.isEmpty(content))
                            {
                                webView.loadData(content, HttpConstant.WEB_TYPE, null);
                            }
                        }
                    }
                    else
                    {
                        Logger.d(TAG, "get messagedetail, but code: " + response.getCode());
                        NToast.shortToast(mContext, response.getMessage());
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onFailure(int requestCode, int state, Object result)
    {
        switch (requestCode)
        {
            case ResponseConstant.APP_MESSAGE_DETAIL:
                LoadDialog.dismiss(mContext);
                NToast.shortToast(mContext, getResources().getString(R.string.http_client_false));
                break;
            default:
                break;
        }
    }
}
