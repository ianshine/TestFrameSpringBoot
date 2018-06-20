package com.springBoot.TestFrame.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GetEvnAddress {
    //数据预览
    private static String dataPreviewUrl;
    private static String isurl;
    private static String delurl;


    static {
        Properties prop = new Properties();
        InputStream in = null;
        try {
            in = new FileInputStream("/Users/lilei/Documents/git/TestFrame/src/main/resources/test-address.properties");
            try {
                prop.load(in);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //数据预览
            dataPreviewUrl = prop.getProperty("dataPreviewUrl").toString();
            isurl = prop.getProperty("isurl").toString();
            delurl = prop.getProperty("delurl").toString();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
    //黑白名单服务
    public static String getDataPreviewUrl() {
        return dataPreviewUrl;
    }

    public static String getIsurl() {return isurl;}

    public static String getDelurl() {return delurl;}


    public static void main(String args[]) {
        System.out.println(getDataPreviewUrl());
        System.out.println(getIsurl());
        System.out.println(getDelurl());

    }

}
