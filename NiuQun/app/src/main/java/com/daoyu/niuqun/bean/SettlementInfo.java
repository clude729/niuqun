package com.daoyu.niuqun.bean;

import java.util.List;

/**
 * 结算信息（包括商品和默认收货地址）
 */

public class SettlementInfo
{

    private static final String TAG = "AddressBean";

    private List<CartGoodsInfo> list;

    private List<AddressBean> address;

    public List<CartGoodsInfo> getList()
    {
        return list;
    }

    public void setList(List<CartGoodsInfo> list)
    {
        this.list = list;
    }

    public List<AddressBean> getAddress()
    {
        return address;
    }

    public void setAddress(List<AddressBean> address)
    {
        this.address = address;
    }

    public String toString()
    {
        return TAG + "{ list = " + list + " ,address = " + address + "}";
    }
}
