package edu.cs.scu.analyse

import java.util
import java.util.ArrayList

import edu.cs.scu.bean.{PropertyBean, UserBean, UserVisitBean, UserVisitTimeBean}
import edu.cs.scu.dao.impl.{UserDaoImpl, UserVisitDaoImpl, UserVisitTimeDaoImpl}
import edu.cs.scu.javautils.{DateUtil, MacAdressUtil}
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

    // 数据
    val wifiProbeData = InitUnits.getDStream(streamingContext)

    wifiProbeData.foreachRDD(foreachFunc = rdd => {
      // 如果当前窗口记录不为空
      if (rdd.count() >= 1) {
        // 读取格式化json
        val df = sQLContext.read.json(rdd)
        // 打印表结构
        //df.printSchema()
        val dfRDD = df.foreach(t => {
          val datas = t.getSeq(0).asInstanceOf[Seq[Row]]
          val id = t.getString(1)
          val mmac = t.getString(2)
          val rate = t.getString(3)
          val time = DateUtil.parseTime(t.getString(4))
          val wmac = t.getString(5)
          val wssid = t.getString(6)

          val datasIterator = datas.iterator
          // 总人数，根据mac地址判断
          var totalFlow = 0
          // 入店总人数，根据rssi判断
          var checkInFlow = 0

          // 用户访问时间列表
          val userVisitTimeBeanArrayList: util.ArrayList[UserVisitTimeBean] = new util.ArrayList[UserVisitTimeBean]
          // 用户列表
          val userBeanArrayList: util.ArrayList[UserBean] = new util.ArrayList[UserBean]()

          while (datasIterator.hasNext) {
            val currentData = datasIterator.next()
            val mac = currentData.getString(0)
            totalFlow = totalFlow + 1
            val range = currentData.getString(1).toDouble
            if (range < property.getVisitRange) {
              checkInFlow = checkInFlow + 1
            }
            val rssi = currentData.getString(2)

            // 向用户列表中加入新数据
            val userBean = new UserBean
            userBean.setShopId(1)
            userBean.setMac(mac)
            userBean.setBrand(MacAdressUtil.getBrandByMac(mac))
            userBeanArrayList.add(userBean)

            // 向用户访问列表中加入新数据
            val userVisitTimeBean = new UserVisitTimeBean
            userVisitTimeBean.setShopId(1)
            userVisitTimeBean.setMac(mac)
            userVisitTimeBean.setVisitTime(time)
            userVisitTimeBeanArrayList.add(userVisitTimeBean)
          } //end while

          // 插入用户数据
          val userDaoImpl = new UserDaoImpl
          userDaoImpl.addUserByBatch(userBeanArrayList)

          // 插入用户访问时间数据
          val userVisitTimeDaoImpl = new UserVisitTimeDaoImpl
          userVisitTimeDaoImpl.addUserVisitTimeByBatch(userVisitTimeBeanArrayList)

          // 进店率
          var checkInRate = 0
          try {
            checkInRate = checkInFlow / totalFlow
          } catch {
            case e: ArithmeticException => checkInFlow = 0
          }

          // 添加用户相关信息
          val userVisitDaoIml = new UserVisitDaoImpl
          val userVisit = new UserVisitBean
          userVisit.setShopId(1L)
          userVisit.setMmac(mmac)
          userVisit.setTime(time)
          userVisit.setTotalFlow(totalFlow)
          userVisit.setCheckInFlow(checkInFlow)
          userVisit.setCheckInRate(checkInRate)
          userVisitDaoIml.addUserVisit(userVisit)
          println("insert finished")
        }
        )
      }
    }
    )

    streamingContext.start()
    streamingContext.awaitTermination()

  }
}
