package com.shopkeeper.service;

import com.rop.annotation.NeedInSessionType;
import com.rop.annotation.ServiceMethod;
import com.rop.annotation.ServiceMethodBean;
import com.rop.session.SimpleSession;
import com.shopkeeper.exception.ModelException;
import com.shopkeeper.model.AutoShowcaseModel;
import com.shopkeeper.service.domain.AutoShowcaseSetting;
import com.shopkeeper.service.request.AutoShowcaseGetRequest;
import com.shopkeeper.service.request.AutoShowcaseUpdateRequest;
import com.shopkeeper.service.response.AutoShowcaseGetResponse;
import com.shopkeeper.service.response.AutoShowcaseUpdateResponse;

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
    public Object getAutoShowcase(AutoShowcaseGetRequest request) {
        SimpleSession session = (SimpleSession)request.getRopRequestContext().getSession();
        Long userId = (Long)session.getAttribute("user_id");
        AutoShowcaseModel showcaseModel = new AutoShowcaseModel();
        try {
            AutoShowcaseSetting setting = showcaseModel.getAutoShowcaseSetting(userId);
            if (setting != null) {
                AutoShowcaseGetResponse response = new AutoShowcaseGetResponse();
                response.setAutoShowcaseSetting(setting);
                return response;
            }
            else {
                // todo
            }
        } catch (ModelException e) {

        }
        return null;
    }

    @ServiceMethod(method = "autoshowcase.update", version = "1.0", needInSession = NeedInSessionType.YES)
    public Object updateAutoShowcase(AutoShowcaseUpdateRequest request) {
        SimpleSession session = (SimpleSession)request.getRopRequestContext().getSession();
        Long userId = (Long)session.getAttribute("user_id");
        AutoShowcaseModel showcaseModel = new AutoShowcaseModel();
        try {
            AutoShowcaseSetting setting = showcaseModel.updateAutoShowcaseSetting(request.getUsedInclude(),
                    request.getUsedExclude(),request.getUsedLimitDiscount(), request.getLaunch(), userId);
            if (setting != null) {
                AutoShowcaseUpdateResponse response = new AutoShowcaseUpdateResponse();
                response.setAutoShowcaseSetting(setting);
                return response;
            }
            else {
                // todo
            }
        } catch (ModelException e) {

        }
        return null;
    }
}
