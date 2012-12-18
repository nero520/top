package com.shopkeeper.model;

import com.shopkeeper.exception.ModelException;
import com.shopkeeper.service.domain.User;

import java.util.HashMap;
import java.util.LinkedList;
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
			String accessToken = this.getAccessToken(userId);
			TopUserModel topUserModel = new TopUserModel();
			topUserModel.updateFromTop(accessToken);

			// 获取用户的全部宝贝信息
			ItemModel itemModel = new ItemModel();
			itemModel.updateFromTop(accessToken);

			// 获取用户订单信息
			TradeModel tradeModel = new TradeModel();
			tradeModel.updateFromTop(accessToken);
		} catch (ModelException e) {
			logger.info(e.getMsg());
		}
	}

	@Override
	public List<User> create(Map<String, Object> data) {
		Map<String, Object> localData = new HashMap<String, Object>(data);
		localData.put("is_init", false);
		List<User> userList = super.create(localData);
		if (userList != null) {
			userInit(userList.get(0).getUserId());
			return userList;
		}
		return null;
	}

	@Override
	public List<User> delete(Map<String, Object> query) {
		List<User> userList = super.delete(query);
		if (userList != null && userList.size() > 0) {
			List<Long> userIdList = new LinkedList<Long>();
			for (User user : userList) {
				Long userId = user.getUserId();
				userIdList.add(userId);
			}
			Map<String, Object> _query = new HashMap<String, Object>();
			Map<String, Object> _in = new HashMap<String, Object>();
			_in.put("$in", userIdList);
			_query.put("_id", _in);

			TopUserModel topUserModel = new TopUserModel();
			topUserModel.delete(_query);

			ItemModel itemModel = new ItemModel();
			itemModel.delete(_query);

			GroupModel groupModel = new GroupModel();
			groupModel.delete(_query);

			OnsaleTaskModel onsaleTaskModel = new OnsaleTaskModel();
			onsaleTaskModel.delete(_query);

			ShowcaseSettingModel showcaseSettingModel = new ShowcaseSettingModel();
			showcaseSettingModel.delete(_query);

			TradeModel tradeModel = new TradeModel();
			tradeModel.delete(_query);
		}
		return userList;
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
