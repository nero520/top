package com.shopkeeper.service.response;

import javax.xml.bind.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 9/22/12
 * Time: 7:10 下午
 * To change this template use File | Settings | File Templates.
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
