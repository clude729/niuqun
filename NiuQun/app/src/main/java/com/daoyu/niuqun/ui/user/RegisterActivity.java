package com.daoyu.niuqun.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Message;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.archie.netlibrary.okhttp.listener.DisposeDataListener;
import com.archie.netlibrary.okhttp.request.RequestParams;
import com.daoyu.niuqun.R;
import com.daoyu.niuqun.bean.AccountInfo;
import com.daoyu.niuqun.constant.ActivityResultConstant;
import com.daoyu.niuqun.constant.HttpConstant;
import com.daoyu.niuqun.constant.MessageConstant;
import com.daoyu.niuqun.constant.SharePreferenceConstant;
import com.daoyu.niuqun.request.RequestCenter;
import com.daoyu.niuqun.response.RegisterRespones;
import com.daoyu.niuqun.response.VefifyCodeResponse;
import com.daoyu.niuqun.ui.MyBaseActivity;
import com.daoyu.niuqun.util.Logger;
import com.daoyu.niuqun.util.SharePreferenceManager;
import com.daoyu.niuqun.util.StringUtil;

public class RegisterActivity extends MyBaseActivity implements OnClickListener
{

    private static final String TAG = "RegisterActivity";

    private EditText etMobile;

    private EditText etCode;

    private TextView tvGetCode;

    private EditText etPassword;

    private ImageView ivPassword;

    private EditText etPasswordAgain;

    private EditText etRecommend;

    private ImageView ivAgreement;

    private TextView tvAgreement;

    private Button btnRegister;

    private TextView tvForget;

