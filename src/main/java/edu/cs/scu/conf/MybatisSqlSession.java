package edu.cs.scu.conf;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

/**
 * 获取mybatis-config.xml中相关配置
 * <p>
 * Created by Wang Han on 2017/4/6 13:37.
 * E-mail address is wanghan0501@vip.qq.com.
 * Copyright © Wang Han. SCU. All Rights Reserved.
 *
 * @author Wang Han
 */

public class MybatisSqlSession {

    private static SqlSession sqlSession = null;
    private static SqlSessionFactory sqlSessionFactory = null;

    static {

        String resource = "mybatis-configuration.xml";
        Reader reader = null;
        try {
            reader = Resources.getResourceAsReader(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);

    }

    public static synchronized SqlSession getSqlSession() {
        sqlSession = sqlSessionFactory.openSession();

        return sqlSession;
    }
}
