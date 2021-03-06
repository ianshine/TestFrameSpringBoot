package com.springBoot.TestFrame.dataSource;

import com.springBoot.TestFrame.util.GetAddress;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * JdbcTemplate指定连接操作
 */
public class InsertRecordInDatabaseWithJdbcTemplate {

//    private static final String driverClassName = "com.mysql.jdbc.Driver";
//    private static final String url = "jdbc:mysql://10.57.22.109:3306/turing?useUnicode=true&characterEncoding=utf-8&useSSL=false";
//    private static final String dbUsername = "jie.qian";
//    private static final String dbPassword = "jie.qian";
//
//    public static DriverManagerDataSource getDataSource() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName(driverClassName);
//        dataSource.setUrl(url);
//        dataSource.setUsername(dbUsername);
//        dataSource.setPassword(dbPassword);
//        return dataSource;
//    }

    GetAddress getEvnAddress = new GetAddress();
    private final String driverClassName = getEvnAddress.GetAdd("driverClassName");
    private final String machineLearningUrl = getEvnAddress.GetAdd("machineLearningUrl");
    private final String machineLearningDbUsername = getEvnAddress.GetAdd("machineLearningDbUsername");
    private final String machineLearningDbPassword = getEvnAddress.GetAdd("machineLearningDbPassword");


//    private final String driverClassName = getEvnAddress.getDriverClassName();
//    private final String machineLearningUrl = getEvnAddress.getMachineLearningUrl();
//    private final String machineLearningDbUsername = getEvnAddress.getMachineLearningDbUsername();
//    private final String machineLearningDbPassword = getEvnAddress.getMachineLearningDbPassword();



    public DriverManagerDataSource getMachineLearningDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(machineLearningUrl);
        dataSource.setUsername(machineLearningDbUsername);
        dataSource.setPassword(machineLearningDbPassword);
        return dataSource;
    }

    public static DataSource dataSource;


    /**
     * 测试连接操作数据库
     */
    @Test
    public void test(){

        dataSource = getMachineLearningDataSource();

        JdbcTemplate template = new JdbcTemplate(dataSource);

//        List rows = template.queryForList("select * from t_data where 1=1");
////        List rows = template.queryForList("SELECT * FROM t_data WHERE 1=1  and name = 'data_test3'");
//
//        for (int i = 0; i < rows.size(); i++) {
//            System.out.println("===============================");
//            System.out.println(rows.get(i));  //.get(index)
//        }

//        List<Map<String, Object>> result = template.queryForList("select * from t_data where 1=1");
        List<Map<String, Object>> result = template.queryForList("SELECT * FROM t_data WHERE 1=1  and name = 'data_test3'");

        for (Map<String, Object> map : result) {
            for (String s : map.keySet()) {
                System.out.print(map.get(s) + "  ");
            }
        }
        System.out.println();
        System.out.println("========================");
        for (int i = 0; i < result.size(); i++) {
            Map<String, Object> map = result.get(i);
            Iterator iterator = map.keySet().iterator();
            while (iterator.hasNext()) {
                String string = (String) iterator.next();
                System.out.println(map.get(string));
            }
        }
        System.out.println("++++++++++++++++++++++++++++");
        for (Map<String, Object> map : result) {
            for (Map.Entry<String, Object> m : map.entrySet()) {
                System.out.print(m.getKey() + "    ");
                System.out.println(m.getValue());
            }
        }
        System.out.println("-----------------------------");

    }




}
