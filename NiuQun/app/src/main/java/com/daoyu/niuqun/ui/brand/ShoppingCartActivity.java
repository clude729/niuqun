package com.daoyu.niuqun.ui.brand;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.daoyu.chat.server.network.http.HttpException;
import com.daoyu.chat.server.response.BaseSealResponse;
import com.daoyu.chat.server.response.CartGoodsResponse;
import com.daoyu.chat.server.utils.NToast;
import com.daoyu.chat.server.widget.LoadDialog;
import com.daoyu.chat.ui.activity.BaseActivity;
import com.daoyu.niuqun.R;
import com.daoyu.niuqun.bean.CartGoodsInfo;
import com.daoyu.niuqun.constant.HttpConstant;
import com.daoyu.niuqun.constant.IntentConstant;
import com.daoyu.niuqun.constant.ResponseConstant;
import com.daoyu.niuqun.ui.App;
import com.daoyu.niuqun.ui.adapter.CartAdapter;
import com.daoyu.niuqun.util.EventManager;
import com.daoyu.niuqun.util.Logger;

import io.rong.eventbus.EventBus;

/**
 * 购物车
 */
public class ShoppingCartActivity extends BaseActivity implements OnClickListener , OnRefreshListener
{

    private static final String TAG = "ShoppingCartActivity";

    private ListView goodsList;

    private CartAdapter cartAdapter;

    private SwipeRefreshLayout swipeRefreshLayout;

    private ImageButton ivAll;

    private TextView tvTotal;

    private TextView tvConfirm;

    private TextView tvDel;

    private boolean loadFinish = true;

    private String orderValue;

