package com.shopkeeper;

import com.mongodb.*;
import com.shopkeeper.utils.Utils;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-11-25
 * Time: 上午7:36
 */
public class UserLogger
{
    private String userNick;

    private static DB db = null;

    private DBCollection collection = null;

    private void init() {
        if (db == null) {
            db = MongoManager.getDB("db_top_log", "root", "edword");
        }

        collection = db.getCollection(userNick);
    }

    private void pushValue(String field, String msg) {
        DBObject error = new BasicDBObject();
        error.put("message", msg);
        error.put("created", Utils.getDate());
        DBObject query = collection.findOne();
        if (query != null) {
            collection.update(new BasicDBObject("_id", query.get("_id")), new BasicDBObject("$push", new BasicDBObject(field, error)), true, false);
        }
        else {
            BasicDBList list = new BasicDBList();
            list.add(error);
            collection.insert(new BasicDBObject(field, list));
        }
    }

    public UserLogger(String userNick) {
        this.userNick = userNick;
        init();
    }

    // 记录用户错误日志
    public void error(String msg) {
        pushValue("error", msg);
    }
    // 记录用户通用信息
    public void info(String msg) {
        pushValue("info", msg);
    }
    // 记录用户警告信息
    public void warn(String msg) {
        pushValue("warning", msg);
    }
    // 跟踪用户操作
    public void trace(String msg) {
        pushValue("trace", msg);
    }
}
