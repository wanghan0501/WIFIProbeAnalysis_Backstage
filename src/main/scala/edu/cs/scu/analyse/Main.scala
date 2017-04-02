package edu.cs.scu.analyse

import edu.cs.scu.scalautils.InitUnits
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
    //    val dataStruct =
    //      StructType(StructField("ds", StringType, true) ::
    //        StructField("essid0", StringType, true) ::
    //        StructField("essid1", StringType, true) ::
    //        StructField("essid2", StringType, true) ::
    //        StructField("essid3", StringType, true) ::
    //        StructField("essid4", StringType, true) ::
    //        StructField("essid5", StringType, true) ::
    //        StructField("essid6", StringType, true) ::
    //        StructField("mac", StringType, true) ::
    //        StructField("range", StringType, true) ::
    //        StructField("rssi", StringType, true) ::
    //        StructField("tc", StringType, true) ::
    //        StructField("tmc", StringType, true) ::
    //        StructField("ts", StringType, true) :: Nil)
    //

    val wifiProbeData = InitUnits.getDStream(streamingContext)
    wifiProbeData.foreachRDD(foreachFunc = rdd => {
      if (rdd.count() >= 1) {
        val df = sQLContext.read.json(rdd)
        df.printSchema()
        println(df.schema)
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
