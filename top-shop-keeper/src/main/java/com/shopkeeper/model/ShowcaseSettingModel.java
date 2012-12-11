package com.shopkeeper.model;

import com.shopkeeper.service.domain.ShowcaseSetting;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-11-23
 * Time: 上午12:05
 */
public class ShowcaseSettingModel extends AbstractModel<ShowcaseSetting>
{
    public static Boolean DEFAULT_USED_INCLUDE_SETTING = true;

	public static Boolean DEFAULT_USED_EXCLUDE_SETTING = true;

	public static Boolean DEFAULT_USED_LIMIT_DISCOUNT = true;

    @Override
    public String getCollectionName() {
        return "sk_showcase_setting";
    }

	/*
    public ShowcaseSetting getShowcaseSetting(Long userId) throws ModelException {
        DBObject query = new BasicDBObject("user_id", userId);
        DBObject object = collection.findOne(query, new BasicDBObject("_id", false));
        ShowcaseSetting setting = parse(object, ShowcaseSetting.class);
        return setting;
    }

    public ShowcaseSetting updateAutoShowcaseSetting(Boolean usedInclude, Boolean usedExclude,
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
        ShowcaseSetting setting = parse(object, ShowcaseSetting.class);
        return setting;
    }

	public void deleteAutoShowcaseSetting(Long userId) {
		if (userId != null) {
			DBObject query = new BasicDBObject("user_id", userId);
			collection.remove(query);
		}
	}
	*/
}
