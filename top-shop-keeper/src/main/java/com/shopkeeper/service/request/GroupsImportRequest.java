package com.shopkeeper.service.request;

import com.rop.AbstractRopRequest;

import javax.validation.constraints.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-11-18
 * Time: 下午10:19
 * To change this template use File | Settings | File Templates.
 */

public class GroupsImportRequest extends AbstractRopRequest
{
    @NotNull
    private String from;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}
