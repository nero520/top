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
 * Time: 上午7:36
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "autoshowcase_get_response")
public class AutoShowcaseGetResponse
{
    @XmlElement(name = "autoshowcase_setting")
    private AutoShowcaseSetting autoShowcaseSetting;

    public AutoShowcaseSetting getAutoShowcaseSetting() {
        return autoShowcaseSetting;
    }

    public void setAutoShowcaseSetting(AutoShowcaseSetting autoShowcaseSetting) {
        this.autoShowcaseSetting = autoShowcaseSetting;
    }
}
