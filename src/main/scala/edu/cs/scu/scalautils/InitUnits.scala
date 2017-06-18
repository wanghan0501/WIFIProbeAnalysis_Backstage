package edu.cs.scu.scalautils

import edu.cs.scu.conf.ConfigurationManager
import edu.cs.scu.constants.SparkConstants
import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SQLContext
import org.apache.spark.sql.hive.HiveContext
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * 初始化spark环境工具
  *
  * Created by Wang Han on 2017/3/29 14:43.
  * E-mail address is wanghan0501@vip.qq.com.
  * Copyright © Wang Han SCU. All Rights Reserved.
  *
  * @author Wang Han
  */

object InitUnits {

  /**
    * 初始化spark、sql环境
    *
    * @return
    */
  def initSparkContext(): (SparkContext, SQLContext, StreamingContext) = {
    // spark配置文件
    val conf = getSparkConf
    // spark上下文环境
    val sc = new SparkContext(conf)
    // SQL上下文环境
    val sqlContext = getSQLContext(sc)
    //streamingContext上下文环境
    val streamingContext = getStreamingContext(sc)
    // 设置Log等级
    Logger.getRootLogger.setLevel(Level.WARN)

    (sc, sqlContext, streamingContext)
  }

  /**
    * 加载spark配置，如果在本地使用单核，如果在集群，则提交作业时候指定
    *
    * @return
    */
  def getSparkConf: SparkConf = {
    val local = ConfigurationManager.getBoolean(SparkConstants.SPARK_LOCAL)
    if (local)
      new SparkConf().setAppName(SparkConstants.SPARK_APP_NAME).setMaster(SparkConstants.SPARK_MASTER)
    else
      new SparkConf().setAppName(SparkConstants.SPARK_APP_NAME)
  }

  /**
    * 加载SQL环境，如果在本地生成sql环境，如果在集群使用hive环境
    *
    * @param sc
    * @return
    */
  def getSQLContext(sc: SparkContext): SQLContext = {
    val local = ConfigurationManager.getBoolean(SparkConstants.SPARK_LOCAL)
    if (local)
      new SQLContext(sc)
    else
      new HiveContext(sc)
  }

  /**
    * 加载streaming环境
    *
    * @param sparkContext
    * @return
    */
  def getStreamingContext(sparkContext: SparkContext): StreamingContext = {
    val local = ConfigurationManager.getBoolean(SparkConstants.SPARK_LOCAL)
    if (local) {

    }

    new StreamingContext(sparkContext, Seconds(ConfigurationManager.getLong(SparkConstants.SPARK_STREAMING_COLLECT_TIME)))
  }


  /**
    * 加载检查点及源数据目录
    *
    * @param streamingContext
    * @return
    */
  def getDStream(streamingContext: StreamingContext): DStream[String] = {
    if (ConfigurationManager.getBoolean(SparkConstants.SPARK_LOCAL)) {
      //streamingContext.checkpoint(ConfigurationManager.getString(SparkConstants.SPARK_LOCAL_CHECK_POINT_DIR))
      streamingContext.textFileStream(ConfigurationManager.getString(SparkConstants.SPARK_LOCAL_DATA_SOURCE))
      //streamingContext.socketTextStream("localhost",ConfigurationManager.getInteger(SparkConstants.SPARK_LOCAL_SOCKET_PORT))
    } else {
      //streamingContext.checkpoint(ConfigurationManager.getString(SparkConstants.SPARK_CHECK_POINT_DIR))
      streamingContext.textFileStream(ConfigurationManager.getString(SparkConstants.SPARK_DATA_SOURCE))
    }
  }

  def getPropertyFromDatabase()={

  }
}
