package com.daoyu.niuqun.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 全部展开的GridView
 * 
 * @time 2015.6.24
 * @author clude
 *
 */
public class MyGridView extends GridView
{

    public MyGridView(Context context)
    {
        super(context);
    }

    public MyGridView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public MyGridView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}
