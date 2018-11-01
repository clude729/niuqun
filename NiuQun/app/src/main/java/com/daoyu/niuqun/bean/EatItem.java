package com.daoyu.niuqun.bean;

import com.daoyu.niuqun.R;
import com.daoyu.niuqun.util.Logger;

import android.content.Context;
import android.view.View;

/**
 * 吃
 */
public class EatItem extends MyBaseItem
{

    private static final String TAG = "EatItem";

    public EatItem(Context context)
    {
        mContext = context;
        stringId = R.string.my_eat;
        imageId = R.mipmap.icon_item_eat;
        listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.d(TAG, "onClick!");
            }
        };
    }

}
