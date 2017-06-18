package edu.cs.scu.dao;

import edu.cs.scu.bean.PropertyBean;

/**
 * Created by Wang Han on 2017/6/18 19:12.
 * E-mail address is wanghan0501@vip.qq.com.
 * Copyright © 2017 Wang Han. SCU. All Rights Reserved.
 */
public interface PropertyDao {
    public PropertyBean getPropertyById(int id);

    // 从数据库中拉去最新配置数据，即最后一条数据
    public PropertyBean getNewProperty();
}
