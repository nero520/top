package com.shopkeeper.model;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.shopkeeper.TopAccessor;
import com.shopkeeper.common.TopCometListener;
import com.shopkeeper.common.TopCometManager;
import com.shopkeeper.common.TradeTaskListener;
import com.shopkeeper.exception.ModelException;
import com.shopkeeper.exception.TopException;
import com.shopkeeper.service.domain.User;

import java.util.Calendar;
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

    private void userInit(Long userId) {
        try {
            // 获取用户的全部宝贝信息
            ItemModel itemModel = new ItemModel();
            itemModel.setAccessToken(accessToken);
            itemModel.updateFromTop(accessToken);


            TopAccessor topAccessor = new TopAccessor(accessToken);
            boolean permit = topAccessor.incrementCustomerPermit("notify", "trade", "all");
            if (!permit) {
                logger.error("创建用户主动通知失败 user_id:" + userId);
            }
            else {
                // 创建任务获取全部交易信息
                Date now = new Date();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(now);
                calendar.add(Calendar.DAY_OF_MONTH, -3);
                Date endTime = calendar.getTime();
                calendar.add(Calendar.MONTH, -1);
                Date startTime = calendar.getTime();
                Map<String, Object> objectMap = topAccessor.createTradeSoldGetTask(startTime, endTime);
                if (objectMap != null) {
                    DBCollection collection1 = db.getCollection("sk_trade_topats_task");
                    objectMap = (Map<String, Object>)objectMap.get("task");
                    objectMap.put("user_id", userId);
                    DBObject query = new BasicDBObject("user_id", userId);
                    DBObject update = new BasicDBObject(objectMap);
                    collection1.update(query, update, true, false);
                    TopCometManager topCometManager = TopCometManager.getInstance();
                    topCometManager.addNewStream(userId, new TradeTaskCometListener());
                }
            }

        } catch (TopException e) {

        }
    }

    public boolean login(Map<String, Object> data) throws ModelException {
        data.put("last_login", new Date().toString());

        userId = (Long)data.get("user_id");
        userNick = (String)data.get("nick");
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

        } catch (TopException e) {
            // todo
        }
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

    class TradeTaskCometListener implements TopCometListener
    {

        @Override
        public void onReceiveMsg(String message) {

        }

        @Override
        public void onException(Exception e) {

        }
    }
}
