package com.shopkeeper.model;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.shopkeeper.exception.ModelException;
import com.shopkeeper.service.domain.AutoShowcaseSetting;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-11-23
 * Time: 上午12:05
 * To change this template use File | Settings | File Templates.
 */
public class AutoShowcaseModel extends AbstractModel
{
    private static String COLLECTION_NAME = "sk_autoshowcase_setting";

    //private static String forbiddenFields = "_id";

    private static Boolean DEFAULT_USED_INCLUDE_SETTING = true;

    private static Boolean DEFAULT_USED_EXCLUDE_SETTING = true;

    private static Boolean DEFAULT_USED_LIMIT_DISCOUNT = true;

    @Override
    public String getCollectionName() {
        return COLLECTION_NAME;
    }

    public AutoShowcaseSetting getAutoShowcaseSetting(Long userId) throws ModelException {
        DBObject query = new BasicDBObject("user_id", userId);
        DBObject object = collection.findOne(query, new BasicDBObject("_id", false));
        AutoShowcaseSetting setting = parse(object, AutoShowcaseSetting.class);
        return setting;
    }

    public AutoShowcaseSetting updateAutoShowcaseSetting(Boolean usedInclude, Boolean usedExclude,
                                                         Boolean usedLimitDiscount, Boolean isLaunch,
                                                         Long userId) throws ModelException {
        DBObject query = new BasicDBObject("user_id", userId);

        DBObject update = new BasicDBObject();

        update.put("used_include", usedInclude != null ? usedInclude : DEFAULT_USED_INCLUDE_SETTING);
        update.put("used_exclude", usedExclude != null ? usedExclude : DEFAULT_USED_EXCLUDE_SETTING);
        update.put("used_limit_discount", usedExclude != null ? usedLimitDiscount : DEFAULT_USED_LIMIT_DISCOUNT);
        if (isLaunch != null) {
            update.put("is_launch", isLaunch);
        }
        update.put("user_id", userId);
        DBObject object = collection.findAndModify(query, new BasicDBObject("_id", false), null, false, update, true, true);
        AutoShowcaseSetting setting = parse(object, AutoShowcaseSetting.class);
        return setting;
    }
}
