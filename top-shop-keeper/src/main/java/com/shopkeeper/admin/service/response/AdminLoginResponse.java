package com.shopkeeper.admin.service.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-11-23
 * Time: 上午5:24
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "admin_login_response")
public class AdminLoginResponse
{
    @XmlElement(name = "access_token")
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
