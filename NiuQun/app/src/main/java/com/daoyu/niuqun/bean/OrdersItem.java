package com.daoyu.niuqun.bean;

import com.daoyu.niuqun.R;
import com.daoyu.niuqun.ui.brand.OrderListActivity;
import com.daoyu.niuqun.util.Logger;

import android.content.Context;
import android.content.Intent;
import android.view.View;

/**
 * 订单
 */
public class OrdersItem extends MyBaseItem
{

    private static final String TAG = "OrdersItem";

    public OrdersItem(Context context)
    {
        mContext = context;
        stringId = R.string.my_orders;
        imageId = R.mipmap.icon_item_order;
        listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.d(TAG, "onClick!");
                Intent intent = new Intent(mContext, OrderListActivity.class);
                mContext.startActivity(intent);
            }
        };
    }

}
