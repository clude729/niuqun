package com.daoyu.niuqun.ui.center;

import java.util.Map;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.PayTask;
import com.daoyu.chat.server.network.http.HttpException;
import com.daoyu.chat.server.response.AlipayTokenResponse;
import com.daoyu.chat.server.response.BaseSealResponse;
import com.daoyu.chat.server.response.RechargeResponse;
import com.daoyu.chat.server.utils.NToast;
import com.daoyu.chat.server.widget.LoadDialog;
import com.daoyu.chat.ui.activity.BaseActivity;
import com.daoyu.niuqun.R;
import com.daoyu.niuqun.alipay.PayResult;
import com.daoyu.niuqun.constant.HttpConstant;
import com.daoyu.niuqun.constant.ResponseConstant;
import com.daoyu.niuqun.util.Logger;

public class RechargeActivity extends BaseActivity implements View.OnClickListener
{

    private static final String TAG = "RechargeActivity";

    private static final int ALI_PAY = 1;

    private static final int WEIXIN_PAY = 2;

    /**
     * 获取权限使用的 RequestCode
     */
    private static final int PERMISSIONS_REQUEST_CODE = 1002;

    //支付宝支付
    private static final int SDK_PAY_FLAG = 1111;

    private EditText etAmount;

    private LinearLayout llAlipay;

    private LinearLayout llWeixin;

    private ImageView ivAlipay;

    private ImageView ivWeixin;

    private Button btnConfirm;

    //支付宝：1；微信：2；
    private int payType = 1;

    private String money = "";

