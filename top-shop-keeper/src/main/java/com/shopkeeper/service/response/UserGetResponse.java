package com.shopkeeper.service.response;

import com.shopkeeper.service.domain.User;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-11-3
 * Time: 上午9:04
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "user_get_response")
public class UserGetResponse
{
    @XmlElement
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
