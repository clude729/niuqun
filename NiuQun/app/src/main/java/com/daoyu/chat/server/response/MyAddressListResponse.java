package com.daoyu.chat.server.response;

import java.util.List;

import com.daoyu.niuqun.bean.AddressBean;

/**
 * 我的收货地址响应类
 */
public class MyAddressListResponse
{

    private static final String TAG = "MyAddressListResponse";

    private String code;

    private String message;

    private List<AddressBean> data;

    public List<AddressBean> getData()
    {
        return data;
    }

    public void setData(List<AddressBean> data)
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
