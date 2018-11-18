package com.daoyu.niuqun.bean;

import java.util.List;

/**
 * 订单列表
 */

public class OrderData
{

    private static final String TAG = "OrderData";

    private List<Order> list;

    private int nowpage;

    private int countpage;

    public OrderData()
    {

    }

    public List<Order> getList()
    {
        return list;
    }

    public void setList(List<Order> list)
    {
        this.list = list;
    }

    public int getNowpage()
    {
        return nowpage;
    }

    public void setNowpage(int nowpage)
    {
        this.nowpage = nowpage;
    }

    public int getCountpage()
    {
        return countpage;
    }

    public void setCountpage(int countpage)
    {
        this.countpage = countpage;
    }

    public String toString()
    {
        return TAG + ": { nowpage = " + nowpage + " ,countpage = " + countpage + " ,list = " + list + " }";
    }

}
