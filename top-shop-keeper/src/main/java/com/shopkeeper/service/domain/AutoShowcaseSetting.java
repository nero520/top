package com.shopkeeper.service.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-11-22
 * Time: 上午7:38
 * To change this template use File | Settings | File Templates.
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "autoshowcase_setting")
public class AutoShowcaseSetting
{
    @XmlElement(name = "used_include")
    private Boolean usedInclude;
    @XmlElement(name = "used_exclude")
    private Boolean usedExclude;
    @XmlElement(name = "used_limit_discount")
    private Boolean usedLimitDiscount;
    @XmlElement(name = "is_launch")
    private Boolean isLaunch;
    @XmlElement(name = "user_id")
    private Long userId;

    public Boolean getUsedInclude() {
        return usedInclude;
    }

    public void setUsedInclude(Boolean usedInclude) {
        this.usedInclude = usedInclude;
    }

    public Boolean getUsedExclude() {
        return usedExclude;
    }

    public void setUsedExclude(Boolean usedExclude) {
        this.usedExclude = usedExclude;
    }

    public Boolean getUsedLimitDiscount() {
        return usedLimitDiscount;
    }

    public void setUsedLimitDiscount(Boolean usedLimitDiscount) {
        this.usedLimitDiscount = usedLimitDiscount;
    }

    public Boolean getLaunch() {
        return isLaunch;
    }

    public void setLaunch(Boolean launch) {
        isLaunch = launch;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
