package com.shopkeeper.service.request;

import com.rop.AbstractRopRequest;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-11-18
 * Time: 下午8:59
 * To change this template use File | Settings | File Templates.
 */
public class GroupsGetRequest extends AbstractRopRequest
{
    private String ids;

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }
}
