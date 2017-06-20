package edu.cs.scu.scalautils

import edu.cs.scu.bean.PropertyBean

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

  // 配置属性
  private val property: PropertyBean = InitUnits.getPropertyFromDatabase()

  /**
    * 判断用户是否入店
    *
    * @param range 距离
    * @param rssi 信号强度
    * @return
    */
  def isCheckIn(range:Double,rssi:Int): Boolean ={
    if(range < property.getVisitRange)
      true
    else
      false
  }
}

class DataUtils {

}
