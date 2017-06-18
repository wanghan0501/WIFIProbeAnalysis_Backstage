package edu.cs.scu;

import edu.cs.scu.bean.PropertyBean;
import edu.cs.scu.dao.impl.PropertyDaoImpl;

/**
 * Created by Wang Han on 2017/6/18 19:34.
 * E-mail address is wanghan0501@vip.qq.com.
 * Copyright © 2017 Wang Han. SCU. All Rights Reserved.
 */
public class testPropertyBean {
    public static void main(String args[]) {
        PropertyDaoImpl propertyDao = new PropertyDaoImpl();
        PropertyBean propertyBean = new PropertyBean();
        propertyBean = propertyDao.getPropertyById(1L);
        System.out.println(propertyBean.getVisitTimeSplit());
    }
}
