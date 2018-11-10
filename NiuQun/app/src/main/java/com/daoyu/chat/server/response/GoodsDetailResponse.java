package com.daoyu.chat.server.response;

import com.daoyu.niuqun.bean.GoodsInfo;

/**
 * 品牌/新品详情响应类
 */
public class GoodsDetailResponse
{

    private static final String TAG = "GoodsDetailResponse";

    private String code;

    private String message;

    private GoodsInfo data;

    public GoodsInfo getData()
    {
        return data;
    }

    public void setData(GoodsInfo data)
    {
        this.data = data;
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

    public String toString()
    {
        return TAG + "{code = " + code + " ,message " + message + "}";
    }

}
