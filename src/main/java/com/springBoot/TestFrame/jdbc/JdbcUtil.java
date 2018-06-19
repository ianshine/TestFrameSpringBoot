package com.springBoot.TestFrame.jdbc;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.*;

/**
 * 数据库常用操作
 */

public class JdbcUtil {

    private static final Log log = LogFactory.getLog(JdbcUtil.class);

    /**
     * 保存数据
     *
     * @param jdbcTemplate         数据源
     * @param tableName            表明
     * @param columnNameValuePairs 列名和对应的value
     * @return
     */
    public static int insertData(JdbcTemplate jdbcTemplate, String tableName, Map<String, Object> columnNameValuePairs) {
        int re = 0;
        try {
            String sql = " insert into " + tableName + " (";
            Set<String> set = columnNameValuePairs.keySet();
            for (String key : set) {
                sql += (key + ",");
            }
            sql = sql.substring(0, sql.length() - 1);
            sql += " ) values ( ";
            for (String key : set) {
                sql += ("'" + columnNameValuePairs.get(key) + "',");
            }
            sql = sql.substring(0, sql.length() - 1);
            sql += " ) ";
            log.info("SQL: " + sql);
            re = jdbcTemplate.update(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return re;
    }

    /**
     * 根据数据库表字段名称和值删除
     *
     * @param jdbcTemplate
     * @param tableName
     * @param columnNameValuePairs
     * @return
     */
    public static int deleteData(JdbcTemplate jdbcTemplate, String tableName,
                                 Map<String, Object> columnNameValuePairs) {
        int result = -1;
        try {
            String sql = " DELETE FROM " + tableName + " WHERE 1=1 ";
            Set<String> set = columnNameValuePairs.keySet();
            for (String key : set) {
                sql += (" and " + key + " = ");
                sql += ("'" + columnNameValuePairs.get(key) + "'");
            }
            log.info("SQL: " + sql);
            result = jdbcTemplate.update(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 根据数据库表字段名称和值查询
     *
     * @param jdbcTemplate
     * @param tableName
     * @param columnNameValuePairs
     * @return
     */
    public static List<Map<String, Object>> queryData(
            JdbcTemplate jdbcTemplate, String tableName,
            Map<String, Object> columnNameValuePairs) {
        List<Map<String, Object>> result = null;
        try {
            String sql = " SELECT * FROM " + tableName + " WHERE 1=1 ";
            Set<String> set = columnNameValuePairs.keySet();
            for (String key : set) {
                sql += (" and " + key + " = ");
                sql += ("'" + columnNameValuePairs.get(key) + "'");
            }
            log.info("SQL: " + sql);
            result = jdbcTemplate.queryForList(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 根据主键查询表中数据
     *
     * @param jdbcTemplate
     *            包含数据源信息的JcbcTemplate
     * @param tableName
     *            表名
     * @param primaryKeyName
     *            主键字段名
     * @param primaryKeyValue
     *            主键值
     * @return
     */
    public static List<Map<String, Object>> queryData(
            JdbcTemplate jdbcTemplate, String tableName, String primaryKeyName,
            Object primaryKeyValue) {
        Map<String, Object> columnNameValuePairs = new HashMap<String, Object>();
        columnNameValuePairs.put(primaryKeyName, primaryKeyValue);
        return queryData(jdbcTemplate, tableName, columnNameValuePairs);
    }

    /**
     * 带排序查询
     * @param jdbcTemplate
     * @param tableName
     * @param columnNameValuePairs
     * @return
     */
    public static List<Map<String, Object>> queryDataWithSort(
            JdbcTemplate jdbcTemplate, String tableName,
            Map<String, Object> columnNameValuePairs, String sort) {
        List<Map<String, Object>> result = null;
        final List<String> keys = new ArrayList<String>();
        final List<Object> values = new ArrayList<Object>();
        if ((tableName != null && !tableName.equals(""))
                && (columnNameValuePairs != null && !columnNameValuePairs
                .equals(""))) {
            StringBuffer sql = new StringBuffer("SELECT * FROM " + tableName
                    + " WHERE 1=1 ");
            for (Map.Entry<String, Object> kv : columnNameValuePairs.entrySet()) {
                sql.append(" AND " + kv.getKey() + "= '" + kv.getValue() + "'");
                values.add(kv.getValue());
                keys.add(kv.getKey());
            }
            sql.append("order by 1 " + sort);
            log.info("SQL: "+sql.toString());
            result= jdbcTemplate.queryForList(sql.toString());
        }
        return result;
    }


    /**
     * 获取各种最大值
     * @param jdbcTemplate
     * @param tableName
     * @param columnName
     * @return
     */
    public static long getMaxId(JdbcTemplate jdbcTemplate, String tableName,
                                String columnName) {
        String sql = "select max(" + columnName + ") as id from " + tableName +";";
        System.out.println("sql =="+ sql);
        Object id = null;
        try {
            id = jdbcTemplate.queryForList(sql).get(0).get("id");
            if (null == id) {
                id = "1";
            } else {
                id = id.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Long.valueOf(id.toString());
    }

    /**
     * 根据条件更新数据
     * @param jdbcTemplate
     * @param tableName
     * @param columnNameValuePairs
     * @param conditions
     * @return
     */
    public static int update(JdbcTemplate jdbcTemplate, String tableName, Map<String, Object> columnNameValuePairs,
                             Map<String, Object> conditions) {
        final List<String> keys = new ArrayList<String>();
        final List<Object> values = new ArrayList<Object>();
        if ((tableName != null || !tableName.equals(""))
                && (columnNameValuePairs != null || !columnNameValuePairs
                .equals(""))) {
            StringBuffer sql = new StringBuffer("update " + tableName + " set "
            );
            int i = columnNameValuePairs.entrySet().size();
            for (Map.Entry<String, Object> kv : columnNameValuePairs.entrySet()) {
                if (i > 1) {
                    if(kv.getValue() == null) {
                        sql.append(kv.getKey() + "= " + kv.getValue() + ",");
                    } else {
                        sql.append(kv.getKey() + "= '" + kv.getValue() + "',");
                    }

                } else {
                    if(kv.getValue() == null) {
                        sql.append(kv.getKey() + "= " + kv.getValue());
                    } else {
                        sql.append(kv.getKey() + "= '" + kv.getValue() + "'");
                    }

                }
                i--;
            }
            if(conditions == null) {
                return jdbcTemplate.update(sql.toString());
            } else{
                int j = conditions.entrySet().size();
                sql.append(" where ");
                for(Map.Entry<String, Object> kv : conditions.entrySet()) {
                    if(j > 1) {
                        sql.append(kv.getKey() + "= '" + kv.getValue() + "' and ");
                    }else {
                        sql.append(kv.getKey() + "= '" + kv.getValue() + "'");
                    }
                    j--;
                }
                System.out.println(sql.toString());
                return jdbcTemplate.update(sql.toString());
            }

        }
        return 0;
    }

}
