package com.daoyu.chat.server.response;

import com.daoyu.niuqun.bean.AddressBean;

/**
 * 收货地址详情响应类
 */
public class AddressDetailResponse
{

    private static final String TAG = "AddressDetailResponse";

    private String code;

    private String message;

    private AddressBean data;

    public AddressBean getData()
    {
        return data;
    }

    public void setData(AddressBean data)
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
        return TAG + "{code = " + code + " ,message = " + message + "}";
    }

}
