package com.daoyu.niuqun.ui.brand;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daoyu.chat.server.network.http.HttpException;
import com.daoyu.chat.server.response.BaseSealResponse;
import com.daoyu.chat.server.response.GoodsDetailResponse;
import com.daoyu.chat.server.response.OverCartResponse;
import com.daoyu.chat.server.utils.NToast;
import com.daoyu.chat.server.widget.LoadDialog;
import com.daoyu.chat.ui.activity.BaseActivity;
import com.daoyu.niuqun.R;
import com.daoyu.niuqun.bean.AddressBean;
import com.daoyu.niuqun.bean.GoodsInfo;
import com.daoyu.niuqun.bean.SettlementInfo;
import com.daoyu.niuqun.constant.HttpConstant;
import com.daoyu.niuqun.constant.IntentConstant;
import com.daoyu.niuqun.constant.ResponseConstant;
import com.daoyu.niuqun.ui.App;
import com.daoyu.niuqun.ui.adapter.GoodsPagerAdapter;
import com.daoyu.niuqun.util.Logger;
import com.daoyu.niuqun.util.ViewUtil;
import com.daoyu.niuqun.view.AdDialog;
import com.daoyu.niuqun.view.AdResultCallBack;
import com.viewpagerindicator.CirclePageIndicator;

/**
 * 品牌/新品详情
 */
public class GoodsDetailActivity extends BaseActivity implements View.OnClickListener
{

    private static final String TAG = "GoodsDetailActivity";

    private ViewPager viewPager;

    private CirclePageIndicator indicator;

    private TextView tvName;

    private TextView tvDesc;

    private TextView tvPrice;

    private Button btnGet;

    private LinearLayout llGoods;

    private String goodsId;

    private String goodsPrice;
    
    private String adWords;
    
    private AdResultCallBack callBack = new AdResultCallBack()
    {
        @Override
        public void clickRight()
        {
            Logger.d(TAG, "adwords is right!");
            request(ResponseConstant.BRANDS_SCORE);
        }

        @Override
        public void clickWrong()
        {
            Logger.d(TAG, "adwords is wrong!");
            NToast.shortToast(mContext, getResources().getString(R.string.goods_ad_wrong_hint));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);
        initView();
        initData();
    }

    private void initView()
    {
        setTitle(R.string.goods_detail);
        viewPager = findViewById(R.id.viewpager);
        ImageView ivShare = findViewById(R.id.iv_share);
        indicator = findViewById(R.id.indicator);
        tvName = findViewById(R.id.tv_name);
        tvDesc = findViewById(R.id.tv_desc);
        tvPrice = findViewById(R.id.tv_price);
        btnGet = findViewById(R.id.btn_get);
        Button btnAdd = findViewById(R.id.btn_add);
        Button btnBuy = findViewById(R.id.btn_buy);
        llGoods = findViewById(R.id.ll_goods);
        llGoods.setVisibility(View.GONE);
        btnGet.setVisibility(View.INVISIBLE);
        indicator.setVisibility(View.INVISIBLE);
        ivShare.setOnClickListener(this);
        btnGet.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        btnBuy.setOnClickListener(this);
        int width = ViewUtil.getIntance().getDisplayWidth(mContext);
        ViewGroup.LayoutParams layoutParams = viewPager.getLayoutParams();
        layoutParams.width = width;
        layoutParams.height = width;
        viewPager.setLayoutParams(layoutParams);
    }

    private void initData()
    {
        Intent intent = getIntent();
        goodsId = intent.getStringExtra(IntentConstant.GOODS_ID);
        if (TextUtils.isEmpty(goodsId) || "null".equals(goodsId))
        {
            Logger.d(TAG, "initData, goods is null!");
            NToast.shortToast(mContext, "获取信息失败，请重新进入！");
            finish();
            return;
        }
        LoadDialog.show(mContext);
        request(ResponseConstant.GOODS_DETAIL);
    }

