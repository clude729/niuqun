package com.daoyu.niuqun.view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daoyu.niuqun.R;
import com.daoyu.niuqun.util.Logger;

/**
 * 广告对话框
 */

public class AdDialog
{

    private static final String TAG = "AdDialog";

    private Context mContext;

    //装载所以textView
    private List<TextView> textViews = new ArrayList<>();

    //显示的view
    private List<TextView> views = new ArrayList<>();

    private List<Integer> nums = new ArrayList<>();

    private String adString;

    private AdResultCallBack callBack;
    
    private AlertDialog dialog;

    private String result;

    public AdDialog(Context context, String ad, AdResultCallBack callBack)
    {
        this.mContext = context;
        this.adString = ad;
        this.callBack = callBack;
        //广告语不超过10
        if (!TextUtils.isEmpty(adString) && adString.length() > 10)
        {
            adString = adString.substring(0, 10);
        }
        Logger.d(TAG, "onCreat!");
    }

    private void creatDialog()
    {
        Logger.d(TAG, "creatDialog!");
        AlertDialog.Builder alterDialog = new AlertDialog.Builder(mContext);
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.dialog_ad, null);
        TextView ad1 = layout.findViewById(R.id.tv_ad1);
        TextView ad2 = layout.findViewById(R.id.tv_ad2);
        TextView ad3 = layout.findViewById(R.id.tv_ad3);
        TextView ad4 = layout.findViewById(R.id.tv_ad4);
        TextView ad5 = layout.findViewById(R.id.tv_ad5);
        TextView ad6 = layout.findViewById(R.id.tv_ad6);
        TextView ad7 = layout.findViewById(R.id.tv_ad7);
        TextView ad8 = layout.findViewById(R.id.tv_ad8);
        TextView ad9 = layout.findViewById(R.id.tv_ad9);
        TextView ad10 = layout.findViewById(R.id.tv_ad10);
        TextView tvAd = layout.findViewById(R.id.tv_ad);
        TextView tvOk = layout.findViewById(R.id.tv_ok);
        tvAd.setText(adString);
        textViews.clear();
        textViews.add(ad1);
        textViews.add(ad2);
        textViews.add(ad3);
        textViews.add(ad4);
        textViews.add(ad5);
        textViews.add(ad6);
        textViews.add(ad7);
        textViews.add(ad8);
        textViews.add(ad9);
        textViews.add(ad10);
        consortTextView();
        consortString();
        alterDialog.setView(layout);
        dialog = alterDialog.create();
        tvOk.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getResult();
            }
        });
    }
    
    public void show()
    {
        if (null == dialog)
        {
            creatDialog();
        }
        Logger.d(TAG, "show!");
        if (null != dialog && !dialog.isShowing())
        {
            result = "";
            dialog.show();
        }
    }
    
    public void dismiss()
    {
        if (null !=dialog && dialog.isShowing())
        {
            dialog.dismiss();
            dialog = null;
        }
    }

    private void getResult()
    {
        Logger.d(TAG, "getResult, result: " + result + ", ad: " + adString);
        dismiss();
        if (null != callBack)
        {
            if (adString.equals(result))
            {
                callBack.clickRight();
            }
            else
            {
                callBack.clickWrong();
            }
        }
    }
    
    private void setClickListener(TextView view)
    {
        if (null != view)
        {
            Logger.d(TAG, "setClickListener!");
            view.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if (v instanceof TextView)
                    {
                        TextView textView = (TextView) v;
                        result = result + textView.getText().toString();
                        textView.setTextColor(mContext.getResources().getColor(R.color.color1));
                        v.setOnClickListener(null);
                    }
                }
            });
        }
    }

    private void consortTextView()
    {
        Logger.d(TAG, "consortTextView!");
        int length = adString.length();
        switch (length)
        {
            case 1:
                getOne();
                break;
            case 2:
                getTwo();
                break;
            case 3:
                getThree();
                break;
            case 4:
                getFour();
                break;
            case 5:
                getFive();
                break;
            case 6:
                getSix();
                break;
            case 7:
                getSeven();
                break;
            case 8:
                getEight();
                break;
            case 9:
                getNine();
                break;
            case 10:
                getTen();
                break;
            default:
                break;
        }
        for (TextView view : textViews)
        {
            view.setVisibility(View.GONE);
        }
    }

    private void consortString()
    {
        char[] chars = adString.toCharArray();
        nums.clear();
        for (int i = 0; i < adString.length(); i++)
        {
            nums.add(i);
        }
        Collections.sort(nums, new Comparator<Integer>()
        {
            @Override
            public int compare(Integer integer, Integer t1)
            {
                return Math.random() > 0.5 ? 1 : -1;
            }
        });
//        for (Integer num : nums)
//        {
//            views.get(num).setText(String.valueOf(chars[num]));
//            views.get(num).setVisibility(View.VISIBLE);
//            setClickListener(views.get(num));
//        }
        for (int i = 0; i < nums.size(); i ++)
        {
            TextView tv = views.get(nums.get(i));
            tv.setText(String.valueOf(chars[i]));
            tv.setVisibility(View.VISIBLE);
            setClickListener(tv);
        }
    }

    private void getOne()
    {
        if (textViews.size() > 4)
        {
            views.clear();
            views.add(textViews.get(4));
        }
    }

    private void getTwo()
    {
        if (textViews.size() > 6)
        {
            views.clear();
            views.add(textViews.get(2));
            views.add(textViews.get(6));
        }
    }

    private void getThree()
    {
        if (textViews.size() > 6)
        {
            views.clear();
            views.add(textViews.get(2));
            views.add(textViews.get(4));
            views.add(textViews.get(6));
        }
    }

    private void getFour()
    {
        if (textViews.size() > 8)
        {
            views.clear();
            views.add(textViews.get(0));
            views.add(textViews.get(2));
            views.add(textViews.get(6));
            views.add(textViews.get(8));
        }
    }

    private void getFive()
    {
        if (textViews.size() > 8)
        {
            views.clear();
            views.add(textViews.get(0));
            views.add(textViews.get(2));
            views.add(textViews.get(4));
            views.add(textViews.get(6));
            views.add(textViews.get(8));
        }
    }

    private void getSix()
    {
        if (textViews.size() > 8)
        {
            views.clear();
            views.add(textViews.get(1));
            views.add(textViews.get(2));
            views.add(textViews.get(4));
            views.add(textViews.get(5));
            views.add(textViews.get(7));
            views.add(textViews.get(8));
        }
    }

    private void getSeven()
    {
        if (textViews.size() > 8)
        {
            views.clear();
            views.add(textViews.get(1));
            views.add(textViews.get(2));
            views.add(textViews.get(3));
            views.add(textViews.get(4));
            views.add(textViews.get(5));
            views.add(textViews.get(7));
            views.add(textViews.get(8));
        }
    }

    private void getEight()
    {
        if (textViews.size() > 8)
        {
            views.clear();
            views.add(textViews.get(0));
            views.add(textViews.get(1));
            views.add(textViews.get(2));
            views.add(textViews.get(3));
            views.add(textViews.get(4));
            views.add(textViews.get(5));
            views.add(textViews.get(7));
            views.add(textViews.get(8));
        }
    }

    private void getNine()
    {
        if (textViews.size() > 9)
        {
            views.clear();
            views.add(textViews.get(1));
            views.add(textViews.get(2));
            views.add(textViews.get(3));
            views.add(textViews.get(4));
            views.add(textViews.get(5));
            views.add(textViews.get(6));
            views.add(textViews.get(7));
            views.add(textViews.get(8));
            views.add(textViews.get(9));
        }
    }

    private void getTen()
    {
        if (textViews.size() > 9)
        {
            views.clear();
            views.addAll(textViews);
        }
    }

}