    private String lodId = "";

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler()
    {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case SDK_PAY_FLAG:
                {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    Logger.d(TAG, "payResult: " + payResult);
                    //对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000"))
                    {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        showAlert(RechargeActivity.this, "支付成功: " + payResult);
                        request(ResponseConstant.GET_PAY_RESULT, true);
                    }
                    else
                    {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        showAlert(RechargeActivity.this, "支付失败: " + payResult);
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);
        initView();
        initListener();
    }

    private void initView()
    {
        setTitle(R.string.change_recharge);
        etAmount = findViewById(R.id.et_amount);
        llAlipay = findViewById(R.id.ll_alipay);
        llWeixin = findViewById(R.id.ll_weixin);
        ivAlipay = findViewById(R.id.iv_alipay);
        ivWeixin = findViewById(R.id.iv_weixin);
        btnConfirm = findViewById(R.id.btn_confirm);
    }

    private void initListener()
    {
        llAlipay.setOnClickListener(this);
        llWeixin.setOnClickListener(this);
        btnConfirm.setOnClickListener(this);
    }

    private void selectPayType(int type)
    {
        switch (type)
        {
            case ALI_PAY:
                ivWeixin.setImageResource(R.mipmap.icon_cart_unselect);
                ivAlipay.setImageResource(R.mipmap.icon_cart_select);
                payType = type;
                break;
            case WEIXIN_PAY:
                ivAlipay.setImageResource(R.mipmap.icon_cart_unselect);
                ivWeixin.setImageResource(R.mipmap.icon_cart_select);
                payType = type;
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
            case ResponseConstant.RECHARGE_TO_PAY:
                return action.rechargeToPay(money);
            case ResponseConstant.GET_ALIPAY:
                return action.getAliPay(lodId);
            case ResponseConstant.GET_PAY_RESULT:
                return action.getPayResult(lodId);
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
            case ResponseConstant.RECHARGE_TO_PAY:
                if (result instanceof RechargeResponse)
                {
                    RechargeResponse response = (RechargeResponse) result;
                    if (HttpConstant.SUCCESS.equals(response.getCode()))
                    {
                        Logger.d(TAG, "recharge to pay success!");
                        lodId = response.getData();
                        if (!TextUtils.isEmpty(lodId))
                        {
                            switch (payType)
                            {
                                case ALI_PAY:
                                    request(ResponseConstant.GET_ALIPAY);
                                    break;
                                case WEIXIN_PAY:
                                    LoadDialog.dismiss(mContext);
                                    break;
                                default:
                                    LoadDialog.dismiss(mContext);
                                    break;
                            }
                        }
                        else
                        {
                            LoadDialog.dismiss(mContext);
                        }
                    }
                    else
                    {
                        LoadDialog.dismiss(mContext);
                        NToast.shortToast(mContext, response.getMessage());
                    }
                }
                break;
            case ResponseConstant.GET_ALIPAY:
                LoadDialog.dismiss(mContext);
                if (result instanceof AlipayTokenResponse)
                {
                    AlipayTokenResponse response = (AlipayTokenResponse) result;
                    if (HttpConstant.SUCCESS.equals(response.getCode()))
                    {
                        Logger.d(TAG, "get alipay success!");
                        final String orderInfo = response.getData();
                        if (!TextUtils.isEmpty(orderInfo))
                        {
                            Runnable payRunnable = new Runnable()
                            {

                                @Override
                                public void run()
                                {
                                    PayTask alipay = new PayTask(RechargeActivity.this);
                                    Map<String, String> result = alipay.payV2(orderInfo, true);
                                    Message msg = new Message();
                                    msg.what = SDK_PAY_FLAG;
                                    msg.obj = result;
                                    mHandler.sendMessage(msg);
                                }
                            };
                            // 必须异步调用
                            Thread payThread = new Thread(payRunnable);
                            payThread.start();
                        }
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
        switch (requestCode)
        {
            case ResponseConstant.RECHARGE_TO_PAY:
                LoadDialog.dismiss(mContext);
                NToast.shortToast(mContext, getResources().getString(R.string.http_client_false));
                break;
            case ResponseConstant.GET_ALIPAY:
                LoadDialog.dismiss(mContext);
                NToast.shortToast(mContext, getResources().getString(R.string.http_client_false));
                break;
            case ResponseConstant.GET_PAY_RESULT:
                LoadDialog.dismiss(mContext);
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.ll_alipay:
                selectPayType(ALI_PAY);
                break;
            case R.id.ll_weixin:
                selectPayType(WEIXIN_PAY);
                break;
            case R.id.btn_confirm:
                String amount = etAmount.getText().toString().trim();
                if (TextUtils.isEmpty(amount))
                {
                    NToast.shortToast(this, "请输入充值金额");
                }
                else
                {
                    double aIn = Double.parseDouble(amount);
                    if (aIn > 0.00)
                    {
                        money = amount;
                        requestPermission(amount);
                    }
                    else
                    {
                        NToast.shortToast(this, "请输入充值金额");
                    }
                }
                break;
        }
    }

    private void toRecharge(String amount)
    {
        money = amount;
        request(ResponseConstant.RECHARGE_TO_PAY, true);
    }

    /**
     * 检查支付宝 SDK 所需的权限，并在必要的时候动态获取。 在 targetSDK = 23 以上，READ_PHONE_STATE 和 WRITE_EXTERNAL_STORAGE 权限需要应用在运行时获取。 如果接入支付宝
     * SDK 的应用 targetSdk 在 23 以下，可以省略这个步骤。
     */
    private void requestPermission(String amount)
    {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
            Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED
            || ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {

            ActivityCompat.requestPermissions(this,
                new String[] {Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE },
                PERMISSIONS_REQUEST_CODE);
        }
        else
        {
            NToast.shortToast(this, "支付宝 SDK 已有所需的权限");
            toRecharge(amount);
        }
    }

    /**
     * 权限获取回调
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults)
    {
        switch (requestCode)
        {
            case PERMISSIONS_REQUEST_CODE:
            {
                // 用户取消了权限弹窗
                if (grantResults.length == 0)
                {
                    NToast.shortToast(this, "无法获取支付宝 SDK 所需的权限, 请到系统设置开启");
                    return;
                }

                // 用户拒绝了某些权限
                for (int x : grantResults)
                {
                    if (x == PackageManager.PERMISSION_DENIED)
                    {
                        NToast.shortToast(this, "无法获取支付宝 SDK 所需的权限, 请到系统设置开启");
                        return;
                    }
                }

                // 所需的权限均正常获取
                NToast.shortToast(this, "支付宝 SDK 所需的权限已经正常获取");
                toRecharge(money);
            }
        }
    }

    private static void showAlert(Context ctx, String info)
    {
        showAlert(ctx, info, null);
    }

    private static void showAlert(Context ctx, String info, DialogInterface.OnDismissListener onDismiss)
    {
        new AlertDialog.Builder(ctx).setMessage(info).setPositiveButton("确定", null).setOnDismissListener(onDismiss)
            .show();
    }

}
