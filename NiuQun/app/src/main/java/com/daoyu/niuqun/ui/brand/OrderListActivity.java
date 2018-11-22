package com.daoyu.niuqun.ui.brand;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.daoyu.chat.ui.activity.BaseActivity;
import com.daoyu.niuqun.R;
import com.daoyu.niuqun.util.Logger;

/**
 * 订单
 */
public class OrderListActivity extends BaseActivity implements OnClickListener, OnPageChangeListener
{

    private static final String TAG = "OrderListActivity";

    private ViewPager viewPager;

    private TextView tvAll;

    private TextView tvWait;

    private TextView tvHad;

    private View viewAll;

    private View viewWait;

    private View viewHad;

    private List<Fragment> mFragment = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        Logger.d(TAG, "onCreate!");
        initViews();
        initListener();
        initData();
    }

    private void initViews()
    {
        setTitle(R.string.my_orders);
        viewPager = findViewById(R.id.order_viewpager);
        tvAll = findViewById(R.id.tv_all);
        tvWait = findViewById(R.id.tv_wait);
        tvHad = findViewById(R.id.tv_had);
        viewAll = findViewById(R.id.view_all);
        viewWait = findViewById(R.id.view_wait);
        viewHad = findViewById(R.id.view_had);
    }

    private void initListener()
    {
        tvAll.setOnClickListener(this);
        tvWait.setOnClickListener(this);
        tvHad.setOnClickListener(this);
    }

    private void initData()
    {
        mFragment.add(new OrderFragment());
        mFragment.add(new SendOrderFragment());
        mFragment.add(new GetOrderFragment());
        FragmentPagerAdapter fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager())
        {
            @Override
            public Fragment getItem(int position)
            {
                return mFragment.get(position);
            }

            @Override
            public int getCount()
            {
                return mFragment.size();
            }
        };
        viewPager.setAdapter(fragmentPagerAdapter);
        viewPager.setOffscreenPageLimit(3);
        viewPager.addOnPageChangeListener(this);
    }

    private void showView(int position)
    {
        switch (position)
        {
            case 0:
                viewAll.setVisibility(View.VISIBLE);
                viewWait.setVisibility(View.INVISIBLE);
                viewHad.setVisibility(View.INVISIBLE);
                break;
            case 1:
                viewAll.setVisibility(View.INVISIBLE);
                viewWait.setVisibility(View.VISIBLE);
                viewHad.setVisibility(View.INVISIBLE);
                break;
            case 2:
                viewAll.setVisibility(View.INVISIBLE);
                viewWait.setVisibility(View.INVISIBLE);
                viewHad.setVisibility(View.VISIBLE);
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
            case R.id.tv_all:
                viewPager.setCurrentItem(0);
                break;
            case R.id.tv_wait:
                viewPager.setCurrentItem(1);
                break;
            case R.id.tv_had:
                viewPager.setCurrentItem(2);
                break;
            default:
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
    {

    }

    @Override
    public void onPageSelected(int position)
    {
        showView(position);
    }

    @Override
    public void onPageScrollStateChanged(int state)
    {

    }
}
