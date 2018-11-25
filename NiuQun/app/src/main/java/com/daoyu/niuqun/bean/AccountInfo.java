package com.daoyu.niuqun.bean;

/**
 * 账号信息（包括id和token）
 */

public class AccountInfo
{

    private String user_id;
    
    private String token;
    
    private ReuserInfo reuser;

    public String getUser_id()
    {
        return user_id;
    }

    public void setUser_id(String user_id)
    {
        this.user_id = user_id;
    }

    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }

    public ReuserInfo getReuser()
    {
        return reuser;
    }

    public void setReuser(ReuserInfo reuser)
    {
        this.reuser = reuser;
    }

    public String toString()
    {
        return "AccountInfo{ user_id = " + user_id + " ,token = " + token + "reuser: " + reuser + "}";
    }
}
