package com.shopkeeper.model;

import com.shopkeeper.TopAccessor;
import com.shopkeeper.exception.ModelException;
import com.shopkeeper.exception.TopException;
import com.shopkeeper.service.domain.TopUser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-11-18
 * Time: 上午2:20
 */

/*
 * sk_top_user struct
 * Long user_id
 * user on top
 *
 */
public class TopUserModel extends AbstractModel<TopUser> implements TopUpdate
{
    public TopUserModel() {

    }

    public String getCollectionName() {
        return "sk_top_user";
    }

	@Override
	public List<TopUser> create(Map<String, Object> data) {
		Long userId = (Long)data.get("user_id");
		if (userId != null) {
			return super.create(data);
		}
		return null;
	}

	@Override
    public void updateFromTop(String topAccessToken) throws ModelException {
        TopAccessor topAccessor = new TopAccessor(topAccessToken);
        Map userInfo;
        try {
            userInfo = topAccessor.getUserInfo();
	        Map<String, Object> query = new HashMap<String, Object>();
	        query.put("user_id", this.getUserId(topAccessToken));
	        update(query, (Map<String, Object>)userInfo, true);
        } catch (TopException e) {
			e.printStackTrace();
	        // todo
        }
    }
}
