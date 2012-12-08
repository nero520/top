package com.shopkeeper.service.request;

import com.rop.AbstractRopRequest;
import com.rop.annotation.AliasName;

import javax.validation.constraints.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-11-19
 * Time: 上午3:29
 */
public class ItemGetRequest extends AbstractRopRequest
{
    @NotNull
    @AliasName(name = "num_iid")
    private Long numIid;

    @NotNull
    private String fields;

    public Long getNumIid() {
        return numIid;
    }

    public void setNumIid(Long numIid) {
        this.numIid = numIid;
    }

    public String getFields() {
        return fields;
    }

    public void setFields(String fields) {
        this.fields = fields;
    }
}
