package edu.cs.scu.analyse

import edu.cs.scu.javautils.DateUtil
import edu.cs.scu.scalautils.InitUnits
import org.apache.hadoop.hive.ql.exec.spark.session.SparkSession
import org.apache.spark.SparkContext
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
      if (rdd.count() >= 1) {
        val df = sQLContext.read.json(rdd)
        df.printSchema()
        println(df.schema)
        val dfRDD = df.map(t => {
          val sta = t.getString(0)
          val time = t.getString(1)

          // 如果间隔超过10分钟，则写入数据库
          if (DateUtil.intervalTenMin(lastTime, time)) {
            lastTime = time

          }

          val dataType = t.getString(2)
          val datas = t.getSeq(3).asInstanceOf[Seq[Row]]
          val datasIterator = datas.iterator
          while (datasIterator.hasNext) {
            val currentData = datasIterator.next()
            val rssiSeq = currentData.getSeq(0).asInstanceOf[Seq[Int]]
            val rssi = rssiSeq.sum / rssiSeq.length
            val brand = currentData.getLong(1)
            val mac = currentData.getString(2)
          }

          (sta, time, dataType, datasIterator)
        })

        val data = df.select("data").flatMap(t => {
          val a = t.getSeq(0).asInstanceOf[Seq[Row]]
          a.iterator
        })

        data.foreach(t => {
          println(t.schema)
          println(t.getString(0))
        })
      }
    }
    )

    streamingContext.start()
    streamingContext.awaitTermination()

  }
}
