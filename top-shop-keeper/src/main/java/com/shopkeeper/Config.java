package com.shopkeeper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-9-29
 * Time: 上午5:35
 */
public class Config
{
    public static final String TOP_APP_KEY = "1012681999";
    public static final String TOP_APP_SECRET = "sandbox41a796536c687440d5a35ef2e";
    public static final String TOP_SERVER_URL = "http://gw.api.tbsandbox.com/router/rest";
    public static final String TOP_COMET_SERVER_URL = "http://stream.api.tbsandbox.com/stream";
    public static final String MONGODB_USER = "root";
    public static final String MONGODB_PASSWORD = "edword";

	protected static final Map<String, Object> COLLECTION_FORBIDDEN_FIELDS = new HashMap<String, Object>();

	public static void init() {
		Map<String, Object> userForbiddenFields = new HashMap<String, Object>();
		userForbiddenFields.put("_id", false);
		userForbiddenFields.put("access_token", false);
		userForbiddenFields.put("expires_in", false);
		userForbiddenFields.put("r1_expires_in", false);
		userForbiddenFields.put("r2_expires_in", false);
		userForbiddenFields.put("refresh_token", false);
		userForbiddenFields.put("sub_user_id", false);
		userForbiddenFields.put("sub_user_nick", false);
		userForbiddenFields.put("w1_expires_in", false);
		userForbiddenFields.put("w2_expires_in", false);
		COLLECTION_FORBIDDEN_FIELDS.put("sk_user", userForbiddenFields);
	}

	public static Map getCollectionForbiddenFields(String collectionName) {
		return (Map) COLLECTION_FORBIDDEN_FIELDS.get(collectionName);
	}
}
