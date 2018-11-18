package com.daoyu.niuqun.bean;

import java.util.List;

/**
 * 订单
 */

public class Order
{

    private static final String TAG = "Order";

    private String order_id;

    private String order_sn;

    private String status;

    private String status_txt;

    private List<CartGoodsInfo> list;

    public String getOrder_id()
    {
        return order_id;
    }

    public void setOrder_id(String order_id)
    {
        this.order_id = order_id;
    }

    public String getOrder_sn()
    {
        return order_sn;
    }

    public void setOrder_sn(String order_sn)
    {
        this.order_sn = order_sn;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus_txt()
    {
        return status_txt;
    }

    public void setStatus_txt(String status_txt)
    {
        this.status_txt = status_txt;
    }

    public List<CartGoodsInfo> getList()
    {
        return list;
    }

    public void setList(List<CartGoodsInfo> list)
    {
        this.list = list;
    }

    public String toString()
    {
        return TAG + ": { order_id = " + order_id + " }";
    }

}
