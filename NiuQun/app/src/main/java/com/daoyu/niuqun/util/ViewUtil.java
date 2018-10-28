package com.daoyu.niuqun.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.view.WindowManager;

/**
 * View工具类
 */

public class ViewUtil {

    private static final String TAG = "ViewUtil";

    private static ViewUtil intance;
    
    private ViewUtil()
    {

    }

    public static ViewUtil getIntance()
    {
        if (null == intance)
        {
            synchronized (ViewUtil.class)
            {
                if (null == intance)
                {
                    intance = new ViewUtil();
                }
            }
        }
        return intance;
    }

    /**
     * 获取状态栏高度
     *
     * @param context 上下文
     * @return 高度 int
     */
    public int getStatusBarHeight(Context context)
    {

        int statusBarHeight = 0;
        if (null == context)
        {
            return statusBarHeight;
        }
        Resources res = context.getResources();
        statusBarHeight = res.getDimensionPixelSize(res.getIdentifier("status_bar_height", "dimen", "android"));
        return statusBarHeight;
    }

    /**
     * 获取当前屏幕宽度
     *
     * @return 屏幕宽度
     */
    public int getDisplayWidth(Context context)
    {
        return getDisplaySize(context).x;
    }

    /**
     * 获取当前屏幕高度
     *
     * @return 屏幕高度
     */
    public int getDisplayHeight(Context context)
    {
        return getDisplaySize(context).y;
    }

    private Point getDisplaySize(Context context)
    {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point size = new Point();
        windowManager.getDefaultDisplay().getSize(size);
        return size;
    }

}
