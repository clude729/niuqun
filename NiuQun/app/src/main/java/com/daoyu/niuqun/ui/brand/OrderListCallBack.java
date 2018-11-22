package com.daoyu.niuqun.ui.brand;

/**
 * 订单执行接口
 */
public interface OrderListCallBack
{

    /**
     * 支付订单
     * 
     * @param orderId 订单Id
     */
    void orderToPay(String orderId);

    /**
     * 取消订单
     * 
     * @param orderId 订单Id
     */
    void orderToCancel(String orderId);

    /**
     * 提醒订单发货
     * 
     * @param orderId 订单Id
     */
    void orderToRemind(String orderId);

    /**
     * 查看订单物流
     * 
     * @param orderId 订单Id
     */
    void orderGetView(String orderId);

    /**
     * 确认订单已接收
     * 
     * @param orderId 订单Id
     */
    void orderConfirmReceipt(String orderId);

    /**
     * 删除订单
     * 
     * @param orderId 订单Id
     */
    void orderToDel(String orderId);

}
