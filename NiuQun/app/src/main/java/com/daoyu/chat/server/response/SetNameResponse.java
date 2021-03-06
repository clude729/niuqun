package com.daoyu.chat.server.response;

/**
 * 更新用户名
 */
public class SetNameResponse
{
    private static final String TAG = "SetNameResponse";

    private String code;

    private String message;

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
