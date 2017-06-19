package edu.cs.scu.dao.impl;

import edu.cs.scu.bean.UserBean;
import edu.cs.scu.conf.MybatisSqlSession;
import edu.cs.scu.dao.UserDao;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

/**
 * Created by Wang Han on 2017/6/19 14:02.
 * E-mail address is wanghan0501@vip.qq.com.
 * Copyright © 2017 Wang Han. SCU. All Rights Reserved.
 */
public class UserDaoImpl implements UserDao {
    // 得到log记录器
    private static final Logger logger = Logger.getLogger(UserDaoImpl.class);

    @Override
    public void addUser(UserBean userBean) {
        SqlSession sqlSession = MybatisSqlSession.getSqlSession();

        try {
            UserDao userDao = sqlSession.getMapper(UserDao.class);
            userDao.addUser(userBean);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getStackTrace());
        } finally {
            sqlSession.close();
        }
    }

    @Override
    public void updateUserLastVisitTimeByMac(String mac, String lastVisitTime) {
        SqlSession sqlSession = MybatisSqlSession.getSqlSession();

        try {
            UserDao userDao = sqlSession.getMapper(UserDao.class);
            userDao.updateUserLastVisitTimeByMac(mac,lastVisitTime);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getStackTrace());
        } finally {
            sqlSession.close();
        }
    }

    @Override
    public void updateUserNowVisitTImeByMac(String mac, String nowVisitTime) {
        SqlSession sqlSession = MybatisSqlSession.getSqlSession();

        try {
            UserDao userDao = sqlSession.getMapper(UserDao.class);
            userDao.updateUserLastVisitTimeByMac(mac,nowVisitTime);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getStackTrace());
        } finally {
            sqlSession.close();
        }
    }

    @Override
    public void updateUserActivityDegreeByMac(String mac, String activityDegree) {
        SqlSession sqlSession = MybatisSqlSession.getSqlSession();

        try {
            UserDao userDao = sqlSession.getMapper(UserDao.class);
            userDao.updateUserLastVisitTimeByMac(mac,activityDegree);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getStackTrace());
        } finally {
            sqlSession.close();
        }
    }
}
