package com.shopkeeper.service.response;

import javax.xml.bind.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 9/22/12
 * Time: 6:37 下午
 * To change this template use File | Settings | File Templates.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "login_response")
public class LoginResponse
{
    @XmlElement
    private String session = null;

    public void setSession(String session) {
        this.session = session;
    }
    public String getSession() {
        return session;
    }
}
