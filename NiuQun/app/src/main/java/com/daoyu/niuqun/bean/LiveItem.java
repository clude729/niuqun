package com.daoyu.niuqun.bean;

import com.daoyu.niuqun.R;
import com.daoyu.niuqun.util.Logger;

import android.content.Context;
import android.view.View;

/**
 * 住
 */
public class LiveItem extends MyBaseItem
{

    private static final String TAG = "LiveItem";

    public LiveItem(Context context)
    {
        mContext = context;
        stringId = R.string.my_live;
        imageId = R.mipmap.icon_item_live;
        listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.d(TAG, "onClick!");
            }
        };
    }

}
