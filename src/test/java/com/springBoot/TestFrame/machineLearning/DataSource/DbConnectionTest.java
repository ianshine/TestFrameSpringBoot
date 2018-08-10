package com.springBoot.TestFrame.machineLearning.DataSource;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class DbConnectionTest extends DataSourceBaseCase{
    private static final Log log = LogFactory.getLog(DbConnectionTest.class);
    //请求地址
    String DbConnectionUrl = GetAdd("DbConnection")+GetAdd("UserNamelilei");

    /**
     * 数据源引入–数据库数据
     * 链接失败
     */
    @Test
    public void ConnectionTest() {
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
        log.info(resp);

        JSONObject jsonObject = JSON.parseObject(resp);
        String msg = jsonObject.getString("msg");
        String status = jsonObject.getString("status");
        String detail = jsonObject.getString("detail");

        //返回值校验
        Assert.assertEquals("数据库链接失败", msg);
        Assert.assertEquals("102", status);
        Assert.assertEquals("不支持当前数据库类型", detail);

    }
}
