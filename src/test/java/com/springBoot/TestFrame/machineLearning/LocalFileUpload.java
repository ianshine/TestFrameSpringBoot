package com.springBoot.TestFrame.machineLearning;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class LocalFileUpload extends BaseCase {
    private static final Log log = LogFactory.getLog(DataPreviewTest.class);
    String GenerationPath = null;


    String HdfsPathCheck = "http://10.58.10.48:8080/data/manage/check";
    String GenerationPath00 = "hdfs://tdhdfs/user/turing/data/lei.li/data_temp/u-ef68b138-a7bd-418f-a417-de86d38bee30-creditCARD_Mac.csv";
    String GenerationPath01 = "hdfs://tdhdfs/user/turing/data/lei.li/data_temp/u-75df488a-9654-4962-8358-19a3976b4dc8-creditCARD_Mac.csv";

    String delDataUrl = "http://10.58.10.48:8080/data/manage/tableDelete";



    /**
     * 数据源引入–文件上传
     */
    @Test
    public void FileUploadTest() {
        //配置文件地址
        String FileUploadUrl = GetAdd("LocalFileUpload");
        String UserName = GetAdd("UserNamelilei");
        //数据准备的文件目录
        String filePath = GetAdd("filePath");

        //发起数据源引入–文件上传请求
        String resp = null;
        try {
            resp = httpRequestUtil.upload(FileUploadUrl + UserName, filePath);

            log.info(resp);

        } catch (Exception e) {
            e.printStackTrace();
        }

        //返回值解析
        JSONObject jsonObject = JSON.parseObject(resp);
        String msg = jsonObject.getString("msg");
        String status = jsonObject.getString("status");
        String path = jsonObject.getString("path");
        GenerationPath = path;

        //返回值校验
        Assert.assertEquals("上传文件成功", msg);
        Assert.assertEquals("200", status);
        Assert.assertEquals(GenerationPath,path);


    }

    /**
     * 数据源引入–HDFS数据路径校验
     */
    @Test
    public void HdfsPathCheckTest() {
        Map<String, String> dbMap = new HashMap<>();
        dbMap.put("uri", GenerationPath01);
//        String DataJson = JSON.toJSONString(dbMap);
//        System.out.println(DataJson);

//        String CheckUrl = HdfsPathCheck+UserName;
        String CheckUrl = "http://10.58.10.48:8080/data/manage/check?loginName=lei.li";

        String resp = null;
        try {
            resp = httpRequestUtil.doGet(CheckUrl, dbMap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(resp);
        JSONObject jsonObject = JSON.parseObject(resp);
        String msg = jsonObject.getString("msg");
        String status = jsonObject.getString("status");
        String valid = jsonObject.getString("valid");
        String existed = jsonObject.getString("existed");

        //返回值校验
        Assert.assertEquals("文件存在", msg);
        Assert.assertEquals("200", status);
        Assert.assertEquals("true", valid);
        Assert.assertEquals("true", existed);

    }


    /**
     * 数据源引入–数据库数据
     */
    @Test
    public void DbConnectionTest() {
        String DbConnectionUrl = "http://10.58.10.48:8080/data/manage/dbConnection?loginName=lei.li";

        Map<String, String> dbMap = new HashMap<>();
        dbMap.put("userName", "turing");
        dbMap.put("password", "turing123");
        dbMap.put("dbName", "turing");
        dbMap.put("host", "10.57.17.233");
        dbMap.put("port", "3306");
        dbMap.put("encoding", "utf8");
        dbMap.put("type", "MySQL");
        dbMap.put("version", "5.7.19");
        dbMap.put("tablePattern", "");

//        String DataJson = JSON.toJSONString(dbMap);
//        log.info(DataJson);
        String resp = httpRequestUtil.doGet(DbConnectionUrl, dbMap);
        System.out.println(resp);

        JSONObject jsonObject = JSON.parseObject(resp);
        String msg = jsonObject.getString("msg");
        String status = jsonObject.getString("status");
        String detail = jsonObject.getString("detail");

        //返回值校验
        Assert.assertEquals("数据库链接失败", msg);
        Assert.assertEquals("102", status);
        Assert.assertEquals("不支持当前数据库类型", detail);
    }

    /**
     * 数据库数据预览
     */
    @Test
    public void DbPreviewTest() {
        String dbPreviewUrl = "http://10.58.10.48:8080/data/manage/dbPreview?loginName=lei.li";

        Map<String, Object> dbMap = new HashMap<>();
        dbMap.put("host", "10.57.17.233");
        dbMap.put("port", "3306");
        dbMap.put("userName", "turing");
        dbMap.put("password", "turing123");
        dbMap.put("tableName", "page");
        dbMap.put("version", "5.7.19");
        dbMap.put("type", "MySQL");
        dbMap.put("encoding", "utf8");
        dbMap.put("previewLines", "100");

        String DataJson = JSON.toJSONString(dbMap);
        log.info(DataJson);

        String resp = null;
        try {
            resp = httpRequestUtil.doPost(dbPreviewUrl, DataJson);
//            resp = httpRequestUtil.sendPost(dbPreviewUrl,DataJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(resp);

    }


}
