package com.springBoot.TestFrame.machineLearning;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class LocalFileUpload extends BaseCase{
    private static final Log log = LogFactory.getLog(DataPreviewTest.class);

    String FileUploadUrl = "http://10.58.10.48:8080/data/manage/temp";
    String HdfsPathCheck = "http://10.58.10.48:8080/data/manage/check";
    String UserName = "?loginName=lei.li";

    String filePath = "/Users/lilei/Documents/git/TestFrame/src/main/resources/dataFile/creditCARD_Mac.csv";
    String GenerationPath00 = "hdfs://tdhdfs/user/turing/data/lei.li/data_temp/u-ef68b138-a7bd-418f-a417-de86d38bee30-creditCARD_Mac.csv";
    String GenerationPath01 = "hdfs://tdhdfs/user/turing/data/lei.li/data_temp/u-75df488a-9654-4962-8358-19a3976b4dc8-creditCARD_Mac.csv";

    String delDataUrl = "http://10.58.10.48:8080/data/manage/tableDelete";

    String GenerationPath = null;

    /**
     * 数据源引入–文件上传
     */
    @Test
    public void FileUploadTest() {

        String resp = null;
        try {
            resp = httpRequestUtil.upload(FileUploadUrl+UserName,filePath);

            log.info(resp);

        } catch (Exception e) {
            e.printStackTrace();
        }

        JSONObject jsonObject = JSON.parseObject(resp);
        String msg = jsonObject.getString("msg");
        String status = jsonObject.getString("status");
        String path = jsonObject.getString("path");

        GenerationPath = path;

        Assert.assertEquals("上传文件成功",msg);
        Assert.assertEquals("200",status);
//        Assert.assertEquals(GenerationPath00,path);


    }

    /**
     * 数据源引入–HDFS数据路径校验
     */
    @Test
    public void HdfsPathCheckTest(){
        Map<String, Object> dbMap = new HashMap<>();
//        dbMap.put("uri",GenerationPath01);
//        String DataJson = JSON.toJSONString(dbMap);
//        System.out.println(DataJson);

        String CheckUrl = HdfsPathCheck+UserName+"&uri="+GenerationPath01;
//        String CheckUrl = HdfsPathCheck+UserName;
//        String params = "uri=hdfs://tdhdfs/user/turing/data/lei.li/data_temp/u-75df488a-9654-4962-8358-19a3976b4dc8-creditCARD_Mac.csv";

        String resp = null;
        try {
//            resp = httpRequestUtil.sendGetNoData(CheckUrl);
            resp = httpRequestUtil.get(CheckUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(resp);

    }
}
