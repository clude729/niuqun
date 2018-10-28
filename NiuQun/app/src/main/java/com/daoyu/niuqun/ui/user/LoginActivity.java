package com.daoyu.niuqun.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.archie.netlibrary.okhttp.listener.DisposeDataListener;
import com.archie.netlibrary.okhttp.request.RequestParams;
import com.daoyu.chat.SealUserInfoManager;
import com.daoyu.niuqun.R;
import com.daoyu.niuqun.bean.AccountInfo;
import com.daoyu.niuqun.constant.ActivityResultConstant;
import com.daoyu.niuqun.constant.HttpConstant;
import com.daoyu.niuqun.constant.MessageConstant;
import com.daoyu.niuqun.constant.SharePreferenceConstant;
import com.daoyu.niuqun.response.LoginResponse;
import com.daoyu.niuqun.request.RequestCenter;
import com.daoyu.niuqun.ui.MyBaseActivity;
import com.daoyu.niuqun.ui.chat.PhoneMainActivity;
import com.daoyu.niuqun.util.Logger;
import com.daoyu.niuqun.util.SharePreferenceManager;
import com.daoyu.niuqun.util.StringUtil;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;

public class LoginActivity extends MyBaseActivity implements View.OnClickListener
{

    private static final String TAG = "LoginActivity";

    private TextView btn_register;

    private EditText et_mobile;

    private EditText et_password;

    private ImageView iv_password;

    private TextView tv_error;

    private Button btn_login;

    private TextView tv_register;

    private TextView tv_forget;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTranslucentStatus(true);
        initView();
        initListener();
    }

    private void initView()
    {
        View view_top = findViewById(R.id.view_top);
        view_top.getLayoutParams().height = getResources().getDisplayMetrics().widthPixels * 7 / 18;
        btn_register = findViewById(R.id.btn_register);
        et_mobile = findViewById(R.id.et_mobile);
        et_password = findViewById(R.id.et_password);
        iv_password = findViewById(R.id.iv_password);
        tv_error = findViewById(R.id.tv_error);
        tv_error.setVisibility(View.INVISIBLE);
        btn_login = findViewById(R.id.btn_login);
        tv_register = findViewById(R.id.tv_register);
        tv_forget = findViewById(R.id.tv_forget);
    }

    private void initListener()
    {
        btn_register.setOnClickListener(this);
        iv_password.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        tv_register.setOnClickListener(this);
        tv_forget.setOnClickListener(this);
    }

    private void passwordShowOrHide()
    {
        if (null == et_password)
        {
            Logger.w(TAG, "passwordShowOrHide, no et_password, return!");
            return;
        }
        if (null == iv_password.getTag() || 0 == (int) (iv_password.getTag()))
        {
            iv_password.setTag(1);
            iv_password.setImageResource(R.mipmap.icon_password_show);
            et_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        }
        else
        {
            iv_password.setTag(0);
            iv_password.setImageResource(R.mipmap.icon_password_hide);
            et_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
    }

    private void toLogin()
    {
        String username = et_mobile.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        if (TextUtils.isEmpty(username))
        {
            showError(getResources().getString(R.string.error_mobile_empty));
        }
        else if (!StringUtil.isMobileNO(username))
        {
            showError(getResources().getString(R.string.error_mobile_number));
        }
        else if (TextUtils.isEmpty(password))
        {
            showError(getResources().getString(R.string.error_password_empty));
        }
        else if (password.length() < 6)
        {
            showError(getResources().getString(R.string.error_password_length));
        }
        else
        {
            RequestParams requestParams = new RequestParams();
            requestParams.put("mobile", username);
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
                        Logger.d(TAG, "Login onSuccess, but error: " + responseObj);
                        handler.sendEmptyMessage(MessageConstant.LOGIN_SUCCESS_ERROR);
                    }
                }

                @Override
                public void onFailure(Object responseObj)
                {
                    Logger.d(TAG, "toLogin onFailure: " + responseObj.toString());
                    showToast(getResources().getString(R.string.http_client_false));
                }
            });
        }
    }

    private void showError(String error)
    {
        if (null == tv_error)
        {
            Logger.w(TAG, "showError, no tv_error, return!");
            return;
        }
        if (null != error && !TextUtils.isEmpty(error))
        {
            tv_error.setText(error);
            tv_error.setVisibility(View.VISIBLE);
        }
        else
        {
            tv_error.setVisibility(View.INVISIBLE);
        }
    }

    private void loginMessage(Message msg)
    {
        LoginResponse loginResponse = (LoginResponse) msg.obj;
        String code = loginResponse.getCode();
        if (HttpConstant.SUCCESS.equals(code))
        {
            showError(null);
            AccountInfo accountInfo = loginResponse.getData();
            if (null != accountInfo)
            {
                Logger.d(TAG, "account: " + accountInfo.toString());
                String userId = accountInfo.getUser_id();
                String token = accountInfo.getToken();
                SharePreferenceManager.setCachedUsername(et_mobile.getText().toString().trim());
                SharePreferenceManager.setCacheMobile(et_mobile.getText().toString().trim());
                SharePreferenceManager.setKeyCachedPassword(et_password.getText().toString().trim());
                SharePreferenceManager.setKeyCachedUserid(userId);
                SharePreferenceManager.setKeyStringValue(SharePreferenceConstant.IM_TOKEN, token);
                goToMain(token);
                return;
            }
        }
        showError(loginResponse.getMessage());
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
            case MessageConstant.LOGIN_SUCCESS_ERROR:
                showError(getResources().getString(R.string.error_from_service));
                break;
            default:
                break;
        }
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
                    showToast("请重新登录！");
                }

                @Override
                public void onTokenIncorrect()
                {
                    Logger.e(TAG, "RongIM.connect, onTokenIncorrect!");
                    showToast("请重新登录！");
                }
            });
        }
        else
        {
            showToast("请重新登录！");
        }
    }

    private void goToMain()
    {
        Intent intent = new Intent(this, PhoneMainActivity.class);
        startActivity(intent);
        finish();
    }

    private void goToRegister()
    {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivityForResult(intent, ActivityResultConstant.LOGIN);
    }

    private void goToForgetActivity()
    {
        Intent intent = new Intent(this, ForgetActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ActivityResultConstant.REGISTER && resultCode == ActivityResultConstant.REGISTER
            && null != data)
        {
            String token = data.getStringExtra("token");
            goToMain(token);
        }
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_register:
                goToRegister();
                break;
            case R.id.iv_password:
                passwordShowOrHide();
                break;
            case R.id.btn_login:
                toLogin();
                break;
            case R.id.tv_register:
                goToRegister();
                break;
            case R.id.tv_forget:
                goToForgetActivity();
                break;
            default:
                break;
        }
    }
}
