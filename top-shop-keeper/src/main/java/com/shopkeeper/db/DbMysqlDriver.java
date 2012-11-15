package com.shopkeeper.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.*;


/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-9-25
 * Time: 上午5:55
 * To change this template use File | Settings | File Templates.
 */
public class DbMysqlDriver extends AbstractDatabaseDriver
{
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected String lastSql = null;

    protected static Context ctx = null;
    protected static DataSource dataSource = null;
    private Connection conn = null;
    private PreparedStatement preparedStatement = null;
    private Statement statement = null;
    private int transTimes = 0;
    static {
        try {
            ctx = new InitialContext();
            dataSource = (DataSource)ctx.lookup("java:comp/env/jdbc/MySQL");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    public DbMysqlDriver() {

    }

    public DbMysqlDriver(boolean autoConnect) throws SQLException {
        if (autoConnect) {
            connect();
        }
    }

    @Override
    public void connect() throws SQLException {
        conn = dataSource.getConnection();
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void free() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    private Statement getStatement() throws SQLException {
        if (statement == null) {
            statement = conn.createStatement();
        }
        return statement;
    }

    private PreparedStatement getPreparedStatement(String sql) throws SQLException {
        if (preparedStatement == null) {
            preparedStatement = conn.prepareStatement(sql);
        }
        return preparedStatement;
    }

    private List<String> getFields(ResultSet resultSet) throws SQLException{
        ResultSetMetaData metaData = resultSet.getMetaData();
        int colCount = metaData.getColumnCount();
        List<String> fields = new LinkedList<String>();
        for(int i = 0; i < colCount; i++) {
            String columnLabel = metaData.getColumnLabel(i + 1);
            fields.add(columnLabel);
        }
        return fields;
    }

    private List<Map<String, Object>> parseResultSet(ResultSet rs) throws SQLException{
        ResultSetMetaData metaData = rs.getMetaData();
        int colCount = metaData.getColumnCount();
        List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();

        List<String> fields = getFields(rs);
        while (rs.next()) {
            Map<String, Object> row = new HashMap<String, Object>();
            for (int i = 0; i < colCount; i++) {
                Object obj = rs.getObject(i + 1);
                row.put(fields.get(i), obj);
            }
            datas.add(row);
        }
        return datas;
    }

    @Override
    public List<Map<String, Object>> query(String str) throws SQLException {
        Statement statement = getStatement();
        lastSql = str;
        ResultSet resultSet = statement.executeQuery(str);
        List<Map<String, Object>> result = parseResultSet(resultSet);
        resultSet.close();
        return result;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean execute(String str) throws SQLException {
        Statement statement = getStatement();
        lastSql = str;
        return statement.execute(str);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int executeUpdate(String str) throws SQLException {
        Statement statement = getStatement();
        lastSql = str;
        return statement.executeUpdate(str);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void startTrans() throws SQLException {
        Statement statement = getStatement();
        if (transTimes == 0) {
            statement.execute("START TRANSACTION");
        }
        transTimes++;
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean commit() throws SQLException {
        Statement statement = getStatement();
        if (transTimes > 0) {
            transTimes = 0;
            return  statement.execute("COMMIT");
        }
        return true;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean rollback() throws SQLException {
        Statement statement = getStatement();
        if (transTimes > 0) {
            transTimes = 0;
            return statement.execute("ROLLBACK");
        }
        return true;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<String> getTables(String dbName) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void close() throws SQLException {
        if (statement != null) {
            statement.close();
            statement = null;
        }
        if (conn != null) {
            conn.close();
            conn = null;
        }
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String error() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String escapeString(String str) {
        return str;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getLastSql() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
