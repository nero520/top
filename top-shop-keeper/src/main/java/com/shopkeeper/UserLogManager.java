package com.shopkeeper;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-11-25
 * Time: 上午7:35
 */
public class UserLogManager
{
    private UserLogManager() {}

    private static String getUserNick(Long userId) {
        DB db = MongoManager.getDB("db_top", "root", "edword");
        DBCollection collection = db.getCollection("sk_user");
        DBObject query = new BasicDBObject("user_id", userId);
        DBObject user = collection.findOne(query, new BasicDBObject("nick", true));
        if (user != null) {
            return (String)user.get("nick");
        }
        return null;
    }

    public static UserLogger getUserLogger(Long userId) {
        String userNick = getUserNick(userId);
        if (userNick != null) {
            return new UserLogger(userNick);
        }
        return null;
    }

    public static UserLogger getUserLogger(String userNick) {
        if (userNick != null) {
            return new UserLogger(userNick);
        }
        return null;
    }
}
