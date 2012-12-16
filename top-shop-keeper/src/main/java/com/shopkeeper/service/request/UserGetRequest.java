package com.shopkeeper.service.request;

import com.rop.AbstractRopRequest;
import com.rop.annotation.AliasName;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-11-7
 * Time: 上午10:03
 */
public class UserGetRequest extends AbstractRopRequest
{
    @AliasName(name = "fields")
    private String fields;

    public String getFields() {
        return fields;
    }

    public void setFields(String fields) {
        this.fields = fields;
    }
}
