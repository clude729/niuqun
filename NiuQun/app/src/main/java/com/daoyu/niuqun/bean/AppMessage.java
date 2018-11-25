package com.daoyu.niuqun.bean;

/**
 * 系统消息明细
 */

public class AppMessage
{

    private static final String TAG = "AppMessage";

    private String title;

    private String ms_id;

    private String thumb_image;

    private String view_num;

    private String add_time;

    private String is_check;

    private String content;

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

    public String getView_num()
    {
        return view_num;
    }

    public void setView_num(String view_num)
    {
        this.view_num = view_num;
    }

    public String getAdd_time()
    {
        return add_time;
    }

    public void setAdd_time(String add_time)
    {
        this.add_time = add_time;
    }

    public String getIs_check()
    {
        return is_check;
    }

    public void setIs_check(String is_check)
    {
        this.is_check = is_check;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String toString()
    {
        return TAG + "{ ms_id = " + ms_id + " ,title = " + title + "}";
    }

}
