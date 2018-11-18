package com.daoyu.niuqun.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.request.RequestOptions;
import com.daoyu.niuqun.R;
import com.daoyu.niuqun.bean.BrandInfo;
import com.daoyu.niuqun.constant.HttpConstant;
import com.daoyu.niuqun.constant.IntentConstant;
import com.daoyu.niuqun.ui.brand.GoodsDetailActivity;
import com.daoyu.niuqun.util.ImageLoad;
import com.daoyu.niuqun.util.Logger;
import com.daoyu.niuqun.util.ViewUtil;

/**
 * 品牌/新品列表适配器
 */

public class BrandsListAdapter extends BaseAdapter
{

    private static final String TAG = "BrandsListAdapter";

    private Context context;

    private List<BrandInfo> dataList = new ArrayList<>();

    private int height;

    public BrandsListAdapter(Context context, List<BrandInfo> dataList)
    {
        this.context = context;
        if (null != dataList && dataList.size() > 0)
        {
            this.dataList.addAll(dataList);
        }
        height = (int) ((ViewUtil.getIntance().getDisplayWidth(context) - ViewUtil.getIntance().dip2px(context, 24)) * 4
            / 17);
    }

    public void addAllItem(List<BrandInfo> newDataList)
    {
        if (null != newDataList)
        {
            dataList.addAll(newDataList);
        }
    }

    public void clear()
    {
        dataList.clear();
    }

    @Override
    public int getCount()
    {
        return dataList.size();
    }

    @Override
    public Object getItem(int position)
    {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder viewHolder;
        if (null == convertView)
        {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.item_grid_brand, null);
            viewHolder.item_image = convertView.findViewById(R.id.item_image);
            ViewGroup.LayoutParams params = viewHolder.item_image.getLayoutParams();
            params.height = height;
            viewHolder.item_image.setLayoutParams(params);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final BrandInfo baseItem = dataList.get(position);
        if (null != baseItem)
        {
            String url = baseItem.getThumb_image();
            if (!TextUtils.isEmpty(url) && !"null".equals(url))
            {
                if (!url.contains(HttpConstant.URL) && !url.contains("http") && !url.contains("https"))
                {
                    url = HttpConstant.URL + url;
                }
            }
            ImageLoad.getInstance().load(context, viewHolder.item_image, url,
                new RequestOptions().error(R.mipmap.default__avatar).placeholder(R.mipmap.default__avatar));
            convertView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Logger.d(TAG, "getView, onClick at: " + baseItem.getGoods_id());
                    Intent intent = new Intent(context, GoodsDetailActivity.class);
                    intent.putExtra(IntentConstant.GOODS_ID, baseItem.getGoods_id());
                    context.startActivity(intent);
                }
            });
        }
        else
        {
            convertView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Logger.d(TAG, "getView, onClick at null!");
                }
            });
        }
        return convertView;
    }

    private static class ViewHolder
    {
        ImageView item_image;
    }

}
