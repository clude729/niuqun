package com.daoyu.niuqun.ui.center;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;

import com.daoyu.chat.ui.activity.BaseActivity;
import com.daoyu.niuqun.R;
import com.daoyu.niuqun.ui.adapter.ChangeDetailListAdapter;

public class ChangeDetailActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener
{

    private static final String TAG = "ChangeDetailActivity";

    private SwipeRefreshLayout swipeRefreshLayout;

    private ListView listView;

    private ChangeDetailListAdapter changeDetailListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_detail);
    }

    @Override
    public void onRefresh()
    {

    }
}
