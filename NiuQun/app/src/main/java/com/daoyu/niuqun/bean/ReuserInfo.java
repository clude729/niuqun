package com.daoyu.niuqun.bean;

/**
 * 特助信息
 */

public class ReuserInfo
{

    private static final String TAG = "ReuserInfo";

    private String user_name;

    private String user_id;

    private String avatar;

    private String token;

    public String getUser_name()
    {
        return user_name;
    }

    public void setUser_name(String user_name)
    {
        this.user_name = user_name;
    }

    public String getUser_id()
    {
        return user_id;
    }

    public void setUser_id(String user_id)
    {
        this.user_id = user_id;
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
        return TAG + "{ user_id = " + user_id + " ,user_name = " + user_name + "}";
    }

}
