package com.shopkeeper.model;

import com.mongodb.*;
import com.rop.client.RopUnmarshaller;
import com.rop.client.unmarshaller.JacksonJsonRopUnmarshaller;
import com.shopkeeper.Config;
import com.shopkeeper.MongoManager;
import com.shopkeeper.exception.ModelException;
import com.shopkeeper.utils.Utils;
import org.apache.commons.lang.StringUtils;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-11-17
 * Time: 下午4:16
 * To change this template use File | Settings | File Templates.
 */
abstract public class AbstractModel implements Model
{
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private String dbName = "db_top";

    protected DBCollection collection;

    private String accessToken;

    protected Long userId = null;

    protected DB db;


    public AbstractModel() {
        init();
    }

    public void init() {
  		db = MongoManager.getDB(dbName, Config.MONGODB_USER, Config.MONGODB_PASSWORD);
        collection = db.getCollection(this.getCollectionName());
    }

    public DBCollection getCollection() {
        return collection;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Long getUserId() {
        if (userId == null) {
            DBCollection collection = db.getCollection("sk_user");
            BasicDBObject query = new BasicDBObject("access_token", this.getAccessToken());
            BasicDBObject fields = new BasicDBObject("user_id", 1);
            DBObject object = collection.findOne(query, fields);
            if (object != null) {
                userId = (Long)object.get("user_id");
            }
        }
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    protected DBObject filterFields(DBObject fields, String forbiddenFields) {
        StringUtils.split(forbiddenFields, ",");
        String[] fieldsList = StringUtils.split(forbiddenFields, ",");
        if (fieldsList.length == 0) {
            return fields;
        }
        for(String field : fieldsList) {
            field = field.trim();
            if (field.compareTo("_id") == 0) {
                fields.put(field, 0);
            }
            else {
                fields.removeField(field);
            }
        }
        return fields;
    }

    protected <T> T parse(DBObject object, Class<T> clazz) {
        if (object == null) {
            return null;
        }
        ObjectId oid = (ObjectId)object.get("_id");
        String id;
        if (oid != null) {
            id = oid.toString();
            object.put("id", id);
            object.removeField("_id");
        }
        String json = object.toString();
        RopUnmarshaller unmarshaller = new JacksonJsonRopUnmarshaller();
        T t = unmarshaller.unmarshaller(json, clazz);
        return t;
    }

    protected <T> T get(DBObject query, String fields, String forbiddenFields, Class<T> clazz) throws ModelException{
        DBObject field = Utils.splitFilds(fields);
        if (forbiddenFields != null) {
            filterFields(field, forbiddenFields);
        }
        DBObject object = collection.findOne(query, field);
        if (object == null) {
            return null;
        }
        T t = parse(object, clazz);
        return t;

    }

    protected <T> T get(Map<String, Object> query, String fields, String forbiddenFields, Class<T> clazz) throws ModelException{
        DBObject queryObj = new BasicDBObject(query);
        T t = get(queryObj, fields, forbiddenFields, clazz);
        return t;

    }

    protected <T> List<T> gets(DBObject query, String fields, String forbiddenFields, Class<T> clazz) throws ModelException {
        DBObject field = Utils.splitFilds(fields);
        if (forbiddenFields != null) {
            filterFields(field, forbiddenFields);
        }
        DBCursor cursor = collection.find(query, field);
        List<T> list = new LinkedList<T>();
        while (cursor.hasNext()) {
            DBObject object = cursor.next();
            T t = parse(object, clazz);
            list.add(t);
        }
        return list;
    }

    protected <T> List<T> gets(Map<String, Object> query, String fields, String forbiddenFields, Class<T> clazz) throws ModelException {
        DBObject queryObj = new BasicDBObject(query);
        List<T> t = gets(queryObj, fields, forbiddenFields, clazz);
        return t;
    }

    protected boolean isDocExist(String id, String docName) {
        DBCollection collection1 = db.getCollection(docName);
        if (collection1 == null) {
            return false;
        }
        ObjectId objectId = null;
        try {
            objectId = new ObjectId(id);
        }catch (IllegalArgumentException e) {
            return false;
        }
        DBObject group = collection1.findOne(new BasicDBObject("_id", objectId));
        if (group == null) {
            return false;
        }
        return true;
    }
}
