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
		if (groupList == null) {
			return null;
		}
		else {
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
		_update.put("group_id", null);

		ItemModel itemModel = new ItemModel();
		itemModel.update(_query, _update);

		return groupList;
	}

	// TODO 判断from参数
	@SuppressWarnings(value = "unchecked")
    public List<Group> importGroup(String from, Long userId) throws ModelException {
        TopAccessor topAccessor = new TopAccessor(accessToken);
        try {
            Map groups = topAccessor.getSellerCatsList(this.getUserNick(userId));
	        logger.info(groups.toString());
            List<Map<String, Object>> groupList = (List<Map<String, Object>>)groups.get("seller_cat");
            List<Group> rsp = new LinkedList<Group>();
            if (groupList != null && groupList.size() > 0) {
                for (Map<String, Object> group : groupList) {
                    Map<String, Object> data = new HashMap<String, Object>(group);
	                data.put("user_id", userId);
	                data.put("category", from);
	                Map<String, Object> update = new HashMap<String, Object>();
	                update.put("user_id", userId);
	                update.put("cid", group.get("cid"));
	                List<Group> tGroups = update(update, data, true);
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
