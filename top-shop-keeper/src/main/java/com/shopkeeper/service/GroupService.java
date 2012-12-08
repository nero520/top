package com.shopkeeper.service;

import com.rop.annotation.NeedInSessionType;
import com.rop.annotation.ServiceMethod;
import com.rop.annotation.ServiceMethodBean;
import com.rop.response.ErrorResponse;
import com.rop.security.SubError;
import com.rop.session.SimpleSession;
import com.shopkeeper.exception.ModelException;
import com.shopkeeper.model.GroupModel;
import com.shopkeeper.service.domain.Group;
import com.shopkeeper.service.request.*;
import com.shopkeeper.service.response.*;
import org.apache.commons.lang.StringUtils;

import java.util.LinkedList;
import java.util.List;

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
        GroupModel groupModel = getGroupModel();

        try {
            Group group = groupModel.getGroup(request.geId(), (Long)session.getAttribute("user_id"));
            if (group != null) {
                GroupGetResponse response = new GroupGetResponse();
                response.setGroup(group);
                return response;
            }
            else {
                ErrorResponse response = new ErrorResponse();
                response.setCode("103");
                response.setMessage("group id doesn't exist");
                return response;
            }
        } catch (ModelException e) {
            // TODO return Error response
        }
        return null;
    }

    @ServiceMethod(method = "groups.get", version = "1.0", needInSession = NeedInSessionType.YES)
    public Object getGroups(GroupsGetRequest request) {
        SimpleSession session = (SimpleSession)request.getRopRequestContext().getSession();
        GroupModel groupModel = new GroupModel();
        try {
            String[] groupIds = StringUtils.split(request.getIds(), ",");
            List<Group> groups = groupModel.getGroups(groupIds, (Long)session.getAttribute("user_id"));
            if (groups != null) {
                GroupsGetResponse response = new GroupsGetResponse();
                response.setGroups(groups);
                return response;
            }
            else {
                ErrorResponse response = new ErrorResponse();
                response.setCode("103");
                response.setMessage("group id doesn't exist");
                return response;
            }
        } catch (ModelException e) {
            // TODO return Error response
        }
        return null;
    }

    @ServiceMethod(method = "group.add", version = "1.0", needInSession = NeedInSessionType.YES)
    public Object createGroup(GroupAddRequest request) {
        SimpleSession session = (SimpleSession)request.getRopRequestContext().getSession();
        GroupModel groupModel = new GroupModel();
        try {
            Group group = groupModel.createGroup((Long)session.getAttribute("user_id"), request.getName(),
                    request.getDescription(), request.getCategory());
            GroupAddResponse response = new GroupAddResponse();
            response.setGroup(group);
            return response;
        } catch (ModelException e) {
            ErrorResponse response = new ErrorResponse();
            response.setCode(e.getErrorCode());
            response.setMessage(e.getMsg());
            SubError subError = new SubError();
            subError.setCode(e.getSubCode());
            subError.setMessage(e.getSubMsg());

            List<SubError> subErrors = new LinkedList<SubError>();
            subErrors.add(subError);
            response.setSubErrors(subErrors);
            return response;
            // todo 需要优化
        }
    }

    @ServiceMethod(method = "group.delete", version = "1.0", needInSession = NeedInSessionType.YES)
    public Object deleteGroup(GroupDeleteRequest request) {
        String id = request.getId();
        String name = request.getName();
        if (id == null && name == null) {
            // todo 返回错误代码
        }
        GroupModel groupModel = new GroupModel();
        try {
            Group group = groupModel.deleteGroup(id, name);
            if (group != null) {
                GroupDeleteResponse response = new GroupDeleteResponse();
                response.setGroup(group);
                return response;
            }
            else {
                // todo 返回错误代码
            }
        } catch (ModelException e) {
            // todo 返回错误代码
        }

        return null;
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
