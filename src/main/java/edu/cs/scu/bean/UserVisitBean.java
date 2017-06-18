package edu.cs.scu.bean;

/**
 * 用户访问类
 * <p>
 * Created by Wang Han on 2017/6/18 15:11.
 * E-mail address is wanghan0501@vip.qq.com.
 * Copyright © 2017 Wang Han. SCU. All Rights Reserved.
 */
public class UserVisitBean {
    private long shopId;
    private String mac;
    private String time;
    private long totalFlow;
    private long checkInFlow;
    private long checkInRate;
    private long shallowVisitRate;
    private long deepVisitRate;

    public UserVisitBean() {

    }

    public long getShopId() {
        return shopId;
    }

    public void setShopId(long shopId) {
        this.shopId = shopId;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getTotalFlow() {
        return totalFlow;
    }

    public void setTotalFlow(long totalFlow) {
        this.totalFlow = totalFlow;
    }

    public long getCheckInFlow() {
        return checkInFlow;
    }

    public void setCheckInFlow(long checkInFlow) {
        this.checkInFlow = checkInFlow;
    }

    public long getCheckInRate() {
        return checkInRate;
    }

    public void setCheckInRate(long checkInRate) {
        this.checkInRate = checkInRate;
    }

    public long getShallowVisitRate() {
        return shallowVisitRate;
    }

    public void setShallowVisitRate(long shallowVisitRate) {
        this.shallowVisitRate = shallowVisitRate;
    }

    public long getDeepVisitRate() {
        return deepVisitRate;
    }

    public void setDeepVisitRate(long deepVisitRate) {
        this.deepVisitRate = deepVisitRate;
    }

}
