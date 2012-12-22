package com.shopkeeper.service.request;

import com.rop.AbstractRopRequest;

import javax.validation.constraints.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-11-20
 * Time: 上午6:52
 */
public class OnsaleTaskDeleteRequest extends AbstractRopRequest
{
	@NotNull
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
