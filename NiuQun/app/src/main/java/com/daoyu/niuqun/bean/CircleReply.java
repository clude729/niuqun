package com.daoyu.niuqun.bean;

/**
 * 朋友圈的回复信息
 */

public class CircleReply
{

    private static final String TAG = "CircleReply";

    private String content;

    private String to_user_id = "";

    private String fid;

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getTo_user_id()
    {
        return to_user_id;
    }

    public void setTo_user_id(String to_user_id)
    {
        this.to_user_id = to_user_id;
    }

    public String getFid()
    {
        return fid;
    }

    public void setFid(String fid)
    {
        this.fid = fid;
    }

    public String toString()
    {
        return TAG + ": { fid = " + fid + " ,content: " + content + " }";
    }

}
