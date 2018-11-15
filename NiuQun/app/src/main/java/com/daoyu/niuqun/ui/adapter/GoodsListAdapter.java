package com.daoyu.niuqun.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import com.bumptech.glide.request.RequestOptions;
import com.daoyu.niuqun.R;
import com.daoyu.niuqun.bean.CartGoodsInfo;
import com.daoyu.niuqun.util.ImageLoad;
import com.daoyu.niuqun.util.ViewUtil;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 商品列表适配器
 * 
 * @author clude
 *
 */
public class GoodsListAdapter extends BaseAdapter
{
    private static final String TAG = "GoodsListAdapter";

    private Context mContext;

    private List<CartGoodsInfo> listItems = new ArrayList<>();

    private LayoutInflater sInflater;

    private RequestOptions options;

    private int width = 0;

    private String unit;

    public final class ListItemView
    {
        TextView tv_name;
        TextView tv_price;
        TextView tv_number;
        LinearLayout ll_data;
        ImageView image;
    }

    public GoodsListAdapter(Context context)
    {
        this.mContext = context;
        sInflater = LayoutInflater.from(mContext);
        width = (ViewUtil.getIntance().getDisplayWidth(mContext) - ViewUtil.getIntance().dip2px(mContext, 40)) * 2 / 7;
        options = new RequestOptions().error(R.drawable.default_useravatar).placeholder(R.drawable.default_useravatar);
        unit = mContext.getResources().getString(R.string.my_symbol_app);
    }

    /**
     * 添加数据
     *
     * @param cartGoodsInfos 购物车商品
     */
    public void addAll(List<CartGoodsInfo> cartGoodsInfos)
    {
        listItems.clear();
        if (null != cartGoodsInfos && cartGoodsInfos.size() > 0)
        {
            listItems.addAll(cartGoodsInfos);
        }
        notifyDataSetChanged();
    }

    public List<CartGoodsInfo> getList()
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
        final ListItemView listItemView;
        final int p = position;
        if (convertView == null)
        {
            listItemView = new ListItemView();
            convertView = sInflater.inflate(R.layout.item_list_goods, null);
            listItemView.tv_name = convertView.findViewById(R.id.tv_name);
            listItemView.ll_data = convertView.findViewById(R.id.ll_data);
            listItemView.ll_data.getLayoutParams().height = width;
            listItemView.tv_price = convertView.findViewById(R.id.tv_price);
            listItemView.tv_number = convertView.findViewById(R.id.tv_number);
            listItemView.image = convertView.findViewById(R.id.image);
            LayoutParams params1 = listItemView.image.getLayoutParams();
            params1.width = width;
            params1.height = width;
            listItemView.image.setLayoutParams(params1);
            convertView.setTag(listItemView);
        }
        else
        {
            listItemView = (ListItemView) convertView.getTag();
        }

        CartGoodsInfo goodsInfo = listItems.get(position);
        if (null == goodsInfo)
        {
            return convertView;
        }

        String name = goodsInfo.getGoods_name();
        if ("null".equals(name))
        {
            name = "";
        }
        listItemView.tv_name.setText(name);

        String price = goodsInfo.getGoods_price();
        if ("null".equals(price) || TextUtils.isEmpty(price))
        {
            price = "0";
        }
        price = price + unit;
        listItemView.tv_price.setText(price);

        String number = goodsInfo.getQuantity();
        if ("null".equals(number) || TextUtils.isEmpty(number) || "0".equals(number))
        {
            number = "1";
        }
        listItemView.tv_number.setText(number);

        String url = goodsInfo.getThumb_image();
        if (!TextUtils.isEmpty(url) && !"null".equals(url))
        {
            ImageLoad.getInstance().load(mContext, listItemView.image, url, options);
        }
        else
        {
            listItemView.image.setImageResource(R.mipmap.default__avatar);
        }

        return convertView;
    }

}
