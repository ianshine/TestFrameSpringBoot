package com.springBoot.TestFrame.dataSource;

import com.springBoot.TestFrame.util.GetAddress;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * JdbcTemplate指定连接操作
 */
public class MachinelearningJdbcTemplate {

    GetAddress getEvnAddress = new GetAddress();
    private final String driverClassName = getEvnAddress.GetAdd("driverClassName");
    private final String machineLearningUrl = getEvnAddress.GetAdd("machineLearningUrl");
    private final String machineLearningDbUsername = getEvnAddress.GetAdd("machineLearningDbUsername");
    private final String machineLearningDbPassword = getEvnAddress.GetAdd("machineLearningDbPassword");

    public DriverManagerDataSource getMachinelearningDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(machineLearningUrl);
        dataSource.setUsername(machineLearningDbUsername);
        dataSource.setPassword(machineLearningDbPassword);
        return dataSource;
    }

    public static DataSource dataSource;

}
