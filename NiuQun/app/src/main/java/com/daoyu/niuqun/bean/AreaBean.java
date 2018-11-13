package com.daoyu.niuqun.bean;

/**
 * 地区类型
 */

public class AreaBean
{

    private static final String TAG = "AreaBean";

    private String name;

    private String id;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String toString()
    {
        return TAG + ": { name = " + name + " }";
    }

}
