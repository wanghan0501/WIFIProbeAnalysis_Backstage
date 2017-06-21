package edu.cs.scu;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import edu.cs.scu.constants.TimeConstants;
import edu.cs.scu.javautils.DateUtil;

/**
 * Created by Wang Han on 2017/4/17 15:45.
 * E-mail address is wanghan0501@vip.qq.com.
 * Copyright © Wang Han. SCU. All Rights Reserved.
 */
public class TestJson {
    public static void main(String[] args) {
        JSONObject jsonObject = JSON.parseObject("{\"sta\":\"5ccf7f07ce5a\",\"time\":\"20160604123043\",\"type\":\"probe\",\"data\":[{\"brand\":\"14200\", \"mac\":\"28c2dd2918d1\",\"rssi\":[\"32\",\"30\"]},{\"brand\":\"18452\",\"mac\":\"a4d1d25edf4e\",\"rssi\":[\"93\",\"93\"]}]}");
        System.out.println(jsonObject.getString("sta"));
        System.out.println(DateUtil.parseTime(jsonObject.getString("time"), TimeConstants.TIME_FORMAT));
        System.out.println(jsonObject.getString("type"));
        System.out.println(jsonObject.getJSONArray("data"));
        System.out.println(DateUtil.before("20160604123043", "20160704123043"));

        Runnable r = new Runnable1();

        Thread t = new Thread(r);
        t.start();

        for (int i = 0; i < 10; i++) {
            System.out.println("main" + i + " " + DateUtil.intervalTenMin("20160604123043", "20160604124043"));
        }
    }
}

class Runnable1 implements Runnable {
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("thread" + i + " " + DateUtil.intervalTenMin("20160604123043", "20160604124043"));
        }
    }
}