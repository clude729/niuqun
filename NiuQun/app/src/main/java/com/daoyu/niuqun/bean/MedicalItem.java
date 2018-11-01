package com.daoyu.niuqun.bean;

import com.daoyu.niuqun.R;
import com.daoyu.niuqun.util.Logger;

import android.content.Context;
import android.view.View;

/**
 * 医疗
 */
public class MedicalItem extends MyBaseItem
{

    private static final String TAG = "MedicalItem";

    public MedicalItem(Context context)
    {
        mContext = context;
        stringId = R.string.my_medical;
        imageId = R.mipmap.icon_item_medical;
        listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.d(TAG, "onClick!");
            }
        };
    }

}
