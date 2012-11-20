package com.shopkeeper.service;

import com.rop.annotation.NeedInSessionType;
import com.rop.annotation.ServiceMethod;
import com.rop.annotation.ServiceMethodBean;
import com.rop.session.SimpleSession;
import com.shopkeeper.service.request.AutoOnsaleTaskGetRequest;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-11-20
 * Time: 上午6:22
 * To change this template use File | Settings | File Templates.
 */

@ServiceMethodBean(version = "1.0")
public class AutoOnsaleService
{
    @ServiceMethod(method = "autoonsale.task.get", version = "1.0", needInSession = NeedInSessionType.YES)
    public Object getTask(AutoOnsaleTaskGetRequest request) {
        return null;
    }

    @ServiceMethod(method = "autoonsale.task.add", version = "1.0", needInSession = NeedInSessionType.YES)
    public Object addTask(AutoOnsaleTaskGetRequest request) {
        SimpleSession session = (SimpleSession)request.getRopRequestContext().getSession();
        return null;
    }

    @ServiceMethod(method = "autoonsale.task.update", version = "1.0", needInSession = NeedInSessionType.YES)
    public Object updateTask(AutoOnsaleTaskGetRequest request) {
        return null;
    }

    @ServiceMethod(method = "autoonsale.task.delete", version = "1.0", needInSession = NeedInSessionType.YES)
    public Object deleteTask(AutoOnsaleTaskGetRequest request) {
        return null;
    }
}
