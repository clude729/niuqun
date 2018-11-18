package com.daoyu.niuqun.wxapi;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.text.TextUtils;

import com.daoyu.chat.server.network.http.RequestParams;
import com.daoyu.chat.server.utils.NToast;
import com.daoyu.niuqun.ui.center.RechargeActivity;
import com.daoyu.niuqun.util.Logger;
import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * 微信支付
 * 
 * @author clude
 *
 */
public class ToWXpay
{

    private static final String TAG = "ToWXpay";

    private IWXAPI api;

    private Context mContext;

    private String lodId = "";//订单id

    private RequestParams params;

    /**
     * -1、没有选择方式，0、get方式，1、post方式
     */
    private int type = -1;

    private WxpayTokenInterface tokenInterface;

    private WXToPayInterface toPayInterface = new WXToPayInterface()
    {
        @Override
        public void toWXpay(String result)
        {
            if (TextUtils.isEmpty(result))
            {
                Logger.d(TAG, "WXToPayInterface, result in null return!");
                return;
            }
            toPayTest(result);
        }
    };

    /**
     * get方式
     *
     * @param context 上下文
     * @param id 订单id
     */
    public ToWXpay(Context context, String id, WxpayTokenInterface tokenInterface)
    {
        this.mContext = context;
        this.lodId = id;
        this.tokenInterface = tokenInterface;
        type = 0;
    }

    /**
     * post方式
     *
     * @param context 上下文
     * @param params post携带参数
     * @param id 订单id
     */
    public ToWXpay(Context context, RequestParams params, String id, WxpayTokenInterface tokenInterface)
    {
        this.mContext = context;
        this.params = params;
        this.lodId = id;
        this.tokenInterface = tokenInterface;
        type = 0;
    }

    public void toPay()
    {
        if (null == tokenInterface)
        {
            Logger.d(TAG, "tokenInterface in null, return!");
            return;
        }
        if (mContext instanceof RechargeActivity)
        {
            ((RechargeActivity) mContext).setWxToPayInterface(toPayInterface);
        }
        if (type == 0)
        {
            getWXpay();
        }
        else if (type == 1)
        {
            postWXpay();
        }
    }

    private void getWXpay()
    {
        api = WXAPIFactory.createWXAPI(mContext, WeixinConstant.APP_ID);
        boolean isPaySupported = api.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
        if (isPaySupported)
        {
            tokenInterface.getWXpayToken(lodId);
        }
        else
        {
            NToast.shortToast(mContext, "微信版本不支持或者未安装微信");
        }
    }

    private void postWXpay()
    {
        if (params != null)
        {
            api = WXAPIFactory.createWXAPI(mContext, WeixinConstant.APP_ID);
            boolean isPaySupported = api.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
            if (isPaySupported)
            {
                tokenInterface.getWXpayToken(lodId);
            }
            else
            {
                NToast.shortToast(mContext, "微信版本不支持或者未安装微信");
            }
        }
        else
        {
            NToast.shortToast(mContext, "params数据缺失");
        }
    }

    /**
     * 获取支付信息后进行支付
     * 
     * @param result 支付信息
     */
    private void toPay(String result)
    {
        try
        {
            JSONObject json = new JSONObject(result);
            String code = json.getString("code");
            if ("000".equals(code))
            {
                JSONObject js = json.getJSONObject("data");
                api = WXAPIFactory.createWXAPI(mContext, js.getString("appid"));
                PayReq req = new PayReq();
                req.appId = js.getString("appid");
                req.partnerId = js.getString("partnerid");
                req.prepayId = js.getString("prepayid");
                req.nonceStr = js.getString("noncestr");
                req.timeStamp = js.getString("timestamp");
                req.packageValue = js.getString("package");
                req.sign = js.getString("sign");
                api.sendReq(req);
            }
            else if (!TextUtils.isEmpty(code))
            {
                NToast.shortToast(mContext, json.getString("message"));
            }
            else
            {
                NToast.shortToast(mContext, "返回状态值错误");
            }

        }
        catch (JSONException e)
        {
            Logger.e(TAG, "toPay, jsonException: " + e.getMessage());
            NToast.shortToast(mContext, "返回数值解析错误");
        }
    }

    /**
     * 测试
     * 
     * @param result 支付信息
     */
    public void toPayTest(String result)
    {
        try
        {
            JSONObject js = new JSONObject(result);
            api = WXAPIFactory.createWXAPI(mContext, js.getString("appid"));
            PayReq req = new PayReq();
            req.appId = js.getString("appid");
            req.partnerId = js.getString("partnerid");
            req.prepayId = js.getString("prepayid");
            req.nonceStr = js.getString("noncestr");
            req.timeStamp = js.getString("timestamp");
            req.packageValue = js.getString("package");
            req.sign = js.getString("sign");
            api.sendReq(req);
        }
        catch (JSONException e)
        {
            Logger.e(TAG, "toPayTest, jsonException: " + e.getMessage());
            NToast.shortToast(mContext, "返回数值解析错误");
        }
    }

}
