package com.daoyu.niuqun.bean;

import java.util.List;

/**
 * 朋友圈列表
 */

public class CircleMessageData
{

    private static final String TAG = "CircleMessageData";

    private List<CircleMessage> list;

    private int nowpage;

    private int countpage;

    public CircleMessageData()
    {

    }

    public List<CircleMessage> getList()
    {
        return list;
    }

    public void setList(List<CircleMessage> list)
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
