package edu.cs.scu.constants;

/**
 * 常量接口类
 * <p>
 * Created by Wang Han on 2017/3/29 14:40.
 * E-mail address is wanghan0501@vip.qq.com.
 * Copyright © Wang Han SCU. All Rights Reserved.
 *
 * @author Wang Han
 */
public interface Constants {
    // 定义本地运行的线程数量
    String SPARK_MASTER = "local[2]";
    String SPARK_LOCAL = "spark.local";
    // 定义本地源数据目录
    String SPARK_LOCAL_DATA_SOURCE="spark.local.data.source";
    // 定义本地检查点目录
    String SPARK_LOCAL_CHECK_POINT_DIR = "spark.local.check.point.dir";
    // 定义任务名称
    String SPARK_APP_NAME = "WIFIProbeAnalysis_Backstage";
    // 定义spark流间隔时间
    String SPARK_STREAMING_COLLECT_TIME = "spark.streaming.collect.time";
    // 定义集群源数据目录
    String SPARK_DATA_SOURCE="spark.data.source";
    // 定义集群检查点目录
    String SPARK_CHECK_POINT_DIR = "spark.check.point.dir";
}
