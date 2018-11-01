package com.daoyu.niuqun.bean;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * 个人中心item
 */

public class MyBaseItem
{

    protected Context mContext;

    protected int stringId = 0;

    protected int imageId = 0;

    public MyBaseItem()
    {

    }

    protected OnClickListener listener = new OnClickListener()
    {
        @Override
        public void onClick(View v)
        {

        }
    };

    public int getStringId()
    {
        return stringId;
    }

    public void setStringId(int stringId)
    {
        this.stringId = stringId;
    }

    public int getImageId()
    {
        return imageId;
    }

    public void setImageId(int imageId)
    {
        this.imageId = imageId;
    }

    public OnClickListener getListener()
    {
        return listener;
    }

    public void setListener(OnClickListener listener)
    {
        this.listener = listener;
    }
}
