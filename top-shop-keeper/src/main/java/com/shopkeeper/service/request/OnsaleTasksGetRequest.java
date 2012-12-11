package com.shopkeeper.service.request;

import com.rop.AbstractRopRequest;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-11-22
 * Time: 上午5:35
 */
public class OnsaleTasksGetRequest extends AbstractRopRequest
{
    private List<String> ids;

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }
}
