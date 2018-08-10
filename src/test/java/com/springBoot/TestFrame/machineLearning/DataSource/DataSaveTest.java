package com.springBoot.TestFrame.machineLearning.DataSource;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataSaveTest extends DataSourceBaseCase{

    private static final Log log = LogFactory.getLog(DataSaveTest.class);
    //请求地址
    String SaveUrl = GetAdd("SaveUrl")+GetAdd("UserNamelilei");
    String tableDeleteUrl = GetAdd("tableDelete")+GetAdd("UserNamelilei");

    int name;
    String id;

    JdbcTemplate jdbcTemplate = new JdbcTemplate(machinelearningJdbcTemplate.getMachinelearningDataSource());

    /**
     * 数据保存(data/manage/save)
     */
    @Test
    public void SaveTest() throws Exception {
        //文件名称
        name = (int)(Math.random() * 10000);
        log.info("name====test_"+name);
        //meta字段准备
        String meta = "[{'name':'id','type':'bigint'},{'name':'limit_bal','type':'bigint'},{'name':'sex','type':'bigint'},{'name':'education','type':'bigint'},{'name':'marriage','type':'bigint'},{'name':'age','type':'bigint'},{'name':'pay_0','type':'bigint'},{'name':'pay_2','type':'bigint'},{'name':'pay_3','type':'bigint'},{'name':'pay_4','type':'bigint'},{'name':'pay_5','type':'bigint'},{'name':'pay_6','type':'bigint'},{'name':'bill_amt1','type':'bigint'},{'name':'bill_amt2','type':'bigint'},{'name':'bill_amt3','type':'bigint'},{'name':'bill_amt4','type':'bigint'},{'name':'bill_amt5','type':'bigint'},{'name':'bill_amt6','type':'bigint'},{'name':'pay_amt1','type':'bigint'},{'name':'pay_amt2','type':'bigint'},{'name':'pay_amt3','type':'bigint'},{'name':'pay_amt4','type':'bigint'},{'name':'pay_amt5','type':'bigint'},{'name':'pay_amt6','type':'bigint'},{'name':'next_month','type':'bigint'}]";
        List list = JSONObject.parseObject(meta, List.class);

        //上传文件地址
        String FileUploadUrl = GetAdd("LocalFileUpload");
        //数据准备的文件目录
        String filePath = GetAdd("filePath");
        //发起数据源引入–文件上传请求，生成：数据源地址
        JSONObject jsonObject = JSON.parseObject(httpRequestUtil.upload(FileUploadUrl + GetAdd("UserNamelilei"), filePath));
        String GenerationPath = jsonObject.getString("path");

        Map<String, Object> dbMap = new HashMap<>();
        dbMap.put("name", "test_"+name);//hive表名称
        dbMap.put("targetFormat", "parquet");//存入hive的文件格式
        dbMap.put("protocol", "LOCAL");//数据上传方式:LOCAL（本地），HDFS（HDFS），DATABASE(数据库)
        dbMap.put("uri", GenerationPath);
        dbMap.put("meta", JSONObject.toJSONString(list));
        dbMap.put("format", "csv");
        dbMap.put("firstLineSchema", "false");//首行是否是字段名
        dbMap.put("colDelimiter", "0x2C");
        dbMap.put("dbTable", "");
        dbMap.put("format", "csv");
        dbMap.put("columnTypeCheck", "PERMISSIVE");//列类型检查PERMISSIVE（设置为null），DROPMALFORMED（抛掉异常行），FAILFAST（引入失败）
        dbMap.put("rowDelimiter", "自动识别处理");
        dbMap.put("password", "turing123");
        dbMap.put("dbHost", "10.57.17.233");
        dbMap.put("dbName", "turing");
        dbMap.put("sqlStatement", "");
        dbMap.put("columnLengthCheck", "DROPMALFORMED");//列长度检查DROPMALFORMED（抛掉异常行），FAILFAST（引入失败）
        dbMap.put("encoding", "UTF-8");
        dbMap.put("userName", "turing");
        dbMap.put("dbType", "UTF-8");
        dbMap.put("dbPort", "3306");

        String DataJson = JSON.toJSONString(dbMap);
        log.info("DataJson========"+DataJson);

        String resp = httpRequestUtil.doPost(SaveUrl, dbMap);
        log.info(resp);

        JSONObject jsonObjectSave = JSON.parseObject(resp);
        String msg = jsonObjectSave.getString("msg");
        String status = jsonObjectSave.getString("status");

        //返回值校验
        Assert.assertEquals(msg, "任务提交成功");
        Assert.assertEquals(status, "200");
    }

    /**
     * 清除数据,调用删除接口
     */
    @After
    public void delData() {
        DelSourceDataTest delSourceDataTest = new DelSourceDataTest();
        delSourceDataTest.delData("test_"+name);

    }
}
