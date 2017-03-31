package cn.cs.scu.analyse

import cn.cs.scu.scalautils.InitUnits
import org.apache.spark.SparkContext
import org.apache.spark.sql.SQLContext
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
    val init: (SparkContext, SQLContext, StreamingContext) = InitUnits.initSparkContext()
    val sQLContext = init._2
    val streamingContext: StreamingContext = init._3

    val wifiProbeData = InitUnits.getDStream(streamingContext)
    wifiProbeData.foreachRDD(t=>{
      val df = sQLContext.read.json(t)
      df.show()
    })

    streamingContext.start()
    streamingContext.awaitTermination()
  }
}
