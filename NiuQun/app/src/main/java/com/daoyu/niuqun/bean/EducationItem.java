package com.daoyu.niuqun.bean;

import com.daoyu.niuqun.R;
import com.daoyu.niuqun.util.Logger;

import android.content.Context;
import android.view.View;

/**
 * 教育
 */
public class EducationItem extends MyBaseItem
{

    private static final String TAG = "EducationItem";

    public EducationItem(Context context)
    {
        mContext = context;
        stringId = R.string.my_education;
        imageId = R.mipmap.icon_item_education;
        listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logger.d(TAG, "onClick!");
            }
        };
    }

}
