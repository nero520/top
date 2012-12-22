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
@XmlRootElement(name = "showcase_setting_update_response")
public class ShowcaseSettingUpdateResponse
{
    @XmlElement(name = "showcase_setting")
    private ShowcaseSetting showcaseSetting;

    public ShowcaseSetting getShowcaseSetting() {
        return showcaseSetting;
    }

    public void setShowcaseSetting(ShowcaseSetting showcaseSetting) {
        this.showcaseSetting = showcaseSetting;
    }
}
