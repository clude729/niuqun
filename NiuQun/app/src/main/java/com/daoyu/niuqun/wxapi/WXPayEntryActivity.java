package com.daoyu.niuqun.wxapi;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.daoyu.niuqun.R;
import com.daoyu.niuqun.util.EventManager;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import io.rong.eventbus.EventBus;

/**
 * 微信支付结果返回界面
 * 
 * @author clude
 *
 */
public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler
{

    private static final String TAG = "WXPayEntryActivity";

    private IWXAPI api;

    private TextView tv_result;

    private Button btn_ok;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);
        ActionBar actionBar = getActionBar();
        if (null != actionBar)
        {
            actionBar.hide();
        }
        tv_result = findViewById(R.id.tv_result);
        btn_ok = findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                WXPayEntryActivity.this.finish();
                WXPayEntryActivity.this.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }

        });
        api = WXAPIFactory.createWXAPI(this, WeixinConstant.APP_ID);
        api.handleIntent(getIntent(), this);
    }

    protected void onNewIntent(Intent intent)
    {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req)
    {
    }

    @Override
    public void onResp(BaseResp resp)
    {
        Log.e(TAG, "errCode: " + resp.errCode + "err: " + resp.errStr);
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX)
        {
            switch (resp.errCode)
            {
                case WeixinConstant.ERR_AUTH_DENIED:
                    tv_result.setText("认证被否决");
                    btn_ok.setOnClickListener(new OnClickListener()
                    {

                        @Override
                        public void onClick(View v)
                        {
                            WXPayEntryActivity.this.finish();
                            WXPayEntryActivity.this.overridePendingTransition(R.anim.push_left_in,
                                R.anim.push_left_out);
                        }

                    });
                    break;
                case WeixinConstant.ERR_COMM:
                    tv_result.setText("一般错误");
                    btn_ok.setOnClickListener(new OnClickListener()
                    {

                        @Override
                        public void onClick(View v)
                        {
                            WXPayEntryActivity.this.finish();
                            WXPayEntryActivity.this.overridePendingTransition(R.anim.push_left_in,
                                R.anim.push_left_out);
                        }

                    });
                    break;
                case WeixinConstant.ERR_OK:
                    tv_result.setText("支付完成");
					EventBus.getDefault().post(new EventManager.WxpaySuccess());
                    btn_ok.setOnClickListener(new OnClickListener()
                    {

                        @Override
                        public void onClick(View v)
                        {
                            WXPayEntryActivity.this.finish();
                            WXPayEntryActivity.this.overridePendingTransition(R.anim.push_left_in,
                                R.anim.push_left_out);
                        }

                    });
                    break;
                case WeixinConstant.ERR_SENT_FAILED:
                    tv_result.setText("发送失败");
                    btn_ok.setOnClickListener(new OnClickListener()
                    {

                        @Override
                        public void onClick(View v)
                        {
                            WXPayEntryActivity.this.finish();
                            WXPayEntryActivity.this.overridePendingTransition(R.anim.push_left_in,
                                R.anim.push_left_out);
                        }

                    });
                    break;
                case WeixinConstant.ERR_UNSUPPORT:
                    tv_result.setText("不支持错误");
                    btn_ok.setOnClickListener(new OnClickListener()
                    {

                        @Override
                        public void onClick(View v)
                        {
                            WXPayEntryActivity.this.finish();
                            WXPayEntryActivity.this.overridePendingTransition(R.anim.push_left_in,
                                R.anim.push_left_out);
                        }

                    });
                    break;
                case WeixinConstant.ERR_USER_CANCEL:
                    tv_result.setText("支付取消");
                    btn_ok.setOnClickListener(new OnClickListener()
                    {

                        @Override
                        public void onClick(View v)
                        {
                            WXPayEntryActivity.this.finish();
                            WXPayEntryActivity.this.overridePendingTransition(R.anim.push_left_in,
                                R.anim.push_left_out);
                        }

                    });
                    break;
                default:
                    tv_result.setText("支付异常");
                    btn_ok.setOnClickListener(new OnClickListener()
                    {

                        @Override
                        public void onClick(View v)
                        {
                            WXPayEntryActivity.this.finish();
                            WXPayEntryActivity.this.overridePendingTransition(R.anim.push_left_in,
                                R.anim.push_left_out);
                        }

                    });
                    break;
            }
        }
    }

    protected void onResume()
    {
        super.onResume();
    }

    protected void onPause()
    {
        super.onPause();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event)
    {

        return keyCode != KeyEvent.KEYCODE_BACK;
    }

}
