package com.daoyu.niuqun.util;

/**
 * EventBus事件
 */
public class EventManager
{

    /**
     * 结算完成购物车销毁
     */
    public static class CartListFinish
    {

    }

    /**
     * 个人中心设置是否忙碌
     */
    public static class BusyEvent
    {

        private boolean busy = false;

        public boolean isBusy()
        {
            return busy;
        }

        public void setBusy(boolean busy)
        {
            this.busy = busy;
        }
    }

    /**
     * 个人中心个人信息同步
     */
    public static class MineEditEvent
    {

        private String name = "";
        private String age = "";
        private String region = "";
        private String wordyear = "";
        private String addtime = "";
        private String avatar = "";

        public String getName()
        {
            return name;
        }

        public void setName(String name)
        {
            this.name = name;
        }

        public String getAge()
        {
            return age;
        }

        public void setAge(String age)
        {
            this.age = age;
        }

        public String getRegion()
        {
            return region;
        }

        public void setRegion(String region)
        {
            this.region = region;
        }

        public String getWordyear()
        {
            return wordyear;
        }

        public void setWordyear(String wordyear)
        {
            this.wordyear = wordyear;
        }

        public String getAddtime()
        {
            return addtime;
        }

        public void setAddtime(String addtime)
        {
            this.addtime = addtime;
        }

        public String getAvatar()
        {
            return avatar;
        }

        public void setAvatar(String avatar)
        {
            this.avatar = avatar;
        }
    }

    /**
     * 提现同步
     */
    public static class WithdrawEvent
    {

    }

    /**
     * 积分同步
     */
    public static class IntegralEvent
    {

    }

    /**
     * 身份认证
     */
    public static class AttestEvent
    {

    }

    /**
     * 接单后通知刷新已接单列表
     */
    public static class PendingToHasEvent
    {

    }

    /**
     * 订单完成后通知刷新已完成列表
     */
    public static class HasToFinishEvent
    {

    }

    /**
     * 写完日志
     */
    public static class NewRecordEvent
    {

        private String oid = "";

        public String getOid()
        {
            return oid;
        }

        public void setOid(String oid)
        {
            this.oid = oid;
        }
    }

}
