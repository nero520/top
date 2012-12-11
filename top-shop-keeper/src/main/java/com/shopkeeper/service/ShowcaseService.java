package com.shopkeeper.service;

import com.rop.annotation.NeedInSessionType;
import com.rop.annotation.ServiceMethod;
import com.rop.annotation.ServiceMethodBean;
import com.rop.response.NotExistErrorResponse;
import com.rop.session.SimpleSession;
import com.shopkeeper.model.ShowcaseSettingModel;
import com.shopkeeper.service.domain.ShowcaseSetting;
import com.shopkeeper.service.request.ShowcaseGetRequest;
import com.shopkeeper.service.request.ShowcaseUpdateRequest;
import com.shopkeeper.service.response.ShowcaseGetResponse;
import com.shopkeeper.service.response.ShowcaseUpdateResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Object getAutoShowcase(ShowcaseGetRequest request) {
        SimpleSession session = (SimpleSession)request.getRopRequestContext().getSession();
        Long userId = (Long)session.getAttribute("user_id");
        ShowcaseSettingModel showcaseModel = new ShowcaseSettingModel();

	    Map<String, Object> query = new HashMap<String, Object>();
	    query.put("user_id", userId);

	    List<ShowcaseSetting> showcaseSettingList = showcaseModel.query(query);
	    if (showcaseSettingList != null && showcaseSettingList.size() > 0) {
		    ShowcaseGetResponse response = new ShowcaseGetResponse();
		    response.setShowcaseSetting(showcaseSettingList.get(0));
		    return response;
	    }
	    else {
		    return new NotExistErrorResponse();
	    }
    }

    @ServiceMethod(method = "showcase.update", version = "1.0", needInSession = NeedInSessionType.YES)
    public Object updateAutoShowcase(ShowcaseUpdateRequest request) {
        SimpleSession session = (SimpleSession)request.getRopRequestContext().getSession();
        Long userId = (Long)session.getAttribute("user_id");
        ShowcaseSettingModel showcaseModel = new ShowcaseSettingModel();

	    Map<String, Object> query = new HashMap<String, Object>();
	    query.put("user_id", userId);

	    Map<String, Object> update = new HashMap<String, Object>();

	    Object value = request.getLaunch();
	    if (value != null) {
		    update.put("is_launch", value);
	    }

	    value = request.getUsedExclude();
	    if (value != null) {
		    update.put("is_used_include", value);
	    }

	    value = request.getLaunch();
	    if (value != null) {
		    update.put("is_used_exclude", value);
	    }

	    value = request.getLaunch();
	    if (value != null) {
		    update.put("is_used_limit_discount", value);
	    }

	    List<ShowcaseSetting> showcaseSettingList = showcaseModel.update(query, update);
		if (showcaseSettingList != null && showcaseSettingList.size() > 0) {
			ShowcaseUpdateResponse response = new ShowcaseUpdateResponse();
			response.setShowcaseSetting(showcaseSettingList.get(0));
			return response;
		}
	    else {
			return new NotExistErrorResponse();
		}
    }
}
