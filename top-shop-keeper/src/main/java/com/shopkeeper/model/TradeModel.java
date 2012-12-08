package com.shopkeeper.model;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.shopkeeper.MongoManager;
import com.shopkeeper.TopAccessor;
import com.shopkeeper.common.TopCometManager;
import com.shopkeeper.common.TradeTaskCometListener;
import com.shopkeeper.exception.ModelException;
import com.shopkeeper.exception.TopException;
import com.shopkeeper.service.domain.TradeTask;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-12-6
 * Time: 下午1:38
 */
public class TradeModel extends AbstractModel implements TopUpdate
{
    private static String COLLECTION_NAME = "sk_autoonsale_task";

    @Override
    public String getCollectionName() {
        return COLLECTION_NAME;
    }

    private TradeTask getLastTradeTask(Long userId) {
        DB db = MongoManager.getDB("db_top");
        DBCollection collection1 = db.getCollection("sk_trade_task");
        DBObject query = new BasicDBObject("user_id", userId);
        DBObject order = new BasicDBObject("create", true);
        DBObject objTask = collection1.findOne(query, null, order);
        TradeTask tradeTask = parse(objTask, TradeTask.class);
        return tradeTask;
    }

    private boolean incrementCustomerPermit(String accessToken, String type, String topics, String status) {
        this.setAccessToken(accessToken);
        TopAccessor topAccessor = new TopAccessor(accessToken);
        UserModel userModel = new UserModel();
        //userModel.getSubscriptionPermit(this.getUserId(), "trade");
        boolean permit = false;
        try {
            permit = topAccessor.incrementCustomerPermit("notify", "trade", "all");
            if (!permit) {
                logger.error("创建用户主动通知失败 user_id:" + userId);
            }
            return true;
        } catch (TopException e) {

        }
        return false;
    }

    @Override
    public void updateFromTop(String topAccessToken) throws ModelException {
        this.setAccessToken(topAccessToken);
        Long userId = this.getUserId();
        TradeTask tradeTask = getLastTradeTask(userId);
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();

/*
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
        if (tradeTask != null) {
            //Date createdTime = tradeTask.getCreate();

        }
        else {

        }
        */
    }
}
