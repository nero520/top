package com.shopkeeper.common;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.rop.client.RopUnmarshaller;
import com.rop.client.unmarshaller.JacksonJsonRopUnmarshaller;
import com.shopkeeper.Config;
import com.shopkeeper.MongoManager;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-12-6
 * Time: 下午12:06
 * To change this template use File | Settings | File Templates.
 */
public class TradeTaskCometListener implements TopCometListener
{
    private String getAccessToken(Long userId) {
        DB db = MongoManager.getDB("db_top", Config.MONGODB_USER, Config.MONGODB_PASSWORD);
        DBCollection collection = db.getCollection("sk_user");
        DBObject object = collection.findOne(new BasicDBObject("user_id", userId), new BasicDBObject("access_token", true));
        String accessToken = null;
        if (object != null) {
            accessToken = (String)object.get("access_token");
        }
        return accessToken;
    }
    @Override
    public void onReceiveMsg(String message) {
        RopUnmarshaller unmarshaller = new JacksonJsonRopUnmarshaller();
        Map<String, Object> rspObj = unmarshaller.unmarshaller(message, Map.class);
        Map<String, Object> notifyTopats = (Map<String, Object>)rspObj.get("notify_topats");
        if (notifyTopats != null) {
            String topic = (String)notifyTopats.get("topic");
            String apiName = (String)notifyTopats.get("api_name");
            String status = (String)notifyTopats.get("status");
            Long taskId = (Long)notifyTopats.get("task_id");
            String taskStatus = (String)notifyTopats.get("task_status");
            String strUserId = (String)notifyTopats.get("user_id");
            Long userId = Long.parseLong(strUserId);
            if (topic != null && topic.equalsIgnoreCase("topats") &&
                    apiName != null && apiName.equalsIgnoreCase("taobao.topats.trades.sold.get") &&
                    status != null && status.equalsIgnoreCase("TaskComplete") &&
                    taskStatus != null && taskStatus.equalsIgnoreCase("done")) {
                TradeTaskDownloadPool pool = TradeTaskDownloadPool.getInstance();
                pool.addTask(taskId.toString(), this.getAccessToken(userId));
            }
        }
    }

    @Override
    public void onException(Exception e) {

    }

    class MyTradeTaskDownloadListener implements TradeTaskDownloadListener
    {

        @Override
        public void receivedData(Object object) {

        }

        @Override
        public void taskFinished() {

        }

        @Override
        public void taskError(String msg) {

        }
    }
}