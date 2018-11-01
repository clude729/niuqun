package com.daoyu.niuqun.bean;

import com.daoyu.niuqun.R;
import com.daoyu.niuqun.util.Logger;

import android.content.Context;
import android.view.View;

/**
 * 理财
 */
public class FinancialItem extends MyBaseItem
{

    private static final String TAG = "FinancialItem";

    public FinancialItem(Context context)
    {
        mContext = context;
        stringId = R.string.my_financial;
        imageId = R.mipmap.icon_item_financial;
        listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.d(TAG, "onClick!");
            }
        };
    }

}
