package com.daoyu.niuqun.bean;

/**
 * 系统消息
 */

public class BaseAppMessage
{

    private static final String TAG = "BaseAppMessage";

    private String title;

    private String ms_id;

    private String thumb_image;

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getMs_id()
    {
        return ms_id;
    }

    public void setMs_id(String ms_id)
    {
        this.ms_id = ms_id;
    }

    public String getThumb_image()
    {
        return thumb_image;
    }

    public void setThumb_image(String thumb_image)
    {
        this.thumb_image = thumb_image;
    }

    public String toString()
    {
        return TAG + "{ ms_id = " + ms_id + " ,title = " + title + "}";
    }

}
