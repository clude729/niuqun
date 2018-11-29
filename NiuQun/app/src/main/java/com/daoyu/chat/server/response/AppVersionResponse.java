package com.daoyu.chat.server.response;

import com.daoyu.niuqun.bean.VersionBean;

/**
 * 版本信息响应类
 */
public class AppVersionResponse
{

    private static final String TAG = "AppVersionResponse";

    private String code;

    private String message;

    private VersionBean data;

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

    public VersionBean getData()
    {
        return data;
    }

    public void setData(VersionBean data)
    {
        this.data = data;
    }

    public String toString()
    {
        return TAG + "{code = " + code + " ,message = " + message + " ,data = " + data + "}";
    }

}
