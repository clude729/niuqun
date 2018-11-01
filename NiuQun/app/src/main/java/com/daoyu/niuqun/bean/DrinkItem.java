package com.daoyu.niuqun.bean;

import com.daoyu.niuqun.R;
import com.daoyu.niuqun.util.Logger;

import android.content.Context;
import android.view.View;

/**
 * 喝
 */
public class DrinkItem extends MyBaseItem
{

    private static final String TAG = "DrinkItem";

    public DrinkItem(Context context)
    {
        mContext = context;
        stringId = R.string.my_drink;
        imageId = R.mipmap.icon_item_drink;
        listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.d(TAG, "onClick!");
            }
        };
    }

}
