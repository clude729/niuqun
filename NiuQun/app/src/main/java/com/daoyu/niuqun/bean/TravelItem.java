package com.daoyu.niuqun.bean;

import com.daoyu.niuqun.R;
import com.daoyu.niuqun.util.Logger;

import android.content.Context;
import android.view.View;

/**
 * 行
 */
public class TravelItem extends MyBaseItem
{

    private static final String TAG = "TravelItem";

    public TravelItem(Context context)
    {
        mContext = context;
        stringId = R.string.my_travel;
        imageId = R.mipmap.icon_item_travel;
        listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.d(TAG, "onClick!");
            }
        };
    }

}
