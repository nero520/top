package com.shopkeeper.service;

import com.rop.annotation.NeedInSessionType;
import com.rop.annotation.ServiceMethod;
import com.rop.annotation.ServiceMethodBean;
import com.rop.response.NotExistErrorResponse;
import com.rop.session.SimpleSession;
import com.shopkeeper.exception.ModelException;
import com.shopkeeper.model.GroupModel;
import com.shopkeeper.service.domain.Group;
import com.shopkeeper.service.request.*;
import com.shopkeeper.service.response.*;
import org.apache.commons.lang.StringUtils;
import org.bson.types.ObjectId;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-11-18
 * Time: 上午6:28
 */

@ServiceMethodBean(version = "1.0")
public class GroupService
{
    @ServiceMethod(method = "group.get", version = "1.0", needInSession = NeedInSessionType.DEFAULT)
    public Object getGroup(GroupGetRequest request) {
        SimpleSession session = (SimpleSession)request.getRopRequestContext().getSession();
	    Long userId = (Long)session.getAttribute("user_id");
        GroupModel groupModel = new GroupModel();

	    Map<String, Object> query = new HashMap<String, Object>();
	    query.put("user_id", userId);
	    try {
		    query.put("_id", new ObjectId(request.geId()));
		    List<Group> groupList = groupModel.query(query);
		    if (groupList != null && groupList.size() > 0) {
			    GroupGetResponse response = new GroupGetResponse();
			    response.setGroup(groupList.get(0));
			    return response;
		    }
		    else {
			    return new NotExistErrorResponse();
		    }
	    } catch (IllegalArgumentException e) {
		    return  new NotExistErrorResponse();
	    }
    }

    @ServiceMethod(method = "groups.get", version = "1.0", needInSession = NeedInSessionType.DEFAULT)
    public Object getGroups(GroupsGetRequest request) {
        SimpleSession session = (SimpleSession)request.getRopRequestContext().getSession();
	    Long userId = (Long)session.getAttribute("user_id");
        GroupModel groupModel = new GroupModel();

	    Map<String, Object> query = new HashMap<String, Object>();
	    String ids = request.getIds();
	    if (ids != null && ids.length() > 0) {
		    Map<String, Object> _in = new HashMap<String, Object>();
		    String[] groupIds = StringUtils.split(ids, ",");
		    List<ObjectId> groupObjectIdList = new LinkedList<ObjectId>();
		    for (String groupId : groupIds) {
			    try {
			        groupObjectIdList.add(new ObjectId(groupId));
			    } catch (IllegalArgumentException e) {

			    }
		    }
		    _in.put("$in", groupObjectIdList);
		    query.put("_id", _in);
	    }
	    query.put("user_id", userId);
	    List<Group> groups = groupModel.query(query);
	    if (groups != null) {
		    GroupsGetResponse response = new GroupsGetResponse();
		    response.setGroups(groups);
		    return response;
	    }
	    else {
		    return new NotExistErrorResponse();
	    }
    }

    @ServiceMethod(method = "group.add", version = "1.0", needInSession = NeedInSessionType.DEFAULT)
    public Object createGroup(GroupAddRequest request) {
        SimpleSession session = (SimpleSession)request.getRopRequestContext().getSession();
	    Long userId = (Long)session.getAttribute("user_id");
        GroupModel groupModel = new GroupModel();

	    Map<String, Object> query = new HashMap<String, Object>();
	    query.put("user_id", userId);
	    query.put("name", request.getName());
	    query.put("description", request.getDescription());
	    query.put("category", GroupModel.GROUP_CATEGORY_CUSTOM);
	    List<Group> groupList = groupModel.create(query);
	    if (groupList != null && groupList.size() > 0) {
		    GroupAddResponse response = new GroupAddResponse();
		    response.setGroup(groupList.get(0));
		    return response;
	    }
	    else {
		    return new NotExistErrorResponse();
	    }
    }

    @ServiceMethod(method = "group.delete", version = "1.0", needInSession = NeedInSessionType.DEFAULT)
    public Object deleteGroup(GroupDeleteRequest request) {
	    SimpleSession session = (SimpleSession)request.getRopRequestContext().getSession();
	    Long userId = (Long)session.getAttribute("user_id");

        String id = request.getId();
        GroupModel groupModel = new GroupModel();

	    Map<String, Object> query = new HashMap<String, Object>();
	    query.put("user_id", userId);
	    if (id != null) {
		    try {
			    query.put("_id", new ObjectId(request.getId()));
		    } catch (IllegalArgumentException e) {

		    }
	    }
	    List<Group> groupList = groupModel.delete(query);
	    if (groupList != null && groupList.size() > 0) {
		    GroupDeleteResponse response = new GroupDeleteResponse();
		    response.setGroup(groupList.get(0));
		    return response;
	    }
	    else {
		    return new NotExistErrorResponse();
	    }
    }

    @ServiceMethod(method = "groups.import", version = "1.0", needInSession = NeedInSessionType.DEFAULT)
    public Object importGroups(GroupsImportRequest request) {
        SimpleSession session = (SimpleSession)request.getRopRequestContext().getSession();
        Long userId = (Long)session.getAttribute("user_id");
        String from = request.getFrom();
        GroupModel groupModel = new GroupModel();
        try {
            List<Group> groups = groupModel.importGroup(from, userId);
            GroupsImportResponse response = new GroupsImportResponse();
            response.setGroups(groups);
            return response;
        } catch (ModelException e) {
            // todo
        }
        return null;
    }
}
