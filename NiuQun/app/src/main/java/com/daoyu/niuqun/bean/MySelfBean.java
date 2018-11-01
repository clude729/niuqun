package com.daoyu.niuqun.bean;

/**
 * 个人中心
 */

public class MySelfBean
{

    private static final String TAG = "MySelfBean";

    private String user_name;

    private String herdno;

    private String avatar;

    private String token;

    public MySelfBean()
    {

    }

    public String getUser_name()
    {
        return user_name;
    }

    public void setUser_name(String user_name)
    {
        this.user_name = user_name;
    }

    public String getHerdno()
    {
        return herdno;
    }

    public void setHerdno(String herdno)
    {
        this.herdno = herdno;
    }

    public String getAvatar()
    {
        return avatar;
    }

    public void setAvatar(String avatar)
    {
        this.avatar = avatar;
    }

    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }

    public String toString()
    {
        return TAG + ": {user_name = " + user_name + " ,avatar = " + avatar + "}";
    }

}
