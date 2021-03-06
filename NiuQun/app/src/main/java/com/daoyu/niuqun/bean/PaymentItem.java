package com.daoyu.niuqun.bean;

import android.content.Context;
import android.view.View;

import com.daoyu.niuqun.R;
import com.daoyu.niuqun.util.Logger;

/**
 * 收付款
 */
public class PaymentItem extends MyBaseItem
{

    private static final String TAG = "PaymentItem";

    public PaymentItem(Context context)
    {
        mContext = context;
        stringId = R.string.my_receive_payment;
        imageId = R.mipmap.icon_item_payment;
        listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.d(TAG, "onClick!");
            }
        };
    }

}
