package com.daoyu.niuqun.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import com.daoyu.niuqun.R;
import com.daoyu.niuqun.bean.AddressBean;
import com.daoyu.niuqun.constant.ActivityResultConstant;
import com.daoyu.niuqun.constant.IntentConstant;
import com.daoyu.niuqun.constant.MessageConstant;
import com.daoyu.niuqun.ui.App;
import com.daoyu.niuqun.ui.center.AddressDetailActivity;
import com.daoyu.niuqun.ui.center.AddressListActivity;
import com.daoyu.niuqun.util.Logger;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 收货地址列表适配器
 */

public class AddressListAdapter extends BaseAdapter
{

    private static final String TAG = "AddressListAdapter";

    private Context context;

    private List<AddressBean> dataList = new ArrayList<>();

    private Handler handler;

    public AddressListAdapter(Context context, List<AddressBean> dataList, Handler handler)
    {
        this.context = context;
        this.handler = handler;
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
    public void addAllItem(List<AddressBean> newDataList)
    {
        dataList.clear();
        if (null != newDataList)
        {
            dataList.addAll(newDataList);
        }
    }

    /**
     * 设置默认地址
     * 
     * @param addressId 地址Id
     */
    public void setDefaultAddress(String addressId)
    {
        if (!TextUtils.isEmpty(addressId))
        {
            for (AddressBean addressBean : dataList)
            {
                if (addressId.equals(addressBean.getAdd_id()))
                {
                    addressBean.setStatus("1");
                }
                else
                {
                    addressBean.setStatus("0");
                }
            }
            notifyDataSetChanged();
        }
    }

    public void removeAddress(String addressId)
    {
        if (!TextUtils.isEmpty(addressId))
        {
            for (AddressBean bean : dataList)
            {
                if (addressId.equals(bean.getAdd_id()))
                {
                    dataList.remove(bean);
                    break;
                }
            }
            notifyDataSetChanged();
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
            convertView = inflater.inflate(R.layout.item_list_address, null);
            viewHolder.ivDefault = convertView.findViewById(R.id.iv_default);
            viewHolder.tvContact = convertView.findViewById(R.id.tv_contact);
            viewHolder.tvAddress = convertView.findViewById(R.id.tv_address);
            viewHolder.llDefault = convertView.findViewById(R.id.ll_default);
            viewHolder.llEdit = convertView.findViewById(R.id.ll_edit);
            viewHolder.llDel = convertView.findViewById(R.id.ll_del);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final AddressBean baseItem = dataList.get(position);
        if (null != baseItem)
        {
            String name = baseItem.getReal_name();
            String mobile = baseItem.getMobile();
            if (!TextUtils.isEmpty(name))
            {
                name = name + "\t\t\t\t" + mobile;
            }
            else
            {
                name = mobile;
            }
            viewHolder.tvContact.setText(name);

            viewHolder.tvAddress.setText(baseItem.getAddress());

            if ("1".equals(baseItem.getStatus()))
            {
                viewHolder.ivDefault.setImageResource(R.mipmap.icon_cart_select);
            }
            else
            {
                viewHolder.ivDefault.setImageResource(R.mipmap.icon_cart_unselect);
            }
            convertView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Logger.d(TAG, "getView, onClick at: " + baseItem.getAddress());
                    App app = (App) context.getApplicationContext();
                    app.setAddressBean(baseItem);
                    if (null != handler)
                    {
                        Message msg = Message.obtain();
                        msg.what = MessageConstant.ADDRESS_ITEM_CLICK;
                        msg.obj = baseItem.getAdd_id();
                        handler.sendMessage(msg);
                    }
                }
            });
            viewHolder.llDefault.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Logger.d(TAG, "setDefault, onClick at: " + baseItem.getAdd_id());
                    if (null != handler)
                    {
                        Message msg = Message.obtain();
                        msg.what = MessageConstant.ADDRESS_DEFAULT;
                        msg.obj = baseItem.getAdd_id();
                        handler.sendMessage(msg);
                    }
                }
            });
            viewHolder.llEdit.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Logger.d(TAG, "toEdit, onClick at: " + baseItem.getAdd_id());
                    App app = (App) context.getApplicationContext();
                    app.setAddressBean(baseItem);
                    Intent intent = new Intent(context, AddressDetailActivity.class);
                    intent.putExtra(IntentConstant.ADDRESS_ID, baseItem.getAdd_id());
                    intent.putExtra(IntentConstant.ADDRESS_TYPE, 1);
                    if (context instanceof AddressListActivity)
                    {
                        ((AddressListActivity) context).startActivityForResult(intent,
                            ActivityResultConstant.ADDRESS_EDIT);
                    }
                    else
                    {
                        context.startActivity(intent);
                    }
                }
            });
            viewHolder.llDel.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Logger.d(TAG, "toDel, onClick at: " + baseItem.getAdd_id());
                    if (null != handler)
                    {
                        Message msg = Message.obtain();
                        msg.what = MessageConstant.ADDRESS_DEL;
                        msg.obj = baseItem.getAdd_id();
                        handler.sendMessage(msg);
                    }
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
            viewHolder.llDefault.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Logger.d(TAG, "setDefault, onClick at null!");
                }
            });
            viewHolder.llEdit.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Logger.d(TAG, "toEdit, onClick at null!");
                }
            });
            viewHolder.llDel.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Logger.d(TAG, "toDel, onClick at null!");
                }
            });
        }
        return convertView;
    }

    private static class ViewHolder
    {
        ImageView ivDefault;
        TextView tvContact;
        TextView tvAddress;
        LinearLayout llDefault;
        LinearLayout llEdit;
        LinearLayout llDel;
    }

}
