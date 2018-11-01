package com.daoyu.chat.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ScrollView;

public class CSEvaluateScrollView extends ScrollView
{
    Context mContext;

    public CSEvaluateScrollView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        mContext = context;
    }

    private int getWindowHeight()
    {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);

        int height = wm.getDefaultDisplay().getHeight();
        return height;
    }

    private int getStatusBarHeight()
    {
        int statusBarHeight1 = -1;
        //获取status_bar_height资源的ID
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0)
        {
            //根据资源ID获取响应的尺寸值
            statusBarHeight1 = getResources().getDimensionPixelSize(resourceId);
        }
        Log.e("WangJ", "状态栏-方法1:" + statusBarHeight1);
        return statusBarHeight1;
    }
}
