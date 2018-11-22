package com.daoyu.niuqun.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import com.daoyu.niuqun.R;
import com.daoyu.niuqun.bean.CartGoodsInfo;
import com.daoyu.niuqun.bean.Order;
import com.daoyu.niuqun.ui.brand.OrderListCallBack;
import com.daoyu.niuqun.util.Logger;
import com.daoyu.niuqun.view.MyListView;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 订单列表适配器
 * 
 * @author clude
 *
 */
public class OrderListAdapter extends BaseAdapter
{
    private static final String TAG = "OrderListAdapter";

    private Context mContext;

    private List<Order> listItems = new ArrayList<>();

    private LayoutInflater sInflater;

    private OrderListCallBack callBack;

    private final class ViewHolder
    {
        //'待支付','待发货','已发货','待评价','已完成','已取消'0 -6
        TextView tvOrder;
        TextView tvStatus;
        TextView tvDel;
        TextView tvView;
        TextView tvCancel;
        TextView tvPay;
        TextView tvRemind;
        TextView tvConfirm;
        MyListView list;
        LinearLayout llAction;
    }

    public OrderListAdapter(Context context, OrderListCallBack callBack)
    {
        this.mContext = context;
        sInflater = LayoutInflater.from(mContext);
        this.callBack = callBack;
    }

    /**
     * 更新订单
     *
     * @param orderId 订单号
     * @param status 新状态
     */
    public void setOrder(String orderId, String status)
    {
        if (TextUtils.isEmpty(orderId))
        {
            Logger.d(TAG, "setOrder, orderId is null return!");
            return;
        }
        for (int i = 0; i < listItems.size(); i ++)
        {
            Order order = listItems.get(i);
            if (orderId.equals(order.getOrder_id()))
            {
                order.setStatus(status);
                if ("0".equals(status))
                {
                    order.setStatus_txt("待支付");
                }
                else if ("1".equals(status))
                {
                    order.setStatus_txt("待发货");
                }
                else if ("2".equals(status))
                {
                    order.setStatus_txt("已发货");
                }
                else if ("3".equals(status))
                {
                    order.setStatus_txt("待评价");
                }
                else if ("4".equals(status))
                {
                    order.setStatus_txt("已完成");
                }
                else if ("5".equals(status))
                {
                    order.setStatus_txt("已取消");
                }
                else
                {
                    listItems.remove(i);
                }
                notifyDataSetChanged();
            }
        }
    }

    /**
     * 添加数据
     *
     * @param orders 订单
     */
    public void addAll(List<Order> orders)
    {
        if (null != orders)
        {
            listItems.addAll(orders);
        }
    }

    public void clear()
    {
        listItems.clear();
    }

    public List<Order> getList()
    {
        return listItems;
    }

    public int getCount()
    {
        return listItems.size();
    }

    @Override
    public Object getItem(int position)
    {
        return listItems.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder listItemView;
        if (convertView == null)
        {
            listItemView = new ViewHolder();
            convertView = sInflater.inflate(R.layout.item_list_order, null);
            listItemView.tvOrder = convertView.findViewById(R.id.tv_order);
            listItemView.tvStatus = convertView.findViewById(R.id.tv_status);
            listItemView.tvDel = convertView.findViewById(R.id.tv_del);
            listItemView.tvView = convertView.findViewById(R.id.tv_view);
            listItemView.tvCancel = convertView.findViewById(R.id.tv_cancel);
            listItemView.tvPay = convertView.findViewById(R.id.tv_pay);
            listItemView.tvRemind = convertView.findViewById(R.id.tv_remind);
            listItemView.tvConfirm = convertView.findViewById(R.id.tv_confirm);
            listItemView.list = convertView.findViewById(R.id.list);
            listItemView.llAction = convertView.findViewById(R.id.ll_action);
            convertView.setTag(listItemView);
        }
        else
        {
            listItemView = (ViewHolder) convertView.getTag();
        }

        listItemView.tvDel.setVisibility(View.GONE);
        listItemView.tvView.setVisibility(View.GONE);
        listItemView.tvCancel.setVisibility(View.GONE);
        listItemView.tvRemind.setVisibility(View.GONE);
        listItemView.tvPay.setVisibility(View.GONE);
        listItemView.tvConfirm.setVisibility(View.GONE);
        listItemView.llAction.setVisibility(View.GONE);
        final Order order = listItems.get(position);
        if (null == order)
        {
            Logger.d(TAG, "order in null! return");
            listItemView.list.setVisibility(View.GONE);
            return convertView;
        }

        String orderNo = order.getOrder_sn();
        if ("null".equals(orderNo))
        {
            orderNo = "";
        }
        listItemView.tvOrder.setText(orderNo);

        String statusTxt = order.getStatus_txt();
        if ("null".equals(statusTxt))
        {
            statusTxt = "";
        }
        listItemView.tvStatus.setText(statusTxt);

        List<CartGoodsInfo> cartGoodsInfos = order.getList();
        GoodsListAdapter adapter = new GoodsListAdapter(mContext, cartGoodsInfos);
        listItemView.list.setAdapter(adapter);
        listItemView.list.setVisibility(View.VISIBLE);

        String status = order.getStatus();
        if ("0".equals(status))
        {
            //待支付
            listItemView.tvPay.setVisibility(View.VISIBLE);
            listItemView.tvCancel.setVisibility(View.VISIBLE);
            listItemView.llAction.setVisibility(View.VISIBLE);
        }
        else if ("1".equals(status))
        {
            //待发货
            listItemView.tvRemind.setVisibility(View.VISIBLE);
            listItemView.tvCancel.setVisibility(View.VISIBLE);
            listItemView.llAction.setVisibility(View.VISIBLE);
        }
        else if ("2".equals(status))
        {
            //待收货
            listItemView.tvView.setVisibility(View.VISIBLE);
            listItemView.tvConfirm.setVisibility(View.VISIBLE);
            listItemView.llAction.setVisibility(View.VISIBLE);
        }
        else if ("3".equals(status))
        {
            //待评价
            listItemView.tvDel.setVisibility(View.VISIBLE);
            listItemView.llAction.setVisibility(View.VISIBLE);
        }
        else if ("4".equals(status))
        {
            //已完成
            listItemView.tvDel.setVisibility(View.VISIBLE);
            listItemView.llAction.setVisibility(View.VISIBLE);
        }
        listItemView.tvPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != callBack)
                {
                    callBack.orderToPay(order.getOrder_id());
                }
            }
        });

        listItemView.tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != callBack)
                {
                    callBack.orderToCancel(order.getOrder_id());
                }
            }
        });

        listItemView.tvRemind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != callBack)
                {
                    callBack.orderToRemind(order.getOrder_id());
                }
            }
        });

        listItemView.tvView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != callBack)
                {
                    callBack.orderGetView(order.getOrder_id());
                }
            }
        });

        listItemView.tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != callBack)
                {
                    callBack.orderConfirmReceipt(order.getOrder_id());
                }
            }
        });

        listItemView.tvDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != callBack)
                {
                    callBack.orderToDel(order.getOrder_id());
                }
            }
        });
        return convertView;
    }

}
