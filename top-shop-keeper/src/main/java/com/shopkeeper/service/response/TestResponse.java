package com.shopkeeper.service.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 9/22/12
 * Time: 11:46 下午
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "test_response")
public class TestResponse {

    //@XmlElement()
    private String result;

    public void setResult(String result) {
        this.result = result;
    }
    public String getResult() {
        return result;
    }
}
