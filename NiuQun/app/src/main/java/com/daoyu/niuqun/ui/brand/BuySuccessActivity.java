package com.daoyu.niuqun.ui.brand;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.daoyu.chat.ui.activity.BaseActivity;
import com.daoyu.niuqun.R;
import com.daoyu.niuqun.ui.chat.PhoneMainActivity;

/**
 * 结算成功
 */
public class BuySuccessActivity extends BaseActivity implements View.OnClickListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_success);
        setTitle(R.string.goods_buy_over);
        Button btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_back:
                goToBrands();
                break;
            default:
                break;
        }
    }

    private void goToBrands()
    {
        Intent intent = new Intent(this, PhoneMainActivity.class);
        startActivity(intent);
    }

}
