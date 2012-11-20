package com.shopkeeper.service.request;

import com.rop.AbstractRopRequest;
import com.rop.annotation.AliasName;

import javax.validation.constraints.NotNull;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-11-20
 * Time: 上午6:44
 * To change this template use File | Settings | File Templates.
 */
public class AutoOnsaleTaskGetRequest extends AbstractRopRequest
{
    @NotNull
    @AliasName(name = "task_id")
    private String taskId;
}
