package com.shopkeeper.service.response;

import com.shopkeeper.service.domain.TopUser;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-11-18
 * Time: 上午6:16
 * To change this template use File | Settings | File Templates.
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "top_user_get_response")
public class TopUserGetResponse
{
    @XmlElement(name = "top_user")
    private TopUser topUser;

    public TopUser getTopUser() {
        return topUser;
    }

    public void setTopUser(TopUser topUser) {
        this.topUser = topUser;
    }
}
