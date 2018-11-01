package com.daoyu.niuqun.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daoyu.niuqun.R;
import com.daoyu.niuqun.bean.MyBaseItem;
import com.daoyu.niuqun.util.ViewUtil;

/**
 * 个人中心菜单适配器
 */

public class MyItemListAdapter extends BaseAdapter
{

    private static final String TAG = "MyItemListAdapter";

    private Context context;

    private List<MyBaseItem> dataList = new ArrayList<>();

    private int height;

    public MyItemListAdapter(Context context, List<MyBaseItem> dataList)
    {
        this.context = context;
        if (null != dataList && dataList.size() > 0)
        {
            this.dataList.addAll(dataList);
        }
        height = (int) (ViewUtil.getIntance().getDisplayWidth(context) / 5);
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
            convertView = inflater.inflate(R.layout.item_my_list, null);
            viewHolder.item_image = convertView.findViewById(R.id.item_image);
            viewHolder.item_name = convertView.findViewById(R.id.item_name);
            viewHolder.viewparent = convertView.findViewById(R.id.viewparent);
            ViewGroup.LayoutParams params = viewHolder.viewparent.getLayoutParams();
            params.height = height;
            viewHolder.viewparent.setLayoutParams(params);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        MyBaseItem baseItem = dataList.get(position);
        if (null != baseItem)
        {
            if (0 != baseItem.getStringId())
            {
                viewHolder.item_name.setText(baseItem.getStringId());
            }
            else
            {
                viewHolder.item_name.setText("");
            }
            if (0 != baseItem.getImageId())
            {
                viewHolder.item_image.setImageResource(baseItem.getImageId());
            }
            else
            {
                viewHolder.item_image.setImageBitmap(null);
            }
            convertView.setOnClickListener(baseItem.getListener());
        }
        return convertView;
    }

    private static class ViewHolder
    {
        ImageView item_image;
        TextView item_name;
        LinearLayout viewparent;
    }

}
