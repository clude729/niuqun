package com.daoyu.niuqun.ui.center;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.daoyu.chat.server.network.http.HttpException;
import com.daoyu.chat.server.response.BaseSealResponse;
import com.daoyu.chat.server.utils.NToast;
import com.daoyu.chat.server.widget.LoadDialog;
import com.daoyu.chat.ui.activity.BaseActivity;
import com.daoyu.niuqun.R;
import com.daoyu.niuqun.constant.ActivityResultConstant;
import com.daoyu.niuqun.constant.HttpConstant;
import com.daoyu.niuqun.constant.IntentConstant;
import com.daoyu.niuqun.constant.ResponseConstant;
import com.daoyu.niuqun.util.EventManager;
import com.daoyu.niuqun.util.SharePreferenceManager;

import io.rong.eventbus.EventBus;

/**
 * 更新用户信息
 */
public class TextEditActivity extends BaseActivity
{
    public static final int SIGN = 3;

    public static final int APP_NUMBER = 2;

    public static final int NICKNAME = 1;

    private static final String TAG = "TextEditActivity";

    private EditText etName;

    private EditText etNum;

    private EditText etSign;

    private int type;

    private String userName;

    private String appNumber;

    private String sign;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_edit);
        initView();
    }

    private void initView()
    {
        mHeadRightText.setText(R.string.to_save);
        mHeadRightText.setVisibility(View.VISIBLE);
        etName = findViewById(R.id.et_nickname);
        etNum = findViewById(R.id.et_num);
        etSign = findViewById(R.id.et_sign);
        type = getIntent().getIntExtra(IntentConstant.UPDATA_TYPE, NICKNAME);
        switch (type)
        {
            case NICKNAME:
                setTitle(R.string.change_nickname);
                etName.setVisibility(View.VISIBLE);
                etName.setText(SharePreferenceManager.getCachedUsername());
                break;
            case APP_NUMBER:
                setTitle(R.string.change_niuqun_num);
                etNum.setVisibility(View.VISIBLE);
                etNum.setText(SharePreferenceManager.getCacheHerdNo());
                break;
            case SIGN:
                setTitle(R.string.change_sign);
                etSign.setVisibility(View.VISIBLE);
                etSign.setText(SharePreferenceManager.getCacheSign());
                break;
            default:
                break;
        }
    }

    private void updataName()
    {
        String name = etName.getText().toString().trim();
        if (TextUtils.isEmpty(name))
        {
            NToast.shortToast(mContext, "名字不能为空。");
        }
        else if (name.equals(SharePreferenceManager.getCachedUsername()))
        {
            NToast.shortToast(mContext, "名字未更改，无需提交。");
        }
        else
        {
            LoadDialog.show(mContext);
            userName = name;
            request(ResponseConstant.UPDATA_NICKNAME, true);
        }
    }

    private void updataNumber()
    {
        String name = etNum.getText().toString().trim();
        if (TextUtils.isEmpty(name))
        {
            NToast.shortToast(mContext, "牛群号不能为空。");
        }
        else if (name.equals(SharePreferenceManager.getCacheHerdNo()))
        {
            NToast.shortToast(mContext, "牛群号未更改，无需提交。");
        }
        else
        {
            LoadDialog.show(mContext);
            appNumber = name;
            request(ResponseConstant.UPDATA_NIUQUN_NUM, true);
        }
    }

    private void updataSign()
    {
        String name = etSign.getText().toString().trim();
        if (name.equals(SharePreferenceManager.getCacheSign()))
        {
            NToast.shortToast(mContext, "签名未更改，无需提交。");
        }
        else
        {
            LoadDialog.show(mContext);
            sign = name;
            request(ResponseConstant.UPDATA_SIGN, true);
        }
    }

    @Override
    public void onHeadRightTextViewClick(View view)
    {
        switch (type)
        {
            case NICKNAME:
                updataName();
                break;
            case APP_NUMBER:
                updataNumber();
                break;
            case SIGN:
                updataSign();
                break;
            default:
                break;
        }
    }

    @Override
    public Object doInBackground(int requestCode, String id) throws HttpException
    {
        switch (requestCode)
        {
            case ResponseConstant.UPDATA_NICKNAME:
                return action.updataUserName(userName);
            case ResponseConstant.UPDATA_NIUQUN_NUM:
                return action.updataNiuqunNumber(appNumber);
            case ResponseConstant.UPDATA_SIGN:
                return action.updataSign(sign);
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
            case ResponseConstant.UPDATA_NICKNAME:
                LoadDialog.dismiss(mContext);
                if (result instanceof BaseSealResponse)
                {
                    BaseSealResponse response = (BaseSealResponse) result;
                    if (HttpConstant.SUCCESS.equals(response.getCode()))
                    {
                        NToast.shortToast(mContext, "修改成功");
                        SharePreferenceManager.setCachedUsername(userName);
                        EventManager.PersonInfoSuccess event = new EventManager.PersonInfoSuccess();
                        event.setType(ActivityResultConstant.UPDATA_USERNAME);
                        EventBus.getDefault().post(event);
                        finish();
                    }
                    else
                    {
                        NToast.shortToast(mContext, response.getMessage());
                    }
                }
                break;
            case ResponseConstant.UPDATA_NIUQUN_NUM:
                LoadDialog.dismiss(mContext);
                if (result instanceof BaseSealResponse)
                {
                    BaseSealResponse response = (BaseSealResponse) result;
                    if (HttpConstant.SUCCESS.equals(response.getCode()))
                    {
                        NToast.shortToast(mContext, "设置成功");
                        SharePreferenceManager.setCacheHerdNo(appNumber);
                        EventManager.PersonInfoSuccess event = new EventManager.PersonInfoSuccess();
                        event.setType(ActivityResultConstant.UPDATA_NIUQUN_NUMBER);
                        EventBus.getDefault().post(event);
                        finish();
                    }
                    else
                    {
                        NToast.shortToast(mContext, response.getMessage());
                    }
                }
                break;
            case ResponseConstant.UPDATA_SIGN:
                LoadDialog.dismiss(mContext);
                if (result instanceof BaseSealResponse)
                {
                    BaseSealResponse response = (BaseSealResponse) result;
                    if (HttpConstant.SUCCESS.equals(response.getCode()))
                    {
                        NToast.shortToast(mContext, "设置成功");
                        SharePreferenceManager.setCacheSign(sign);
                        finish();
                    }
                    else
                    {
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
        LoadDialog.dismiss(mContext);
        switch (requestCode)
        {
            case ResponseConstant.UPDATA_NICKNAME:
                NToast.shortToast(mContext, getResources().getString(R.string.http_client_false));
                break;
            case ResponseConstant.UPDATA_NIUQUN_NUM:
                NToast.shortToast(mContext, getResources().getString(R.string.http_client_false));
                break;
            case ResponseConstant.UPDATA_SIGN:
                NToast.shortToast(mContext, getResources().getString(R.string.http_client_false));
                break;
            default:
                break;
        }
    }
}
