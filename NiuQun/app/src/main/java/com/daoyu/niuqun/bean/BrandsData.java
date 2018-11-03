package com.daoyu.niuqun.bean;

import java.util.List;

/**
 * 品牌、新品列表
 */

public class BrandsData
{

    private static final String TAG = "BrandsData";

    private List<BrandInfo> list;

    private int nowpage;

    private int countpage;

    public BrandsData()
    {

    }

    public List<BrandInfo> getList()
    {
        return list;
    }

    public void setList(List<BrandInfo> list)
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
