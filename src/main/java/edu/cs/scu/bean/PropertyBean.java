package edu.cs.scu.bean;

/**
 * Created by Wang Han on 2017/6/18 18:22.
 * E-mail address is wanghan0501@vip.qq.com.
 * Copyright © 2017 Wang Han. SCU. All Rights Reserved.
 */
public class PropertyBean {
    private long propertyId;
    private String visitCycle;
    private long visitRange;
    private String activityDegree;
    private String visitTimeSplit;
    private String propertyType;

    public PropertyBean(){

    }

    public long getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(long propertyId) {
        this.propertyId = propertyId;
    }

    public String getVisitCycle() {
        return visitCycle;
    }

    public void setVisitCycle(String visitCycle) {
        this.visitCycle = visitCycle;
    }

    public long getVisitRange() {
        return visitRange;
    }

    public void setVisitRange(long visitRange) {
        this.visitRange = visitRange;
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
