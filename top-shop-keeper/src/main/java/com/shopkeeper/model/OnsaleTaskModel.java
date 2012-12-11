package com.shopkeeper.model;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-11-20
 * Time: 上午10:06
 */

import com.shopkeeper.service.domain.OnsaleTask;
import org.bson.types.ObjectId;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * sk_onsale_task struck:
 * Long             user_id
 * String           id  // task_id
 * String           name
 * String           description
 * List<TimeSlot>   time_slot
 *   Map    TimeSlot :
 *     String   week
 *     String   hour
 *
 */
public class OnsaleTaskModel extends AbstractModel<OnsaleTask>
{
    @Override
    public String getCollectionName() {
        return "sk_onsale_task";
    }

	@Override
	public List<OnsaleTask> delete(Map<String, Object> query) {
		List<OnsaleTask> onsaleTaskList = super.delete(query);
		if (onsaleTaskList != null && onsaleTaskList.size() > 0) {
			Map<String, Object> _query = new HashMap<String, Object>();
			List<Object> taskIdList = new LinkedList<Object>();
			for (OnsaleTask onsaleTask : onsaleTaskList) {
				taskIdList.add(new ObjectId(onsaleTask.getId()));
				_query.put("user_id", onsaleTask.getUserId());
			}
			Map<String, Object> _in = new HashMap<String, Object>();
			_in.put("$in", taskIdList);
			_query.put("_id", _in);
			ItemModel itemModel = new ItemModel();

			Map<String, Object> _update = new HashMap<String, Object>();
			Map<String, Object> _value = new HashMap<String, Object>();
			_value.put("onsale_task_id", null);
			_update.put("$set", _value);

			itemModel.update(_query, _update);
		}
		return onsaleTaskList;
	}


	/*
    public OnsaleTask getTask(String taskId, String name, Long userId) throws ModelException {
        if (taskId == null && name == null) {
            return null;
        }
        DBObject query = new BasicDBObject();
        if (taskId != null) {
            if (isDocExist(taskId, "sk_autoonsale_task")) {
                query.put("_id", new ObjectId(taskId));
            }
            else {
                throw new ModelException(); //todo
            }
        }
        if (name != null) {
            query.put("name", name);
        }
        query.put("user_id", userId);
        DBObject rsp = collection.findOne(query);
        OnsaleTask task = parse(rsp, OnsaleTask.class);
        return task;
    }

    public List<OnsaleTask> getTasks(List<String> taskIds, Long userId) throws ModelException {
        if (taskIds == null) {
            return null;
        }
        DBObject query = new BasicDBObject();
        BasicDBList idList = new BasicDBList();
        for (String taskId :taskIds) {
            if (isDocExist(taskId, "sk_autoonsale_task")) {
                idList.add(new ObjectId(taskId));
            }
        }
        query.put("_id", new BasicDBObject("$in", idList));
        query.put("user_id", userId);
        List<OnsaleTask> taskList = gets(query, null, null, OnsaleTask.class);
        return taskList;
    }

    public OnsaleTask createTask(String name, String description, List<TimeSlot> timeSlotList, Long userId) throws ModelException{
        DBObject query = new BasicDBObject();
        query.put("name", name);
        DBObject task = collection.findOne(query);
        if (task == null) {
            DBObject newTaskObj = new BasicDBObject();
            newTaskObj.put("name", name);
            newTaskObj.put("description", description);
            BasicDBList objList = new BasicDBList();
            if (timeSlotList != null) {
                for (TimeSlot timeSlot : timeSlotList) {
                    DBObject objTimeSlot = new BasicDBObject();
                    objTimeSlot.put("week", timeSlot.getWeek());
                    objTimeSlot.put("hour", timeSlot.getHour());
                    objList.add(objTimeSlot);
                }
                newTaskObj.put("time_slot", objList);
            }
            newTaskObj.put("user_id", userId);
            newTaskObj.put("created", Utils.getDate());
            newTaskObj.put("is_launch", false);
            WriteResult writeResult = collection.insert(newTaskObj);
            if (writeResult.getLastError().ok()) {
                return getTask(null, name, userId);
            }
        }
        else {
            throw new ModelException(); // todo 任务名已经存在
        }
        return null;
    }



    public OnsaleTask deleteTask(String id, String name, Long userId) throws ModelException{
        if (id == null && name == null) {
            return null;
        }
        DBObject query = new BasicDBObject();
        if (id != null) {
            if (isDocExist(id, "sk_autoonsale_task")) {
                query.put("_id", new ObjectId(id));
            }
            else {
                throw new ModelException(); //todo
            }
        }
        if (name != null) {
            query.put("name", name);
        }
        query.put("user_id", userId);
        DBObject object = collection.findAndRemove(query);
        if (object != null) {
            OnsaleTask task = parse(object, OnsaleTask.class);
            // 设置item的task_id字段为null
            DBCollection collection1 =  db.getCollection("sk_item");
            collection1.update(new BasicDBObject("task_id", task.getId()),
                    new BasicDBObject("$set", new BasicDBObject("task_id", null)),
                    false, true);
            return task;
        }
        else {
            throw new ModelException();// todo 无效id， name
        }
    }

	public void deleteTasks(Long userId, List<String> taskIds) {
		if (userId != null) {
			DBObject query = new BasicDBObject("user_id", userId);
			if (taskIds != null) {
				BasicDBList taskList = new BasicDBList();
				for (String taskId : taskIds) {
					taskList.add(taskId);
				}
				query.put("task_id", new BasicDBObject("$in", taskList));
			}
			collection.remove(query);
		}
	}

    public OnsaleTask updateTask(String taskId, String name, String description, List<TimeSlot> timeSlotList, Boolean isLaunch, Long userId) throws ModelException{
        DBObject query = new BasicDBObject();
        query.put("user_id", userId);
        query.put("_id", new ObjectId(taskId));
        DBObject task = collection.findOne(query);
        if (task != null) {
            DBObject update = new BasicDBObject();
            if (name != null) {
                update.put("name", name);
            }
            if (description != null) {
                update.put("description", description);
            }
            BasicDBList objList = new BasicDBList();
            if (timeSlotList != null) {
                for (TimeSlot timeSlot : timeSlotList) {
                    DBObject objTimeSlot = new BasicDBObject();
                    objTimeSlot.put("week", timeSlot.getWeek());
                    objTimeSlot.put("hour", timeSlot.getHour());
                    objList.add(objTimeSlot);
                }
                update.put("time_slot", objList);
            }
            if (isLaunch != null) {
                update.put("is_launch", isLaunch);
            }
            collection.findAndModify(query, null, null, false, new BasicDBObject("$set", update), true, false);
            WriteResult writeResult = collection.insert(update);
            if (writeResult.getLastError().ok()) {
                return getTask(null, name, userId);
            }
        }
        else {
            throw new ModelException(); // todo 要更新的任务不存在
        }
        return null;
    }
    */
}
