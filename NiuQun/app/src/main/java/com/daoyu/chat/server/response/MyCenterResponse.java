package com.daoyu.chat.server.response;

import com.daoyu.niuqun.bean.MySelfBean;

/**
 * 个人中心响应
 */

public class MyCenterResponse
{

    private static final String TAG = "MyCenterResponse";

    private String code;

    private String message;

    private MySelfBean data;

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

    public MySelfBean getData()
    {
        return data;
    }

    public void setData(MySelfBean data)
    {
        this.data = data;
    }

    public String toString()
    {
        return TAG + "{code = " + code + " ,message " + message + " ,data = " + data.toString() + "}";
    }

}
