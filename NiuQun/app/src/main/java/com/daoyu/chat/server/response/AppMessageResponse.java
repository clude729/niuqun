package com.daoyu.chat.server.response;

import com.daoyu.niuqun.bean.AppMessage;

/**
 * 系统消息响应
 */
public class AppMessageResponse
{

    private static final String TAG = "AppMessageResponse";

    private String code;

    private String message;

    private AppMessage data;

    public AppMessageResponse()
    {

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

    public AppMessage getData()
    {
        return data;
    }

    public void setData(AppMessage data)
    {
        this.data = data;
    }

    public String toString()
    {
        return TAG + "{code = " + code + " ,message = " + message + " ,data = " + data + "}";
    }

}
