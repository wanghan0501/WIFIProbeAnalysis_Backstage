package edu.cs.scu.scalautils

import edu.cs.scu.bean.PropertyBean
import edu.cs.scu.constants.{TableConstants, TimeConstants}
import edu.cs.scu.javautils.{DateUtil, MacAdressUtil}
import org.apache.log4j.Logger
import org.apache.spark.sql.{Row, SQLContext}
import org.apache.spark.streaming.dstream.DStream

/**
  * 获取数据的工具类
  *
  * Created by Wang Han on 2017/6/20 18:44.
  * E-mail address is wanghan0501@vip.qq.com.
  * Copyright © 2017 Wang Han. SCU. All Rights Reserved.
  *
  * @author Wang Han
  */
object DataUtils {
  // 得到log记录器
  private val logger = Logger.getLogger(classOf[DataUtils])
  // 配置属性
  private val property: PropertyBean = InitUnits.getPropertyFromDatabase()


  /**
    * 原始数据经过预处理得到预处理数据
    *
    * @param sQLContext
    * @param originData
    * @return
    */
  def getPreData(sQLContext: SQLContext, originData: DStream[String]):
  DStream[(Row, String, String, String, String, String, String)] = {
    val preDStream = originData.transform(rdd => {
      val df = sQLContext.read.json(rdd)
      val preData = df.flatMap(t => {
        val id = t.getString(1)
        // Wi-Fi探针Mac地址
        val mmac = t.getString(2)
        val rate = t.getString(3)
        val time = DateUtil.parseTime(t.getString(4), TimeConstants.TIME_FORMAT)
        val wmac = t.getString(5)
        val wssid = t.getString(6)
        val data = t.getSeq(0).asInstanceOf[Seq[Row]].iterator
        val preIterator = data.map(t => (t, id, mmac, rate, time, wmac, wssid))
        preIterator
      })
      preData
    })
    preDStream
  }

  /**
    * 获得原始数据中data字段中的手机数据,并处理统计
    * 返回值形如：(mac,brand,range,rssi)
    *
    * @param sQLContext
    * @return
    */

  def getPhoneData(sQLContext: SQLContext, preData: DStream[(Row, String, String, String, String, String, String)]):
  DStream[(String, String, Double, Int)] = {
    val phoneRDD = preData.map(tuple => {
      val mmac = tuple._3
      val time = DateUtil.parseTime(tuple._5, TimeConstants.DATE_FORMAT)
      val mac = tuple._1.getString(0)
      val brand = MacAdressUtil.getBrandByMac(tuple._1.getString(0))
      val range = tuple._1.getString(1).toDouble
      val rssi = tuple._1.getString(2).toInt
      val key = s"${TableConstants.FILED_MMAC}=${mmac}|${TableConstants.FIELD_TIME}=${time}|" +
        s"${TableConstants.FIELD_BRAND}=${brand}"
      (key, brand, range, rssi)
    })
    phoneRDD
  }

  /**
    * 判断用户是否入店
    *
    * @param range 距离
    * @param rssi  信号强度
    * @return
    */
  def isCheckIn(range: Double, rssi: Int): Boolean = {
    if (range < property.getVisitRange)
      true
    else
      false
  }

  /**
    * 计算入店率
    *
    * @param checkInFlow
    * @param totalFlow
    * @return
    */
  def getCheckInRate(checkInFlow: Int, totalFlow: Int): Double = {
    var checkInRate: Double = 0.0
    try {
      checkInRate = checkInFlow.toDouble / totalFlow.toDouble
    } catch {
      case e: ArithmeticException => {
        checkInRate = 0.0
        logger.error("checkInRate compute error")
      }
    }
    checkInRate
  }
}

class DataUtils {

}
