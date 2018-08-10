package com.springBoot.TestFrame.machineLearning.DataSource;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.springBoot.TestFrame.util.ReplaceString;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TablePreviewTest extends DataSourceBaseCase{
    private static final Log log = LogFactory.getLog(TablePreviewTest.class);
    ReplaceString replaceString = new ReplaceString();
    //请求地址
    String TablePreviewUrl = GetAdd("TablePreview")+GetAdd("UserNamelilei");
    String SaveUrl = GetAdd("SaveUrl")+GetAdd("UserNamelilei");

    int name;

    /**
     * tablePreview数据预览
     * @throws Exception
     */
    @Test
    public void TablePreviewTest() throws Exception {
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
        JSONObject jsonObjectDa = JSON.parseObject(httpRequestUtil.upload(FileUploadUrl + GetAdd("UserNamelilei"), filePath));
        String GenerationPath = jsonObjectDa.getString("path");

        Map<String, Object> dbMapDa = new HashMap<>();
        dbMapDa.put("name", "test_"+name);//hive表名称
        dbMapDa.put("targetFormat", "parquet");//存入hive的文件格式
        dbMapDa.put("protocol", "LOCAL");//数据上传方式:LOCAL（本地），HDFS（HDFS），DATABASE(数据库)
        dbMapDa.put("uri", GenerationPath);
        dbMapDa.put("meta", JSONObject.toJSONString(list));
        dbMapDa.put("format", "csv");
        dbMapDa.put("firstLineSchema", "false");//首行是否是字段名
        dbMapDa.put("colDelimiter", "0x2C");
        dbMapDa.put("dbTable", "");
        dbMapDa.put("format", "csv");
        dbMapDa.put("columnTypeCheck", "PERMISSIVE");//列类型检查PERMISSIVE（设置为null），DROPMALFORMED（抛掉异常行），FAILFAST（引入失败）
        dbMapDa.put("rowDelimiter", "自动识别处理");
        dbMapDa.put("password", "turing123");
        dbMapDa.put("dbHost", "10.57.17.233");
        dbMapDa.put("dbName", "turing");
        dbMapDa.put("sqlStatement", "");
        dbMapDa.put("columnLengthCheck", "DROPMALFORMED");//列长度检查DROPMALFORMED（抛掉异常行），FAILFAST（引入失败）
        dbMapDa.put("encoding", "UTF-8");
        dbMapDa.put("userName", "turing");
        dbMapDa.put("dbType", "UTF-8");
        dbMapDa.put("dbPort", "3306");

        String DataJson = JSON.toJSONString(dbMapDa);
        log.info("DataJson========"+DataJson);

        String respDa = httpRequestUtil.doPost(SaveUrl, dbMapDa);
        log.info(respDa);

        //等待文件上传时间
        Thread.sleep(1000*60*2);

        Map<String, String> dbMap = new HashMap<>();
        dbMap.put("tableName", "test_"+name);

        String resp = httpRequestUtil.doGet(TablePreviewUrl, dbMap);
        System.out.println(resp);

        JSONObject jsonObject = JSON.parseObject(resp);
        String success = jsonObject.getString("success");
        JSONObject dataObject = JSON.parseObject(jsonObject.getString("data"));
        String preview = dataObject.getString("preview");

        //解析json里的previe字段
        String[] arr ={"[[","]]","\"","[","]"};
        String[] data =replaceString.StrReplace(arr,preview,",");

        //返回值校验
        Assert.assertEquals(success, "true");
        Assert.assertEquals("20000", data[26]);
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
