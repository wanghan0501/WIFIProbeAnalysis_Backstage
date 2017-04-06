package edu.cs.scu.dao.impl;

import com.alibaba.fastjson.JSON;
import edu.cs.scu.bean.TaskBean;
import edu.cs.scu.conf.MybatisSqlSession;
import edu.cs.scu.dao.TaskInfoDao;

import java.util.List;

/**
 * 任务信息数据库接口实现
 * <p>
 * Created by Wang Han on 2017/4/6 17:18.
 * E-mail address is wanghan0501@vip.qq.com.
 * Copyright © Wang Han. SCU. All Rights Reserved.
 *
 * @author Wang Han
 */
public class TaskInfoDaoImpl implements TaskInfoDao {
    @Override
    public int getTaskCount() {
        int count;
        try {
            TaskInfoDao taskInfoDao = MybatisSqlSession.getSqlSession().getMapper(TaskInfoDao.class);
            count = taskInfoDao.getTaskCount();
        } finally {
            MybatisSqlSession.getSqlSession().close();
        }
        return count;
    }

    @Override
    public List<TaskBean> getTaskInfo() {
        return null;
    }

    @Override
    public TaskBean getTaskById(JSON json) {
        return null;
    }
}
