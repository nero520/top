package com.shopkeeper.service;

import com.rop.annotation.NeedInSessionType;
import com.rop.annotation.ServiceMethod;
import com.rop.annotation.ServiceMethodBean;
import com.rop.session.SimpleSession;
import com.shopkeeper.exception.ModelException;
import com.shopkeeper.model.ShowcaseSettingModel;
import com.shopkeeper.service.domain.ShowcaseSetting;
import com.shopkeeper.service.request.AutoShowcaseGetRequest;
import com.shopkeeper.service.request.AutoShowcaseUpdateRequest;
import com.shopkeeper.service.response.AutoShowcaseGetResponse;
import com.shopkeeper.service.response.AutoShowcaseUpdateResponse;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 12-11-22
 * Time: 上午7:34
 */

@ServiceMethodBean(version = "1.0")
public class ShowcaseService
{
    @ServiceMethod(method = "showcase.get", version = "1.0", needInSession = NeedInSessionType.YES)
    public Object getAutoShowcase(AutoShowcaseGetRequest request) {
        SimpleSession session = (SimpleSession)request.getRopRequestContext().getSession();
        Long userId = (Long)session.getAttribute("user_id");
        ShowcaseSettingModel showcaseModel = new ShowcaseSettingModel();
        try {
            ShowcaseSetting setting = showcaseModel.getAutoShowcaseSetting(userId);
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

    @ServiceMethod(method = "showcase.update", version = "1.0", needInSession = NeedInSessionType.YES)
    public Object updateAutoShowcase(AutoShowcaseUpdateRequest request) {
        SimpleSession session = (SimpleSession)request.getRopRequestContext().getSession();
        Long userId = (Long)session.getAttribute("user_id");
        ShowcaseSettingModel showcaseModel = new ShowcaseSettingModel();
        try {
            ShowcaseSetting setting = showcaseModel.updateAutoShowcaseSetting(request.getUsedInclude(),
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
