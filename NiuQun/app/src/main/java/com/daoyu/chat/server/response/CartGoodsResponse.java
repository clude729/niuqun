package com.daoyu.chat.server.response;

import java.util.List;

import com.daoyu.niuqun.bean.CartGoodsInfo;

/**
 * 获取购物车内商品响应类
 */
public class CartGoodsResponse
{

    private static final String TAG = "CartGoodsResponse";

    private String code;

    private String message;

    private List<CartGoodsInfo> data;

    public List<CartGoodsInfo> getData()
    {
        return data;
    }

    public void setData(List<CartGoodsInfo> data)
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
