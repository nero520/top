package com.shopkeeper.service.request;

import com.rop.AbstractRopRequest;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-11-20
 * Time: 上午6:44
 */
public class OnsaleTaskGetRequest extends AbstractRopRequest
{
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
