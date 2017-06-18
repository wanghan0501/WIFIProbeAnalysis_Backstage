package edu.cs.scu.analyse

import edu.cs.scu.bean.{PropertyBean, UserVisitBean}
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
    // 初始化变量
    val init: (SparkContext, SQLContext, StreamingContext) = InitUnits.initSparkContext()
    // sql环境
    val sQLContext = init._2
    // 流环境
    val streamingContext: StreamingContext = init._3
    // 配置属性
    val property: PropertyBean = InitUnits.getPropertyFromDatabase()

    var lastTime: String = DateUtil.getToday

    // 数据
    val wifiProbeData = InitUnits.getDStream(streamingContext)
    wifiProbeData.foreachRDD(foreachFunc = rdd => {

      if (rdd.count() >= 1) {
        val df = sQLContext.read.json(rdd)
        df.printSchema()
        val dfRDD = df.foreach(t => {
          val datas = t.getSeq(0).asInstanceOf[Seq[Row]]
          val datasIterator = datas.iterator
          // 总人数，根据mac地址判断
          var totalFlow = 0
          // 入店总人数，根据rssi判断
          var checkInFlow = 0

          while (datasIterator.hasNext) {
            val currentData = datasIterator.next()
            val mac = currentData.getString(0)
            totalFlow = totalFlow + 1
            val range = currentData.getString(1).toDouble
            if (range > property.getVisitRange) {
              checkInFlow = checkInFlow + 1
            }
            val rssi = currentData.getString(2)
          }
          // 进店率
          val checkInRate = checkInFlow/checkInFlow

          val id = t.getString(1)
          val mmac = t.getString(2)
          val rate = t.getString(3)
          val time = DateUtil.parseTime(t.getString(4))
          val wmac = t.getString(5)
          val wssid = t.getString(6)

          val userVisitDaoIml = new UserVisitDaoImpl
          val userVisit = new UserVisitBean
          userVisit.setShopId(1L)
          userVisit.setMac(mmac)
          userVisit.setTime(time)
          userVisit.setTotalFlow(totalFlow)
          userVisit.setCheckInFlow(checkInFlow)
          userVisit.setCheckInRate(checkInRate)
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
