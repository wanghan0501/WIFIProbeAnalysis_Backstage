package edu.cs.scu.analyse

import edu.cs.scu.javautils.DateUtil
import edu.cs.scu.scalautils.InitUnits
import org.apache.hadoop.hive.ql.exec.spark.session.SparkSession
import org.apache.spark.SparkContext
import org.apache.spark.sql.types.{StringType, StructField, StructType}
import org.apache.spark.sql.{Row, SQLContext}
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

    var lastTime: String = DateUtil.getToday

    val wifiProbeData = InitUnits.getDStream(streamingContext)
    wifiProbeData.foreachRDD(foreachFunc = rdd => {
//          val dataStruct =
//            StructType(
//              StructField("id",StringType,true)::
//              StructField("essid0", StringType, true) ::
//              StructField("essid1", StringType, true) ::
//              StructField("essid2", StringType, true) ::
//              StructField("essid3", StringType, true) ::
//              StructField("essid4", StringType, true) ::
//              StructField("essid5", StringType, true) ::
//              StructField("essid6", StringType, true) ::
//              StructField("mac", StringType, true) ::
//              StructField("range", StringType, true) ::
//              StructField("rssi", StringType, true) ::
//              StructField("tc", StringType, true) ::
//              StructField("tmc", StringType, true) ::
//              StructField("ts", StringType, true) :: Nil)

      if (rdd.count() >= 1) {
        val dataFrameReader = sQLContext.read
        val df = dataFrameReader.json(rdd)
        df.printSchema()
        println(df.schema)
        val dfRDD = df.map(t => {
          val id = t.getString(0)
          val mmac = t.getString(1)
          val rate = t.getString(2)
          val wssid = t.getString(3)
          val time = t.getString(4)

          // 如果间隔超过1分钟，则写入数据库
          if (DateUtil.intervalOneMin(lastTime, time)) {
            lastTime = time
          }

          val datas = t.getSeq(5).asInstanceOf[Seq[Row]]
          val datasIterator = datas.iterator
          while (datasIterator.hasNext) {
            val currentData = datasIterator.next()
            val mac = currentData.getString(0)
            val rssi = currentData.getString(1)
            val range = currentData.getFloat(2)
          }
        })
      }
    }
    )

    streamingContext.start()
    streamingContext.awaitTermination()

  }
}
