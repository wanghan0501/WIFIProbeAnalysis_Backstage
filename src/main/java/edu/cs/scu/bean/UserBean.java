package edu.cs.scu.bean;

import java.io.Serializable;

/**
 * Created by Wang Han on 2017/6/19 13:46.
 * E-mail address is wanghan0501@vip.qq.com.
 * Copyright © 2017 Wang Han. SCU. All Rights Reserved.
 */
public class UserBean implements Serializable{

    private static final long serialVersinUID = 351877796426921776L;

    // Mac地址
    private String mac;
    // 手机品牌
    private String brand;
    // 首次访问时间
    private String firstVisitTime;
    // 上次访问时间
    private String lastVisitTime;
    // 本次访问时间
    private String newVisitTime;
    // 活跃程度
    private String activityDegree;

    public static long getSerialVersinUID() {
        return serialVersinUID;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getMac() {
        return mac;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getFirstVisitTime() {
        return firstVisitTime;
    }

    public void setFirstVisitTime(String firstVisitTime) {
        this.firstVisitTime = firstVisitTime;
    }

    public String getLastVisitTime() {
        return lastVisitTime;
    }

    public void setLastVisitTime(String lastVisitTime) {
        this.lastVisitTime = lastVisitTime;
    }

    public String getNewVisitTime() {
        return newVisitTime;
    }

    public void setNewVisitTime(String newVisitTime) {
        this.newVisitTime = newVisitTime;
    }

    public void setActivityDegree(String activityDegree) {
        this.activityDegree = activityDegree;
    }

    public String getActivityDegree() {
        return activityDegree;
    }
}
