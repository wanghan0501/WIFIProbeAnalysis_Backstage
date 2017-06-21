package edu.cs.scu.analyse

import edu.cs.scu.scalautils.{InitUtil}
import org.apache.spark.SparkContext
import org.apache.spark.sql.{SQLContext}
import org.apache.spark.streaming.StreamingContext


/**
  * 分析程序入口
  *
  * Created by Wang Han on 2017/3/29 14:42.
  * E-mail address is wanghan0501@vip.qq.com.
  * Copyright © Wang Han SCU. All Rights Reserved.
  *
  * @author Wang Han
  */
object Main {
  def main(args: Array[String]): Unit = {
    // 初始化变量
    val initContext: (SparkContext, SQLContext, StreamingContext) = InitUtil.initSparkContext()
    // sql环境
    val sQLContext = initContext._2
    // 流环境
    val streamingContext: StreamingContext = initContext._3

    // 获取原始数据
    val originData = InitUtil.getDStream(streamingContext)

    // 如果读入的数据不为空
    if (originData != null) {
      RealTimeAnalysis.analysis(sQLContext, streamingContext, originData)
    }

    streamingContext.start()
    streamingContext.awaitTermination()

  }
}
