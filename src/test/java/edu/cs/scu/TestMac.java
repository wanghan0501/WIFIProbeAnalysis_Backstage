package edu.cs.scu;

import edu.cs.scu.javautils.MacUtil;

/**
 * Created by Wang Han on 2017/6/19 17:48.
 * E-mail address is wanghan0501@vip.qq.com.
 * Copyright © 2017 Wang Han. SCU. All Rights Reserved.
 */
public class TestMac {
    public static void main(String args[]){
        String name = MacUtil.getVendorByMac("00:00:00:28:7f:aa");
        System.out.println(name);
        String brand = MacUtil.getBrandByMac("00:00:00:28:7f:aa");
        System.out.println(brand);
    }
}
