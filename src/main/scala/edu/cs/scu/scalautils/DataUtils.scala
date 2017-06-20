package edu.cs.scu.scalautils

import edu.cs.scu.bean.PropertyBean
import org.apache.log4j.Logger

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
