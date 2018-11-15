package com.daoyu.chat.server.response;

/**
 * 支付宝支付获取token响应基类
 */
public class AlipayTokenResponse
{

    private static final String TAG = "AlipayTokenResponse";

    private String code;

    private String message;

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
        return TAG + "{code = " + code + " ,message = " + message + "}";
    }

}
