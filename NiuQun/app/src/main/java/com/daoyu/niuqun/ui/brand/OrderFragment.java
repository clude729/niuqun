package com.daoyu.niuqun.ui.brand;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.daoyu.chat.SealUserInfoManager;
import com.daoyu.chat.server.response.BaseSealResponse;
import com.daoyu.chat.server.utils.NToast;
import com.daoyu.chat.server.widget.DialogWithYesOrNoUtils;
import com.daoyu.niuqun.R;
import com.daoyu.niuqun.bean.OrderData;
import com.daoyu.niuqun.constant.HttpConstant;
import com.daoyu.niuqun.ui.adapter.OrderListAdapter;
import com.daoyu.niuqun.ui.center.RechargeActivity;
import com.daoyu.niuqun.util.Logger;
import com.daoyu.niuqun.view.AutoLoadListener;

/**
 * 订单列表
 */
public class OrderFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener
{

    private static final String TAG = "OrderFragment";

    private SwipeRefreshLayout swipeRefreshLayout;

    private ListView listView;

    private View footView;

    private OrderListAdapter adapter;

    //加载是否完成，默认已完成
    private boolean finishLoad = true;

    //数据是否已全部显示
    private boolean isAll = false;

    //下一次加载的页码
    private int page = 1;

    //private boolean isHide = true;

    private AutoLoadListener.AutoLoadCallBack autoLoadCallBack = new AutoLoadListener.AutoLoadCallBack()
    {
        @Override
        public void execute()
        {
            if (isAll)
            {
                NToast.shortToast(getActivity(), getResources().getString(R.string.has_load_all));
                return;
            }
            if (finishLoad)
            {
                footView.setVisibility(View.VISIBLE);
                getOrderList(page + 1);
            }
        }
    };

    private OrderListCallBack callBack = new OrderListCallBack()
    {
        @Override
        public void orderToPay(String orderId)
        {
            Logger.d(TAG, "orderToPay!");
            toPayOrder(orderId);
        }

        @Override
        public void orderToCancel(String orderId)
        {
            Logger.d(TAG, "orderToCancel!");
            toCancelOrder(orderId);
        }

        @Override
        public void orderToRemind(String orderId)
        {
            Logger.d(TAG, "orderToRemind!");
        }

        @Override
        public void orderGetView(String orderId)
        {
            Logger.d(TAG, "orderGetView!");
        }

        @Override
        public void orderConfirmReceipt(String orderId)
        {
            Logger.d(TAG, "orderConfirmReceipt!");
            toReceiveOrder(orderId);
        }

        @Override
        public void orderToDel(String orderId)
        {
            Logger.d(TAG, "orderToDel!");
            toDelOrder(orderId);
        }
    };

