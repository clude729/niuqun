package com.daoyu.niuqun.ui.center;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.daoyu.chat.server.network.http.HttpException;
import com.daoyu.chat.server.response.PersonChangeResponse;
import com.daoyu.chat.server.utils.NToast;
import com.daoyu.chat.server.widget.LoadDialog;
import com.daoyu.chat.ui.activity.BaseActivity;
import com.daoyu.niuqun.R;
import com.daoyu.niuqun.constant.HttpConstant;
import com.daoyu.niuqun.constant.ResponseConstant;
import com.daoyu.niuqun.util.EventManager;
import com.daoyu.niuqun.util.Logger;

import io.rong.eventbus.EventBus;

/**
 * 我的零钱
 */
public class MyChangeActivity extends BaseActivity implements View.OnClickListener
{

    private static final String TAG = "MyChangeActivity";

    private TextView tvBalance;

    private Button btnRechange;

    private Button btnWithout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_chaner);
        initView();
        initListener();
        initData();
    }

    private void initView()
    {
        setTitle(R.string.the_change);
        mHeadRightText.setText(getResources().getString(R.string.change_detail));
        mHeadRightText.setVisibility(View.VISIBLE);
        tvBalance = findViewById(R.id.tv_balance);
        btnRechange = findViewById(R.id.btn_rechange);
        btnWithout = findViewById(R.id.btn_without);
    }

    private void initListener()
    {
        btnRechange.setOnClickListener(this);
        btnWithout.setOnClickListener(this);
        EventBus.getDefault().register(this);
    }

    private void initData()
    {
        LoadDialog.show(mContext);
        request(ResponseConstant.GET_CHANGE_BALANCE, true);
    }

    @Override
    public Object doInBackground(int requestCode, String id) throws HttpException
    {
        switch (requestCode)
        {
            case ResponseConstant.GET_CHANGE_BALANCE:
                return action.getMyChange();
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
            case ResponseConstant.GET_CHANGE_BALANCE:
                LoadDialog.dismiss(mContext);
                if (result instanceof PersonChangeResponse)
                {
                    PersonChangeResponse response = (PersonChangeResponse) result;
                    if (HttpConstant.SUCCESS.equals(response.getCode()))
                    {
                        Logger.d(TAG, "get my change success!");
                        String balance = response.getData();
                        if (TextUtils.isEmpty(balance))
                        {
                            balance = "0";
                        }
                        balance = getResources().getString(R.string.my_symbol_app) + balance;
                        tvBalance.setText(balance);
                        return;
                    }
                }
                String last = getResources().getString(R.string.my_symbol_app) + "0";
                tvBalance.setText(last);
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
            case ResponseConstant.GET_CHANGE_BALANCE:
                LoadDialog.dismiss(mContext);
                String last = getResources().getString(R.string.my_symbol_app) + "0";
                tvBalance.setText(last);
                NToast.shortToast(mContext, getResources().getString(R.string.http_client_false));
                break;
            default:
                break;
        }
    }

    @Override
    public void onHeadRightTextViewClick(View view)
    {
        goToDetail();
    }

    @Override
    protected void onDestroy()
    {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_rechange:
                goToRechange();
                break;
            case R.id.btn_without:

                break;
            default:
                break;
        }
    }

    private void goToRechange()
    {
        Intent intent = new Intent(this, RechargeActivity.class);
        startActivity(intent);
    }

    private void goToDetail()
    {
        Intent intent = new Intent(this, ChangeDetailActivity.class);
        startActivity(intent);
    }

    public void onEventMainThread(EventManager.AlipaySuccess alipaySuccess)
    {
        request(ResponseConstant.GET_CHANGE_BALANCE, true);
    }

    public void onEventMainThread(EventManager.WxpaySuccess wxpaySuccess)
    {
        request(ResponseConstant.GET_CHANGE_BALANCE, true);
    }

}
