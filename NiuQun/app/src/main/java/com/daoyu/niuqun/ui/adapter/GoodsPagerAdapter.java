package com.daoyu.niuqun.ui.adapter;

import java.util.List;

import android.content.Context;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.request.RequestOptions;
import com.daoyu.niuqun.R;
import com.daoyu.niuqun.constant.HttpConstant;
import com.daoyu.niuqun.util.ImageLoad;
import com.daoyu.niuqun.util.Logger;

/**
 * 商品详情viewPager适配器
 * 
 * @author clude
 *
 */
public class GoodsPagerAdapter extends PagerAdapter
{

    private static final String TAG = "GoodsPagerAdapter";

    private String[] listItems;

    private List<View> views;

    private RequestOptions options;

    private Context mContext;

    public GoodsPagerAdapter(Context c, List<View> vs, String[] ls)
    {
        this.mContext = c;
        this.views = vs;
        this.listItems = ls;
        options = new RequestOptions().error(R.drawable.default_useravatar).placeholder(R.drawable.default_useravatar);
    }

    @Override
    public int getCount()
    {
        return views.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View v, @NonNull Object object)
    {
        return v.equals(object);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup v, int position, @NonNull Object object)
    {
        ((ViewPager) v).removeView((View) object);
    }

    //初始化position位置的界面
    @NonNull
    public Object instantiateItem(@NonNull ViewGroup v, int position)
    {
        View view = views.get(position);
        ImageView image = (ImageView) view.findViewById(R.id.image);
        String imag = listItems[position];
        if (!TextUtils.isEmpty(imag) && !"null".equals(imag))
        {
            if (!imag.contains(HttpConstant.URL) && !imag.contains("http") && !imag.contains("https"))
            {
                imag = HttpConstant.URL + imag;
            }
            ImageLoad.getInstance().load(mContext, image, imag, options);
        }
        else
        {
            image.setImageResource(R.drawable.default_useravatar);
        }
        final int p = position;
        image.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                Logger.d(TAG, "onClick, position: " + p);
                switch (p)
                {
                    case 0:

                        break;
                    case 1:

                        break;
                    case 2:

                        break;
                    default:
                        break;
                }
            }

        });
        v.addView(view, 0);
        return view;
    }

    public void restoreState(Parcelable state, ClassLoader loader)
    {

    }

    public Parcelable saveState()
    {
        return null;
    }

}
