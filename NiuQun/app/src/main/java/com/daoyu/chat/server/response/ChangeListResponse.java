package com.daoyu.chat.server.response;

import com.daoyu.niuqun.bean.ChangeData;

/**
 * 零钱明细列表响应类
 */
public class ChangeListResponse
{

    private static final String TAG = "ChangeListResponse";

    private String code;

    private String message;

    //零钱
    private ChangeData data;

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

    public ChangeData getData()
    {
        return data;
    }

    public void setData(ChangeData data)
    {
        this.data = data;
    }

    public String toString()
    {
        return TAG + "{code = " + code + " ,message " + message + "}";
    }

}
