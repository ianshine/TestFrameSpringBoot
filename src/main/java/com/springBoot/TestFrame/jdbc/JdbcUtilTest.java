package com.springBoot.TestFrame.jdbc;

import com.springBoot.TestFrame.dataSource.InsertRecordInDatabaseWithJdbcTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class JdbcUtilTest extends InsertRecordInDatabaseWithJdbcTemplate{


    @Test
    public void JdbcTest(){

        dataSource = getDataSource();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        String tablename = "t_data";
        Map<String,Object> value = new HashMap<String, Object>();
        value.put("name","BankingData111");

        List<Map<String, Object>> result = JdbcUtil.queryData(jdbcTemplate,tablename,value);

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
