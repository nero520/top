package com.sktest;

//import org.junit.*;

import com.shopkeeper.db.DatabaseDriver;
import com.shopkeeper.db.DatabaseDriverFactory;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-10-12
 * Time: 下午5:50
 * To change this template use File | Settings | File Templates.
 */
public class DbMysqlDriverTest {
    @Test
    public void testFree() throws Exception {

    }

    @Test
    public void testQuery() throws Exception {
        DatabaseDriver dirver = DatabaseDriverFactory.getInstance().defaultDriver();
        List<Map<String, Object>> result = dirver.query("SELECT * FROM db_top.sk_user");
        System.out.print(result);
    }

    @Test
    public void testExecute() throws Exception {

    }

    @Test
    public void testExecuteUpdate() throws Exception {

    }

    @Test
    public void testStartTrans() throws Exception {

    }

    @Test
    public void testCommit() throws Exception {

    }

    @Test
    public void testRollback() throws Exception {

    }

    @Test
    public void testClose() throws Exception {

    }

    @Test
    public void testSelect() throws Exception {

    }

    @Test
    public void testUpdate() throws Exception {

    }

    @Test
    public void testInsert() throws Exception {

    }

    @Test
    public void testDelete() throws Exception {

    }
}
