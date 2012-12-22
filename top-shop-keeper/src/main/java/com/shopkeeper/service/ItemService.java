package com.shopkeeper.service;

import com.rop.annotation.NeedInSessionType;
import com.rop.annotation.ServiceMethod;
import com.rop.annotation.ServiceMethodBean;
import com.rop.response.NotExistErrorResponse;
import com.rop.session.SimpleSession;
import com.shopkeeper.model.GroupModel;
import com.shopkeeper.model.ItemModel;
import com.shopkeeper.model.OnsaleTaskModel;
import com.shopkeeper.service.domain.Item;
import com.shopkeeper.service.request.ItemGetRequest;
import com.shopkeeper.service.request.ItemUpdateRequest;
import com.shopkeeper.service.request.ItemsGetRequest;
import com.shopkeeper.service.response.ItemGetResponse;
import com.shopkeeper.service.response.ItemUpdateResponse;
import com.shopkeeper.service.response.ItemsGetResponse;
import org.apache.commons.lang.StringUtils;
import org.bson.types.ObjectId;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-11-19
 * Time: 上午3:27
 */
@ServiceMethodBean(version = "1.0")
public class ItemService
{

    @ServiceMethod(method = "item.get", version = "1.0", needInSession = NeedInSessionType.DEFAULT)
    public Object getItem(ItemGetRequest request) {
        SimpleSession session = (SimpleSession)request.getRopRequestContext().getSession();
        Long userId = (Long)session.getAttribute("user_id");
        ItemModel itemModel = new ItemModel();

	    Map<String, Object> query = new HashMap<String, Object>();
	    query.put("num_iid", request.getNumIid());
	    query.put("user_id", userId);
	    List<Item> itemList = itemModel.query(query);
	    if (itemList != null && itemList.size() > 0) {
		    ItemGetResponse response = new ItemGetResponse();
		    response.setItem(itemList.get(0));
		    return response;
	    }
	    else {
		    return new NotExistErrorResponse();
	    }
    }

    @ServiceMethod(method = "item.update", version = "1.0", needInSession = NeedInSessionType.DEFAULT)
    public Object updateItem(ItemUpdateRequest request) {
        SimpleSession session = (SimpleSession)request.getRopRequestContext().getSession();
        Long userId = (Long)session.getAttribute("user_id");

        ItemModel itemModel = new ItemModel();
	    Map<String, Object> query = new HashMap<String, Object>();
	    query.put("user_id", userId);
	    query.put("num_iid", request.getNumIid());

	    Map<String, Object> update = new HashMap<String, Object>();
	    Object value = request.getGroupId();
	    if (value != null) {
		    GroupModel groupModel = new GroupModel();
		    Map<String, Object> groupQuery = new HashMap<String, Object>();
		    groupQuery.put("user_id", userId);
		    try {
		        groupQuery.put("_id", new ObjectId((String)value));
		    } catch (IllegalArgumentException e) {
			    return new NotExistErrorResponse();  // todo
		    }
		    long groupNum = groupModel.count(groupQuery);
		    if (groupNum > 0) {
	            update.put("group_id", value);
		    }
		    else {
			    return new NotExistErrorResponse();
		    }
	    }
	    value = request.getTaskId();
	    if (value != null) {
		    OnsaleTaskModel onsaleTaskModel = new OnsaleTaskModel();
		    Map<String, Object> onsaleTaskQuery = new HashMap<String, Object>();
		    onsaleTaskQuery.put("user_id", userId);
		    try {
			    onsaleTaskQuery.put("_id", new ObjectId((String)value));
		    } catch (IllegalArgumentException e) {
			    return new NotExistErrorResponse();  // todo
		    }
		    long groupNum = onsaleTaskModel.count(onsaleTaskQuery);
		    if (groupNum > 0) {
			    update.put("task_id", value);
		    }
		    else {
			    return new NotExistErrorResponse();  //todo
		    }
		    update.put("task_id", value);
	    }
		value = request.getTimeOnsale();
	    if (value != null) {
		    update.put("time_onsale", value);
	    }
	    value = request.getShowcaseStatus();
	    if (value != null) {
		    update.put("showcase_status", value);
	    }
	    List<Item> itemList = itemModel.update(query, update);
	    if (itemList != null && itemList.size() > 0) {
		    ItemUpdateResponse response = new ItemUpdateResponse();
		    response.setItem(itemList.get(0));
		    return response;
	    }
	    else {
		    return new NotExistErrorResponse();
	    }
    }

    @ServiceMethod(method = "items.get", version = "1.0", needInSession = NeedInSessionType.DEFAULT)
    public Object getItems(ItemsGetRequest request) {
        SimpleSession session = (SimpleSession)request.getRopRequestContext().getSession();
        Long userId = (Long)session.getAttribute("user_id");
        ItemModel itemModel = new ItemModel();

	    Map<String, Object> query = new HashMap<String, Object>();
	    query.put("user_id", userId);
	    Object value = request.getGroupId();
	    if (value != null) {
		    query.put("group_id", value);
	    }
	    value = request.getTaskId();
	    if (value != null) {
		    query.put("task_id", value);
	    }
	    value = request.getShowcaseStatus();
	    if (value != null) {
		    query.put("showcase_status", value);
	    }
	    value = request.isHasShowcase();
	    if (value != null) {
		    query.put("has_showcase", value);
	    }

	    List<String> numIids = request.getNumIids();
	    if (numIids != null) {
		    Map<String, Object> _in = new HashMap<String, Object>();
	        List<Long> itemObjectIdList = new LinkedList<Long>();
		    for (String numIid : numIids) {
			    try {
				    itemObjectIdList.add(Long.parseLong(numIid));
			    } catch (IllegalArgumentException e) {

			    }
		    }
		    _in.put("$in", itemObjectIdList);
		    query.put("num_iid", _in);
	    }

	    List<Item> itemList = itemModel.query(query);
	    if (itemList != null && itemList.size() > 0) {
		    ItemsGetResponse response = new ItemsGetResponse();
		    response.setItems(itemList);
		    return response;
	    }
	    else {
		    return new NotExistErrorResponse();
	    }
    }
}
