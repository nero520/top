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
    public Object updateFromTop(String topAccessToken) throws ModelException {
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
	    return null;
    }

    @Override
    public String getCollectionName() {
        return "sk_item";
    }

	@Override
	public List<Item> create(Map<String, Object> data) {
		if (_create(data)) {
			Map<String, Object> _query = new HashMap<String, Object>();
			_query.put("user_id", data.get("user_id"));
			_query.put("num_iid", data.get("num_iid"));
			return query(_query);
		}
		return null;
	}
}
