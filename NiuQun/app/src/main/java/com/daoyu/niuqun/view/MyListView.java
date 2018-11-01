package com.daoyu.niuqun.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 全部展开的ListView
 * 
 * @time 2015.6.24
 * @author clude
 *
 */
public class MyListView extends ListView
{

    public MyListView(Context context)
    {
        super(context);
    }

    public MyListView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public MyListView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}
