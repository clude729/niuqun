package com.daoyu.niuqun.bean;

import java.util.List;

/**
 * 系统消息列表
 */

public class AppMessageData
{

    private static final String TAG = "AppMessageData";

    private List<BaseAppMessage> list;

    private int nowpage;

    private int countpage;

    public List<BaseAppMessage> getList()
    {
        return list;
    }

    public void setList(List<BaseAppMessage> list)
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
