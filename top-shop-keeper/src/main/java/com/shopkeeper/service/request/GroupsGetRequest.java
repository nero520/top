package com.shopkeeper.service.request;

import com.rop.AbstractRopRequest;

import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-11-18
 * Time: 下午8:59
 */
public class GroupsGetRequest extends AbstractRopRequest
{
    private List<String> ids;

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }
}
