package com.daoyu.niuqun.ui.center;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ListView;

import com.daoyu.chat.server.network.http.HttpException;
import com.daoyu.chat.server.response.ChangeListResponse;
import com.daoyu.chat.server.utils.NToast;
import com.daoyu.chat.server.widget.LoadDialog;
import com.daoyu.chat.ui.activity.BaseActivity;
import com.daoyu.niuqun.R;
import com.daoyu.niuqun.bean.ChangeData;
import com.daoyu.niuqun.constant.HttpConstant;
import com.daoyu.niuqun.constant.ResponseConstant;
import com.daoyu.niuqun.ui.adapter.ChangeDetailListAdapter;
import com.daoyu.niuqun.util.Logger;
import com.daoyu.niuqun.view.AutoLoadListener;

public class ChangeDetailActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener
{

    private static final String TAG = "ChangeDetailActivity";

    private SwipeRefreshLayout swipeRefreshLayout;

    private ListView listView;

    private View footView;

    private ChangeDetailListAdapter changeDetailListAdapter;

    //加载是否完成，默认已完成
    private boolean finishLoad = true;

    //数据是否已全部显示
    private boolean isAll = false;

    //下一次加载的页码
    private int page = 1;

    private AutoLoadListener.AutoLoadCallBack callBack = new AutoLoadListener.AutoLoadCallBack()
    {
        @Override
        public void execute()
        {
            if (isAll)
            {
                NToast.shortToast(mContext, getResources().getString(R.string.has_load_all));
                return;
            }
            if (finishLoad)
            {
                footView.setVisibility(View.VISIBLE);
                getChangeList(page + 1);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_detail);
        initView();
        initListener();
        initData();
    }
    
    private void initView()
    {
        setTitle(R.string.change_detail);
        swipeRefreshLayout = findViewById(R.id.srl);
        listView = findViewById(R.id.list);
        footView = findViewById(R.id.footview);
    }
    
    private void initListener()
    {
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_green_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_light, android.R.color.holo_blue_light);
        changeDetailListAdapter = new ChangeDetailListAdapter(this, null);
        listView.setAdapter(changeDetailListAdapter);
        AutoLoadListener autoLoadListener = new AutoLoadListener(callBack);
        listView.setOnScrollListener(autoLoadListener);
    }
    
    private void initData()
    {
        LoadDialog.show(mContext);
        getChangeList(1);
    }

    private void getChangeList(int nextPage)
    {
        if (!finishLoad)
        {
            Logger.d(TAG, "getChangeList, is on loading, return!");
            LoadDialog.dismiss(mContext);
            return;
        }
        finishLoad = false;
        page = nextPage;
        request(ResponseConstant.GET_CHANGE_DETAIL, true);
    }

    public void updataList(ChangeData data)
    {
        if (null == data)
        {
            Logger.d(TAG, "updataList, changeData is null, return!");
            return;
        }
        page = data.getNowpage();
        isAll = page == data.getCountpage();
        if (1 == page)
        {
            changeDetailListAdapter.clear();
        }
        changeDetailListAdapter.addAllItem(data.getList());
        changeDetailListAdapter.notifyDataSetChanged();
    }

    private void loadFinish()
    {
        finishLoad = true;
        footView.setVisibility(View.GONE);
    }

    @Override
    public Object doInBackground(int requestCode, String id) throws HttpException
    {
        switch (requestCode)
        {
            case ResponseConstant.GET_CHANGE_DETAIL:
                return action.getChangeList(page);
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
            case ResponseConstant.GET_CHANGE_DETAIL:
                LoadDialog.dismiss(mContext);
                loadFinish();
                if (result instanceof ChangeListResponse)
                {
                    ChangeListResponse response = (ChangeListResponse) result;
                    if (HttpConstant.SUCCESS.equals(response.getCode()))
                    {
                        Logger.d(TAG, "get changeList is success!");
                        ChangeData data = response.getData();
                        updataList(data);
                    }
                    else
                    {
                        Logger.d(TAG, "get ChangeList success, but code: " + response.getCode());
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
            case ResponseConstant.GET_CHANGE_DETAIL:
                LoadDialog.dismiss(mContext);
                loadFinish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onRefresh()
    {
        swipeRefreshLayout.setRefreshing(false);
        if (finishLoad)
        {
            getChangeList(1);
        }
    }
}
