package com.daoyu.niuqun.ui.center;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.daoyu.chat.server.network.http.HttpException;
import com.daoyu.chat.server.response.BaseSealResponse;
import com.daoyu.chat.server.utils.NToast;
import com.daoyu.chat.server.widget.LoadDialog;
import com.daoyu.chat.ui.activity.BaseActivity;
import com.daoyu.niuqun.R;
import com.daoyu.niuqun.bean.AddressBean;
import com.daoyu.niuqun.bean.AreaBean;
import com.daoyu.niuqun.constant.ActivityResultConstant;
import com.daoyu.niuqun.constant.HttpConstant;
import com.daoyu.niuqun.constant.IntentConstant;
import com.daoyu.niuqun.constant.ResponseConstant;
import com.daoyu.niuqun.ui.App;
import com.daoyu.niuqun.util.DataBaseHelper;
import com.daoyu.niuqun.util.Logger;
import com.daoyu.niuqun.util.StringUtil;

import java.util.List;

/**
 * 收货地址详情
 */
public class AddressDetailActivity extends BaseActivity implements OnClickListener
{

    private static final String TAG = "AddressDetailActivity";

    private EditText etName;

    private EditText etMobile;

    private ImageView ivArea;

    private TextView tvArea;

    private EditText etDetail;

    private AddressBean addressBean;

    private int addType = 2;

    private String province = "";

    private String city = "";

    private String area = "";

    private String[] areas;

