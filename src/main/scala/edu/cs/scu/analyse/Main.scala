package edu.cs.scu.analyse

import java.text.SimpleDateFormat

import edu.cs.scu.bean.UserVisitBean
import edu.cs.scu.dao.impl.UserVisitDaoImpl
import edu.cs.scu.javautils.DateUtil
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
        val df = sQLContext.read.json(rdd)
        df.printSchema()
        val dfRDD = df.foreach(t => {
          val datas = t.getSeq(0).asInstanceOf[Seq[Row]]
          val datasIterator = datas.iterator
          var macCount=0
          while (datasIterator.hasNext) {
            val currentData = datasIterator.next()
            val mac = currentData.getString(0)
            macCount=macCount+1
            val range = currentData.getString(1)
            val rssi = currentData.getString(2)
          }
          val id = t.getString(1)
          val mmac = t.getString(2)
          val rate = t.getString(3)
          val time = DateUtil.parseTime(t.getString(4))
          val wmac = t.getString(5)
          val wssid = t.getString(6)

          val userVisitDaoIml= new UserVisitDaoImpl
          val userVisit=new UserVisitBean
          userVisit.setShopId(1L)
          userVisit.setMac(mmac)
          userVisit.setTime(time)
          userVisit.setTotalFlow(macCount)
          userVisitDaoIml.addUserVisit(userVisit)
          println("insert finished")
          // 如果间隔超过1分钟，则写入数据库
//          if (DateUtil.intervalOneMin(lastTime, time)) {
//            lastTime = time
//          }
        }
        )
      }
    }
    )

    streamingContext.start()
    streamingContext.awaitTermination()

  }
}
