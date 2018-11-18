package com.daoyu.niuqun.ui.brand;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daoyu.chat.server.network.http.HttpException;
import com.daoyu.chat.server.response.BaseSealResponse;
import com.daoyu.chat.server.response.CartToOrderResponse;
import com.daoyu.chat.server.utils.NToast;
import com.daoyu.chat.server.widget.DialogWithYesOrNoUtils;
import com.daoyu.chat.server.widget.LoadDialog;
import com.daoyu.chat.ui.activity.BaseActivity;
import com.daoyu.niuqun.R;
import com.daoyu.niuqun.bean.AddressBean;
import com.daoyu.niuqun.bean.CartGoodsInfo;
import com.daoyu.niuqun.constant.ActivityResultConstant;
import com.daoyu.niuqun.constant.HttpConstant;
import com.daoyu.niuqun.constant.IntentConstant;
import com.daoyu.niuqun.constant.ResponseConstant;
import com.daoyu.niuqun.ui.App;
import com.daoyu.niuqun.ui.adapter.GoodsListAdapter;
import com.daoyu.niuqun.ui.center.AddressListActivity;
import com.daoyu.niuqun.ui.center.RechargeActivity;
import com.daoyu.niuqun.util.EventManager;
import com.daoyu.niuqun.util.Logger;
import com.daoyu.niuqun.view.MyListView;

import io.rong.eventbus.EventBus;

/**
 * 结算
 */
public class SettlementActivity extends BaseActivity implements View.OnClickListener
{

    private static final String TAG = "SettlementActivity";

    private RelativeLayout rlAddress;

    private TextView tvPerson;

    private TextView tvAddress;

    private EditText etRemark;

    private MyListView myListView;

    private TextView tvTotal;

    private TextView tvComfirm;

    private AddressBean bean;

    private String remark;