    public OrderFragment()
    {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View mainView = inflater.inflate(R.layout.fragment_order, container, false);
        swipeRefreshLayout = mainView.findViewById(R.id.srl);
        listView = mainView.findViewById(R.id.list);
        footView = mainView.findViewById(R.id.footview);
        return mainView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_green_light, android.R.color.holo_red_light,
            android.R.color.holo_orange_light, android.R.color.holo_blue_light);
        adapter = new OrderListAdapter(getActivity(), callBack);
        listView.setAdapter(adapter);
        AutoLoadListener loadListener = new AutoLoadListener(autoLoadCallBack);
        listView.setOnScrollListener(loadListener);
        getOrderList(1);
    }

    @Override
    public void onHiddenChanged(boolean hidden)
    {
        //isHide = hidden;
        super.onHiddenChanged(hidden);
    }

    private void getOrderList(int nextpage)
    {
        finishLoad = false;
        SealUserInfoManager.getInstance().getOrderList(String.valueOf(getType()), nextpage, new SealUserInfoManager.ResultCallback<OrderData>()
        {
            @Override
            public void onSuccess(OrderData orderData)
            {
                Logger.d(TAG, "getOrderList successful!");
                updataList(orderData);
                loadFinish();
            }

            @Override
            public void onError(String errString)
            {
                Logger.d(TAG, "getOrderList, error: " + errString);
                loadFinish();
            }
        });
    }

    private void loadFinish()
    {
        finishLoad = true;
        footView.setVisibility(View.GONE);
    }

    private void updataList(OrderData orderData)
    {
        if (null == orderData)
        {
            Logger.d(TAG, "updataList, brandsData is null, return!");
            return;
        }
        page = orderData.getNowpage();
        isAll = page == orderData.getCountpage();
        if (1 == page)
        {
            adapter.clear();
        }
        if (null != orderData.getList())
        {
            adapter.addAll(orderData.getList());
        }
        else
        {
            Logger.d(TAG, "orderList is null!");
        }
        adapter.notifyDataSetChanged();
    }

    private void toPayOrder(final String orderId)
    {
        finishLoad = false;
        SealUserInfoManager.getInstance().toPayOrder(orderId, new SealUserInfoManager.ResultCallback<BaseSealResponse>()
        {
            @Override
            public void onSuccess(BaseSealResponse response)
            {
                Logger.d(TAG, "toPayOrder successful!");
                loadFinish();
                if (HttpConstant.SUCCESS.equals(response.getCode()))
                {
                    Logger.d(TAG, "to pay order success!");
                    if (null != adapter)
                    {
                        adapter.setOrder(orderId, "1");
                    }
                }
                else if (HttpConstant.MONEY_NOT_ENOUGH.equals(response.getCode()))
                {
                    //余额不足
                    DialogWithYesOrNoUtils.getInstance().showOnlyDialog(getActivity(), "", "", "",
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
                    NToast.shortToast(getActivity(), response.getMessage());
                }
            }

            @Override
            public void onError(String errString)
            {
                Logger.d(TAG, "toPayOrder, error: " + errString);
                NToast.shortToast(getActivity(), getResources().getString(R.string.http_client_false));
                loadFinish();
            }
        });
    }

    private void toCancelOrder(final String orderId)
    {
        finishLoad = false;
        SealUserInfoManager.getInstance().toCancelOrder(orderId, new SealUserInfoManager.ResultCallback<BaseSealResponse>()
        {
            @Override
            public void onSuccess(BaseSealResponse response)
            {
                Logger.d(TAG, "toCancelOrder successful!");
                loadFinish();
                if (HttpConstant.SUCCESS.equals(response.getCode()))
                {
                    Logger.d(TAG, "to cancel order success!");
                    if (null != adapter)
                    {
                        adapter.setOrder(orderId, "5");
                    }
                }
                else
                {
                    NToast.shortToast(getActivity(), response.getMessage());
                }
            }

            @Override
            public void onError(String errString)
            {
                Logger.d(TAG, "toCancelOrder, error: " + errString);
                NToast.shortToast(getActivity(), getResources().getString(R.string.http_client_false));
                loadFinish();
            }
        });
    }

    private void toDelOrder(final String orderId)
    {
        finishLoad = false;
        SealUserInfoManager.getInstance().toDelOrder(orderId, new SealUserInfoManager.ResultCallback<BaseSealResponse>()
        {
            @Override
            public void onSuccess(BaseSealResponse response)
            {
                Logger.d(TAG, "toDelOrder successful!");
                loadFinish();
                if (HttpConstant.SUCCESS.equals(response.getCode()))
                {
                    Logger.d(TAG, "to del order success!");
                    if (null != adapter)
                    {
                        adapter.setOrder(orderId, "6");
                    }
                }
                else
                {
                    NToast.shortToast(getActivity(), response.getMessage());
                }
            }

            @Override
            public void onError(String errString)
            {
                Logger.d(TAG, "toDelOrder, error: " + errString);
                NToast.shortToast(getActivity(), getResources().getString(R.string.http_client_false));
                loadFinish();
            }
        });
    }

    private void toReceiveOrder(final String orderId)
    {
        finishLoad = false;
        SealUserInfoManager.getInstance().toReceiveOrder(orderId, new SealUserInfoManager.ResultCallback<BaseSealResponse>()
        {
            @Override
            public void onSuccess(BaseSealResponse response)
            {
                Logger.d(TAG, "toReceiveOrder successful!");
                loadFinish();
                if (HttpConstant.SUCCESS.equals(response.getCode()))
                {
                    Logger.d(TAG, "to receive order success!");
                    if (null != adapter)
                    {
                        adapter.setOrder(orderId, "3");
                    }
                }
                else
                {
                    NToast.shortToast(getActivity(), response.getMessage());
                }
            }

            @Override
            public void onError(String errString)
            {
                Logger.d(TAG, "toReceiveOrder, error: " + errString);
                NToast.shortToast(getActivity(), getResources().getString(R.string.http_client_false));
                loadFinish();
            }
        });
    }

    @Override
    public void onRefresh()
    {
        swipeRefreshLayout.setRefreshing(false);
        if (finishLoad)
        {
            getOrderList(1);
        }
    }

    private void goToRecharge()
    {
        Intent intent = new Intent(getActivity(), RechargeActivity.class);
        startActivity(intent);
    }

    protected int getType()
    {
        return 0;
    }

}
