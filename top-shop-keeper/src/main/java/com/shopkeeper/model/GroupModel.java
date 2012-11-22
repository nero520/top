package com.shopkeeper.model;

import com.mongodb.*;
import com.shopkeeper.TopAccessor;
import com.shopkeeper.exception.ModelException;
import com.shopkeeper.exception.TopException;
import com.shopkeeper.service.domain.Group;
import com.shopkeeper.utils.Utils;
import org.apache.commons.lang.StringUtils;
import org.bson.types.ObjectId;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-11-18
 * Time: 上午3:50
 * To change this template use File | Settings | File Templates.
 */
public class GroupModel extends AbstractModel
{
    private static String COLLECTION_NAME = "sk_group";

    public static String GROUP_CATEGORY_TOPCATS = "group_topcats";
    public static String GROUP_CATEGORY_SHOPCATS = "group_shopcats";
    public static String GROUP_CATEGORY_CUSTOM = "group_custom";

    @Override
    public String getCollectionName() {
        return COLLECTION_NAME;
    }

    @Override
    public void updateFromTop() throws ModelException {

    }

    public Group getGroup(String groupId, Long userId) throws ModelException{
        try {
            Map<String, Object> query = new HashMap<String, Object>();
            query.put("_id", new ObjectId(groupId));
            query.put("user_id", userId);
            Group group = get(query, null, null, Group.class);
            return group;
        } catch (IllegalArgumentException e) {
            throw new ModelException("103", "parameter error", "1", "bad group id"); //todo
        }
    }

    public List<Group> getGroups(String[] groupIds, Long userId) throws ModelException{
        try {
            DBObject query = new BasicDBObject();
            query.put("user_id", userId);

            if (groupIds != null && groupIds.length > 0) {
                BasicDBList dbList = new BasicDBList();
                for (String id : groupIds) {
                    id.trim();
                    dbList.add(new ObjectId(id));
                }
                DBObject idsObj = new BasicDBObject("$in", dbList);
                query.put("_id", idsObj);
            }
            List<Group> list = gets(query, null, null, Group.class);
            return list;
        } catch (IllegalArgumentException e) {
            throw new ModelException("103", "parameter error", "1", "bad group id"); //todo
        }
    }

    public Group createGroup(Long userId, String groupName, String description, String category) throws ModelException {
        return createGroup(userId, groupName, description, category, false);
    }

    private Group createGroup(Long userId, String groupName, String description, String category, boolean skipDumplicated) throws ModelException {
        BasicDBObject query = new BasicDBObject("name", groupName);
        DBObject o = collection.findOne(query);
        if (o == null) {
            BasicDBObject object = new BasicDBObject();
            ObjectId objectId = new ObjectId();
            object.put("user_id", userId);
            object.put("name", groupName);
            object.put("description", description);
            object.put("category", category == null ? GROUP_CATEGORY_CUSTOM : category);
            object.put("_id", objectId);
            object.put("created", Utils.getDate());

            WriteResult result = collection.insert(object, WriteConcern.SAFE);
            if (result.getLastError().ok()) {
                return getGroup(objectId.toString(), userId);
            }
            else {
                String msg = result.getLastError().getErrorMessage();
                throw new ModelException("104", "create group failed", "1", msg); // todo
            }
        }
        else if (!skipDumplicated) {
            throw new ModelException("103", "parameter error", "1", "group name duplicated"); //todo
        }
        return null;
    }

    public Group deleteGroup(String groupId, String name) throws ModelException {
        try {
            if (groupId == null && name == null) {
                return null;
            }
            BasicDBObject query = new BasicDBObject();
            if (groupId != null) {
                if (isDocExist(groupId, "sk_autoonsale_task")) {
                    query.put("_id", new ObjectId(groupId));
                }
                else {
                    throw new ModelException(); //todo
                }
            }
            if (name != null) {
                query.put("name", name);
            }
            DBObject object = collection.findAndRemove(query);
            if (object != null) {
                Group group = parse(object, Group.class);
                // 设置item的group_id字段为null
                DBCollection collection1 =  db.getCollection("sk_item");
                collection1.update(new BasicDBObject("group_id", group.getId()),
                        new BasicDBObject("$set", new BasicDBObject("group_id", null)),
                        false, true);

                return group;
            }
            else {
                throw new ModelException();// todo 无效id， name
            }

        } catch (IllegalArgumentException e) {
            throw new ModelException("103", "parameter error", "1", "bad group id"); // todo
        }
    }

    public List<Group> importGroup(String from, Long userId, String userNick, String accessToken) throws ModelException {
        TopAccessor topAccessor = new TopAccessor(accessToken);
        try {
            Map<String, Object> groups = topAccessor.getSellerCatsList(userNick);
            List<Map<String, Object>> groupList = (List<Map<String, Object>>)groups.get("seller_cat");
            List<Group> rsp = new LinkedList<Group>();
            if (groupList != null && groupList.size() > 0) {
                for (Map<String, Object> group : groupList) {
                    Group groupObj = createGroup(userId, (String)group.get("name"), "", GROUP_CATEGORY_SHOPCATS, true);
                    if (groupObj != null) {
                        rsp.add(groupObj);
                    }
                }
                return rsp;
            }
            else {
                return null;
            }
        } catch (TopException e) {
            throw new ModelException(e.getErrorCode(), e.getMsg(), e.getSubCode(), e.getSubMsg());
        }
    }
}
