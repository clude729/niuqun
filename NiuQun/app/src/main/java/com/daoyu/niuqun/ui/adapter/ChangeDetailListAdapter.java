package com.daoyu.niuqun.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import com.daoyu.niuqun.R;
import com.daoyu.niuqun.bean.ChangeInfo;
import com.daoyu.niuqun.util.Logger;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 零钱明细列表适配器
 */

public class ChangeDetailListAdapter extends BaseAdapter
{

    private static final String TAG = "ChangeDetailListAdapter";

    private Context context;

    private List<ChangeInfo> dataList = new ArrayList<>();

    public ChangeDetailListAdapter(Context context, List<ChangeInfo> dataList)
    {
        this.context = context;
        if (null != dataList && dataList.size() > 0)
        {
            this.dataList.addAll(dataList);
        }
    }

    /**
     * 添加数据
     * 
     * @param newDataList 地址数据
     */
    public void addAllItem(List<ChangeInfo> newDataList)
    {
        clear();
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
            convertView = inflater.inflate(R.layout.item_list_changedetail, null);
            viewHolder.tvAmount = convertView.findViewById(R.id.tv_amount);
            viewHolder.tvType = convertView.findViewById(R.id.tv_type);
            viewHolder.tvTime = convertView.findViewById(R.id.tv_time);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final ChangeInfo changeInfo = dataList.get(position);
        if (null != changeInfo)
        {
            String amount = changeInfo.getPay_amount();
            if (TextUtils.isEmpty(amount))
            {
                amount = "0";
            }
            String comeType = changeInfo.getCome_type();
            if ("1".equals(comeType))
            {
                comeType = "+";
                viewHolder.tvAmount.setTextColor(context.getResources().getColor(R.color.color10));
            }
            else
            {
                comeType = "-";
                viewHolder.tvAmount.setTextColor(context.getResources().getColor(R.color.color1));
            }
            amount = comeType + amount;
            viewHolder.tvAmount.setText(amount);

            String time = changeInfo.getAdd_time();
            viewHolder.tvTime.setText(time);

            String type = changeInfo.getPay_type();
            if ("0".equals(type))
            {
                type = context.getString(R.string.recharge_by_alipay);
            }
            else if ("1".equals(type))
            {
                type = context.getString(R.string.recharge_by_weixin);
            }
            else if ("2".equals(type))
            {
                type = context.getString(R.string.red_package_reward);
            }
            else
            {
                type = context.getString(R.string.shopping_consumption);
            }
            viewHolder.tvType.setText(type);
        }
        else
        {
            Logger.d(TAG, "getView, ChangeInfo is null!");
        }
        return convertView;
    }

    private static class ViewHolder
    {
        TextView tvAmount;
        TextView tvType;
        TextView tvTime;
    }

}
