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
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import com.daoyu.chat.server.widget.SelectableRoundedImageView;
import com.daoyu.niuqun.R;
import com.daoyu.niuqun.bean.BaseAppMessage;
import com.daoyu.niuqun.constant.HttpConstant;
import com.daoyu.niuqun.constant.IntentConstant;
import com.daoyu.niuqun.ui.brand.GoodsDetailActivity;
import com.daoyu.niuqun.ui.center.MessageDetailActivity;
import com.daoyu.niuqun.util.ImageLoad;
import com.daoyu.niuqun.util.Logger;
import com.daoyu.niuqun.util.ViewUtil;

/**
 * 系统消息列表适配器
 */

public class MessageListAdapter extends BaseAdapter
{

    private static final String TAG = "MessageListAdapter";

    private Context context;

    private List<BaseAppMessage> dataList = new ArrayList<>();

    private int height;

    public MessageListAdapter(Context context, List<BaseAppMessage> dataList)
    {
        this.context = context;
        if (null != dataList && dataList.size() > 0)
        {
            this.dataList.addAll(dataList);
        }
        height = (int) ((ViewUtil.getIntance().getDisplayWidth(context) - ViewUtil.getIntance().dip2px(context, 32)) / 3);
    }

    public void addAllItem(List<BaseAppMessage> newDataList)
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
            convertView = inflater.inflate(R.layout.item_list_message, null);
            viewHolder.item_image = convertView.findViewById(R.id.image);
            viewHolder.tvTitle = convertView.findViewById(R.id.tv_title);
            ViewGroup.LayoutParams params = viewHolder.item_image.getLayoutParams();
            params.height = height;
            viewHolder.item_image.setLayoutParams(params);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final BaseAppMessage appMessage = dataList.get(position);
        if (null != appMessage)
        {
            String url = appMessage.getThumb_image();
            if (!TextUtils.isEmpty(url) && !"null".equals(url))
            {
                if (!url.contains(HttpConstant.URL) && !url.contains("http") && !url.contains("https"))
                {
                    url = HttpConstant.URL + url;
                }
            }
            ImageLoad.getInstance().load(context, viewHolder.item_image, url,
                new RequestOptions().error(R.mipmap.default__avatar).placeholder(R.mipmap.default__avatar));

            String title = appMessage.getTitle();
            viewHolder.tvTitle.setText(title);

            convertView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Logger.d(TAG, "getView, onClick at: " + appMessage.getMs_id());
                    Intent intent = new Intent(context, MessageDetailActivity.class);
                    intent.putExtra(IntentConstant.MESSAGE_ID, appMessage.getMs_id());
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
        SelectableRoundedImageView item_image;
        TextView tvTitle;
    }

}
