package com.shopkeeper.model;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-11-20
 * Time: 上午10:06
 */

import com.shopkeeper.service.domain.Group;
import com.shopkeeper.service.domain.OnsaleTask;
import com.shopkeeper.utils.Utils;
import org.bson.types.ObjectId;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * sk_onsale_task struck:
 * Long             user_id
 * String           id  // task_id
 * String           name
 * String           description
 * List<TimeSlot>   time_slot
 *   Map    TimeSlot :
 *     String   week
 *     String   hour
 *
 */
public class OnsaleTaskModel extends AbstractModel<OnsaleTask>
{
    @Override
    public String getCollectionName() {
        return "sk_onsale_task";
    }

	@Override
	public List<OnsaleTask> create(Map<String, Object> data) {
		Long userId = (Long)data.get("user_id");
		if (userId != null) {
			Map<String, Object> localData = new HashMap<String, Object>(data);
			localData.put("created", Utils.getDate());
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
	public List<OnsaleTask> delete(Map<String, Object> query) {
		List<OnsaleTask> onsaleTaskList = super.delete(query);
		if (onsaleTaskList != null && onsaleTaskList.size() > 0) {
			Map<String, Object> _query = new HashMap<String, Object>();
			List<Object> taskIdList = new LinkedList<Object>();
			for (OnsaleTask onsaleTask : onsaleTaskList) {
				taskIdList.add(onsaleTask.getId());
				_query.put("user_id", onsaleTask.getUserId());
			}
			Map<String, Object> _in = new HashMap<String, Object>();
			_in.put("$in", taskIdList);
			_query.put("task_id", _in);
			ItemModel itemModel = new ItemModel();

			Map<String, Object> _update = new HashMap<String, Object>();
			_update.put("task_id", null);

			itemModel.update(_query, _update);
		}
		return onsaleTaskList;
	}
}
