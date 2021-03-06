package com.daoyu.chat.server.response;

/**
 * 个人零钱响应类
 */
public class PersonChangeResponse
{

    private static final String TAG = "PersonChangeResponse";

    private String code;

    private String message;

    //零钱
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