    private List<CartGoodsInfo> settlementGoods;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        initView();
        initListenter();
        initData();
    }

    private void initView()
    {
        setTitle(R.string.my_shopping_cart);
        mHeadRightText.setText(R.string.to_edit_cart);
        mHeadRightText.setVisibility(View.VISIBLE);
        goodsList = findViewById(R.id.goodsList);
        swipeRefreshLayout = findViewById(R.id.srl);
        ivAll = findViewById(R.id.ibtn_check);
        tvTotal = findViewById(R.id.tv_total);
        tvConfirm = findViewById(R.id.tv_confirm);
        tvDel = findViewById(R.id.tv_del);
    }

    private void initListenter()
    {
        ivAll.setOnClickListener(this);
        tvConfirm.setOnClickListener(this);
        tvDel.setOnClickListener(this);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_green_light, android.R.color.holo_red_light,
            android.R.color.holo_orange_light, android.R.color.holo_blue_light);
    }

    private void initData()
    {
        EventBus.getDefault().register(this);
        cartAdapter = new CartAdapter(this, ivAll, tvTotal);
        goodsList.setAdapter(cartAdapter);
        LoadDialog.show(mContext);
        request(ResponseConstant.GET_CART_LIST, true);
    }

    private void toOverCart()
    {
        JSONArray array = new JSONArray();
        List<CartGoodsInfo> cartGoodsInfos = cartAdapter.getList();
        int size = cartGoodsInfos.size();
        if (size > 0)
        {
            settlementGoods = new ArrayList<>();
            for (CartGoodsInfo cartGoodsInfo : cartGoodsInfos)
            {
                if (cartGoodsInfo.isHasCheck())
                {
                    settlementGoods.add(cartGoodsInfo);
                    JSONObject js = new JSONObject();
                    try
                    {
                        js.put("cart_Id", cartGoodsInfo.getCart_id());
                        js.put("quantity", cartGoodsInfo.getQuantity());
                        array.put(js);
                    }
                    catch (JSONException e)
                    {
                        Logger.e(TAG, "toOverCart, JSONException: " + e.getMessage());
                    }
                }
            }
            LoadDialog.show(mContext);
            orderValue = array.toString();
            request(ResponseConstant.BUY_GOODS_FROM_CART, true);
        }
    }

    private void toDelGoods()
    {
        List<CartGoodsInfo> cartGoodsInfos = cartAdapter.getList();
        int size = cartGoodsInfos.size();
        if (size > 0)
        {
            String str = "";
            for (CartGoodsInfo cartGoodsInfo : cartGoodsInfos)
            {
                if (cartGoodsInfo.isHasCheck())
                {
                    if (!TextUtils.isEmpty(str))
                    {
                        str = str + ",";
                    }
                    str = str + cartGoodsInfo.getCart_id();
                }
            }
            if (!TextUtils.isEmpty(str))
            {
                // TODO 调用删除接口
                Logger.d(TAG, "toDelGoods, goodsIds: " + str);
            }
        }
    }

    /**
     * 去结算
     */
    private void goToSettlement()
    {
        App app = (App) getApplication();
        app.setCartGoodsInfos(settlementGoods);
        Intent intent = new Intent(this, SettlementActivity.class);
        intent.putExtra(IntentConstant.CART_TO_SETTLEMENT_TOTAL, tvTotal.getText().toString().trim());
        startActivity(intent);
    }

    @Override
    public Object doInBackground(int requestCode, String id) throws HttpException
    {
        switch (requestCode)
        {
            case ResponseConstant.GET_CART_LIST:
                loadFinish = false;
                return action.getCartGoods();
            case ResponseConstant.DEL_GOODS_FROM_CART:
                loadFinish = false;
                break;
            case ResponseConstant.BUY_GOODS_FROM_CART:
                loadFinish = false;
                return action.overCartGoods(orderValue);
            default:
                break;
        }
        return super.doInBackground(requestCode, id);
    }

    @Override
    public void onSuccess(int requestCode, Object result)
    {
        switch (requestCode)
        {
            case ResponseConstant.GET_CART_LIST:
                loadFinish = true;
                if (result instanceof CartGoodsResponse)
                {
                    CartGoodsResponse response = (CartGoodsResponse) result;
                    if (HttpConstant.SUCCESS.equals(response.getCode()))
                    {
                        Logger.d(TAG, "get cart List success!");
                        cartAdapter.addAll(response.getData());
                        cartAdapter.selectAll(false);
                    }
                    else
                    {
                        NToast.shortToast(mContext, response.getMessage());
                    }
                }
                LoadDialog.dismiss(mContext);
                break;
            case ResponseConstant.DEL_GOODS_FROM_CART:
                loadFinish = true;
                if (result instanceof BaseSealResponse)
                {
                    BaseSealResponse response = (BaseSealResponse) result;
                    if (HttpConstant.SUCCESS.equals(response.getCode()))
                    {
                        Logger.d(TAG, "del goods from cart success!");
                        if (cartAdapter.toDelete())
                        {
                            mHeadRightText.setText(R.string.to_edit_cart);
                            tvTotal.setVisibility(View.VISIBLE);
                            tvDel.setVisibility(View.GONE);
                            tvConfirm.setVisibility(View.VISIBLE);
                        }
                    }
                    else
                    {
                        NToast.shortToast(mContext, response.getMessage());
                    }
                }
                LoadDialog.dismiss(mContext);
                break;
            case ResponseConstant.BUY_GOODS_FROM_CART:
                loadFinish = true;
                if (result instanceof BaseSealResponse)
                {
                    BaseSealResponse response = (BaseSealResponse) result;
                    if (HttpConstant.SUCCESS.equals(response.getCode()))
                    {
                        Logger.d(TAG, "over goods from cart success!");
                        goToSettlement();
                    }
                    else
                    {
                        NToast.shortToast(mContext, response.getMessage());
                    }
                }
                LoadDialog.dismiss(mContext);
                break;
            default:
                break;
        }
    }

    @Override
    public void onFailure(int requestCode, int state, Object result)
    {
        switch (requestCode)
        {
            case ResponseConstant.GET_CART_LIST:
                loadFinish = true;
                LoadDialog.dismiss(mContext);
                NToast.shortToast(mContext, getResources().getString(R.string.http_client_false));
                break;
            case ResponseConstant.DEL_GOODS_FROM_CART:
                loadFinish = true;
                LoadDialog.dismiss(mContext);
                NToast.shortToast(mContext, getResources().getString(R.string.http_client_false));
                break;
            case ResponseConstant.BUY_GOODS_FROM_CART:
                loadFinish = true;
                LoadDialog.dismiss(mContext);
                NToast.shortToast(mContext, getResources().getString(R.string.http_client_false));
                break;
            default:
                break;
        }
    }

    @Override
    public void onHeadRightTextViewClick(View v)
    {
        String str = getResources().getString(R.string.to_edit_cart);
        String btnStr = mHeadRightText.getText().toString();
        if (str.equals(btnStr))
        {
            //编辑
            mHeadRightText.setText(R.string.edit_cart_over);
            tvTotal.setVisibility(View.INVISIBLE);
            tvConfirm.setVisibility(View.GONE);
            tvDel.setVisibility(View.VISIBLE);
        }
        else
        {
            str = getResources().getString(R.string.edit_cart_over);
            if (str.equals(btnStr))
            {
                //编辑完成
                mHeadRightText.setText(R.string.to_edit_cart);
                tvTotal.setVisibility(View.VISIBLE);
                tvDel.setVisibility(View.GONE);
                tvConfirm.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onRefresh()
    {
        swipeRefreshLayout.setRefreshing(false);
        if (loadFinish)
        {
            request(ResponseConstant.GET_CART_LIST, true);
        }
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.ibtn_check:
                if (null != cartAdapter)
                {
                    boolean all = cartAdapter.getAll();
                    cartAdapter.selectAll(!all);
                    if (all)
                    {
                        ivAll.setImageResource(R.mipmap.icon_cart_unselect);
                    }
                    else
                    {
                        ivAll.setImageResource(R.mipmap.icon_cart_select);
                    }
                }
                break;
            case R.id.tv_confirm:
                if (null != cartAdapter)
                {
                    boolean sel = cartAdapter.getSelect();
                    if (sel)
                    {
                        toOverCart();
                    }
                    else
                    {
                        NToast.shortToast(mContext, getResources().getString(R.string.has_no_goods_in_cart));
                    }
                }
                break;
            case R.id.tv_del:
                if (null != cartAdapter)
                {
                    boolean sel = cartAdapter.getSelect();
                    if (sel)
                    {
                        toDelGoods();
                    }
                    else
                    {
                        NToast.shortToast(mContext, getResources().getString(R.string.has_no_goods_in_cart));
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy()
    {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    public void onEventMainThread(EventManager.CartListFinish cartListFinish)
    {
        Logger.d(TAG, "onEventMainThread, cartListFinish!");
        finish();
    }

}
