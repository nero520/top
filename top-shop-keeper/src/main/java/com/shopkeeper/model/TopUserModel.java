package com.shopkeeper.model;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.rop.client.RopUnmarshaller;
import com.rop.client.unmarshaller.JacksonJsonRopUnmarshaller;
import com.shopkeeper.TopAccessor;
import com.shopkeeper.exception.ModelException;
import com.shopkeeper.exception.SkException;
import com.shopkeeper.service.domain.TopUser;
import com.shopkeeper.service.domain.User;
import com.shopkeeper.utils.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-11-18
 * Time: 上午2:20
 * To change this template use File | Settings | File Templates.
 */
public class TopUserModel extends AbstractModel
{
    private static String COLLECTION_NAME = "sk_top_user";

    private static String forbiddenFields = "_id";

    public TopUserModel() {

    }

    public String getCollectionName() {
        return COLLECTION_NAME;
    }

    @Override
    public void updateFromTop() throws ModelException {
        TopAccessor topAccessor = new TopAccessor(this.getAccessToken());
        try {
            Map<String, Object> userInfo = topAccessor.getUserInfo();
            BasicDBObject query = new BasicDBObject("user_id", this.getUserId());
            BasicDBObject update = new BasicDBObject(userInfo);
            collection.update(query, update, true, false);
        } catch (SkException e) {
            throw new ModelException(e.getErrorCode(), e.getMsg(), e.getSubCode(), e.getSubMsg());
        }
    }

    public TopUser getTopUser(String fields, Long userId) throws ModelException{
        Map<String, Object> query = new HashMap<String, Object>();
        query.put("user_id", userId);
        TopUser topUser = get(query, fields, forbiddenFields, TopUser.class);
        return topUser;
    }
}
