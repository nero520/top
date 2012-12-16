package com.shopkeeper.model;

import com.mongodb.DBCursor;
import com.shopkeeper.TopAccessor;
import com.shopkeeper.exception.ModelException;
import com.shopkeeper.exception.TopException;
import com.shopkeeper.service.domain.Group;
import com.shopkeeper.utils.Utils;
import org.bson.types.ObjectId;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-11-18
 * Time: 上午3:50
 */

/*
 * sk_group struct:
 * Long         user_id
 * String       name
 * String       description
 * Integer      item_count
 * String       category
 * Date         created
 */
public class GroupModel extends AbstractModel<Group>
{
    public static String GROUP_CATEGORY_TOPCATS = "group_topcats";
    public static String GROUP_CATEGORY_SHOPCATS = "group_shopcats";
    public static String GROUP_CATEGORY_CUSTOM = "group_custom";

    @Override
    public String getCollectionName() {
        return "sk_group";
    }

	// fix me: 如果名字一样则失败
	@Override
	public List<Group> create(Map<String, Object> data) {
		Long userId = (Long)data.get("user_id");
		if (userId != null) {
			Map<String, Object> localData = new HashMap<String, Object>(data);
			localData.put("created", Utils.getDate());
			localData.put("item_count", 0);
			ObjectId objectId = new ObjectId();
			localData.put("_id", objectId);
			if ( _create(localData)) {
				Map<String, Object> query = new HashMap<String, Object>();
				query.put("_id", objectId);
				return query(query);
			}
		}
		return null;
	}

	@Override
	public List<Group> delete(Map<String, Object> query) {
		DBCursor rsp = _query(query);
		List<Group> groupList = parse(rsp, Group.class);
		if (groupList.size() == 0) {
			return null;
		}
		if (rsp.hasNext()) {
			_delete(query);
		}

		List<String> groupIds = new LinkedList<String>();
		for (Group group : groupList) {
			groupIds.add(group.getId());
		}
		Map<String, Object> _query = new HashMap<String, Object>();
		Map<String, Object> _in = new HashMap<String, Object>();
		_in.put("$in", groupIds);
		_query.put("group_id", _in);
		_query.put("user_id", groupList.get(0).getUserId());
		Map<String, Object> _update = new HashMap<String, Object>();
		Map<String, Object> _itemGroupIdValue = new HashMap<String, Object>();
		_itemGroupIdValue.put("group_id", null);
		_update.put("$set", _itemGroupIdValue);

		ItemModel itemModel = new ItemModel();
		itemModel.update(_query, _update);

		return groupList;
	}

	/*

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

	public void deleteGroups(Long userId, List<String> groupIds) {
		if (userId != null) {
			DBObject query = new BasicDBObject("user_id", userId);
			if (groupIds != null) {
				BasicDBList groupList = new BasicDBList();
				for (String groupId : groupIds) {
					groupList.add(groupId);
				}
				query.put("_id", new BasicDBObject("$in", groupList));
			}
			collection.remove(query);
		}
	}
*/

	// TODO 判断from参数
	@SuppressWarnings(value = "unchecked")
    public List<Group> importGroup(String from, Long userId, String userNick, String accessToken) throws ModelException {
        TopAccessor topAccessor = new TopAccessor(accessToken);
        try {
            Map groups = topAccessor.getSellerCatsList(userNick);
            List<Map<String, Object>> groupList = (List<Map<String, Object>>)groups.get("seller_cat");
            List<Group> rsp = new LinkedList<Group>();
            if (groupList != null && groupList.size() > 0) {
                for (Map<String, Object> group : groupList) {
                    Map<String, Object> data = new HashMap<String, Object>();
	                data.put("user_id", userId);
	                data.put("name", group.get("name"));
	                data.put("category", GROUP_CATEGORY_SHOPCATS);
	                List<Group> tGroups = create(data);
	                if (tGroups != null && tGroups.size() > 0) {
		                Group objGroup = tGroups.get(0);
		                rsp.add(objGroup);
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
