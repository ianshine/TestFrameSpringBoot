package com.springBoot.TestFrame.dataSource;

import com.springBoot.TestFrame.util.GetEvnAddress;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.List;

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

    GetEvnAddress getEvnAddress = new GetEvnAddress();
    private final String driverClassName = getEvnAddress.getDriverClassName();
    private final String machineLearningUrl = getEvnAddress.getMachineLearningUrl();
    private final String machineLearningDbUsername = getEvnAddress.getMachineLearningDbUsername();
    private final String machineLearningDbPassword = getEvnAddress.getMachineLearningDbPassword();

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

        List rows = template.queryForList("select * from t_data  where 1=1");
//        List rows = template.queryForList("SELECT * FROM t_data WHERE 1=1  and name = 'BankingData111'");


        for (int i = 0; i < rows.size(); i++) {
            System.out.println("===============================");
            System.out.println(rows.get(i));  //.get(index)
        }

    }


}
