package com.shopkeeper.service.response;

import com.shopkeeper.service.domain.ShowcaseSetting;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-11-22
 * Time: 上午8:37
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "autoshowcase_update_response")
public class AutoShowcaseUpdateResponse
{
    @XmlElement(name = "autoshowcase_setting")
    private ShowcaseSetting autoShowcaseSetting;

    public ShowcaseSetting getAutoShowcaseSetting() {
        return autoShowcaseSetting;
    }

    public void setAutoShowcaseSetting(ShowcaseSetting autoShowcaseSetting) {
        this.autoShowcaseSetting = autoShowcaseSetting;
    }
}
