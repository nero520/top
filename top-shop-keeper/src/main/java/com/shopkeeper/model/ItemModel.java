package com.shopkeeper.model;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.shopkeeper.TopAccessor;
import com.shopkeeper.exception.ModelException;
import com.shopkeeper.exception.TopException;
import com.shopkeeper.service.domain.Item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-11-19
 * Time: 上午2:39
 */

/*
 * Item struct
 * top data:    num_iid, ...
 * Long         user_id
 * Date         time_onsale
 */
public class ItemModel extends AbstractModel<Item> implements TopUpdate
{

    @Override
    public void updateFromTop(String topAccessToken) throws ModelException {
        TopAccessor topAccessor = new TopAccessor(topAccessToken);
	    Long userId = this.getUserId(topAccessToken);
        try {
            List<Map<String, Object>> itemList = topAccessor.getOnsaleItems();
            if (itemList.size() > 0) {
                DBObject query = new BasicDBObject();
                for (Map<String, Object> item : itemList) {
                    DBObject object = new BasicDBObject(item);
                    object.put("user_id", userId);
                    query.put("num_iid", item.get("num_iid"));
                    query.put("user_id", userId);
                    collection.update(query, new BasicDBObject("$set", object), true, false);
                }
            }
        } catch (TopException e) {
	        // todo
        }
    }

    @Override
    public String getCollectionName() {
        return "sk_item";
    }

	@Override
	public List<Item> create(Map<String, Object> data) {
		if (_create(data) > 0) {
			Map<String, Object> _query = new HashMap<String, Object>();
			_query.put("user_id", data.get("user_id"));
			_query.put("num_iid", data.get("num_iid"));
			return query(_query);
		}
		return null;
	}

	/*
	public Item getItem(Long numIid, String fields, Long userId) throws ModelException {
        if (numIid != null && userId != null) {
            Map<String, Object> query = new HashMap<String, Object>();
            query.put("user_id", userId);
            query.put("num_iid", numIid);
            Item item = get(query, fields, forbiddenFields, Item.class);
            return item;
        }
        return null;
    }

    public List<Item> getItems(String numIids, String fields, String groupId, String taskId, String autoShowcaseStatus, Long userId) throws ModelException {
        DBObject query = new BasicDBObject();
        // 解析numIids
        if (numIids != null) {
            String[] numIidList = StringUtils.split(numIids, ",");
            if (numIidList.length > 0) {
                BasicDBList numIidCondition = new BasicDBList();
                for (String numIid : numIidList) {
                    numIid = numIid.trim();
                    Long numIidNo = Long.parseLong(numIid);
                    numIidCondition.add(numIidNo);
                }
                query.put("num_iid", new BasicDBObject("$in", numIidCondition));
            }
        }

        if (groupId != null) {
            if (isDocExist(groupId, "sk_group")) {
                query.put("group_id", groupId);
            }
            else {
                throw new ModelException(); //todo group_id 不存在
            }
        }
        if (taskId != null) {
            if (isDocExist(groupId, "sk_task")) {
                query.put("task_id", groupId);
            }
            else {
                throw new ModelException(); //todo task_id 不存在
            }
        }
        if (autoShowcaseStatus != null) {
            query.put("auto_showcase_status", autoShowcaseStatus);
        }
        query.put("user_id", userId);
        List<Item> itemList = gets(query, fields, forbiddenFields, Item.class);
        return itemList;
    }

    public Item updateItem(Long numIid, String groupId, String taskId, String timeOnsale, String autoShowcaseStatus, Long userId) throws ModelException {
        DBObject query = new BasicDBObject("num_iid", numIid);
        DBObject updates = new BasicDBObject();
        if (groupId != null) {
            if (isDocExist(groupId, "sk_group")) {
                updates.put("group_id", groupId);
            }
            else {
                throw new ModelException(); //todo group_id 不存在
            }
        }
        if (taskId != null) {
            if (isDocExist(groupId, "sk_task")) {
                updates.put("task_id", groupId);
            }
            else {
                throw new ModelException(); //todo task_id 不存在
            }
        }
        if (timeOnsale != null) {
            updates.put("time_onsale", timeOnsale);
        }
        if (autoShowcaseStatus != null) {
            updates.put("auto_showcase_status", autoShowcaseStatus);
        }
        query.put("user_id", userId);
        DBObject update = new BasicDBObject("$set", updates);
        DBObject object = collection.findAndModify(query, new BasicDBObject("_id", false), null, false, update, true, false);
        Item item = parse(object, Item.class);
        return item;
    }

	public void deleteItems(Long userId, List<Long> itemsId) {
		if (userId != null) {
			DBObject query = new BasicDBObject("user_id", userId);
			if (itemsId != null && itemsId.size() > 0) {
				BasicDBList itemList = new BasicDBList();
				for (Long itemId : itemsId) {
					itemList.add(itemId);
				}
				query.put("num_iid", new BasicDBObject("$in", itemList));
			}
			collection.remove(query);
		}
	}
*/
/*
    public Item updateItem(Long numIid, Map<String, Object> updates, Long userId) throws ModelException {
        DBObject query = new BasicDBObject("num_iid", numIid);
        query.put("user_id", userId);
        DBObject update = new BasicDBObject("$set", new BasicDBObject(updates));
        DBObject object = collection.findAndModify(query, new BasicDBObject("_id", false), null, false, update, true, false);
        Item item = parse(object, Item.class);
        return item;
    }
    */
}
