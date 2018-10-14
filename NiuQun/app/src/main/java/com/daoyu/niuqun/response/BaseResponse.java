package com.daoyu.niuqun.response;

/**
 * 数据基类
 */

public class BaseResponse
{

    private static final String TAG = "BaseResponse";

    private String code;

    private String message;

    private Object obj;

    public String getCode()
    {
        return code;
    }

    public String getMessage()
    {
        return message;
    }

    public Object getData()
    {
        return obj;
    }

    public String toString()
    {
        return TAG + "{code = " + code + " ,message " + message + " ,data = " + obj + "}";
    }
}
