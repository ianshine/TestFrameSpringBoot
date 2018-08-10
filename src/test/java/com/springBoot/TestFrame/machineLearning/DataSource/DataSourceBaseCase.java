package com.springBoot.TestFrame.machineLearning.DataSource;

import com.springBoot.TestFrame.dataSource.MachinelearningJdbcTemplate;
import com.springBoot.TestFrame.httpUtil.HttpRequestUtil;
import com.springBoot.TestFrame.util.GetAddress;

public class DataSourceBaseCase extends GetAddress {

//    GetEvnAddress getEvnAddress = new GetEvnAddress();
    HttpRequestUtil httpRequestUtil = new HttpRequestUtil();
    MachinelearningJdbcTemplate machinelearningJdbcTemplate = new MachinelearningJdbcTemplate();

}
