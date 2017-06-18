package edu.cs.scu.bean;

import java.io.Serializable;

/**
 * Created by Wang Han on 2017/6/18 18:22.
 * E-mail address is wanghan0501@vip.qq.com.
 * Copyright © 2017 Wang Han. SCU. All Rights Reserved.
 */
public class PropertyBean implements Serializable{
    private static final long serialVersinUID = 351877796426921776L;

    private int propertyId;
    private String visitCycle;
    private double visitRange;
    private int visitRSSI;
    private String activityDegree;
    private String visitTimeSplit;
    private String propertyType;

    public PropertyBean(){

    }

    public static long getSerialVersinUID() {
        return serialVersinUID;
    }

    public int getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(int propertyId) {
        this.propertyId = propertyId;
    }

    public String getVisitCycle() {
        return visitCycle;
    }

    public void setVisitCycle(String visitCycle) {
        this.visitCycle = visitCycle;
    }

    public double getVisitRange() {
        return visitRange;
    }

    public void setVisitRange(double visitRange) {
        this.visitRange = visitRange;
    }

    public int getVisitRSSI() {
        return visitRSSI;
    }

    public void setVisitRSSI(int visitRSSI) {
        this.visitRSSI = visitRSSI;
    }

    public String getActivityDegree() {
        return activityDegree;
    }

    public void setActivityDegree(String activityDegree) {
        this.activityDegree = activityDegree;
    }

    public String getVisitTimeSplit() {
        return visitTimeSplit;
    }

    public void setVisitTimeSplit(String visitTimeSplit) {
        this.visitTimeSplit = visitTimeSplit;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }
}
