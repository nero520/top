package com.shopkeeper.service;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.rop.annotation.NeedInSessionType;
import com.rop.annotation.ServiceMethod;
import com.rop.annotation.ServiceMethodBean;
import com.rop.response.NotExistErrorResponse;
import com.rop.session.SimpleSession;
import com.shopkeeper.model.OnsaleTaskModel;
import com.shopkeeper.service.domain.OnsaleTask;
import com.shopkeeper.service.domain.TimeSlot;
import com.shopkeeper.service.request.*;
import com.shopkeeper.service.response.*;
import org.bson.types.ObjectId;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-11-20
 * Time: 上午6:22
 */

@ServiceMethodBean(version = "1.0")
public class OnsaleTaskService
{
    @ServiceMethod(method = "onsale.task.get", version = "1.0", needInSession = NeedInSessionType.DEFAULT)
    public Object getTask(OnsaleTaskGetRequest request) {
        SimpleSession session = (SimpleSession)request.getRopRequestContext().getSession();
        Long userId = (Long)session.getAttribute("user_id");

	    String taskId = request.getId();

        OnsaleTaskModel taskModel = new OnsaleTaskModel();

	    Map<String, Object> query = new HashMap<String, Object>();
	    query.put("user_id", userId);
	    if (taskId != null) {
		    query.put("_id", new ObjectId(taskId));
	    }

	    List<OnsaleTask> onsaleTaskList = taskModel.query(query);
	    if (onsaleTaskList != null && onsaleTaskList.size() > 0) {
		    OnsaleTaskGetResponse response = new OnsaleTaskGetResponse();
		    response.setTask(onsaleTaskList.get(0));
		    return response;
	    }
	    else {
		    return new NotExistErrorResponse();
	    }
    }

    @ServiceMethod(method = "onsale.tasks.get", version = "1.0", needInSession = NeedInSessionType.DEFAULT)
    public Object getTasks(OnsaleTasksGetRequest request) {
        SimpleSession session = (SimpleSession)request.getRopRequestContext().getSession();
        Long userId = (Long)session.getAttribute("user_id");
        OnsaleTaskModel taskModel = new OnsaleTaskModel();

	    Map<String, Object> query = new HashMap<String, Object>();
	    List<String> taskIdList = request.getIds();
	    if (taskIdList != null) {
		    Map<String, Object> _in = new HashMap<String, Object>();
		    List<ObjectId> groupObjectIdList = new LinkedList<ObjectId>();
		    for (String groupId : taskIdList) {
			    try {
				    groupObjectIdList.add(new ObjectId(groupId));
			    } catch (IllegalArgumentException e) {

			    }
		    }
		    _in.put("$in", groupObjectIdList);
		    query.put("_id", _in);
	    }
	    query.put("user_id", userId);

	    List<OnsaleTask> onsaleTaskList = taskModel.query(query);
	    if (onsaleTaskList != null) {
		    OnsaleTasksGetResponse response = new OnsaleTasksGetResponse();
		    response.setTasks(onsaleTaskList);
		    return response;
	    }
	    else {
		    return new NotExistErrorResponse();
	    }
    }

    @ServiceMethod(method = "onsale.task.add", version = "1.0", needInSession = NeedInSessionType.DEFAULT)
    public Object addTask(OnsaleTaskAddRequest request) {
        SimpleSession session = (SimpleSession)request.getRopRequestContext().getSession();
        Long userId = (Long)session.getAttribute("user_id");
        OnsaleTaskModel taskModel = new OnsaleTaskModel();

		Map<String, Object> data = new HashMap<String, Object>();
	    data.put("user_id", userId);
	    data.put("name", request.getName());
	    data.put("description", request.getDescription());
	    List<TimeSlot> timeSlotList = request.getTimeSlot();
	    if (timeSlotList != null) {
		    BasicDBList timeSlotObjectList = new BasicDBList();
		    for (TimeSlot timeSlot : timeSlotList) {
			    DBObject timeSlotObject = new BasicDBObject();
			    timeSlotObject.put("week", timeSlot.getWeek());
			    timeSlotObject.put("hour", timeSlot.getHour());
			    timeSlotObjectList.add(timeSlotObject);
		    }
		    data.put("time_slot", timeSlotObjectList);
	    }

	    List<OnsaleTask> onsaleTaskList = taskModel.create(data);
	    if (onsaleTaskList != null && onsaleTaskList.size() > 0) {
		    OnsaleTaskAddResponse response = new OnsaleTaskAddResponse();
		    response.setTask(onsaleTaskList.get(0));
		    return response;
	    }
	    else {
		    return new NotExistErrorResponse();
	    }
    }

    @ServiceMethod(method = "onsale.task.update", version = "1.0", needInSession = NeedInSessionType.DEFAULT)
    public Object updateTask(OnsaleTaskUpdateRequest request) {
        SimpleSession session = (SimpleSession)request.getRopRequestContext().getSession();
        Long userId = (Long)session.getAttribute("user_id");
        OnsaleTaskModel taskModel = new OnsaleTaskModel();

	    Map<String, Object> query = new HashMap<String, Object>();
	    query.put("user_id", userId);
	    query.put("_id", new ObjectId(request.getId()));

	    Map<String, Object> update = new HashMap<String, Object>();
	    Object value = request.getName();
	    if (value != null) {
	        update.put("name", value);
	    }
	    value = request.getDescription();
	    if (value != null) {
		    update.put("description", value);
	    }
	    value = request.getLaunch();
	    if (value != null) {
		    update.put("is_launch", value);
	    }

	    List<TimeSlot> timeSlotList = request.getTimeSlot();
	    if (timeSlotList != null) {
		    BasicDBList timeSlotObjectList = new BasicDBList();
		    for (TimeSlot timeSlot : timeSlotList) {
			    DBObject timeSlotObject = new BasicDBObject();
			    timeSlotObject.put("week", timeSlot.getWeek());
			    timeSlotObject.put("hour", timeSlot.getHour());
			    timeSlotObjectList.add(timeSlotObject);
		    }
		    update.put("time_slot", timeSlotObjectList);
	    }

	    List<OnsaleTask> onsaleTaskList = taskModel.update(query, update);
		if (onsaleTaskList != null && onsaleTaskList.size() > 0) {
			OnsaleTaskUpdateResponse response = new OnsaleTaskUpdateResponse();
			response.setTask(onsaleTaskList.get(0));
			return response;
		}
		else {
			return new NotExistErrorResponse();
		}
    }

    @ServiceMethod(method = "onsale.task.delete", version = "1.0", needInSession = NeedInSessionType.DEFAULT)
    public Object deleteTask(OnsaleTaskDeleteRequest request) {
        SimpleSession session = (SimpleSession)request.getRopRequestContext().getSession();
        Long userId = (Long)session.getAttribute("user_id");
        OnsaleTaskModel taskModel = new OnsaleTaskModel();

	    Map<String, Object> query = new HashMap<String, Object>();
	    query.put("user_id", userId);
	    Object value = request.getId();
	    if (value != null) {
		    try {
			    query.put("_id", new ObjectId((String)value));
		    } catch (IllegalArgumentException e) {
			    return new NotExistErrorResponse();
		    }
	    }
		List<OnsaleTask> onsaleTaskList = taskModel.delete(query);
		if (onsaleTaskList != null && onsaleTaskList.size() > 0) {
			OnsaleTaskDeleteResponse response = new OnsaleTaskDeleteResponse();
			response.setTask(onsaleTaskList.get(0));
			return response;
		}
	    else {
			return new NotExistErrorResponse();
		}
    }
}
