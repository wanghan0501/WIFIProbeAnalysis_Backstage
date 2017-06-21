package edu.cs.scu.analyse

import java.util

import edu.cs.scu.bean.{UserBean, UserVisitBean, UserVisitTimeBean}
import edu.cs.scu.dao.impl.{UserDaoImpl, UserVisitDaoImpl, UserVisitTimeDaoImpl}
import edu.cs.scu.javautils.{DateUtil, MacAdressUtil}
import edu.cs.scu.scalautils.DataUtils
import org.apache.spark.sql.{Row, SQLContext}
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.dstream.DStream

/**
  * 实时数据分析
  *
  * Created by Wang Han on 2017/6/20 19:57.
  * E-mail address is wanghan0501@vip.qq.com.
  * Copyright © 2017 Wang Han. SCU. All Rights Reserved.
  *
  * @author Wang Han
  */
object RealTimeAnalysis {
  def analysis(sQLContext: SQLContext, streamingContext: StreamingContext, data: DStream[String]): Unit = {
    data.foreachRDD(foreachFunc = rdd => {
      // 如果当前窗口记录不为空
      if (rdd.count() >= 1) {
        // 读取格式化json
        val df = sQLContext.read.json(rdd)
        // 打印表结构
        //df.printSchema()
        val dfRDD = df.foreach(t => {
          val datas = t.getSeq(0).asInstanceOf[Seq[Row]]
          val id = t.getString(1)
          // Wi-Fi探针Mac地址
          val mmac = t.getString(2)
          val rate = t.getString(3)
          val time = DateUtil.parseTime(t.getString(4))
          val wmac = t.getString(5)
          val wssid = t.getString(6)

          // 总人数，根据mac地址判断
          var totalFlow: Int = 0
          // 入店总人数，根据rssi判断
          var checkInFlow: Int = 0
          // 用户访问时间列表
          val userVisitTimeBeanArrayList: util.ArrayList[UserVisitTimeBean] = new util.ArrayList[UserVisitTimeBean]
          // 用户列表
          val userBeanArrayList: util.ArrayList[UserBean] = new util.ArrayList[UserBean]()
          // 用户数据迭代器
          val datasIterator = datas.iterator
          while (datasIterator.hasNext) {
            val currentData = datasIterator.next()
            // 手机Mac地址
            val mac = currentData.getString(0)
            totalFlow = totalFlow + 1
            val range = currentData.getString(1).toDouble
            // 信号强度
            val rssi = currentData.getString(2).toInt

            // 判断用户是否入店
            if (DataUtils.isCheckIn(range, rssi)) {
              checkInFlow = checkInFlow + 1
            }

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
          val checkInRate = DataUtils.getCheckInRate(checkInFlow, totalFlow)

          // 添加用户相关信息
          val userVisitDaoIml = new UserVisitDaoImpl
          val userVisit = new UserVisitBean
          userVisit.setShopId(1)
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
  }
}
