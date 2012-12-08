package com.shopkeeper;

import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.MongoOptions;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-11-17
 * Time: 下午6:10
 */
public class MongoManager
{
    private static Mongo mongo = null;

    private static Map<String, DB> dbMap = new HashMap<String, DB>();

    private MongoManager() {}

    public static DB getDB(String name, String user, String password) {
        if (mongo == null) {
            init();
        }
        DB db = dbMap.get(name);
        if (db == null) {
            db = mongo.getDB(name);
            if (db == null) {
                // todo 处理数据库连接失败
            }
	        else {
	            try {
                    db.authenticate(user, password.toCharArray());
	            } catch (MongoException e) {
					throw e;
	            }
            }
            dbMap.put(name, db);
        }
        return db;
    }

    public static DB getDB(String name) {
        return dbMap.get(name);
    }

    private static void init() {
        try {
            mongo = new Mongo("localhost");
            MongoOptions opt = mongo.getMongoOptions();
            opt.connectionsPerHost = 200;
            opt.threadsAllowedToBlockForConnectionMultiplier = 1000;
        } catch (UnknownHostException e) {
            // log error
        }
    }
}
