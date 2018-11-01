package com.daoyu.niuqun.bean;

import com.daoyu.niuqun.R;
import com.daoyu.niuqun.util.Logger;

import android.content.Context;
import android.view.View;

/**
 * 相册
 */
public class PhotosItem extends MyBaseItem
{

    private static final String TAG = "PhotosItem";

    public PhotosItem(Context context)
    {
        mContext = context;
        stringId = R.string.my_photos;
        imageId = R.mipmap.icon_item_play;
        listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.d(TAG, "onClick!");
            }
        };
    }

}
