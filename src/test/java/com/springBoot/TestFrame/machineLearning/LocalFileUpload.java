package com.springBoot.TestFrame.machineLearning;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.springBoot.TestFrame.util.ReplaceString;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
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
     * 链接失败
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

        ReplaceString replaceString = new ReplaceString();
        String dbPreviewUrl = "http://10.58.10.48:8080/data/manage/dbPreview?loginName=lei.li";

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
            resp = httpRequestUtil.doGet(dbPreviewUrl, dbMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        System.out.println(resp);

        JSONObject jsonObject = JSON.parseObject(resp);
        String msg = jsonObject.getString("msg");
        int status = Integer.parseInt(jsonObject.getString("status"));

        String preview = jsonObject.getString("preview");
        System.out.println(preview);

        String[] arr ={"[[","]]","\"",};
        String[] data =replaceString.StrReplace(arr,preview,",");

        //返回值校验
        Assert.assertEquals(msg, "预览数据查询成功");
        Assert.assertEquals(status, 200);
        Assert.assertEquals("data_test3", data[1]);

    }

    /**
     * 数据预览
     */
    @Test
    public void PreviewTest() {
        ReplaceString replaceString = new ReplaceString();

        String PreviewUrl = "http://10.58.10.48:8080/data/manage/preview?loginName=lei.li";

        Map<String, String> dbMap = new HashMap<>();
        dbMap.put("colDelimiter", "0x2C");
        dbMap.put("encoding", "UTF-8");
        dbMap.put("firstLineSchema", "true");
        dbMap.put("format", "csv");
        dbMap.put("lineNumbers", "1");
        dbMap.put("path", "hdfs://tdhdfs/user/turing/data/lei.li/data_temp/u-965d45e9-dfc1-4b01-8e69-cd896fd6f72f-creditCARD_Mac.csv");//上传文件HDFS缓存路径，即上传文件的时候返回值path
        dbMap.put("protocol", "LOCAL");//上传方式，LOCAL（本地），HDFS（HDFS）,DATABASE(数据库)
        dbMap.put("randomPreview", "false");
        dbMap.put("rowDelimiter", "自动识别处理");


        String resp = null;
        try {
            resp = httpRequestUtil.doGet(PreviewUrl, dbMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(resp);

        JSONObject jsonObject = JSON.parseObject(resp);
        int status = Integer.parseInt(jsonObject.getString("status"));

        String preview = jsonObject.getString("preview");
        System.out.println(preview);

        String[] arr ={"[[","]]","\"",};
        String[] data =replaceString.StrReplace(arr,preview,",");

        //返回值校验
        Assert.assertEquals(status, 200);
        Assert.assertEquals("20000", data[1]);



    }

    /**
     * 数据保存
     */
    @Test
    public void DataSaveTest() {
        //配置文件地址
        String FileUploadUrl = GetAdd("LocalFileUpload");

        String SaveUrl = "http://10.58.10.48:8080/data/manage/save?loginName=lei.li";
        String meta = "[{'name':'id','type':'bigint'},{'name':'limit_bal','type':'bigint'},{'name':'sex','type':'bigint'},{'name':'education','type':'bigint'},{'name':'marriage','type':'bigint'},{'name':'age','type':'bigint'},{'name':'pay_0','type':'bigint'},{'name':'pay_2','type':'bigint'},{'name':'pay_3','type':'bigint'},{'name':'pay_4','type':'bigint'},{'name':'pay_5','type':'bigint'},{'name':'pay_6','type':'bigint'},{'name':'bill_amt1','type':'bigint'},{'name':'bill_amt2','type':'bigint'},{'name':'bill_amt3','type':'bigint'},{'name':'bill_amt4','type':'bigint'},{'name':'bill_amt5','type':'bigint'},{'name':'bill_amt6','type':'bigint'},{'name':'pay_amt1','type':'bigint'},{'name':'pay_amt2','type':'bigint'},{'name':'pay_amt3','type':'bigint'},{'name':'pay_amt4','type':'bigint'},{'name':'pay_amt5','type':'bigint'},{'name':'pay_amt6','type':'bigint'},{'name':'next_month','type':'bigint'}]";

        List list = JSONObject.parseObject(meta, List.class);

        Map<String, Object> dbMap = new HashMap<>();
        dbMap.put("name", "test11_1112");//hive表名称
        dbMap.put("targetFormat", "parquet");//存入hive的文件格式
        dbMap.put("protocol", "LOCAL");//数据上传方式:LOCAL（本地），HDFS（HDFS），DATABASE(数据库)
        dbMap.put("uri", FileUploadUrl);
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

//        String DataJson = JSON.toJSONString(dbMap);
//        log.info("DataJson========"+DataJson);

        String resp = null;
        try {
            resp = httpRequestUtil.doPost(SaveUrl, dbMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(resp);


    }


    /**
     * 数据列表管理
     */
    @Test
    public void TableListTets() {
        String TableListUrl = "http://10.58.10.48:8080/data/manage/tableList?loginName=lei.li";

        Map<String, String> dbMap = new HashMap<>();
        dbMap.put("tableName", "t_data");
        dbMap.put("curPage", "1");
        dbMap.put("pageSize", "10");

//      String DataJson = JSON.toJSONString(dbMap);
//      log.info(DataJson);

        String resp = null;
        try {
//            resp = httpRequestUtil.doPost(PreviewUrl, DataJson);
            resp = httpRequestUtil.doGet(TableListUrl, dbMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(resp);


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

    /**
     * table数据预览
     */
    @Test
    public void TablePreview() {
        String TableListUrl = "http://10.58.10.48:8080/data/manage/tablePreview?loginName=lei.li";

        Map<String, String> dbMap = new HashMap<>();
        dbMap.put("tableName", "t_data");

        String resp = null;
        try {
//            resp = httpRequestUtil.doPost(PreviewUrl, DataJson);
            resp = httpRequestUtil.doGet(TableListUrl, dbMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(resp);
    }

    /**
     * 上传日志查看
     */
    @Test
    public void ShowLogTest() {
        String ShowLogUrl = "http://10.58.10.48:8080/data/manage/showLog?loginName=lei.li";

        Map<String, String> dbMap = new HashMap<>();
        dbMap.put("operatorId", "8661");

//      String DataJson = JSON.toJSONString(dbMap);
//      log.info(DataJson);

        String resp = null;
        try {
//            resp = httpRequestUtil.doPost(PreviewUrl, DataJson);
            resp = httpRequestUtil.doGet(ShowLogUrl, dbMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(resp);

        JSONObject jsonObject = JSON.parseObject(resp);
        String status = jsonObject.getString("status");
        String success = jsonObject.getString("success");
        String message = jsonObject.getString("message");

        //返回值校验
        Assert.assertEquals(status, "200");
        Assert.assertEquals(success, "true");
        Assert.assertEquals(message, "opLogQuerySuccess");

    }


}
