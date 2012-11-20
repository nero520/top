package com.shopkeeper.service.request;

import com.rop.AbstractRopRequest;
import com.rop.annotation.AliasName;

import javax.validation.constraints.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-11-7
 * Time: 上午10:03
 * To change this template use File | Settings | File Templates.
 */
public class UserGetRequest extends AbstractRopRequest
{
    @NotNull()
    @AliasName(name = "fields")
    private String fields;

    public String getFields() {
        return fields;
    }

    public void setFields(String fields) {
        this.fields = fields;
    }
}
