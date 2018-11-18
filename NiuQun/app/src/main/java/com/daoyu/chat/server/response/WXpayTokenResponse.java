package com.daoyu.chat.server.response;

/**
 * 微信支付token获取响应类
 */
public class WXpayTokenResponse
{

    private static final String TAG = "WXpayTokenResponse";

    private String code;

    private String message;

    //微信支付相关信息
    private String data;

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public String getData()
    {
        return data;
    }

    public void setData(String data)
    {
        this.data = data;
    }

    public String toString()
    {
        return TAG + "{code = " + code + " ,message " + message + "}";
    }

}
