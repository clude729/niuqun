package com.daoyu.chat.server.response;

import com.daoyu.niuqun.bean.OrderData;

/**
 * 订单列表响应类
 */
public class OrderListResponse
{

    private static final String TAG = "OrderListResponse";

    private String code;

    private String message;

    //零钱
    private OrderData data;

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

    public OrderData getData()
    {
        return data;
    }

    public void setData(OrderData data)
    {
        this.data = data;
    }

    public String toString()
    {
        return TAG + "{code = " + code + " ,message " + message + "}";
    }

}
