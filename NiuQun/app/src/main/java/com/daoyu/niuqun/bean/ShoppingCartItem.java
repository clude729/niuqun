package com.daoyu.niuqun.bean;

import com.daoyu.niuqun.R;
import com.daoyu.niuqun.util.Logger;

import android.content.Context;
import android.view.View;

/**
 * 购物车
 */
public class ShoppingCartItem extends MyBaseItem
{

    private static final String TAG = "ShoppingCartItem";

    public ShoppingCartItem(Context context)
    {
        mContext = context;
        stringId = R.string.my_shopping_cart;
        imageId = R.mipmap.icon_item_shopping_cart;
        listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.d(TAG, "onClick!");
            }
        };
    }

}
