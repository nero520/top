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

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-11-17
 * Time: 下午4:16
 */
abstract public class AbstractModel<T> implements Model<T>
{
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected String dbName = "db_top";

    protected DBCollection collection = null;

	protected String pk = "user_id";

    protected DB db = null;

	protected Long userId = null;

	protected String accessToken = null;

	private Class<T> templeteClazz0;

	@SuppressWarnings(value = "unchecked")
	public AbstractModel() {
		Class clazz = this.getClass();
		ParameterizedType genericSuperClass = (ParameterizedType)clazz.getGenericSuperclass();
		Type type = genericSuperClass.getActualTypeArguments()[0];
		templeteClazz0 = (Class)type;
		init();
	}

	private void init() {
		db = MongoManager.getDB(dbName, Config.MONGODB_USER, Config.MONGODB_PASSWORD);
		collection = db.getCollection(this.getCollectionName());
		collection.setWriteConcern(WriteConcern.SAFE);
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public long count(Map<String, Object> query) {
		DBObject _query = new BasicDBObject(query);
		return collection.count(_query);
	}

	protected DBCollection getCollection(String collectionName, WriteConcern concern) {
		DBCollection collection1 = db.getCollection(collectionName);
		collection1.setWriteConcern(concern);
		return collection1;
	}

	protected DBCollection getCollection(String collectionName) {
		return this.getCollection(collectionName, WriteConcern.SAFE);
	}

	protected DBCollection getCollection() {
		return collection;
	}

	protected String getAccessToken(Long userId) {
		if (accessToken == null) {
			DBCollection userCollection = this.getCollection("sk_user");
			DBObject query = new BasicDBObject("user_id", userId);
			DBObject fields = new BasicDBObject("access_token", true);
			DBObject rsp = userCollection.findOne(query, fields);
			if (rsp != null)
				accessToken = (String)rsp.get("access_token");
		}
		return accessToken;
	}

	protected String getUserNick(Long userId) {
		DBCollection userCollection = this.getCollection("sk_user");
		DBObject query = new BasicDBObject("user_id", userId);
		DBObject fields = new BasicDBObject("nick", true);
		DBObject rsp = userCollection.findOne(query, fields);
		if (rsp != null) {
			return (String)rsp.get("nick");
		}
		return null;
	}

	protected Long getUserId(String accessToken) {
        if (userId == null) {
            DBCollection collection = db.getCollection("sk_user");
            BasicDBObject query = new BasicDBObject("access_token", accessToken);
            BasicDBObject fields = new BasicDBObject("user_id", 1);
            DBObject object = collection.findOne(query, fields);
            if (object != null) {
                userId = (Long)object.get("user_id");
            }
        }
        return userId;
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
        if (oid != null) {
	        String id = oid.toString();
            object.put("id", id);
            object.removeField("_id");
        }

        String json = object.toString();
        RopUnmarshaller unmarshaller = new JacksonJsonRopUnmarshaller();
        return unmarshaller.unmarshaller(json, clazz);
    }

	protected <T> List<T> parse(DBCursor cursor, Class<T> clazz) {
		List<T> objList = new LinkedList<T>();
		while (cursor.hasNext()) {
			DBObject object = cursor.next();
			T t = parse(object, clazz);
			objList.add(t);
		}
		return objList.size() > 0 ? objList : null;
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
        return parse(object, clazz);
    }

    protected <T> T get(Map<String, Object> query, String fields, String forbiddenFields, Class<T> clazz) throws ModelException{
        DBObject queryObj = new BasicDBObject(query);
        return get(queryObj, fields, forbiddenFields, clazz);
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
        return gets(queryObj, fields, forbiddenFields, clazz);
    }

	protected boolean _create(Map<String, Object> data) {
		Map<String, Object> localData = new HashMap<String, Object>(data);
		DBObject objData = new BasicDBObject(localData);
		WriteResult result = collection.insert(objData);
		return result.getLastError().ok();
	}

	protected int _update(Map<String, Object> query, Map<String, Object> update) {
		return _update(query, update, false);
	}

	protected int _update(Map<String, Object> query, Map<String, Object> update, boolean upsert) {
		DBObject _query = new BasicDBObject(query);
		DBObject _update = new BasicDBObject(update);
		DBObject _set = new BasicDBObject("$set", _update);
		WriteResult result = collection.update(_query, _set, upsert, true);
		return result.getN();
	}

	protected DBCursor _query(Map<String, Object> query) {
		if (query != null) {
			DBObject _query = new BasicDBObject(query);
			return collection.find(_query);
		}
		return null;
	}

	protected int _delete(Map<String, Object> query) {
		if (query != null) {
			DBObject _query = new BasicDBObject(query);
			WriteResult result = collection.remove(_query);
			return result.getN();
		}
		return 0;
	}

	@Override
	public List<T> create(Map<String, Object> data) {
		if (data != null && data.size() > 0 && _create(data)) {
			Map<String, Object> query = new HashMap<String, Object>();
			query.put(pk, data.get(pk));
			return query(query);
		}
		return null;
	}

	@Override
	public List<T> query(Map<String, Object> query) {
		DBCursor rsp = _query(query);
		return parse(rsp, templeteClazz0);
	}

	@Override
	public List<T> update(Map<String, Object> query, Map<String, Object> data) {
		return update(query, data, false);
	}

	@Override
	public List<T> update(Map<String, Object> query, Map<String, Object> data, boolean upsert) {
		if ( _update(query, data, upsert) > 0 ) {
			return query(query);
		}
		return null;
	}

	@Override
	public List<T> delete(Map<String, Object> query) {
		DBCursor rsp = _query(query);
		if (rsp.hasNext()) {
			_delete(query);
		}
		return parse(rsp, templeteClazz0);
	}
}