    //结算订单id（如果未支付，当又生成新的结算订单时，前一个结算订单失效）
    private String orderId;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settlement);
        initView();
        initListener();
        initData();
    }

    private void initView()
    {
        setTitle(R.string.to_settlement);
        rlAddress = findViewById(R.id.rl_address);
        tvPerson = findViewById(R.id.tv_person);
        tvAddress = findViewById(R.id.tv_address);
        myListView = findViewById(R.id.mylist);
        etRemark = findViewById(R.id.et_remark);
        tvTotal = findViewById(R.id.tv_total);
        tvComfirm = findViewById(R.id.tv_comfirm);
    }

    private void initListener()
    {
        tvComfirm.setOnClickListener(this);
        rlAddress.setOnClickListener(this);
    }

    private void initData()
    {
        GoodsListAdapter goodsListAdapter = new GoodsListAdapter(this);
        myListView.setAdapter(goodsListAdapter);
        App app = (App) getApplication();
        List<CartGoodsInfo> cartGoodsInfos = app.getCartGoodsInfos();
        if (null != cartGoodsInfos)
        {
            goodsListAdapter.addAll(cartGoodsInfos);
        }
        Intent intent = getIntent();
        String total = intent.getStringExtra(IntentConstant.CART_TO_SETTLEMENT_TOTAL);
        if (null != total)
        {
            tvTotal.setText(total);
        }
        bean = app.getAddressBean();
        if (null != bean)
        {
            getAddressToView();
        }
    }

    private void toComfirm()
    {
        remark = etRemark.getText().toString().trim();
        if (null == bean)
        {
            NToast.shortToast(mContext, getResources().getString(R.string.please_select_address));
        }
        else
        {
            LoadDialog.show(mContext);
            request(ResponseConstant.SEND_ORDER_FROM_CART, true);
        }
    }

    @Override
    public Object doInBackground(int requestCode, String id) throws HttpException
    {
        switch (requestCode)
        {
            case ResponseConstant.SEND_ORDER_FROM_CART:
                return action.sendOrderFromCart(bean.getAdd_id(), remark, "1");
            case ResponseConstant.ORDER_TO_PAY:
                return action.toPayOrder(orderId);
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
            case ResponseConstant.SEND_ORDER_FROM_CART:
                if (result instanceof CartToOrderResponse)
                {
                    CartToOrderResponse response = (CartToOrderResponse) result;
                    if (HttpConstant.SUCCESS.equals(response.getCode()))
                    {
                        Logger.d(TAG, "settlement order success!");
                        orderId = response.getData();
                        if (!TextUtils.isEmpty(orderId))
                        {
                            request(ResponseConstant.ORDER_TO_PAY);
                        }
                        else
                        {
                            LoadDialog.dismiss(mContext);
                        }
                    }
                    else
                    {
                        LoadDialog.dismiss(mContext);
                        NToast.shortToast(mContext, response.getMessage());
                    }
                }
                break;
            case ResponseConstant.ORDER_TO_PAY:
                LoadDialog.dismiss(mContext);
                if (result instanceof BaseSealResponse)
                {
                    BaseSealResponse response = (BaseSealResponse) result;
                    if (HttpConstant.SUCCESS.equals(response.getCode()))
                    {
                        Logger.d(TAG, "to pay order success!");
                        Intent intent = new Intent(mContext, BuySuccessActivity.class);
                        startActivity(intent);
                        EventBus.getDefault().post(new EventManager.CartListFinish());
                        finish();
                    }
                    else if (HttpConstant.MONEY_NOT_ENOUGH.equals(response.getCode()))
                    {
                        //余额不足
                        DialogWithYesOrNoUtils.getInstance().showOnlyDialog(this, "", "", "",
                            new DialogWithYesOrNoUtils.DialogCallBack()
                            {
                                @Override
                                public void executeEvent()
                                {
                                    //去充值
                                    goToRecharge();
                                }

                                @Override
                                public void executeEditEvent(String editText)
                                {

                                }

                                @Override
                                public void updatePassword(String oldPassword, String newPassword)
                                {

                                }
                            });
                    }
                    else
                    {
                        NToast.shortToast(mContext, response.getMessage());
                    }
                }
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
            case ResponseConstant.SEND_ORDER_FROM_CART:
                LoadDialog.dismiss(mContext);
                NToast.shortToast(mContext, getResources().getString(R.string.http_client_false));
                break;
            case ResponseConstant.ORDER_TO_PAY:
                LoadDialog.dismiss(mContext);
                NToast.shortToast(mContext, getResources().getString(R.string.http_client_false));
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.tv_comfirm:
                if (TextUtils.isEmpty(orderId))
                {
                    toComfirm();
                }
                else
                {
                    LoadDialog.show(mContext);
                    request(ResponseConstant.ORDER_TO_PAY);
                }

                break;
            case R.id.rl_address:
                toSelectAddress();
                break;
            default:
                break;
        }
    }

    private void toSelectAddress()
    {
        Intent intent = new Intent(this, AddressListActivity.class);
        intent.putExtra(IntentConstant.ADDRESS_FROM, 1);
        startActivityForResult(intent, ActivityResultConstant.ADDRESS_BY_GOODS);
    }

    private void goToRecharge()
    {
        Intent intent = new Intent(this, RechargeActivity.class);
        startActivity(intent);
    }

    private void getAddressToView()
    {
        if (null != bean)
        {
            String address = getResources().getString(R.string.address_person_address_comfirm) + bean.getProvince()
                    + bean.getCity() + bean.getDistrict() + bean.getAddress();
            tvAddress.setText(address);
            String name = bean.getReal_name();
            String mobile = bean.getMobile();
            if (!TextUtils.isEmpty(name))
            {
                name = name + "\t\t\t\t" + mobile;
            }
            else
            {
                name = mobile;
            }
            name = getResources().getString(R.string.address_person_comfirm) + name;
            tvPerson.setText(name);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ActivityResultConstant.ADDRESS_BY_GOODS
            && resultCode == ActivityResultConstant.ADDRESS_BY_GOODS)
        {
            App app = (App) getApplication();
            bean = app.getAddressBean();
            getAddressToView();
        }
    }
}
