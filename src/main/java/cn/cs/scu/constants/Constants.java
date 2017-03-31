package cn.cs.scu.constants;

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
    String SPARK_MASTER = "local[4]";
    String SPARK_LOCAL = "spark.local";
    String SPARK_APP_NAME_SESSION = "WIFIProbeAnalysis_Backstage";
    String SPARK_STREAMING_COLLECT_TIME = "spark.streaming.collect.time";
    String SPARK_CHECK_POINT_DIR = "spark.check.point.dir";
    String SPARK_DATA_SOURCE="spark.data.source";
}
