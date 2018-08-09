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

public class TableListTest extends DataSourceBaseCase{
    private static final Log log = LogFactory.getLog(DataPreviewTest.class);
    //请求地址
    String TableListUrl = GetAdd("TableList")+GetAdd("UserNamelilei");

    /**
     * 数据列表（/data/manage/tableList)
     */
    @Test
    public void TableTest() {
        Map<String, String> dbMap = new HashMap<>();
        dbMap.put("tableName", "t_data");
        dbMap.put("curPage", "1");
        dbMap.put("pageSize", "10");
//      String DataJson = JSON.toJSONString(dbMap);
//      log.info(DataJson);

        String resp = httpRequestUtil.doGet(TableListUrl, dbMap);

        log.info(resp);


        JSONObject jsonObject = JSON.parseObject(resp);
        String success = jsonObject.getString("success");

        String data = jsonObject.getString("data");
        JSONObject dataObject = JSON.parseObject(jsonObject.getString("data"));

        String curPage = dataObject.getString("curPage");
        String pageSize = dataObject.getString("pageSize");

        //返回值校验
        Assert.assertEquals("true", success);
        Assert.assertEquals("1", curPage);
        Assert.assertEquals("10", pageSize);
    }
}
