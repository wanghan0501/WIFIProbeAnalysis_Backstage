package edu.cs.scu.registermac;

import edu.cs.scu.bean.VendorMacBean;
import edu.cs.scu.dao.impl.VendorMacDaoImpl;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.List;

/**
 * Created by Wang Han on 2017/6/19 16:38.
 * E-mail address is wanghan0501@vip.qq.com.
 * Copyright © 2017 Wang Han. SCU. All Rights Reserved.
 */
public class Register {

    public void parseXml01() {
        try {
            // 构造解析器
            SAXReader reader = new SAXReader();
            File file = new File("/Users/mac/Workspace/Java/WIFIProbeAnalysis_Backstage/data/vendorMacs.xml");
            Document document = reader.read(file);
            Element rootElement = document.getRootElement();
            List<Element> childElements = rootElement.elements();

            VendorMacDaoImpl vendorMacDaoImpl = new VendorMacDaoImpl();
            int i = 0;
            for (Element child : childElements) {
                //已知属性名情况下
                String mac_prefix = child.attributeValue("mac_prefix");
                String vendor_name = child.attributeValue("vendor_name");
                //System.out.println("mac_prefix: " + mac_prefix);
                //System.out.println("vendor_name" + vendor_name);
                // 设置Mac数据
                VendorMacBean vendorMacBean = new VendorMacBean();
                vendorMacBean.setMacPrefix(mac_prefix);
                vendorMacBean.setVendorName(vendor_name);
                vendorMacDaoImpl.addVendorMac(vendorMacBean);
                System.out.println("item " + i);
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Register demo = new Register();
        demo.parseXml01();
    }
}
