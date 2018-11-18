package com.daoyu.niuqun.bean;

import java.util.List;

/**
 * 零钱明细列表
 */

public class ChangeData
{

    private static final String TAG = "ChangeData";

    private List<ChangeInfo> list;

    private int nowpage;

    private int countpage;

    public ChangeData()
    {

    }

    public List<ChangeInfo> getList()
    {
        return list;
    }

    public void setList(List<ChangeInfo> list)
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
