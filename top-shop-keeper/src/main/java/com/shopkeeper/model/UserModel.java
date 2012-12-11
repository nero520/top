package com.shopkeeper.model;

import com.mongodb.DBCursor;
import com.shopkeeper.exception.ModelException;
import com.shopkeeper.service.domain.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-11-17
 * Time: 下午4:15
 */

/*
   sk_user struct:
   Long         user_id
   String       access_token
   String       expires_in
   String       r1_expires_in
   String       r2_expires_in
   String       re_expires_in
   String       refresh_token
   Long         sub_user_id
   String       sub_user_nick
   String       nick
   String       w1_expires_in
   String       w2_expires_in
   Boolean      is_init
   Map          subscriptions
     Array
       Map  type, topic, status
 */
public class UserModel extends AbstractModel<User>
{
    public UserModel() {
    }

    public String getCollectionName() {
        return "sk_user";
    }

	private void userInit(Long userId) {
		try {
			// 获取用户的全部宝贝信息
			ItemModel itemModel = new ItemModel();
			itemModel.setUserId(userId);
			itemModel.updateFromTop(accessToken);

			// 获取用户订单信息
			TradeModel tradeModel = new TradeModel();
			tradeModel.setUserId(userId);
			tradeModel.updateFromTop(accessToken);
		} catch (ModelException e) {
			logger.info(e.getMsg());
		}
	}

	@Override
	public List<User> create(Map<String, Object> data) {
		Long userId = (Long)data.get("user_id");
		if (userId != null) {
			Map<String, Object> localData = new HashMap<String, Object>(data);
			localData.put("is_init", false);
			List<User> userList = super.create(localData);
			if (userList != null) {
				userInit(userId);
			}
			return userList;
		}
		return null;
	}

/*
    public boolean login(Map<String, Object> data) throws ModelException {
        data.put("last_login", new Date().toString());

        userId = (Long)data.get("user_id");
        accessToken = (String)data.get("access_token");
        BasicDBObject query = new BasicDBObject("user_id", data.get("user_id"));
	    BasicDBObject update = new BasicDBObject(data);

		boolean isFistLogin = false;
        if (collection.getCount(query) == 0) {
	        //update.put("first_login", true);
	        isFistLogin = true;
        }
	    collection.update(query, new BasicDBObject("$set", update), true, false);

        TopUserModel topUserModel = new TopUserModel();
        topUserModel.setAccessToken(accessToken);

	    if (isFistLogin) {
		    userInit(userId);
	    }

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

	public void deleteUser(Long userId) throws ModelException {
		DBObject query = new BasicDBObject();
		query.put("user_id", userId);
		collection.remove(query);
	}

    public void setSubscriptionPermit(Long userId, String topic, String status) {
        DBObject query = new BasicDBObject("user_id", userId);
        DBObject update = new BasicDBObject("$push", new BasicDBObject("subscriptions", new BasicDBObject(topic, status)));
        collection.update(query, update);
    }

    public boolean isSubscriptionPermit(Long userId, String topic, String status) {
        DBObject query = new BasicDBObject("user_id", userId);
        query.put("subscriptions", new BasicDBObject("$elemMatch", new BasicDBObject(topic, status)));
        DBObject subscriptions = collection.findOne(query);
        return (subscriptions != null);
    }
    */
}
