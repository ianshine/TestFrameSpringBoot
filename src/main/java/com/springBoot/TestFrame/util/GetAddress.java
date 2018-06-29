package com.springBoot.TestFrame.util;

import org.junit.jupiter.api.Test;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;

public class GetAddress {

    public static String GetAdd(String EvnAddress) {
        String addresss = null;
        Properties prop = new Properties();
        try{
            //读取属性文件a.properties
            InputStream in = new BufferedInputStream(new FileInputStream("/Users/lilei/Documents/git/TestFrame/src/main/resources/test-address.properties"));
            prop.load(in);     ///加载属性列表
            Iterator<String> it=prop.stringPropertyNames().iterator();
            while(it.hasNext()){
                String key=it.next();

                if (key.equals(EvnAddress)){
                    EvnAddress = prop.getProperty(key);
                    addresss = EvnAddress;
                    System.out.println(key+":"+prop.getProperty(key));
                }

            }

            in.close();

            //保存属性到b.properties文件
//            FileOutputStream oFile = new FileOutputStream("b.properties", true);//true表示追加打开
//            prop.setProperty("phone", "10086");
//            prop.store(oFile, "The New properties file");
//            oFile.close();
        }
        catch(Exception e){
            System.out.println(e);
        }

        return addresss;
    }

    @Test
    public void getTest(){
        System.out.println(GetAdd("UserNamelilei"));
    }

}
