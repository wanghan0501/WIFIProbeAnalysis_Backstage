package edu.cs.scu.dao.impl;

import edu.cs.scu.bean.TaskBean;
import edu.cs.scu.conf.MybatisSqlSession;
import edu.cs.scu.dao.TaskDao;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
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
public class TaskDaoImpl implements TaskDao {
    @Override
    public int getTaskCount() {
        int count;
        try {
            TaskDao taskDao = MybatisSqlSession.getSqlSession().getMapper(TaskDao.class);
            count = taskDao.getTaskCount();
        } finally {
            MybatisSqlSession.getSqlSession().close();
        }

        return count;
    }

    @Override
    public List<TaskBean> getTaskInfo() {
        List<TaskBean> taskBeanList = new ArrayList<>();
        try {
            TaskDao taskDao = MybatisSqlSession.getSqlSession().getMapper(TaskDao.class);
            taskBeanList = taskDao.getTaskInfo();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            MybatisSqlSession.getSqlSession().close();
        }

        return taskBeanList;
    }

    @Override
    public TaskBean getTaskById(Long id) {
        TaskBean taskBean = new TaskBean();
        try {
            TaskDao taskDao = MybatisSqlSession.getSqlSession().getMapper(TaskDao.class);
            taskBean = taskDao.getTaskById(id);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            MybatisSqlSession.getSqlSession().close();
        }

        return taskBean;
    }

    @Override
    public void addTask(TaskBean taskBean) {
        SqlSession sqlSession = MybatisSqlSession.getSqlSession();

        try {
            TaskDao taskDao = sqlSession.getMapper(TaskDao.class);
            System.out.println(taskBean.getCreateTime());
            taskDao.addTask(taskBean);
            sqlSession.commit();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            sqlSession.close();
        }
    }
}
