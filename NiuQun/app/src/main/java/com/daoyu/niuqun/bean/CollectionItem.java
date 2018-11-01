package com.daoyu.niuqun.bean;

import com.daoyu.niuqun.R;
import com.daoyu.niuqun.util.Logger;

import android.content.Context;
import android.view.View;

/**
 * 收藏
 */
public class CollectionItem extends MyBaseItem
{

    private static final String TAG = "CollectionItem";

    public CollectionItem(Context context)
    {
        mContext = context;
        stringId = R.string.my_collection;
        imageId = R.mipmap.icon_item_collection;
        listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.d(TAG, "onClick!");
            }
        };
    }

}
