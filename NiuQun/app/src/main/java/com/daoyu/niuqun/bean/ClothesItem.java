package com.daoyu.niuqun.bean;

import com.daoyu.niuqun.R;
import com.daoyu.niuqun.util.Logger;

import android.content.Context;
import android.view.View;

/**
 * 衣
 */
public class ClothesItem extends MyBaseItem
{

    private static final String TAG = "ClothesItem";

    public ClothesItem(Context c)
    {
        mContext = c;
        stringId = R.string.my_clothes;
        imageId = R.mipmap.icon_item_clothes;
        listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.d(TAG, "onClick!");
            }
        };
    }

}
