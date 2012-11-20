package com.shopkeeper.model;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.rop.client.RopUnmarshaller;
import com.rop.client.unmarshaller.JacksonJsonRopUnmarshaller;
import com.shopkeeper.exception.ModelException;
import com.shopkeeper.service.domain.User;
import com.shopkeeper.utils.Utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-11-17
 * Time: 下午4:15
 * To change this template use File | Settings | File Templates.
 */
public class UserModel extends AbstractModel
{
    private static String COLLECTION_NAME = "sk_user";

    private static String forbiddenFields = "_id, access_token";

    private Long userId;

    private String userNick;

    private String accessToken;

    public UserModel() {
    }

    public String getCollectionName() {
        return COLLECTION_NAME;
    }

    @Override
    public void updateFromTop() throws ModelException {
    }

    public boolean login(Map<String, Object> data) throws ModelException {
        data.put("last_login", new Date().toString());

        userId = (Long)data.get("user_id");
        userNick = (String)data.get("nick");
        accessToken = (String)data.get("access_token");

        BasicDBObject update = new BasicDBObject(data);
        BasicDBObject query = new BasicDBObject("user_id", data.get("user_id"));
        collection.update(query, new BasicDBObject("$set", update), true, false);

        TopUserModel topUserModel = new TopUserModel();
        topUserModel.setAccessToken(accessToken);
        topUserModel.updateFromTop();
        return true;
    }

    public User getUser(String fields, Long userId) throws ModelException {
        Map<String, Object> query = new HashMap<String, Object>();
        query.put("user_id", userId);
        User user = get(query, fields, forbiddenFields, User.class);
        return user;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserNick() {
        return userNick;
    }

    public void setUserNick(String userNick) {
        this.userNick = userNick;
    }


    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
