package com.shopkeeper.service.request;

import com.rop.AbstractRopRequest;
import com.rop.annotation.AliasName;

import javax.validation.constraints.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-11-18
 * Time: 上午6:31
 */
public class GroupGetRequest extends AbstractRopRequest
{
    @NotNull
    private String id;

    public String geId() {
        return id;
    }

    public void setId(String groupId) {
        this.id = groupId;
    }
}
