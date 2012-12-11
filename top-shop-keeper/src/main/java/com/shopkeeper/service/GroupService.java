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
    private GroupModel _groupModel = null;

    private GroupModel getGroupModel() {
        if (_groupModel == null) {
            _groupModel = new GroupModel();
        }
        return _groupModel;
    }

    @ServiceMethod(method = "group.get", version = "1.0", needInSession = NeedInSessionType.YES)
    public Object getGroup(GroupGetRequest request) {
        SimpleSession session = (SimpleSession)request.getRopRequestContext().getSession();
	    Long userId = (Long)session.getAttribute("user_id");
        GroupModel groupModel = getGroupModel();

	    Map<String, Object> query = new HashMap<String, Object>();
	    query.put("user_id", userId);
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
    }

    @ServiceMethod(method = "groups.get", version = "1.0", needInSession = NeedInSessionType.YES)
    public Object getGroups(GroupsGetRequest request) {
        SimpleSession session = (SimpleSession)request.getRopRequestContext().getSession();
	    Long userId = (Long)session.getAttribute("user_id");
        GroupModel groupModel = new GroupModel();

	    Map<String, Object> query = new HashMap<String, Object>();
	    if (request.getIds() != null) {
		    Map<String, Object> _in = new HashMap<String, Object>();
		    String[] groupIds = StringUtils.split(request.getIds(), ",");
		    List<ObjectId> groupObjectIdList = new LinkedList<ObjectId>();
		    for (String groupId : groupIds) {
			    groupObjectIdList.add(new ObjectId(groupId));
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

    @ServiceMethod(method = "group.add", version = "1.0", needInSession = NeedInSessionType.YES)
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

    @ServiceMethod(method = "group.delete", version = "1.0", needInSession = NeedInSessionType.YES)
    public Object deleteGroup(GroupDeleteRequest request) {
	    SimpleSession session = (SimpleSession)request.getRopRequestContext().getSession();
	    Long userId = (Long)session.getAttribute("user_id");

        String id = request.getId();
        String name = request.getName();
        if (id == null && name == null) {
            // todo 返回错误代码
        }
        GroupModel groupModel = new GroupModel();

	    Map<String, Object> query = new HashMap<String, Object>();
	    query.put("user_id", userId);
	    if (id != null) {
		    query.put("_id", new ObjectId(request.getId()));
	    }
	    if (name != null) {
		    query.put("name", request.getName());
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

    @ServiceMethod(method = "groups.import", version = "1.0", needInSession = NeedInSessionType.YES)
    public Object importGroups(GroupsImportRequest request) {
        SimpleSession session = (SimpleSession)request.getRopRequestContext().getSession();
        String accessToken = (String)session.getAttribute("access_token");
        String userNick = (String)session.getAttribute("user_nick");
        Long userId = (Long)session.getAttribute("user_id");
        String from = request.getFrom();
        if (from == null) {
            from = GroupModel.GROUP_CATEGORY_SHOPCATS;
        }
        GroupModel groupModel = getGroupModel();
        try {
            List<Group> groups = groupModel.importGroup(from, userId, userNick, accessToken);
            GroupsImportResponse response = new GroupsImportResponse();
            response.setGroups(groups);
            return response;
        } catch (ModelException e) {
            // todo
        }
        return null;
    }
}