    private TextView tvLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTranslucentStatus();
        initView();
        initListener();
    }

    private void initView()
    {
        updataTitle(getResources().getString(R.string.the_register));
        etMobile = findViewById(R.id.et_mobile);
        etCode = findViewById(R.id.et_code);
        tvGetCode = findViewById(R.id.text_getcode);
        etPassword = findViewById(R.id.et_password);
        ivPassword = findViewById(R.id.iv_password);
        etPasswordAgain = findViewById(R.id.et_password_again);
        etRecommend = findViewById(R.id.et_recommend);
        ivAgreement = findViewById(R.id.image_agreement);
        tvAgreement = findViewById(R.id.text_agreement);
        btnRegister = findViewById(R.id.btn_register);
        tvForget = findViewById(R.id.tv_forget);
        tvLogin = findViewById(R.id.tv_login);
    }

    private void initListener()
    {
        tvGetCode.setOnClickListener(this);
        ivPassword.setOnClickListener(this);
        ivAgreement.setOnClickListener(this);
        tvAgreement.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        tvForget.setOnClickListener(this);
        tvLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.text_getcode:
                getCode();
                break;
            case R.id.iv_password:
                passwordShowOrHide();
                break;
            case R.id.image_agreement:
                checkAgreement();
                break;
            case R.id.text_agreement:
                goToAgreementActivity();
                break;
            case R.id.btn_register:
                registerReady();
                break;
            case R.id.tv_forget:
                goToForgetActivity();
                break;
            case R.id.tv_login:
                goToLogin();
                break;
            default:
                break;
        }
    }

    @Override
    public void onStartImageViewClicked(View view)
    {
        super.onStartImageViewClicked(view);
        onBackPressed();
    }

    @Override
    protected void handlerLogicMessage(Message msg)
    {
        super.handlerLogicMessage(msg);
        switch (msg.what)
        {
            case MessageConstant.VERIFY_CODE:
                verifyMessage(msg);
                break;
            case MessageConstant.REGISTER:
                registerMessage(msg);
                break;
            case MessageConstant.REGISTER_SUCCESS_ERROR:
                showToast(getResources().getString(R.string.error_from_service));
                break;
            default:
                break;
        }
    }

    private void getCode()
    {
        String username = etMobile.getText().toString();
        if (TextUtils.isEmpty(username))
        {
            showToast(getResources().getString(R.string.error_mobile_empty));
        }
        else if (!StringUtil.isMobileNO(username))
        {
            showToast(getResources().getString(R.string.error_mobile_number));
        }
        else
        {
            RequestParams requestParams = new RequestParams();
            requestParams.put("mobile", username);
            RequestCenter.requesetGetCode(requestParams, new DisposeDataListener()
            {
                @Override
                public void onSuccess(Object responseObj)
                {
                    Logger.d(TAG, "getCode onSuccess!");
                    if (null != responseObj && responseObj instanceof VefifyCodeResponse)
                    {
                        Message msg = handler.obtainMessage();
                        msg.what = MessageConstant.VERIFY_CODE;
                        msg.obj = responseObj;
                        handler.sendMessage(msg);
                    }
                    else
                    {
                        Logger.d(TAG, "getCode onSuccess, but error: " + responseObj);
                        handler.sendEmptyMessage(MessageConstant.REGISTER_SUCCESS_ERROR);
                    }
                }

                @Override
                public void onFailure(Object responseObj)
                {
                    Logger.d(TAG, "getCode onFailure: " + responseObj.toString());
                    showToast(getResources().getString(R.string.http_client_false));
                }
            });
        }
    }

    private void toRegister(String username, String password, String code, String recommend)
    {
        RequestParams requestParams = new RequestParams();
        requestParams.put("mobile", username);
        requestParams.put("password", password);
        requestParams.put("smscode", code);
        requestParams.put("npassword", password);
        requestParams.put("recode", recommend);
        RequestCenter.requesetRegister(requestParams, new DisposeDataListener()
        {
            @Override
            public void onSuccess(Object responseObj)
            {
                Logger.d(TAG, "toRegister onSuccess!");
                if (null != responseObj && responseObj instanceof RegisterRespones)
                {
                    Message msg = handler.obtainMessage();
                    msg.what = MessageConstant.REGISTER;
                    msg.obj = responseObj;
                    handler.sendMessage(msg);
                }
                else 
                {
                    Logger.d(TAG, "Register onSuccess, but error: " + responseObj);
                    handler.sendEmptyMessage(MessageConstant.REGISTER_SUCCESS_ERROR);
                }
            }

            @Override
            public void onFailure(Object responseObj)
            {
                Logger.d(TAG, "toRegister onFailure: " + responseObj.toString());
                showToast(getResources().getString(R.string.http_client_false));
            }
        });
    }

    private void registerReady()
    {
        String username = etMobile.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String again = etPasswordAgain.getText().toString().trim();
        String code = etCode.getText().toString().trim();
        Logger.d(TAG, username + ", " + password + ", " + again + ", " + code);
        if (TextUtils.isEmpty(username))
        {
            showToast(getResources().getString(R.string.error_mobile_empty));
        }
        else if (!StringUtil.isMobileNO(username))
        {
            showToast(getResources().getString(R.string.error_mobile_number));
        }
        else if (TextUtils.isEmpty(code))
        {
            showToast(getResources().getString(R.string.error_verify_code_empty));
        }
        else if (TextUtils.isEmpty(password))
        {
            showToast(getResources().getString(R.string.error_password_empty));
        }
        else if (!password.equals(again))
        {
            showToast(getResources().getString(R.string.error_password_no_same));
        }
        else if (password.length() < 6)
        {
            showToast(getResources().getString(R.string.error_password_length));
        }
        else
        {
            String recommend = etRecommend.getText().toString().trim();
            toRegister(username, password, code, recommend);
        }
    }

    private void verifyMessage(Message msg)
    {
        VefifyCodeResponse vefifyCodeResponse = (VefifyCodeResponse) msg.obj;
        if (HttpConstant.SUCCESS.equals(vefifyCodeResponse.getCode()))
        {
            new CodeTimeCount(60000, 1000, tvGetCode).start();
        }
        else
        {
            showToast(vefifyCodeResponse.getMessage());
        }
    }

    private void registerMessage(Message msg)
    {
        RegisterRespones registerRespones = (RegisterRespones) msg.obj;
        Logger.d(TAG, "registerMessage, code: " + registerRespones.getCode());
        if (HttpConstant.SUCCESS.equals(registerRespones.getCode()))
        {
            AccountInfo accountInfo = registerRespones.getData();
            if (null != accountInfo)
            {
                Logger.d(TAG, "registerMessage, account: " + accountInfo.toString());
                SharePreferenceManager.setCachedUsername(etMobile.getText().toString().trim());
                SharePreferenceManager.setCacheMobile(etMobile.getText().toString().trim());
                SharePreferenceManager.setKeyCachedUserid(accountInfo.getUser_id());
                SharePreferenceManager.setKeyCachedPassword(etPassword.getText().toString().trim());
                SharePreferenceManager.setKeyStringValue(SharePreferenceConstant.IM_TOKEN, accountInfo.getToken());
                goToMain(accountInfo.getToken());
            }
        }
        else
        {
            showToast(registerRespones.getMessage());
        }
    }

    private void passwordShowOrHide()
    {
        if (null == etPassword || null == etPasswordAgain)
        {
            Logger.w(TAG, "passwordShowOrHide, no et_password, return!");
            return;
        }
        if (null == ivPassword.getTag() || 0 == (int) (ivPassword.getTag()))
        {
            ivPassword.setTag(1);
            ivPassword.setImageResource(R.mipmap.icon_password_show);
            etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            etPasswordAgain.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        }
        else
        {
            ivPassword.setTag(0);
            ivPassword.setImageResource(R.mipmap.icon_password_hide);
            etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            etPasswordAgain.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
    }

    private void goToMain(String token)
    {
        Intent intent = getIntent();
        intent.putExtra("token", token);
        setResult(ActivityResultConstant.REGISTER, intent);
        onBackPressed();
    }

    private void goToAgreementActivity()
    {
        Intent intent = new Intent(this, AgreementActivity.class);
        startActivity(intent);
    }

    private void goToLogin()
    {
        finish();
//        Intent intent = new Intent(this, LoginActivity.class);
//        startActivity(intent);
    }

    private void goToForgetActivity()
    {
        Intent intent = new Intent(this, ForgetActivity.class);
        startActivity(intent);
    }

    private void checkAgreement()
    {
        if (null == ivAgreement.getTag() || 1 == (int) (ivAgreement.getTag()))
        {
            ivAgreement.setTag(0);
            ivAgreement.setImageResource(R.mipmap.agreement_unselect);
        }
        else
        {
            ivAgreement.setTag(1);
            ivAgreement.setImageResource(R.mipmap.agreement_select);
        }
    }

    private class CodeTimeCount extends CountDownTimer
    {

        private TextView tvCode;

        private CodeTimeCount(long millisInFuture, long countDownInterval, TextView textView)
        {
            //参数依次为总时长,和计时的时间间隔,显示控件
            super(millisInFuture, countDownInterval);
            tvCode = textView;
        }

        @Override
        public void onFinish()
        {
            //计时完毕时触发
            tvCode.setText(getResources().getString(R.string.get_verify_code_again));
            tvCode.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished)
        {
            //计时过程显示
            tvCode.setClickable(false);
            String time = millisUntilFinished / 1000 + getResources().getString(R.string.the_second);
            tvCode.setText(time);
        }
    }

}
