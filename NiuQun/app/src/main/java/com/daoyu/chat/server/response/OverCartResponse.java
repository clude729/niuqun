package com.daoyu.chat.server.response;

import com.daoyu.niuqun.bean.SettlementInfo;

/**
 * 购物车结算响应类
 */
public class OverCartResponse
{

    private static final String TAG = "OverCartResponse";

    private String code;

    private String message;

    private SettlementInfo data;

    public SettlementInfo getData()
    {
        return data;
    }

    public void setData(SettlementInfo data)
    {
        this.data = data;
    }

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

    public String toString()
    {
        return TAG + "{code = " + code + " ,message " + message + "}";
    }

}
