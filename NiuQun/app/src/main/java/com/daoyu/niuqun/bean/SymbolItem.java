package com.daoyu.niuqun.bean;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.daoyu.niuqun.R;
import com.daoyu.niuqun.ui.center.MyChangeActivity;
import com.daoyu.niuqun.util.Logger;

/**
 * 零钱
 */

public class SymbolItem extends MyBaseItem
{

    private static final String TAG = "SymbolItem";

    public SymbolItem(Context context)
    {
        mContext = context;
        stringId = R.string.the_change;
        imageId = R.mipmap.icon_item_symbol_app;
        listener = new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Logger.d(TAG, "onClick!");
                Intent intent = new Intent(mContext, MyChangeActivity.class);
                mContext.startActivity(intent);
            }
        };
    }

}
