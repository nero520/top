package com.shopkeeper.model;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.shopkeeper.TopAccessor;
import com.shopkeeper.common.TopCometManager;
import com.shopkeeper.exception.ModelException;
import com.shopkeeper.exception.TopException;
import com.shopkeeper.service.domain.User;
import com.shopkeeper.common.TradeTaskCometListener;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-11-17
 * Time: 下午4:15
 */
public class UserModel extends AbstractModel
{
    private static String COLLECTION_NAME = "sk_user";

    private static String forbiddenFields = "_id, access_token";

    private Long userId;

    private String accessToken;

    public UserModel() {
    }

    public String getCollectionName() {
        return COLLECTION_NAME;
    }

    private void userInit(Long userId) {
        try {
            // 获取用户的全部宝贝信息
            ItemModel itemModel = new ItemModel();
            itemModel.setAccessToken(accessToken);
            itemModel.updateFromTop(accessToken);
        } catch (ModelException e) {

        }
    }

    public boolean login(Map<String, Object> data) throws ModelException {
        data.put("last_login", new Date().toString());

        userId = (Long)data.get("user_id");
        accessToken = (String)data.get("access_token");
        BasicDBObject query = new BasicDBObject("user_id", data.get("user_id"));
        if (collection.getCount(query) == 0) {
            query.put("first_login", true);
            userInit(userId);
        }

        BasicDBObject update = new BasicDBObject(data);

        collection.update(query, new BasicDBObject("$set", update), true, false);

        TopUserModel topUserModel = new TopUserModel();
        topUserModel.setAccessToken(accessToken);

        try {
            topUserModel.updateFromTop(accessToken);

        } catch (ModelException e) {
            // todo
        }
        return true;
    }

    public User getUser(String fields, Long userId) throws ModelException {
        Map<String, Object> query = new HashMap<String, Object>();
        query.put("user_id", userId);
        return get(query, fields, forbiddenFields, User.class);
    }

    public void setSubscriptionPermit(Long userId, String topic, String status) {
        DBObject query = new BasicDBObject("user_id", userId);
        DBObject update = new BasicDBObject("$push", new BasicDBObject("subscriptions", new BasicDBObject(topic, status)));
        collection.update(query, update);
    }

    public boolean getSubscriptionPermit(Long userId, String topic, String status) {
        DBObject query = new BasicDBObject("user_id", userId);
        query.put("subscriptions", new BasicDBObject("$elemMatch", new BasicDBObject(topic, status)));
        DBObject subscriptions = collection.findOne(query);
        return (subscriptions != null);
    }
}
