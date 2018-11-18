package com.daoyu.niuqun.bean;

/**
 * 零钱明细
 */

public class ChangeInfo
{

    private static final String TAG = "ChangeInfo";

    private String log_id;

    private String pay_no;

    private String pay_amount;

    private String user_id;

    private String transaction_id;

    private String status;

    private String add_time;

    private String pay_type;

    private String come_type;

    public String getLog_id()
    {
        return log_id;
    }

    public void setLog_id(String log_id)
    {
        this.log_id = log_id;
    }

    public String getPay_no()
    {
        return pay_no;
    }

    public void setPay_no(String pay_no)
    {
        this.pay_no = pay_no;
    }

    public String getPay_amount()
    {
        return pay_amount;
    }

    public void setPay_amount(String pay_amount)
    {
        this.pay_amount = pay_amount;
    }

    public String getUser_id()
    {
        return user_id;
    }

    public void setUser_id(String user_id)
    {
        this.user_id = user_id;
    }

    public String getTransaction_id()
    {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id)
    {
        this.transaction_id = transaction_id;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getAdd_time()
    {
        return add_time;
    }

    public void setAdd_time(String add_time)
    {
        this.add_time = add_time;
    }

    public String getPay_type()
    {
        return pay_type;
    }

    public void setPay_type(String pay_type)
    {
        this.pay_type = pay_type;
    }

    public String getCome_type()
    {
        return come_type;
    }

    public void setCome_type(String come_type)
    {
        this.come_type = come_type;
    }

    public String toString()
    {
        return TAG + ": { amount = " + pay_amount + " }";
    }

}
