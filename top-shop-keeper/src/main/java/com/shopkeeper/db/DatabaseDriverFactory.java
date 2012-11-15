package com.shopkeeper.db;

import java.sql.SQLException;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-10-12
 * Time: 上午1:05
 * To change this template use File | Settings | File Templates.
 */
public class DatabaseDriverFactory {
    private static DatabaseDriverFactory ourInstance = new DatabaseDriverFactory();

    public static DatabaseDriverFactory getInstance() {
        return ourInstance;
    }

    private DatabaseDriverFactory() {
    }

    public DatabaseDriver defaultDriver() throws SQLException {
        return new DbMysqlDriver(true);
    }
}
