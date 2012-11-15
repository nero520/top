package com.shopkeeper.service;

import com.rop.RopRequest;
import com.rop.annotation.NeedInSessionType;
import com.rop.annotation.ServiceMethod;
import com.rop.annotation.ServiceMethodBean;
import com.shopkeeper.TopAccessor;
import com.shopkeeper.db.DatabaseDriver;
import com.shopkeeper.db.DatabaseDriverFactory;
import com.shopkeeper.db.DbMysqlDriver;
import com.shopkeeper.service.request.TestRequest;
import com.shopkeeper.service.response.TestResponse;
import com.taobao.api.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 9/22/12
 * Time: 11:44 下午
 * To change this template use File | Settings | File Templates.
 */

@ServiceMethodBean(version = "1.0", needInSession = NeedInSessionType.NO)
public class TestService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

    @ServiceMethod(method = "test.Mysql", version = "1.0")
    public Object TestMethod1(TestRequest request) {
	    try {
            String userId = request.getUserId();
            DbMysqlDriver mysql = new DbMysqlDriver();
            mysql.connect();
            mysql.query("select * from sk_user");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        TestResponse response = new TestResponse();
	    response.setResult("test result");
	    return response;
    }

    @ServiceMethod(method = "test.mysql.query", version = "1.0")
    public Object TestDBDriver(RopRequest request) {
        try {
            DatabaseDriver driver = DatabaseDriverFactory.getInstance().defaultDriver();
            List<Map<String, Object>> result = driver.query("SELECT * FROM db_top.sk_user");
            logger.debug(result.toString());
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        TestResponse response = new TestResponse();
        return response;
    }

    @ServiceMethod(method = "test.mysql.select", version = "1.0")
    public Object TestDBDriver1(RopRequest request) {
        TestResponse response = new TestResponse();
        try {
            DatabaseDriver driver = DatabaseDriverFactory.getInstance().defaultDriver();
            Map<String, String> options = new HashMap<String, String>();
            options.put("table", "sk_user");
            //options.put("where", "user_id=123");
            options.put("field", "user_id, nick");
            options.put("limit", "10");
            options.put("order", "user_id");
            List<Map<String, Object>> result = driver.select(options);
            response.setResult(result.toString());
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return response;
    }

    @ServiceMethod(method = "test.mysql.insert", version = "1.0")
    public Object TestDBDriver2(RopRequest request) {
        TestResponse response = new TestResponse();
        try {
            DatabaseDriver driver = DatabaseDriverFactory.getInstance().defaultDriver();
            Map<String, String> options = new HashMap<String, String>();
            options.put("table", "sk_user");

            Map<String, Object> data = new HashMap<String, Object>();
            data.put("user_id", "127");
            data.put("nick", "eeeeee");
            List<Map<String, Object>> datas = new LinkedList<Map<String, Object>>();
            datas.add(data);

            data = new HashMap<String, Object>();
            data.put("user_id", "128");
            data.put("nick", "fffff");
            //data.put("last_login", new java.util.Date());
            datas.add(data);


            int result = driver.insert(datas, options, true);
            response.setResult("ddddddd");
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return response;
    }

    @ServiceMethod(method = "test.mysql.update", version = "1.0")
    public Object TestDBDriver3(RopRequest request) {
        TestResponse response = new TestResponse();
        try {
            DatabaseDriver driver = DatabaseDriverFactory.getInstance().defaultDriver();
            Map<String, String> options = new HashMap<String, String>();
            options.put("table", "sk_user");
            options.put("where", "user_id=128");

            Map<String, Object> data = new HashMap<String, Object>();
            data.put("user_id", "127");
            data.put("nick", "u1");
            List<Map<String, Object>> datas = new LinkedList<Map<String, Object>>();
            datas.add(data);

            data = new HashMap<String, Object>();
            data.put("user_id", "128");
            data.put("nick", "u2");
            //data.put("last_login", new java.util.Date());
            datas.add(data);


            int result = driver.update(data, options);
            response.setResult("ddddddd");
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return response;
    }

    @ServiceMethod(method = "test.top.accessor", version = "1.0")
    public Object TestTopAccessor(RopRequest request) {
        TestResponse response = new TestResponse();
        TopAccessor accessor = new TopAccessor("6100115e22d3d458067841810b9245c974dbaad119d8c6a2074082786");
        Map<String, Object> ret = null;
        try {
            ret = accessor.getInventoryItems();
            ret = accessor.getOnsaleItems();
            ret = accessor.getOnShowcaseItems();
            ret = accessor.getSellerCatsList("sandbox_c_1");
            ret = accessor.getShopRemainShowcase();
            ret = accessor.getUserInfo();
        } catch (ApiException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        response.setResult(ret.toString());
        return response;
    }
}
