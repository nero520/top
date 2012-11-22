package com.shopkeeper.service.request;

import com.rop.AbstractRopRequest;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-11-22
 * Time: 上午5:35
 * To change this template use File | Settings | File Templates.
 */
public class AutoOnsaleTasksGetRequest extends AbstractRopRequest
{
    private List<String> ids;

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }
}
