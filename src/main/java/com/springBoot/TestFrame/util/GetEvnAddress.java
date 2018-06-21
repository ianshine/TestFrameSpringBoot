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

    //机器学习mySql
    private static String driverClassName;
    private static String machineLearningUrl;
    private static String machineLearningDbUsername;
    private static String machineLearningDbPassword;


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
            //机器学习
            driverClassName = prop.getProperty("driverClassName").toString();
            machineLearningUrl = prop.getProperty("machineLearningUrl").toString();
            machineLearningDbUsername = prop.getProperty("machineLearningDbUsername").toString();
            machineLearningDbPassword = prop.getProperty("machineLearningDbPassword").toString();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
    //数据预览
    public static String getDataPreviewUrl() { return dataPreviewUrl; }
    public static String getIsurl() {return isurl;}
    public static String getDelurl() {return delurl;}
    //机器学习
    public static String getDriverClassName() {
        return driverClassName;
    }
    public static String getMachineLearningUrl() {return machineLearningUrl;}
    public static String getMachineLearningDbUsername() {return machineLearningDbUsername;}
    public static String getMachineLearningDbPassword() {return machineLearningDbPassword;}

    public static void main(String args[]) {
        System.out.println(getDataPreviewUrl());
        System.out.println(getIsurl());
        System.out.println(getDelurl());

        System.out.println(getDriverClassName());
        System.out.println(getMachineLearningUrl());
        System.out.println(getMachineLearningDbUsername());
        System.out.println(getMachineLearningDbPassword());

    }

}
