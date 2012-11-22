package com.shopkeeper.model;

import com.mongodb.*;
import com.shopkeeper.exception.ModelException;
import com.shopkeeper.service.domain.AutoOnsaleTask;
import com.shopkeeper.service.domain.Group;
import com.shopkeeper.service.domain.TimeSlot;
import com.shopkeeper.utils.Utils;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-11-20
 * Time: 上午10:06
 * To change this template use File | Settings | File Templates.
 */
public class AutoOnsaleTaskModel extends AbstractModel
{
    private static String COLLECTION_NAME = "sk_autoonsale_task";

    @Override
    public void updateFromTop() throws ModelException {

    }

    @Override
    public String getCollectionName() {
        return COLLECTION_NAME;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public AutoOnsaleTask getTask(String taskId, String name, Long userId) throws ModelException {
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
        AutoOnsaleTask task = parse(rsp, AutoOnsaleTask.class);
        return task;
    }

    public List<AutoOnsaleTask> getTasks(List<String> taskIds, Long userId) throws ModelException {
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
        List<AutoOnsaleTask> taskList = gets(query, null, null, AutoOnsaleTask.class);
        return taskList;
    }

    public AutoOnsaleTask createTask(String name, String description, List<TimeSlot> timeSlotList, Long userId) throws ModelException{
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

    public AutoOnsaleTask deleteTask(String id, String name, Long userId) throws ModelException{
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
            AutoOnsaleTask task = parse(object, AutoOnsaleTask.class);
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

    public AutoOnsaleTask updateTask(String taskId, String name, String description, List<TimeSlot> timeSlotList, Boolean isLaunch, Long userId) throws ModelException{
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
}
