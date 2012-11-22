package com.shopkeeper.service.response;

import com.shopkeeper.service.domain.AutoShowcaseSetting;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-11-22
 * Time: 上午8:37
 * To change this template use File | Settings | File Templates.
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "autoshowcase_update_response")
public class AutoShowcaseUpdateResponse
{
    @XmlElement(name = "showcase_setting")
    private AutoShowcaseSetting showcaseSetting;

    public AutoShowcaseSetting getShowcaseSetting() {
        return showcaseSetting;
    }

    public void setShowcaseSetting(AutoShowcaseSetting showcaseSetting) {
        this.showcaseSetting = showcaseSetting;
    }
}
