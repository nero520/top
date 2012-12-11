package com.shopkeeper.service.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 9/22/12
 * Time: 7:10 下午
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "logout_response")
public class LogoutResponse{

    @XmlElement
    private boolean successful;

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }
}
