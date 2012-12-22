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

}
