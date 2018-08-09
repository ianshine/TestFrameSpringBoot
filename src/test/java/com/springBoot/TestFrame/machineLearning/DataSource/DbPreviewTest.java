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

public class DbPreviewTest extends DataSourceBaseCase{
    private static final Log log = LogFactory.getLog(DataPreviewTest.class);
    ReplaceString replaceString = new ReplaceString();
    //请求地址
    String DbPreviewUrl = GetAdd("DbPreview")+GetAdd("UserNamelilei");

    /**
     * 数据库数据预览(data/manage/dbPreview)
     */
    @Test
    public void PreviewTest() {
        Map<String, String> dbMap = new HashMap<>();
        dbMap.put("host", "10.57.17.233");
        dbMap.put("port", "3306");
        dbMap.put("dbName", "turing");
        dbMap.put("userName", "turing");
        dbMap.put("password", "turing123");
        dbMap.put("tableName", "t_data");
        dbMap.put("version", "5.7");
        dbMap.put("type", "mysql");
        dbMap.put("encoding", "utf8");
        dbMap.put("previewLines", "1");
//        String DataJson = JSON.toJSONString(dbMap);
//        log.info(DataJson);

        String resp = null;
        try {
            resp = httpRequestUtil.doGet(DbPreviewUrl, dbMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info(resp);

        JSONObject jsonObject = JSON.parseObject(resp);
        String msg = jsonObject.getString("msg");
        int status = Integer.parseInt(jsonObject.getString("status"));

        String preview = jsonObject.getString("preview");
        log.info(preview);

        //解析json里的previe字段
        String[] arr ={"[[","]]","\"",};
        String[] data =replaceString.StrReplace(arr,preview,",");

        //返回值校验
        Assert.assertEquals(msg, "预览数据查询成功");
        Assert.assertEquals(status, 200);
        Assert.assertEquals("data_test3", data[1]);
    }
}