    private String[] dialogName = {"选择省份", "选择城市", "选择区县" };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_detail);
        initView();
        initData();
    }

    private void initView()
    {
        etName = findViewById(R.id.et_name);
        etMobile = findViewById(R.id.et_mobile);
        ivArea = findViewById(R.id.iv_area);
        tvArea = findViewById(R.id.tv_area);
        etDetail = findViewById(R.id.et_detail);
        ivArea.setOnClickListener(this);
    }

    private void initData()
    {
        Intent intent = getIntent();
        addType = intent.getIntExtra(IntentConstant.ADDRESS_TYPE, 2);
        switch (addType)
        {
            case 0:
                //查看
                setTitle(R.string.address_detail);
                etName.setEnabled(false);
                etMobile.setEnabled(false);
                ivArea.setVisibility(View.INVISIBLE);
                etDetail.setEnabled(false);
                setHeadRightButtonVisibility(View.GONE);
                App app0 = (App) getApplication();
                addressBean = app0.getAddressBean();
                break;
            case 1:
                //编辑
                setTitle(R.string.address_detail);
                etName.setEnabled(true);
                etMobile.setEnabled(true);
                ivArea.setVisibility(View.VISIBLE);
                etDetail.setEnabled(true);
                getHeadRightButton().setText(R.string.to_save);
                setHeadRightButtonVisibility(View.VISIBLE);
                App app1 = (App) getApplication();
                addressBean = app1.getAddressBean();
                break;
            case 2:
                //新增
                setTitle(R.string.creat_new_address);
                etName.setEnabled(true);
                etMobile.setEnabled(true);
                ivArea.setVisibility(View.VISIBLE);
                etDetail.setEnabled(true);
                getHeadRightButton().setText(R.string.to_save);
                setHeadRightButtonVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
        if (null != addressBean)
        {
            etName.setText(addressBean.getReal_name());
            etMobile.setText(addressBean.getMobile());
            etDetail.setText(addressBean.getAddress());
            String add = addressBean.getProvince() + addressBean.getCity() + addressBean.getDistrict();
            tvArea.setText(add);
        }
    }

    private void toCreatNew()
    {
        String name = etName.getText().toString().trim();
        String mobile = etMobile.getText().toString().trim();
        String area = tvArea.getText().toString().trim();
        String address = etDetail.getText().toString().trim();
        if (TextUtils.isEmpty(name))
        {
            NToast.shortToast(mContext, getResources().getString(R.string.address_person_empty_hint));
        }
        else if (TextUtils.isEmpty(mobile))
        {
            NToast.shortToast(mContext, getResources().getString(R.string.address_mobile_empty_hint));
        }
        else if (!StringUtil.isMobileNO(mobile))
        {
            NToast.shortToast(mContext, getResources().getString(R.string.address_mobile_error_hint));
        }
        else if (TextUtils.isEmpty(area))
        {
            NToast.shortToast(mContext, getResources().getString(R.string.address_area_empty_hint));
        }
        else if (TextUtils.isEmpty(address))
        {
            NToast.shortToast(mContext, getResources().getString(R.string.address_detail_by_one_empty_hint));
        }
        else
        {
            addressBean = new AddressBean();
            addressBean.setAddress(address);
            addressBean.setReal_name(name);
            addressBean.setMobile(mobile);
            addressBean.setProvince(province);
            addressBean.setCity(city);
            addressBean.setDistrict(area);
            LoadDialog.show(mContext);
            request(ResponseConstant.CREAT_NEW_ADDRESS, true);
        }
    }

    private void toEditAddress()
    {
        String name = etName.getText().toString().trim();
        String mobile = etMobile.getText().toString().trim();
        String area = tvArea.getText().toString().trim();
        String address = etDetail.getText().toString().trim();
        if (TextUtils.isEmpty(name))
        {
            NToast.shortToast(mContext, getResources().getString(R.string.address_person_empty_hint));
        }
        else if (TextUtils.isEmpty(mobile))
        {
            NToast.shortToast(mContext, getResources().getString(R.string.address_mobile_empty_hint));
        }
        else if (!StringUtil.isMobileNO(mobile))
        {
            NToast.shortToast(mContext, getResources().getString(R.string.address_mobile_error_hint));
        }
        else if (TextUtils.isEmpty(area))
        {
            NToast.shortToast(mContext, getResources().getString(R.string.address_area_empty_hint));
        }
        else if (TextUtils.isEmpty(address))
        {
            NToast.shortToast(mContext, getResources().getString(R.string.address_detail_by_one_empty_hint));
        }
        else
        {
            addressBean.setAddress(address);
            addressBean.setReal_name(name);
            addressBean.setMobile(mobile);
            if (!TextUtils.isEmpty(province))
            {
                addressBean.setProvince(province);
            }
            if (!TextUtils.isEmpty(city))
            {
                addressBean.setCity(city);
            }
            if (!TextUtils.isEmpty(area))
            {
                addressBean.setDistrict(area);
            }
            LoadDialog.show(mContext);
            request(ResponseConstant.EDIT_ADDRESS, true);
        }
    }

    @Override
    public void onHeadRightButtonClick(View v)
    {
        switch (addType)
        {
            case 1:
                toEditAddress();
                break;
            case 2:
                toCreatNew();
                break;
            default:
                break;
        }
    }

    @Override
    public Object doInBackground(int requestCode, String id) throws HttpException
    {
        switch (requestCode)
        {
            case ResponseConstant.CREAT_NEW_ADDRESS:
                return action.addNewAddress(addressBean);
            case ResponseConstant.EDIT_ADDRESS:
                return action.editAddress(addressBean);
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
            case ResponseConstant.CREAT_NEW_ADDRESS:
                if (result instanceof BaseSealResponse)
                {
                    BaseSealResponse response = (BaseSealResponse) result;
                    if (HttpConstant.SUCCESS.equals(response.getCode()))
                    {
                        Logger.d(TAG, "creat address success!");
                        NToast.shortToast(mContext, getResources().getString(R.string.to_save_success));
                        Intent intent = getIntent();
                        setResult(ActivityResultConstant.ADDRESS_CREAT_NEW, intent);
                        finish();
                    }
                    else
                    {
                        NToast.shortToast(mContext, response.getMessage());
                    }
                }
                break;
            case ResponseConstant.EDIT_ADDRESS:
                if (result instanceof BaseSealResponse)
                {
                    BaseSealResponse response = (BaseSealResponse) result;
                    if (HttpConstant.SUCCESS.equals(response.getCode()))
                    {
                        Logger.d(TAG, "edit address success!");
                        NToast.shortToast(mContext, getResources().getString(R.string.edit_a_address_success));
                        Intent intent = getIntent();
                        setResult(ActivityResultConstant.ADDRESS_EDIT, intent);
                        finish();
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
            case ResponseConstant.CREAT_NEW_ADDRESS:
                NToast.shortToast(mContext, getResources().getString(R.string.http_client_false));
                break;
            case ResponseConstant.EDIT_ADDRESS:
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
            case R.id.iv_area:
                areas = new String[3];
                toSelectArea(0, null);
                break;
            default:
                break;
        }
    }

    /**
     * 显示地区
     *
     * @param level 0 省 1 市 2 区
     * @param id id
     */
    private void toSelectArea(int level, String id)
    {
        DataBaseHelper myDbHelper = new DataBaseHelper(this);
        try
        {
            myDbHelper.openDataBase();
            switch (level)
            {
                case 0:
                    List<AreaBean> provinces = myDbHelper.getProvinces();
                    creatAreaDialog(provinces, level);
                    break;
                case 1:
                    List<AreaBean> cities = myDbHelper.getCitiesByProvince(id);
                    creatAreaDialog(cities, level);
                    break;
                case 2:
                    List<AreaBean> districts = myDbHelper.getAreasByCity(id);
                    creatAreaDialog(districts, level);
                    break;
                default:
                    break;
            }
            myDbHelper.close();
        }
        catch (SQLException e)
        {
            myDbHelper.close();
            Logger.e(TAG, "toSelectArea, SQLException: " + e);
        }
    }

    private void creatAreaDialog(final List<AreaBean> beans, final int level)
    {
        if (null == beans || beans.size() < 1)
        {
            return;
        }
        final String[] array = new String[beans.size()];
        for (int i = 0; i < beans.size(); i++)
        {
            array[i] = beans.get(i).getName();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(dialogName[level]).setCancelable(true).setItems(array, new DialogInterface.OnClickListener()
        {

            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                areas[level] = array[which];
                dialog.dismiss();
                if (level < 2)
                {
                    toSelectArea(level + 1, beans.get(which).getId());
                }
                else
                {
                    province = areas[0];
                    city = areas[1];
                    area = areas[2];
                    String address = province + city + area;
                    tvArea.setText(address);
                }
            }

        }).show();
    }

}
