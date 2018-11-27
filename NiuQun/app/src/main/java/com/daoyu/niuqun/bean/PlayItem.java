package com.daoyu.niuqun.bean;

import com.daoyu.niuqun.R;
import com.daoyu.niuqun.util.Logger;

import android.content.Context;
import android.view.View;

/**
 * 玩
 */
public class PlayItem extends MyBaseItem
{

    private static final String TAG = "PlayItem";

    public PlayItem(Context context)
    {
        mContext = context;
        stringId = R.string.my_play;
        imageId = R.mipmap.icon_item_play;
        listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.d(TAG, "onClick!");
                //Intent intent = new Intent(mContext, MessageListActivity.class);
                //mContext.startActivity(intent);
            }
        };
    }

}
