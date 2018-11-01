package com.daoyu.niuqun.bean;

import com.daoyu.niuqun.R;
import com.daoyu.niuqun.util.Logger;

import android.content.Context;
import android.view.View;

/**
 * 设置
 */
public class SettingItem extends MyBaseItem
{

    private static final String TAG = "SettingItem";

    public SettingItem(Context context)
    {
        mContext = context;
        stringId = R.string.my_setting;
        imageId = R.mipmap.icon_item_setting;
        listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.d(TAG, "onClick!");
            }
        };
    }

}
