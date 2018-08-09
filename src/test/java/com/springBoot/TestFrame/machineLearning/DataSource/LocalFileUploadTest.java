package com.springBoot.TestFrame.machineLearning.DataSource;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.springBoot.TestFrame.machineLearning.DataPreviewTest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class LocalFileUploadTest extends DataSourceBaseCase {
    private static final Log log = LogFactory.getLog(DataPreviewTest.class);

    /**
     * 数据源引入–文件上传(data/manage/temp)
     */
    @Test
    public void FileloadTest() throws Exception {
        //配置文件地址
        String FileUploadUrl = GetAdd("LocalFileUpload");
        //数据准备的文件目录
        String filePath = GetAdd("filePath");

        //发起数据源引入–文件上传请求
        String resp = httpRequestUtil.upload(FileUploadUrl + GetAdd("UserNamelilei"), filePath);

        log.info(resp);

        //返回值解析
        JSONObject jsonObject = JSON.parseObject(resp);
        String msg = jsonObject.getString("msg");
        String status = jsonObject.getString("status");
        String path = jsonObject.getString("path");
        log.info(path);

        //返回值校验
        Assert.assertEquals("上传文件成功", msg);
        Assert.assertEquals("200", status);
        Assert.assertNotNull(path);
    }

    /**
     * 数据源引入–HDFS数据路径校验(data/manage/check)
     */
    @Test
    public void DataCheckTest() throws Exception {
        //上传文件地址
        String FileUploadUrl = GetAdd("LocalFileUpload");
        //数据准备的文件目录
        String filePath = GetAdd("filePath");

        //发起数据源引入–文件上传请求，生成：数据源地址
        JSONObject jsonObject = JSON.parseObject(httpRequestUtil.upload(FileUploadUrl + GetAdd("UserNamelilei"), filePath));
        String GenerationPath = jsonObject.getString("path");


        Map<String, String> dbMap = new HashMap<>();
        dbMap.put("uri", GenerationPath);
        //校验请求
        String CheckUrl = GetAdd("DataCheck")+GetAdd("UserNamelilei");
        System.out.println("CheckUrl=="+CheckUrl);
        //发起校验
        String resp = httpRequestUtil.doGet(CheckUrl, dbMap);

        log.info(resp);
        //返回解析
        JSONObject jsonObjectRes = JSON.parseObject(resp);
        String msg = jsonObjectRes.getString("msg");
        String status = jsonObjectRes.getString("status");
        String valid = jsonObjectRes.getString("valid");
        String existed = jsonObjectRes.getString("existed");

        //返回值校验
        Assert.assertEquals("文件存在", msg);
        Assert.assertEquals("200", status);
        Assert.assertEquals("true", valid);
        Assert.assertEquals("true", existed);
    }


}
