package com.shopkeeper.db;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-9-25
 * Time: 上午12:36
 * To change this template use File | Settings | File Templates.
 */
public interface DatabaseDriver
{
    //public DatabaseDriver getInstance();

    public void connect() throws SQLException;
    /**
     +----------------------------------------------------------
     * 释放查询结果
     +----------------------------------------------------------
     * @access public
    +----------------------------------------------------------
     */
    public void free();

    public List<Map<String, Object>> query(String str) throws SQLException;

    public boolean execute(String str) throws SQLException;

    public int executeUpdate(String str) throws SQLException;

    public List<Map<String, Object>> select(Map<String, String> options) throws SQLException;

    public int update(Map<String, Object> data, Map<String, String> options) throws SQLException;

    public boolean replace(Map<String, Object> data, Map<String, Object> options);
    public boolean replace(List<Map<String, Object>> datas, Map<String, Object> options);

    public int insert(Map<String, Object> data, Map<String, String> options, boolean replace) throws SQLException;
    public int insert(List<Map<String, Object>> datas, Map<String, String> options, boolean replace) throws SQLException;

    public boolean delete(Map<String, Object> options) throws SQLException;

    public void startTrans() throws SQLException;

    public boolean commit() throws SQLException;

    public boolean rollback() throws SQLException;

    public List<String> getTables(String dbName);

    public void close() throws SQLException;

    public String error();

    public String escapeString(String str);

    public String getLastSql();
}
