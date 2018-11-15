package com.daoyu.niuqun.ui.adapter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import com.daoyu.chat.server.utils.NToast;
import com.daoyu.niuqun.R;
import com.daoyu.niuqun.bean.CartGoodsInfo;
import com.daoyu.niuqun.util.ImageLoad;
import com.daoyu.niuqun.util.ViewUtil;

/**
 * 购物车列表适配器
 * 
 * @author clude
 *
 */
public class CartAdapter extends BaseAdapter
{
    private static final String TAG = "CartAdapter";

    private Context mContext;

    private List<CartGoodsInfo> listItems = new ArrayList<>();

    private LayoutInflater sInflater;

    private RequestOptions options;

    private int width = 0;

    //是否列表在滚动加载重用
    private boolean isChanged = false;

    private ImageButton btn;

    private TextView tv_total;

    private String unit;

    public final class ListItemView
    {
        TextView tv_name;
        TextView tv_price;
        TextView tv_number;
        LinearLayout ll_data;
        ImageView iv_sub;
        ImageView iv_plus;
        ImageView image;
        ImageButton ibtn_check;
    }

    public CartAdapter(Context context, ImageButton btn, TextView tv)
    {
        this.mContext = context;
        sInflater = LayoutInflater.from(mContext);
        this.btn = btn;
        this.tv_total = tv;
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

    /**
     * 去全选全不选（true去全选，false去全不选）
     * 
     * @param all boolean
     */
    public void selectAll(boolean all)
    {
        if (all)
        {
            int s = listItems.size();
            if (tv_total != null)
            {
                //重新计算总价
                String att = "0.00";
                for (int i = 0; i < s; i++)
                {
                    CartGoodsInfo map = listItems.get(i);
                    map.setHasCheck(true);
                    listItems.remove(i);
                    listItems.add(i, map);
                    String pr = map.getGoods_price();
                    if ("null".equals(pr) || TextUtils.isEmpty(pr))
                    {
                        pr = "0.00";
                    }
                    String num = map.getQuantity();
                    if ("null".equals(num) || TextUtils.isEmpty(num))
                    {
                        num = "0";
                    }
                    BigDecimal t = new BigDecimal(att);
                    BigDecimal p = new BigDecimal(pr);
                    BigDecimal n = new BigDecimal(num);
                    Double total = t.add(p.multiply(n)).doubleValue();
                    att = Double.toString(total);
                }
                String totalString = att + unit;
                tv_total.setText(totalString);
            }
            else
            {
                for (int i = 0; i < s; i++)
                {
                    CartGoodsInfo map = listItems.get(i);
                    map.setHasCheck(true);
                    listItems.remove(i);
                    listItems.add(i, map);
                }
            }
        }
        else
        {
            int s = listItems.size();
            for (int i = 0; i < s; i++)
            {
                CartGoodsInfo map = listItems.get(i);
                map.setHasCheck(false);
                listItems.remove(i);
                listItems.add(i, map);
            }
            if (tv_total != null)
            {
                String totalString = "0.00" + unit;
                tv_total.setText(totalString);
            }
        }
        this.notifyDataSetChanged();
    }

    /**
     * 是否全部选中
     * 
     * @return false没有全部选中，true已全部选中
     */
    public boolean getAll()
    {
        int s = listItems.size();
        for (int i = 0; i < s; i++)
        {
            if (!listItems.get(i).isHasCheck())
            {
                return false;
            }
        }
        return true;
    }

    /**
     * 是否有选中项
     * 
     * @return false、没有，true、有
     */
    public boolean getSelect()
    {
        int s = listItems.size();
        for (int i = 0; i < s; i++)
        {
            if (listItems.get(i).isHasCheck())
            {
                return true;
            }
        }
        return false;
    }

    /**
     * 删除操作
     * 
     * @return false 购物车还有商品；true,购物车为空
     */
    public boolean toDelete()
    {
        int s = listItems.size();
        for (int i = (s - 1); i > (-1); i--)
        {
            if (listItems.get(i).isHasCheck())
            {
                listItems.remove(i);
            }
        }
        String totalString = "0.00" + unit;
        tv_total.setText(totalString);
        int size = listItems.size();
        if (size > 0)
        {
            this.notifyDataSetChanged();
        }
        else
        {
            this.notifyDataSetChanged();
            return true;
        }
        return false;
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
            convertView = sInflater.inflate(R.layout.item_list_cart, null);
            listItemView.tv_name = convertView.findViewById(R.id.tv_name);
            listItemView.ll_data = convertView.findViewById(R.id.ll_data);
            listItemView.ll_data.getLayoutParams().height = width;
            listItemView.tv_price = convertView.findViewById(R.id.tv_price);
            listItemView.tv_number = convertView.findViewById(R.id.tv_number);
            listItemView.iv_sub = convertView.findViewById(R.id.iv_sub);
            listItemView.iv_plus = convertView.findViewById(R.id.iv_plus);
            listItemView.ibtn_check = convertView.findViewById(R.id.ibtn_check);
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

        if (goodsInfo.isHasCheck())
        {
            isChanged = true;
            listItemView.ibtn_check.setImageResource(R.mipmap.icon_cart_select);
            isChanged = false;
        }
        else
        {
            isChanged = true;
            listItemView.ibtn_check.setImageResource(R.mipmap.icon_cart_unselect);
            isChanged = false;
        }

        listItemView.iv_sub.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                String before = listItemView.tv_number.getText().toString();
                if ("0".equals(before) && TextUtils.isEmpty(before))
                {
                    before = "1";
                }
                int be = Integer.parseInt(before);
                if (be < 2)
                {
                    NToast.shortToast(mContext, mContext.getResources().getString(R.string.no_less));
                }
                else
                {
                    int af = be - 1;
                    String num = Integer.toString(af);
                    listItemView.tv_number.setText(num);
                    CartGoodsInfo map = listItems.get(p);
                    map.setQuantity(num);
                    listItems.remove(p);
                    listItems.add(p, map);
                    if (map.isHasCheck() && tv_total != null)
                    {
                        String att = tv_total.getText().toString();
                        if (TextUtils.isEmpty(att) || "null".equals(att))
                        {
                            att = "0.00";
                        }
                        String pr = map.getGoods_price();
                        if ("null".equals(pr) || TextUtils.isEmpty(pr))
                        {
                            pr = "0.00";
                        }
                        BigDecimal t = new BigDecimal(att);
                        BigDecimal p = new BigDecimal(pr);
                        double total = t.subtract(p).doubleValue();
                        String totalString = Double.toString(total) + unit;
                        tv_total.setText(totalString);
                    }
                }
            }

        });

        listItemView.iv_plus.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                String before = listItemView.tv_number.getText().toString();
                if ("0".equals(before) && TextUtils.isEmpty(before))
                {
                    before = "1";
                }
                int be = Integer.parseInt(before);
                if (be > 98)
                {
                    NToast.shortToast(mContext, mContext.getResources().getString(R.string.cart_is_full));
                }
                else
                {
                    int af = be + 1;
                    String num = Integer.toString(af);
                    listItemView.tv_number.setText(num);
                    CartGoodsInfo map = listItems.get(p);
                    map.setQuantity(num);
                    listItems.remove(p);
                    listItems.add(p, map);
                    if (map.isHasCheck() && tv_total != null)
                    {
                        String att = tv_total.getText().toString();
                        if (TextUtils.isEmpty(att) || "null".equals(att))
                        {
                            att = "0.00";
                        }
                        String pr = map.getGoods_price();
                        if ("null".equals(pr) || TextUtils.isEmpty(pr))
                        {
                            pr = "0.00";
                        }
                        BigDecimal t = new BigDecimal(att);
                        BigDecimal p = new BigDecimal(pr);
                        double total = t.add(p).doubleValue();
                        String totalString = Double.toString(total) + unit;
                        tv_total.setText(totalString);
                    }
                }
            }

        });

        listItemView.ibtn_check.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                if (isChanged)
                {
                    return;
                }
                CartGoodsInfo map = listItems.get(p);
                if (map.isHasCheck())
                {
                    map.setHasCheck(false);
                    listItems.remove(p);
                    listItems.add(p, map);
                    listItemView.ibtn_check.setImageResource(R.mipmap.icon_cart_unselect);
                    if (tv_total != null)
                    {
                        String att = tv_total.getText().toString();
                        if (TextUtils.isEmpty(att) || "null".equals(att))
                        {
                            att = "0.00";
                        }
                        String pr = map.getGoods_price();
                        if ("null".equals(pr) || TextUtils.isEmpty(pr))
                        {
                            pr = "0.00";
                        }
                        String num = map.getQuantity();
                        if ("null".equals(num) || TextUtils.isEmpty(num))
                        {
                            num = "0";
                        }
                        BigDecimal t = new BigDecimal(att);
                        BigDecimal p = new BigDecimal(pr);
                        BigDecimal n = new BigDecimal(num);
                        double total = t.subtract(p.multiply(n)).doubleValue();
                        String totalString = Double.toString(total) + unit;
                        tv_total.setText(totalString);
                    }
                }
                else
                {
                    map.setHasCheck(true);
                    listItems.remove(p);
                    listItems.add(p, map);
                    listItemView.ibtn_check.setImageResource(R.mipmap.icon_cart_select);
                    if (tv_total != null)
                    {
                        String att = tv_total.getText().toString();
                        if (TextUtils.isEmpty(att) || "null".equals(att))
                        {
                            att = "0.00";
                        }
                        String pr = map.getGoods_price();
                        if ("null".equals(pr) || TextUtils.isEmpty(pr))
                        {
                            pr = "0.00";
                        }
                        String num = map.getQuantity();
                        if ("null".equals(num) || TextUtils.isEmpty(num))
                        {
                            num = "0";
                        }
                        BigDecimal t = new BigDecimal(att);
                        BigDecimal p = new BigDecimal(pr);
                        BigDecimal n = new BigDecimal(num);
                        double total = t.add(p.multiply(n)).doubleValue();
                        String totalString = Double.toString(total) + unit;
                        tv_total.setText(totalString);
                    }
                }
                boolean all = getAll();
                if (all)
                {
                    btn.setImageResource(R.mipmap.icon_cart_select);
                }
                else
                {
                    btn.setImageResource(R.mipmap.icon_cart_unselect);
                }
            }

        });

        return convertView;
    }

}
