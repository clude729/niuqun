package com.daoyu.chat.server.response;

import com.daoyu.niuqun.bean.CircleMessageData;

/**
 * 消息列表响应
 */

public class CircleListResponse
{

    private static final String TAG = "CircleListResponse";

    private String code;

    private String message;

    private CircleMessageData data;

    public CircleListResponse()
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

    public CircleMessageData getData()
    {
        return data;
    }

    public void setData(CircleMessageData data)
    {
        this.data = data;
    }

    public String toString()
    {
        return TAG + "{code = " + code + " ,message = " + message + " ,data = " + data + "}";
    }

}
