package com.shopkeeper.model;

import com.rop.client.RopUnmarshaller;
import com.rop.client.unmarshaller.JacksonJsonRopUnmarshaller;
import com.shopkeeper.common.TopCometListener;
import com.shopkeeper.common.TradeTaskDownloadListener;
import com.shopkeeper.common.TradeTaskDownloadPool;
import com.shopkeeper.exception.ModelException;
import com.taobao.api.domain.Trade;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-12-6
 * Time: 下午1:38
 */
public class TradeModel extends AbstractModel<Trade> implements TopUpdate, TopCometListener, TradeTaskDownloadListener
{

	@Override
    public String getCollectionName() {
		return "sk_trade";
    }
/*
    private TradeTask getLastTradeTask(Long userId) {
        DB db = MongoManager.getDB("db_top");
        DBCollection collection1 = db.getCollection("sk_trade_task");
        DBObject query = new BasicDBObject("user_id", userId);
        DBObject order = new BasicDBObject("create", true);
        DBObject objTask = collection1.findOne(query, null, order);
	    return parse(objTask, TradeTask.class);
    }

    private boolean incrementCustomerPermit(String accessToken, String type, String topic, String status) {
        this.setAccessToken(accessToken);
        TopAccessor topAccessor = new TopAccessor(accessToken);
        UserModel userModel = new UserModel();
	    boolean permit = userModel.isSubscriptionPermit(this.getUserId(), topic, "TradeSuccess");
        try {
	        if (!permit) {
                permit = topAccessor.incrementCustomerPermit(type, topic, status);
		        if (!permit) {
			        logger.error("创建用户主动通知失败 user_id:" + userId);
		        }
	        }
            return permit;
        } catch (TopException e) {
			// todo
	        logger.info(e.getMsg());
        }
        return false;
    }

	// sk_trade_topats_task: task_id, created, user_id, status(new, done, download)
	// start_time, end_time
	@SuppressWarnings(value = "unchecked")
	public Map createTradeTask(Date startTime, Date endTime) {
		TopAccessor topAccessor = new TopAccessor(this.getAccessToken());
		Map objectMap = null;
		try {
			objectMap = topAccessor.createTradeSoldGetTask(startTime, endTime);
			if (objectMap != null) {
				DBCollection collection1 = db.getCollection("sk_trade_topats_task");
				objectMap = (Map)objectMap.get("task");
				objectMap.put("user_id", userId);
				objectMap.put("status", "new");
				objectMap.put("start_time", startTime);
				objectMap.put("end_time", endTime);
				DBObject query = new BasicDBObject("user_id", userId);
				DBObject update = new BasicDBObject(objectMap);
				collection1.update(query, update, true, false);
			}
		} catch (TopException e) {
			logger.info(e.getMsg());
		}
		return objectMap;
	}

    @Override
    public void updateFromTop(String topAccessToken) throws ModelException {
        this.setAccessToken(topAccessToken);
	    if (incrementCustomerPermit(topAccessToken, "notify", "trade", "all")) {
		    Long userId = this.getUserId();
		    TradeTask tradeTask = getLastTradeTask(userId);
		    Date now = new Date();
		    Calendar calendar = Calendar.getInstance();

		    if (tradeTask == null) {
				// 第一次为用户创建获取订单任务
			    calendar.setTime(now);
			    calendar.add(Calendar.MONTH, -3);
			    Date endTime = calendar.getTime();
			    calendar.add(Calendar.MONTH, 3);
			    calendar.add(Calendar.DAY_OF_MONTH, -1);
			    Date startTime = calendar.getTime();
			    Map task = this.createTradeTask(startTime, endTime);
			    if (task != null) {
			        TopCometManager topCometManager = TopCometManager.getInstance();
			        topCometManager.addNewStream(userId, this);
			    }
		    }
		    else {
			    // 创建更新用户订单任务， 开始时间为上次创建的任务的结束时间
			    // todo
		    }
	    }
	    else {
		    logger.info("开通用户通知失败， 参数：notify, trade, all");
	    }
	    */

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
            //Date createdTime = tradeTask.getCreated();

        }
        else {

        }

    }
*/
	// TopCometListener
	@Override
	public void onReceiveMsg(String message) {
		RopUnmarshaller unmarshaller = new JacksonJsonRopUnmarshaller();
		Map rspObj = unmarshaller.unmarshaller(message, Map.class);
		Map notifyTopats = (Map)rspObj.get("notify_topats");
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
				pool.addTask(taskId.toString(), userId, this.getAccessToken(userId));
			}
		}
	}

	@Override
	public void onException(Exception e) {

	}

	// TradeTaskDownloadListener
	@Override
	public void receivedData(Object object) {

	}

	@Override
	public void taskFinished() {

	}

	@Override
	public void taskError(String msg) {

	}

	@Override
	public Object updateFromTop(String topAccessToken) throws ModelException {

		return null;
	}
}
