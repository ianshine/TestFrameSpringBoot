package com.springBoot.TestFrame.machineLearning.DataSource;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.springBoot.TestFrame.machineLearning.DataPreviewTest;
import com.springBoot.TestFrame.util.ReplaceString;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class PreviewTest extends DataSourceBaseCase{
    private static final Log log = LogFactory.getLog(DataPreviewTest.class);
    ReplaceString replaceString = new ReplaceString();
    //请求地址
    String PreviewUrl = GetAdd("PreviewUrl")+GetAdd("UserNamelilei");

    /**
     * 数据预览(data/manage/preview)
     */
    @Test
    public void PreviewTestSuc() throws Exception {
        //上传文件地址
        String FileUploadUrl = GetAdd("LocalFileUpload");
        //数据准备的文件目录
        String filePath = GetAdd("filePath");

        //发起数据源引入–文件上传请求，生成：数据源地址
        JSONObject jsonObject = JSON.parseObject(httpRequestUtil.upload(FileUploadUrl + GetAdd("UserNamelilei"), filePath));
        String GenerationPath = jsonObject.getString("path");

        Map<String, String> dbMap = new HashMap<>();
        dbMap.put("colDelimiter", "0x2C");
        dbMap.put("encoding", "UTF-8");
        dbMap.put("firstLineSchema", "true");
        dbMap.put("format", "csv");
        dbMap.put("lineNumbers", "1");
        dbMap.put("path", GenerationPath);//上传文件HDFS缓存路径，即上传文件的时候返回值path
        dbMap.put("protocol", "LOCAL");//上传方式，LOCAL（本地），HDFS（HDFS）,DATABASE(数据库)
        dbMap.put("randomPreview", "false");
        dbMap.put("rowDelimiter", "自动识别处理");


        String resp = httpRequestUtil.doGet(PreviewUrl, dbMap);
        log.info(resp);

        JSONObject jsonObjectPre = JSON.parseObject(resp);
        int status = Integer.parseInt(jsonObject.getString("status"));

        String preview = jsonObjectPre.getString("preview");
        System.out.println(preview);

        String[] arr ={"[[","]]","\"",};
        String[] data =replaceString.StrReplace(arr,preview,",");

        //返回值校验
        Assert.assertEquals(status, 200);
        Assert.assertEquals("20000", data[1]);
    }
}
