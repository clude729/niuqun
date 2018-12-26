package com.daoyu.niuqun.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.daoyu.niuqun.R;

/**
 * 错误提示界面
 */

public class ErrorLayoutView extends LinearLayout
{

    private static final String TAG = "ErrorLayoutView";

    private LinearLayout viewEmptyCart;

    private LinearLayout viewNoData;

    private LinearLayout viewNoNetwork;

    public ErrorLayoutView(Context context)
    {
        super(context);
        initView(context);
    }

    public ErrorLayoutView(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        initView(context);
    }

    public ErrorLayoutView(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public ErrorLayoutView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    private void initView(Context context)
    {
        LayoutInflater.from(context).inflate(R.layout.layout_no_data, this);
        viewEmptyCart = findViewById(R.id.view_empty_cart);
        viewNoData = findViewById(R.id.view_no_data);
        viewNoNetwork = findViewById(R.id.view_no_network);
    }

    /**
     * 无网络
     */
    public void noNetwork()
    {
        viewEmptyCart.setVisibility(GONE);
        viewNoData.setVisibility(GONE);
        viewNoNetwork.setVisibility(VISIBLE);
    }

    /**
     * 购物车无商品
     */
    public void cartEmpty()
    {
       viewNoNetwork.setVisibility(GONE);
       viewNoData.setVisibility(GONE);
       viewEmptyCart.setVisibility(VISIBLE);
    }

    /**
     * 未检索到内容
     */
    public void noData()
    {
        viewEmptyCart.setVisibility(GONE);
        viewNoNetwork.setVisibility(GONE);
        viewNoData.setVisibility(VISIBLE);
    }

}
