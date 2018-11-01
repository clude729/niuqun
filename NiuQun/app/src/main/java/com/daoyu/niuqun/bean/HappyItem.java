package com.daoyu.niuqun.bean;

import com.daoyu.niuqun.R;
import com.daoyu.niuqun.util.Logger;

import android.content.Context;
import android.view.View;

/**
 * 乐
 */
public class HappyItem extends MyBaseItem
{

    private static final String TAG = "HappyItem";

    public HappyItem(Context context)
    {
        mContext = context;
        stringId = R.string.my_happy;
        imageId = R.mipmap.icon_item_happy;
        listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.d(TAG, "onClick!");
            }
        };
    }

}
