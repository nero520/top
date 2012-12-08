package com.shopkeeper.admin.model;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.shopkeeper.exception.ModelException;
import com.shopkeeper.model.AbstractModel;
import com.shopkeeper.utils.Utils;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-11-24
 * Time: 上午3:33
 */
public class AdminModel extends AbstractModel
{
    @Override
    public String getCollectionName() {
        return "sk_admin";
    }

    public String login(String userName, String password) throws ModelException{
        DBObject query = new BasicDBObject();
        query.put("user_name", userName);
        query.put("password", password);
        long count = collection.getCount(query);
        if (count > 0) {
            try {
                return Utils.byte2hex(Utils.getSHA1Digest(userName));
            } catch (IOException e) {
                throw new ModelException(); // todo
            }
        }
        return null;
    }
}
