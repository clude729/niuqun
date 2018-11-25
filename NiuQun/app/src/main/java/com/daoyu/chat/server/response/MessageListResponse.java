package com.daoyu.chat.server.response;

import com.daoyu.niuqun.bean.AppMessageData;

/**
 * 系统消息列表响应
 */
public class MessageListResponse
{

    private static final String TAG = "MessageListResponse";

    private String code;

    private String message;

    private AppMessageData data;

    public MessageListResponse()
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

    public AppMessageData getData()
    {
        return data;
    }

    public void setData(AppMessageData data)
    {
        this.data = data;
    }

    public String toString()
    {
        return TAG + "{code = " + code + " ,message = " + message + " ,data = " + data + "}";
    }

}
