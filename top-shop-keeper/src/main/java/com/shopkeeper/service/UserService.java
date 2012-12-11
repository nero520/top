package com.shopkeeper.service;

import com.rop.RopRequest;
import com.rop.annotation.NeedInSessionType;
import com.rop.annotation.ServiceMethod;
import com.rop.annotation.ServiceMethodBean;
import com.rop.response.NotExistErrorResponse;
import com.rop.session.SimpleSession;
import com.shopkeeper.model.TopUserModel;
import com.shopkeeper.model.UserModel;
import com.shopkeeper.service.domain.TopUser;
import com.shopkeeper.service.domain.User;
import com.shopkeeper.service.request.LoginRequest;
import com.shopkeeper.service.request.UserGetRequest;
import com.shopkeeper.service.response.LoginResponse;
import com.shopkeeper.service.response.LogoutResponse;
import com.shopkeeper.service.response.TopUserGetResponse;
import com.shopkeeper.service.response.UserGetResponse;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghaojie
 * Date: 9/22/12
 * Time: 6:07 下午
 */
@ServiceMethodBean(version = "1.0")
public class UserService
{
    @ServiceMethod(method = "user.login", version = "1.0", needInSession = NeedInSessionType.NO)
    public Object login(LoginRequest request) {
        Map<String, Object> datas = new HashMap<String, Object>();
        datas.put("access_token", request.getAccessToken());
        datas.put("expires_in", request.getExpiresIn());
        datas.put("r1_expires_in", request.getR1ExpiresIn());
        datas.put("r2_expires_in", request.getR2ExpiresIn());
        datas.put("re_expires_in", request.getReExpiresIn());
        datas.put("refresh_token", request.getRefreshToken());
        datas.put("sub_user_id", request.getSubUserId());
        datas.put("sub_user_nick", request.getSubUserNick());
        datas.put("user_id", request.getUserId());
        datas.put("nick", request.getUserNick());
        datas.put("w1_expires_in", request.getW1ExpiresIn());
        datas.put("w2_expires_in", request.getW2ExpiresIn());

	    datas.put("lash_login", new Date());

	    Long userId = (Long)datas.get("user_id");

	    UserModel userModel = new UserModel();

	    Map<String, Object> query = new HashMap<String, Object>();
	    query.put("user_id", userId);

	    if (userModel.count(query) > 0) {
		    userModel.update(query, datas);
	    }
	    else {
		    userModel.create(datas);
	    }

	    String accessToken = request.getAccessToken();

	    LoginResponse response = new LoginResponse();
	    response.setSession(accessToken);

	    SimpleSession session = new SimpleSession();
	    session.setAttribute("user_id", request.getUserId());
	    request.getRopRequestContext().addSession(accessToken, session);

	    return response;
    }

    @ServiceMethod(method = "user.logout", version = "1.0", needInSession = NeedInSessionType.NO)
    public Object logout(RopRequest request) {
        request.getRopRequestContext().removeSession();
        LogoutResponse response = new LogoutResponse();
        response.setSuccessful(true);
        return response;
    }

    @ServiceMethod(method = "user.get", version = "1.0", needInSession = NeedInSessionType.YES)
    public Object getUser(UserGetRequest request) {
        SimpleSession session = (SimpleSession)request.getRopRequestContext().getSession();
        Long userId = (Long)session.getAttribute("user_id");

	    UserModel userModel = new UserModel();
	    Map<String, Object> query = new HashMap<String, Object>();
	    query.put("user_id", userId);
	    List<User> userList = userModel.query(query);
	    if (userList != null && userList.size() > 0) {
		    User user = userList.get(0);
		    UserGetResponse response = new UserGetResponse();
		    response.setUser(user);
		    return response;
	    }
	    else {
		    return new NotExistErrorResponse();
	    }
    }

    @ServiceMethod(method = "top.user.get", version = "1.0", needInSession = NeedInSessionType.YES)
    public Object getTopUser(UserGetRequest request) {
        SimpleSession session = (SimpleSession)request.getRopRequestContext().getSession();
        Long userId = (Long)session.getAttribute("user_id");
	    TopUserModel topUserModel = new TopUserModel();
	    Map<String, Object> query = new HashMap<String, Object>();
	    query.put("user_id", userId);
	    List<TopUser> topUserList = topUserModel.query(query);
	    if (topUserList != null && topUserList.size() > 0) {
		    TopUser user = topUserList.get(0);
		    TopUserGetResponse response = new TopUserGetResponse();
		    response.setTopUser(user);
		    return response;
	    }
	    else {
		    return new NotExistErrorResponse();
	    }
    }
}
