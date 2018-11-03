package com.daoyu.chat.server.response;

import com.daoyu.niuqun.bean.BrandsData;

/**
 * 新品、品牌列表响应
 */

public class BrandsListResponse
{

    private static final String TAG = "BrandsListResponse";

    private String code;

    private String message;

    private BrandsData data;

    public BrandsListResponse()
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

    public BrandsData getData()
    {
        return data;
    }

    public void setData(BrandsData data)
    {
        this.data = data;
    }

    public String toString()
    {
        return TAG + "{code = " + code + " ,message = " + message + " ,data = " + data + "}";
    }

}