    @Override
    public Object doInBackground(int requestCode, String id) throws HttpException
    {
        switch (requestCode)
        {
            case ResponseConstant.GOODS_DETAIL:
                return action.getGoodsDetail(goodsId);
            case ResponseConstant.BRANDS_SCORE:
                return action.getBrandsScore(goodsId, goodsPrice);
            case ResponseConstant.GOODS_ADD_CART:
                return action.addGoodsToCart(goodsId);
            case ResponseConstant.GOODS_BUY_SINGLE:
                return action.buyGoodsSingle(goodsId);
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
            case ResponseConstant.GOODS_DETAIL:
                if (result instanceof GoodsDetailResponse)
                {
                    GoodsDetailResponse response = (GoodsDetailResponse) result;
                    if (HttpConstant.SUCCESS.equals(response.getCode()))
                    {
                        Logger.d(TAG, "goodsDetail, onSuccess!");
                        GoodsInfo goodsInfo = response.getData();
                        showDetail(goodsInfo);
                    }
                    else
                    {
                        Logger.d(TAG, "response.code: " + response.getCode());
                    }
                }
                LoadDialog.dismiss(mContext);
                break;
            case ResponseConstant.BRANDS_SCORE:
                if (result instanceof BaseSealResponse)
                {
                    BaseSealResponse response = (BaseSealResponse) result;
                    if (HttpConstant.SUCCESS.equals(response.getCode()))
                    {
                        Logger.d(TAG, "brandsScore, onSuccess!");
                        String hintStr = getResources().getString(R.string.get_score_success)
                            + getResources().getString(R.string.my_symbol_app) + goodsPrice;
                        NToast.shortToast(mContext, hintStr);
                    }
                    else
                    {
                        Logger.d(TAG, "brandsScore, onSuccess, but code is: " + response.getCode());
                        NToast.shortToast(mContext, response.getMessage());
                    }
                }
                LoadDialog.dismiss(mContext);
                break;
            case ResponseConstant.GOODS_ADD_CART:
                if (result instanceof BaseSealResponse)
                {
                    BaseSealResponse response = (BaseSealResponse) result;
                    if (HttpConstant.SUCCESS.equals(response.getCode()))
                    {
                        Logger.d(TAG, "add goods to cart, onSuccess!");
                        String hintStr = getResources().getString(R.string.goods_add_cart_success);
                        NToast.shortToast(mContext, hintStr);
                    }
                    else
                    {
                        Logger.d(TAG, "add goods to cart, onSuccess, but code is: " + response.getCode());
                        NToast.shortToast(mContext, response.getMessage());
                    }
                }
                LoadDialog.dismiss(mContext);
                break;
            case ResponseConstant.GOODS_BUY_SINGLE:
                if (result instanceof OverCartResponse)
                {
                    OverCartResponse response = (OverCartResponse) result;
                    if (HttpConstant.SUCCESS.equals(response.getCode()))
                    {
                        Logger.d(TAG, "buy goods single, onSuccess!");
                        SettlementInfo settlementInfo = response.getData();
                        if (null != settlementInfo)
                        {

                            goToSettlement(settlementInfo);
                        }
                    }
                    else
                    {
                        Logger.d(TAG, "bug goods single, onSuccess, but code is: " + response.getCode());
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
            case ResponseConstant.GOODS_DETAIL:
                NToast.shortToast(mContext, getResources().getString(R.string.http_client_false));
                LoadDialog.dismiss(mContext);
                break;
            case ResponseConstant.BRANDS_SCORE:
                NToast.shortToast(mContext, getResources().getString(R.string.http_client_false));
                LoadDialog.dismiss(mContext);
                break;
            case ResponseConstant.GOODS_ADD_CART:
                NToast.shortToast(mContext, getResources().getString(R.string.http_client_false));
                LoadDialog.dismiss(mContext);
                break;
            case ResponseConstant.GOODS_BUY_SINGLE:
                NToast.shortToast(mContext, getResources().getString(R.string.http_client_false));
                LoadDialog.dismiss(mContext);
                break;
            default:
                break;
        }
    }

    /**
     * 单次购买成功去结算
     */
    private void goToSettlement(SettlementInfo settlementInfo)
    {
        if (null == settlementInfo.getList() || null == settlementInfo.getAddress())
        {
            Logger.d(TAG, "goToSettlement, List is null return!");
            return;
        }
        App app = (App) getApplication();
        app.setCartGoodsInfos(settlementInfo.getList());
        app.setAddressBean(null);
        for (AddressBean bean : settlementInfo.getAddress())
        {
            if ("1".equals(bean.getStatus()))
            {
                app.setAddressBean(bean);
            }
        }
        Intent intent = new Intent(this, SettlementActivity.class);
        intent.putExtra(IntentConstant.CART_TO_SETTLEMENT_TOTAL, tvPrice.getText().toString().trim());
        startActivity(intent);
    }

    private void showDetail(GoodsInfo goodsInfo)
    {
        if (null == goodsInfo)
        {
            Logger.d(TAG, "showDetail, goodsInfo is null, return!");
            return;
        }
        adWords = goodsInfo.getAdv_word();
        String cateId = goodsInfo.getCate_id();
        if ("0".equals(cateId))
        {
            llGoods.setVisibility(View.GONE);
            btnGet.setVisibility(View.VISIBLE);
        }
        else
        {
            btnGet.setVisibility(View.GONE);
            llGoods.setVisibility(View.VISIBLE);
        }
        String goodsName = goodsInfo.getGoods_name();
        setTextContent(tvName, goodsName);
        String goodsDesc = goodsInfo.getIntro();
        setTextContent(tvDesc, goodsDesc);
        goodsPrice = goodsInfo.getGoods_price();
        if (TextUtils.isEmpty(goodsPrice) || TextUtils.isEmpty(goodsPrice.trim()) || "null".equals(goodsPrice))
        {
            goodsPrice = "0";
        }
        String priceStr = getResources().getString(R.string.my_symbol_app) + goodsPrice;
        tvPrice.setText(priceStr);
        setViewPagerData(goodsInfo);
    }

    private void setViewPagerData(GoodsInfo goodsInfo)
    {
        String thumb_image = goodsInfo.getThumb_image();
        if (TextUtils.isEmpty(thumb_image) || TextUtils.isEmpty(thumb_image.trim()) || "null".equals(thumb_image))
        {
            thumb_image = "";
        }
        String[] images = thumb_image.split(",");
        int size = images.length;
        List<View> views = new ArrayList<>();
        if (size > 0)
        {
            for (String s : images)
            {
                View v = (View) LayoutInflater.from(mContext).inflate(R.layout.view_pager, null);
                views.add(v);
            }
            GoodsPagerAdapter goodsPagerAdapter = new GoodsPagerAdapter(mContext, views, images);
            viewPager.setAdapter(goodsPagerAdapter);
            indicator.setViewPager(viewPager);
            indicator.setPageColor(getResources().getColor(R.color.color3));//未选中的圈颜色
            indicator.setFillColor(getResources().getColor(R.color.color1));//选中的圈的颜色
            indicator.setStrokeColor(getResources().getColor(R.color.transparent));//圈的边框颜色
            viewPager.setVisibility(View.VISIBLE);
            if (size > 1)
            {
                indicator.setVisibility(View.VISIBLE);
            }
            else
            {
                indicator.setVisibility(View.GONE);
            }
        }
        else
        {
            viewPager.setVisibility(View.GONE);
            indicator.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_get:
                if (!TextUtils.isEmpty(adWords))
                {
                    AdDialog dialog = new AdDialog(mContext, adWords, callBack);
                    dialog.show();
                }
                break;
            case R.id.btn_add:
                if (!TextUtils.isEmpty(goodsId))
                {
                    LoadDialog.show(mContext);
                    request(ResponseConstant.GOODS_ADD_CART);
                }
                break;
            case R.id.btn_buy:
                if (!TextUtils.isEmpty(goodsId))
                {
                    LoadDialog.show(mContext);
                    request(ResponseConstant.GOODS_BUY_SINGLE);
                }
                break;
            case R.id.iv_share:

                break;
            default:
                break;
        }
    }

    private void setTextContent(TextView textContent, String str)
    {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str.trim()) || "null".equals(str))
        {
            textContent.setText("");
        }
        else
        {
            textContent.setText(str);
        }
    }

}
