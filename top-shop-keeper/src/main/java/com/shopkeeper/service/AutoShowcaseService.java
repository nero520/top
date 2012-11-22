package com.shopkeeper.service;

import com.rop.annotation.NeedInSessionType;
import com.rop.annotation.ServiceMethod;
import com.rop.annotation.ServiceMethodBean;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-11-22
 * Time: 上午7:34
 * To change this template use File | Settings | File Templates.
 */

@ServiceMethodBean(version = "1.0")
public class AutoShowcaseService
{
    @ServiceMethod(method = "autoshowcase.get", version = "1.0", needInSession = NeedInSessionType.YES)
    public Object getAutoShowcase() {
        return null;
    }
}
