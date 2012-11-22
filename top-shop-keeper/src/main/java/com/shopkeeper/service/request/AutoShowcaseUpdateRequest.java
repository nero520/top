package com.shopkeeper.service.request;

import com.rop.annotation.AliasName;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-11-22
 * Time: 上午8:36
 * To change this template use File | Settings | File Templates.
 */
public class AutoShowcaseUpdateRequest
{
    @AliasName(name = "used_include_item")
    private Boolean usedIncludeItem;
    @AliasName(name = "used_exclude_item")
    private Boolean usedExcludeItem;
    @AliasName(name = "used_limitdiscount_item")
    private Boolean usedLimitDiscountItem;
    @AliasName(name = "is_launch")
    private Boolean isLaunch;

    public Boolean getUsedIncludeItem() {
        return usedIncludeItem;
    }

    public void setUsedIncludeItem(Boolean usedIncludeItem) {
        this.usedIncludeItem = usedIncludeItem;
    }

    public Boolean getUsedExcludeItem() {
        return usedExcludeItem;
    }

    public void setUsedExcludeItem(Boolean usedExcludeItem) {
        this.usedExcludeItem = usedExcludeItem;
    }

    public Boolean getUsedLimitDiscountItem() {
        return usedLimitDiscountItem;
    }

    public void setUsedLimitDiscountItem(Boolean usedLimitDiscountItem) {
        this.usedLimitDiscountItem = usedLimitDiscountItem;
    }

    public Boolean getLaunch() {
        return isLaunch;
    }

    public void setLaunch(Boolean launch) {
        isLaunch = launch;
    }
}
