package com.daoyu.niuqun.ui.center;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.daoyu.chat.server.network.http.HttpException;
import com.daoyu.chat.server.response.BaseSealResponse;
import com.daoyu.chat.server.response.MyAddressListResponse;
import com.daoyu.chat.server.utils.NToast;
import com.daoyu.chat.server.widget.LoadDialog;
import com.daoyu.chat.ui.activity.BaseActivity;
import com.daoyu.niuqun.R;
import com.daoyu.niuqun.bean.AddressBean;
import com.daoyu.niuqun.constant.ActivityResultConstant;
import com.daoyu.niuqun.constant.HttpConstant;
import com.daoyu.niuqun.constant.IntentConstant;
import com.daoyu.niuqun.constant.MessageConstant;
import com.daoyu.niuqun.constant.ResponseConstant;
import com.daoyu.niuqun.ui.adapter.AddressListAdapter;
import com.daoyu.niuqun.util.Logger;

/**
 * 收货地址列表
 */
public class AddressListActivity extends BaseActivity implements OnClickListener , OnRefreshListener
{

    private static final String TAG = "AddressListActivity";

    private SwipeRefreshLayout swipeRefreshLayout;

    private ListView addressList;

    private AddressListAdapter addressListAdapter;

    private Button btnAdd;

    private boolean loadFinish = true;

    private String addressId = "";

    private int from = 0;

    private Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case MessageConstant.ADDRESS_DEFAULT:
                    if (msg.obj instanceof String)
                    {
                        addressId = (String) msg.obj;
                        request(ResponseConstant.SET_DEFAULT_ADDRESS, true);
                    }
                    break;
                case MessageConstant.ADDRESS_DEL:
                    if (msg.obj instanceof String)
                    {
                        addressId = (String) msg.obj;
                        request(ResponseConstant.DEL_ADDRESS, true);
                    }
                    break;
                case MessageConstant.ADDRESS_ITEM_CLICK:
                    if (msg.obj instanceof String)
                    {
                        onItemClick((String) msg.obj);
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_list);
        initView();
        initListener();
        initData();
    }

    private void initView()
    {
        setTitle(R.string.manage_addresses);
        swipeRefreshLayout = findViewById(R.id.srl);
        addressList = findViewById(R.id.addressList);
        btnAdd = findViewById(R.id.btn_add);
    }

    private void initListener()
    {
        btnAdd.setOnClickListener(this);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_green_light, android.R.color.holo_red_light,
            android.R.color.holo_orange_light, android.R.color.holo_blue_light);
    }

    private void initData()
    {
        Intent intent = getIntent();
        from = intent.getIntExtra(IntentConstant.ADDRESS_FROM, 0);
        addressListAdapter = new AddressListAdapter(mContext, null, handler);
        addressList.setAdapter(addressListAdapter);
        LoadDialog.show(mContext);
        request(ResponseConstant.GET_ADDRESS_LIST, true);
    }

    private void onItemClick(String addId)
    {
        if (from == 0)
        {
            Intent intent = new Intent(this, AddressDetailActivity.class);
            intent.putExtra(IntentConstant.ADDRESS_ID, addId);
            intent.putExtra(IntentConstant.ADDRESS_TYPE, 0);
            startActivity(intent);
        }
        else if (from == 1)
        {
            Intent intent = getIntent();
            setResult(ActivityResultConstant.ADDRESS_BY_GOODS, intent);
            finish();
        }
    }

    @Override
    public Object doInBackground(int requestCode, String id) throws HttpException
    {
        switch (requestCode)
        {
            case ResponseConstant.GET_ADDRESS_LIST:
                loadFinish = false;
                return action.getMyAddressList();
            case ResponseConstant.SET_DEFAULT_ADDRESS:
                loadFinish = false;
                return action.setDefalutAddress(addressId);
            case ResponseConstant.DEL_ADDRESS:
                loadFinish = false;
                return action.setDefalutAddress(addressId);
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
            case ResponseConstant.GET_ADDRESS_LIST:
                loadFinish = true;
                if (result instanceof MyAddressListResponse)
                {
                    MyAddressListResponse response = (MyAddressListResponse) result;
                    if (HttpConstant.SUCCESS.equals(response.getCode()))
                    {
                        Logger.d(TAG, "get address list success!");
                        if (null != addressListAdapter)
                        {
                            List<AddressBean> list = response.getData();
                            addressListAdapter.addAllItem(list);
                            addressListAdapter.notifyDataSetChanged();
                        }
                    }
                    else
                    {
                        NToast.shortToast(mContext, response.getMessage());
                    }
                }
                LoadDialog.dismiss(mContext);
                break;
            case ResponseConstant.SET_DEFAULT_ADDRESS:
                loadFinish = true;
                if (result instanceof BaseSealResponse)
                {
                    BaseSealResponse response = (BaseSealResponse) result;
                    if (HttpConstant.SUCCESS.equals(response.getCode()))
                    {
                        Logger.d(TAG, "set default address success!");
                        if (null != addressListAdapter)
                        {
                            addressListAdapter.setDefaultAddress(addressId);
                        }
                    }
                    else
                    {
                        NToast.shortToast(mContext, response.getMessage());
                    }
                }
                LoadDialog.dismiss(mContext);
                break;
            case ResponseConstant.DEL_ADDRESS:
                loadFinish = true;
                if (result instanceof BaseSealResponse)
                {
                    BaseSealResponse response = (BaseSealResponse) result;
                    if (HttpConstant.SUCCESS.equals(response.getCode()))
                    {
                        Logger.d(TAG, "del address success!");
                        if (null != addressListAdapter)
                        {
                            addressListAdapter.removeAddress(addressId);
                        }
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
            case ResponseConstant.GET_ADDRESS_LIST:
                loadFinish = true;
                LoadDialog.dismiss(mContext);
                NToast.shortToast(mContext, getResources().getString(R.string.http_client_false));
                break;
            case ResponseConstant.SET_DEFAULT_ADDRESS:
                loadFinish = true;
                LoadDialog.dismiss(mContext);
                NToast.shortToast(mContext, getResources().getString(R.string.http_client_false));
                break;
            case ResponseConstant.DEL_ADDRESS:
                loadFinish = true;
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
            case R.id.btn_add:
                goToNewAddress();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if ((requestCode == ActivityResultConstant.ADDRESS_CREAT_NEW
            && resultCode == ActivityResultConstant.ADDRESS_CREAT_NEW)
            || (requestCode == ActivityResultConstant.ADDRESS_EDIT
                && resultCode == ActivityResultConstant.ADDRESS_EDIT))
        {
            LoadDialog.show(mContext);
            request(ResponseConstant.GET_ADDRESS_LIST, true);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRefresh()
    {
        swipeRefreshLayout.setRefreshing(false);
        if (loadFinish)
        {
            request(ResponseConstant.GET_ADDRESS_LIST, true);
        }
    }

    private void goToNewAddress()
    {
        Intent intent = new Intent(this, AddressDetailActivity.class);
        intent.putExtra(IntentConstant.ADDRESS_TYPE, 2);
        startActivityForResult(intent, ActivityResultConstant.ADDRESS_CREAT_NEW);
    }

}
