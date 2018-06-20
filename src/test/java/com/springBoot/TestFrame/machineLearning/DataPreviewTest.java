package com.springBoot.TestFrame.machineLearning;

import com.alibaba.fastjson.JSON;
import com.springBoot.TestFrame.httpUtil.HttpRequestUtil;
import com.springBoot.TestFrame.util.GetEvnAddress;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据预览接口
 */
public class DataPreviewTest{
    GetEvnAddress getEvnAddress = new GetEvnAddress();
    HttpRequestUtil httpRequestUtil = new HttpRequestUtil();

    String DataPreviewUrl = getEvnAddress.getDataPreviewUrl();

    private static final Log log = LogFactory.getLog(DataPreviewTest.class);

    @Test
    public void DataPreviewTest(){
        Map<String, Object> dbMap = new HashMap<>();
        dbMap.put("colDelimiter","0x2C");
        dbMap.put("encoding","UTF-8");
        dbMap.put("firstLineSchema",true);
        dbMap.put("format","csv");
        dbMap.put("lineNumbers",100);
        dbMap.put("path",DataPreviewUrl);
        dbMap.put("protocol","LOCAL");
        dbMap.put("randomPreview",false);
        dbMap.put("rowDelimiter","自动识别处理");

        String DataJson = JSON.toJSONString(dbMap);
//        System.out.println(DataJson);
        log.info(DataJson);

//        httpRequestUtil.sendPost("DataPreviewUrl",DataJson);

    }


}
