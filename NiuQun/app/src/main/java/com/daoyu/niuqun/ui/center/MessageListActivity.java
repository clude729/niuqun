package com.daoyu.niuqun.ui.center;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.daoyu.chat.server.network.http.HttpException;
import com.daoyu.chat.server.response.MessageListResponse;
import com.daoyu.chat.server.utils.NToast;
import com.daoyu.chat.server.widget.LoadDialog;
import com.daoyu.chat.ui.activity.BaseActivity;
import com.daoyu.niuqun.R;
import com.daoyu.niuqun.bean.AppMessageData;
import com.daoyu.niuqun.constant.HttpConstant;
import com.daoyu.niuqun.constant.ResponseConstant;
import com.daoyu.niuqun.ui.adapter.MessageListAdapter;
import com.daoyu.niuqun.util.Logger;
import com.daoyu.niuqun.util.SharePreferenceManager;
import com.daoyu.niuqun.view.AutoLoadListener;

import io.rong.imkit.RongIM;

public class MessageListActivity extends BaseActivity
    implements View.OnClickListener , SwipeRefreshLayout.OnRefreshListener
{

    private static final String TAG = "MessageListActivity";

    private SwipeRefreshLayout swipeRefreshLayout;

    private ListView listView;

    private View footView;

    private MessageListAdapter adapter;

    private TextView tvHelp;

    private TextView tvMessage;

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
                getMessageList(page + 1);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);
        initView();
        initListener();
        initData();
    }

    private void initView()
    {
        setTitle(R.string.app_system_message);
        swipeRefreshLayout = findViewById(R.id.srl);
        listView = findViewById(R.id.list);
        footView = findViewById(R.id.footview);
        tvHelp = findViewById(R.id.tv_help);
        tvMessage = findViewById(R.id.tv_message);
    }

    private void initListener()
    {
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_green_light, android.R.color.holo_red_light,
            android.R.color.holo_orange_light, android.R.color.holo_blue_light);
        tvHelp.setOnClickListener(this);
        tvMessage.setOnClickListener(this);
        adapter = new MessageListAdapter(mContext, null);
        listView.setAdapter(adapter);
        AutoLoadListener autoLoadListener = new AutoLoadListener(callBack);
        listView.setOnScrollListener(autoLoadListener);
    }

    private void initData()
    {
        LoadDialog.show(mContext);
        getMessageList(1);
    }

    private void getMessageList(int nextPage)
    {
        if (!finishLoad)
        {
            Logger.d(TAG, "getMessageList, is on loading, return!");
            LoadDialog.dismiss(mContext);
            return;
        }
        finishLoad = false;
        page = nextPage;
        request(ResponseConstant.APP_MESSAGE_LIST, true);
    }

    public void updataList(AppMessageData data)
    {
        if (null == data)
        {
            Logger.d(TAG, "updataList, AppMessageData is null, return!");
            return;
        }
        page = data.getNowpage();
        isAll = page == data.getCountpage();
        if (1 == page)
        {
            adapter.clear();
        }
        adapter.addAllItem(data.getList());
        adapter.notifyDataSetChanged();
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
            case ResponseConstant.APP_MESSAGE_LIST:
                return action.getAppMessageList(page);
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
            case ResponseConstant.APP_MESSAGE_LIST:
                LoadDialog.dismiss(mContext);
                loadFinish();
                if (result instanceof MessageListResponse)
                {
                    MessageListResponse response = (MessageListResponse) result;
                    if (HttpConstant.SUCCESS.equals(response.getCode()))
                    {
                        Logger.d(TAG, "get messageList is success!");
                        AppMessageData data = response.getData();
                        updataList(data);
                    }
                    else
                    {
                        Logger.d(TAG, "get messageList success, but code: " + response.getCode());
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
            case ResponseConstant.APP_MESSAGE_LIST:
                LoadDialog.dismiss(mContext);
                loadFinish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.tv_help:
                startChat();
                break;
            case R.id.tv_message:

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
            getMessageList(1);
        }
    }

    private void startChat()
    {
        String reId = SharePreferenceManager.getKeyCachedReUserid();
        if (!TextUtils.isEmpty(reId))
        {
            RongIM.getInstance().startPrivateChat(mContext, reId, getResources().getString(R.string.person_help));
        }
    }

}
